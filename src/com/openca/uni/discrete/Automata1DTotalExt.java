package com.openca.uni.discrete;
import java.math.BigInteger;
import java.util.Arrays;


/** Autómata Celular Unidimensional con tipo de regla Total-Ext
 * 
 * @author juanky
 *
 */
public final class Automata1DTotalExt extends OrganismoUnidimensionalDiscreto
{
	/** 
	 *  LookUpTable cada regla de cada posible valor de una célula.
	 */
	private byte reglastring[][];

	/** Constructor
	 * 
	 */
	public Automata1DTotalExt()
	{
		super();
	}
	
	/**
	 * 
	 * @param celulas Lista de celulas del estado inicial del autómata.
	 */
	public Automata1DTotalExt(byte[] celulas)
	{
		super(celulas);
	}

	@Override
	protected void evolucionarCelula(int indice)
	{
		final int cell = celulas[indice];
		final int index = setVecinos(indice);
		celulasnext[indice] = index>= 0 ? reglastring[cell][index] : 0;
	}

	@Override
	public final String getMaxRule()
	{
		final int longitud = implicadas*(estados-1) ;
		final BigInteger estado = new BigInteger(Integer.toString(estados));
		return (estado.pow(longitud+1).subtract(new BigInteger("1"))).toString();
	}

	@Override
	public final void setRule(String regla) throws Exception
	{
		final int longitud = implicadas*(estados-1) ;
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
	
	
	/** Calcula el valor del vecindario de una célula
	 *  Se usará como índice para acceder a la lookUpTable
	 * 
	 * @param indice de la célula central del vecindario.
	 * @return Índice de la lookUpTable que se usará para acceder a la célula
	 */
	protected final int setVecinos(int indice)
	{
		int nuevax;
		int code=0;
		final int implis = implicadas - radio;
		if(indice == 0)
		{
			for(int i = -radio; i < implis; i++)
			{
				nuevax = getRelativeIndex(i);
				code += (celulas[nuevax]);
			}
		}
		else
		{
			int bres = 0;
			int bsum = 0;
			nuevax = getRelativeIndex(indice - radio - 1);
			bres +=  (celulas[nuevax]);
			nuevax = getRelativeIndex(indice + radio);
			bsum +=  (celulas[nuevax]);
			code = (vecinos[indice-1]-bres+bsum);
		}
		vecinos[indice] = code;
		return code;
	}

}
