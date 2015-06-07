package com.openca.uni;

import com.openca.IAutomata;

/** Autómata Celular Unidimensional.
 * 
 * @author juanky
 *
 */
public interface IAutomata1D extends IAutomata
{
	/** Establece el estado de un autómata unidimensional.
	 * 
	 * @param celulas Celulas a asignar (valores discretos).
	 */
	void setCells(byte[] celulas);
	
	/** Establece el estado de un autómata unidimensional.
	 * 
	 * @param celulas Celulas a asignar (valores contínuos).
	 */
	void setCells(double[] celulas);
}
