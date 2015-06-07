package com.openca.bi.discrete.vonnewman;

import com.openca.bi.discrete.Automata2DDiscrete;

import java.math.BigInteger;

/** Organismo Bidimensional con tipo de regla Dinámica y vecindad de Von Neumann
 * 
 * @author juanky
 *
 */
public class Automata2DElementaryVon extends Automata2DDiscrete
{
	/** Factor de escalado. Se usará para calcular el valor del vecindario de una célula. */
	private int factor; 
	/** Factor de escalado. Se usará para calcular el valor del vecindario de una célula. */
	private int factor2; 

	/** Constructor
	 * 
	 */
	public Automata2DElementaryVon()
	{
		super();
	}
	
	/** Constructor
	 * 
	 * @param celulas Matriz con células iniciales del autómata.
	 */
	public Automata2DElementaryVon(byte[][] celulas)
	{
		super(celulas);
	}

	@Override
	protected void evolucionarCelula(int x, int y)
	{
		final int index = reglastring.length - setVecinos(x, y)-1;
		celulasnext[x][y]=(index>=0) && (index < reglastring.length) ? reglastring[index] : 0;
	}

	@Override
	public String getMaxRule() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final void setRadious(int radio)
	{
		super.setRadious(radio);
		factor = (int)Math.pow(estados, radio);
		factor2 = (int)Math.pow(estados, implicadas+radio);
	}

	@Override
	public void setRule(String regla) throws Exception
	{
		// TODO Auto-generated method stub
		final char[] reglacharstring =  (new BigInteger(regla)).toString(estados).toCharArray();
		reglastring = new byte[reglacharstring.length];

		for(int i = 0; i < reglacharstring.length; i++)
		{
			//if(Character.isDigit(reglacharstring[i]))
			//{
			reglastring[i] = (byte) (reglacharstring[i]-'0');
			//}
			//else ////OJITO CON ESTO -> Podría ser necesario para más de 10 estados
			//{
			//	this.reglastring[i] = (byte) (reglacharstring[i]-'W');
			//}
		}
	}


	/** Calcula el valor del vecindario de una célula(Moore)
	 *  Se usará como índice para acceder a la lookUpTable
	 * 
	 * @param x Coordenada x de la célula central
	 * @param y Coordenada y de la célula central
	 * @return Índice de la lookUpTable que se usará para acceder a la célula
	 */
	protected final int setVecinos(int x, int y) //Calcula el vecindario del que forma parte una clula (Von Neumman)
	{
		int code = 0;
		int nuevax;
		int nuevay;
		int exp = 1;
		int exp2 = factor;
		int exp3 = factor2;
		for(int j = 0; j < implicadas; j++)
		{
			if(j < radio)
			{
				nuevax =  getRelativeIndex(x - radio + j);
				code += exp*(celulas[nuevax][y]);
				exp *= estados;
				nuevax = getRelativeIndex(x + j + 1);
				code += exp3*(celulas[nuevax][y]);
				exp3 *= estados;
			}
			nuevay = getRelativeIndex(y - radio + j);
			code += exp2*(celulas[x][nuevay]);
			exp2 *= estados;
		}
		vecinos[x][y]=code;
		return code;
	}

}
