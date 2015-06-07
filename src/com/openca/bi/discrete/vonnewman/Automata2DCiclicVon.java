package com.openca.bi.discrete.vonnewman;

import com.openca.bi.discrete.Automata2DDiscrete;

/** Autómata Celular Bidimensional  de tipo cíclico con vecindad de Von Neumann
 * 
 * @author juanky
 *
 */
public class Automata2DCiclicVon extends Automata2DDiscrete
{
	/**  Regla (Valor del vecindario a superar para cambiar estado de una célula) */
	private short umbral;

	/** Constructor
	 * 
	 */
	public Automata2DCiclicVon()
	{
		super();
	}

	/** Constructor
	 * 
	 * @param celulas Matriz con el estado inicial de las células.
	 */
	public Automata2DCiclicVon(byte[][] celulas)
	{
		super(celulas);
	}

	@Override
	protected void evolucionarCelula(int x, int y)
	{
		final byte cell = celulas[x][y];
		byte cellnext = celulasnext[x][y];
		cellnext = cell;

		int contar = 0;
		final byte estado = cell;
		int nuevax;
		int nuevay;
		if(estado < estados-1)
		{
			for(int j = 0; j < implicadas; j++)
			{
				if(j < radio)
				{
					nuevax =  getRelativeIndex(x - radio + j);
					if(celulas[nuevax][y]==(cell+1))
					{
						contar++;
					}
					nuevax = getRelativeIndex(x + j + 1);
					if(celulas[nuevax][y]==(cell+1))
					{
						contar++;
					}
				}
				nuevay = getRelativeIndex(y - radio + j);
				if(celulas[x][nuevay]==(cell+1))
				{
					contar++;
				}
				if(contar>=umbral)
				{
					cellnext++;
				}
			}
		}
		else if(estado == estados-1)
		{
			for(int j = 0; j < implicadas; j++)
			{
				if(j < radio)
				{
					nuevax =  getRelativeIndex(x - radio + j);
					if(celulas[nuevax][y]==0)
					{
						contar++;
					}
					nuevax = getRelativeIndex(x + j + 1);
					if(celulas[nuevax][y]==0)
					{
						contar++;
					}
				}
				nuevay = getRelativeIndex(y - radio + j);
				if(celulas[x][nuevay]==0)
				{
					contar++;
				}
				if(contar>=umbral)
				{
					cellnext = 0;
				}
			}
		}
		celulasnext[x][y] = cellnext;
	}

	@Override
	public String getMaxRule() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public final void setRule(String regla)
	{
		umbral = (short) Integer.parseInt(regla);
	}
}
