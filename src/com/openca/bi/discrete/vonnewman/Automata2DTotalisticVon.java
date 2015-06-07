package com.openca.bi.discrete.vonnewman;

import com.openca.bi.discrete.Automata2DDiscrete;

import java.math.BigInteger;

/** Organismo Bidimensional con tipo de regla Total y vecindad de Von Neumann
 * 
 * @author juanky
 *
 */
public class Automata2DTotalisticVon extends Automata2DDiscrete
{
	/** Constructor
	 * 
	 */
	public Automata2DTotalisticVon()
	{
		super();
	}
	
	/** Constructor
	 * 
	 * @param celulas Matriz con las células iniciales del autómata.
	 */
	public Automata2DTotalisticVon(byte[][] celulas)
	{
		super(celulas);
	}

	@Override
	protected void evolucionarCelula(int x, int y)
	{
		final int index = reglastring.length - setVecinos(x,y) - 1;
		celulasnext[x][y]=index>=0 ? reglastring[index] : 0;
	}

	@Override
	public String getMaxRule()
	{
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

		vecinos[x][y]=code;
		return code;
	}
}
