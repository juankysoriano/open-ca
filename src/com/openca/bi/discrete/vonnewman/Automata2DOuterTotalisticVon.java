package com.openca.bi.discrete.vonnewman;
import com.openca.bi.discrete.Automata2DDiscrete;

import java.math.BigInteger;
import java.util.Arrays;


/** Organismo Bidimensional con tipo de regla Total-Ext y vecindad de Von Neumann
 * 
 * @author juanky
 *
 */
public class Automata2DOuterTotalisticVon extends Automata2DDiscrete
{
	/** 
	 *  LookUpTable cada regla de cada posible valor de una célula.
	 */
	private byte reglastring[][];

	/** Constructor
	 * 
	 */
	public Automata2DOuterTotalisticVon()
	{
		super();
	}
	
	/** Constructor
	 * 
	 * @param celulas Matriz con las células iniciales del autómata.
	 */
	public Automata2DOuterTotalisticVon(byte[][] celulas)
	{
		super(celulas);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void evolucionarCelula(int x, int y)
	{	//this.celulasnext[x][y] = new Celula();
		final byte cell = celulas[x][y];
		final int index = setVecinos(x,y)
		;
		celulasnext[x][y]=index>=0 ? reglastring[cell][index] : 0;
	}

	@Override
	public final String getMaxRule()
	{
		final BigInteger estado = new BigInteger(Integer.toString(estados));
		final int longitud = (implicadas*2 -1)*(estados-1);
		return (estado.pow(longitud+1).subtract(new BigInteger("1"))).toString();
	}


	@Override
	public final void setRule(String regla) throws Exception
	{
		final int longitud = (implicadas*2 -1)*(estados-1) ;
		reglastring = new byte[estados][longitud+1];
		final String[] reg = new String[estados];
		Arrays.fill(reg,"");

		try
		{
			for(int i = 0; i < estados; i++)
			{
				for(int j=0; j < longitud; j++)
				{
					reglastring[i][j]=0;
				}
			}

			int index = 0;
			for(int i = 0; i < regla.length(); i++)  //Si cumple la condici�n de vida
			{
				if(regla.charAt(i) == '-')
				{
					if(i+1 < regla.length())
					{
						index++;
					}
				}
				else
				{
					reg[index] = reg[index].concat(String.valueOf(regla.charAt(i)));
				}
			}

			for(int i = 0; i < estados; i++)
			{
				final String aux = (new BigInteger(reg[i])).toString(estados);
				for(int j = 0; j <= longitud; j++)
				{
					if(j < aux.length())
					{
						reglastring[i][aux.length()-1-j]=(byte) (aux.charAt(j)-'0');
					}
				}
			}
		}
		catch (final Exception e)
		{
			throw(e);
		}

	}

	/** Calcula el valor del vecindario de una célula(Von Neumann)
	 *  Se usará como índice para acceder a la lookUpTable
	 * 
	 * @param x Coordenada x de la célula central
	 * @param y Coordenada y de la célula central
	 * @return Índice de la lookUpTable que se usará para acceder a la célula
	 */
	protected final int setVecinos(int x, int y) //Calcula el vecindario del que forma parte una clula (Von Neumman)
	{
		int nuevax;
		int nuevay;
		int code=0;
		for(int j = 0; j < implicadas; j++)
		{
			if(j < radio)
			{
				nuevax =  getRelativeIndex(x - radio + j);
				code += (celulas[nuevax][y]);
				nuevax = getRelativeIndex(x + j + 1);
				code += (celulas[nuevax][y]);
			}
			nuevay = getRelativeIndex(y - radio + j);
			code += (celulas[x][nuevay]);
		}

		vecinos[x][y] = code;
		return code;
	}
}
