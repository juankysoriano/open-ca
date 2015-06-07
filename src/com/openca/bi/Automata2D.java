package com.openca.bi;

import com.openca.Automata;

/** Organismo Bidimensional
 * 
 * @author juanky
 *
 */
public abstract class Automata2D extends Automata implements IAutomata2D
{

	/** Constructor
	 * 
	 */
	public Automata2D()
	{
		super();
	}

	/** Evoluciona la célula con coordenadas x e y
	 * 
	 * @param x Coordenada x de la célula a evolve.
	 * @param y Coordenada y de la célula a evolve.
	 */
	protected abstract void evolucionarCelula(int x, int y);
	
	@Override
	public void setRadious(int r)
	{
		radio = r;
		implicadas = 2*radio +1;
	}

}


