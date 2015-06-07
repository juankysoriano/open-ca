package com.openca.uni;

import com.openca.Automata;

/** Organismo Unidimensional
 * 
 * @author juanky
 *
 */
public abstract class Automata1D extends Automata implements IAutomata1D
{
	/** Constructor
	 * 
	 */
	public Automata1D()
	{
		super();
	}

	/** Evoluciona la célula en posición indice
	 * 
	 * @param indice Indice de la célula a evolve
	 */
	protected abstract void evolucionarCelula(int indice);

	@Override
	public  void setRadious(int r)
	{
		radio = r;
		implicadas = radio*2 +1;
	}
}