package com.openca.uni.discrete;

import java.math.BigInteger;

/** Autómata Celular Unidimensional con tipo de regla Total
 * 
 * @author juanky
 *
 */
public final class Automata1DTotal extends OrganismoUnidimensionalDiscreto
{

	/** Constructor
	 * 
	 */
	public Automata1DTotal()
	{
		super();
	}
	
	/** Constructor
	 * 
	 * @param celulas Lista de celulas del estado inicial del autómata.
	 */
	public Automata1DTotal(byte[] celulas)
	{
		super(celulas);
	}

	@Override
	protected void evolucionarCelula(int indice)
	{
		final int index = reglastring.length - setVecinos(indice) - 1;
		celulasnext[indice] = index >= 0? reglastring[index] : 0;
	}

	@Override
	public String getMaxRule() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRule(String regla) throws Exception
	{
		final char[] reglacharstring =  (new BigInteger(regla)).toString(estados).toCharArray();
		reglastring = new byte[reglacharstring.length];

		for(int i = 0; i < reglacharstring.length; i++)
		{
			reglastring[i] = (byte) (reglacharstring[i]-'0');
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
				//Si se sale por la izquierda
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
			//nuevax = this.getRelativeIndex(indice - 1);
			code = (vecinos[indice-1]-bres+bsum);
		}
		vecinos[indice] = code;
		return code;
	}

}