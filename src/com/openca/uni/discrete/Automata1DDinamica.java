package com.openca.uni.discrete;

import java.math.BigInteger;

/** Autómata Celular Unidimensional con tipo de regla Dinámica
 * 
 * @author juanky
 *
 */
public final class Automata1DDinamica extends OrganismoUnidimensionalDiscreto
{

	/** Factor de escalado. Se usará para calcular el valor del vecindario de una célula. */
	private int factor; 
	
	/** Constructor
	 * 
	 */
	public Automata1DDinamica()
	{
		super();
	}
	
	/** Constructor
	 * 
	 * @param celulas Lista de celulas del estado inicial del autómata.
	 */
	public Automata1DDinamica(byte[] celulas)
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
	public final String getMaxRule() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final void setRadious(int radio)
	{
		super.setRadious(radio);
		factor = (int)Math.pow(estados, implicadas-1);
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

	/** Calcula el valor del vecindario de una célula
	 *  Se usará como índice para acceder a la lookUpTable
	 * 
	 * @param indice de la célula central del vecindario.
	 * @return Índice de la lookUpTable que se usará para acceder a la célula
	 */	
	protected final int setVecinos(int indice)
	{
		int exp = factor;
		int nuevax;
		int code=0;
		if(indice == 0)
		{
			for(int i = -radio; i <=radio; i++)
			{
				nuevax = getRelativeIndex(i);
				code += exp* (celulas[nuevax]);
				exp /= estados;
			}
		}
		else
		{
			nuevax = getRelativeIndex(indice - radio - 1);
			final int bres = factor*(celulas[nuevax]);
			nuevax = getRelativeIndex(indice + radio);
			final int bsum = (celulas[nuevax]);
			code = (vecinos[indice-1]-bres)*estados + bsum;
		}
		vecinos[indice] = code;
		return code;
	}

}
