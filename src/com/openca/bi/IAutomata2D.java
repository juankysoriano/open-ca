package com.openca.bi;

import com.openca.IAutomata;

/** Autómata Celular Bidimensional.
 * 
 * @author juanky
 *
 */
public interface IAutomata2D extends IAutomata
{
	/** Establece el estado de un autómata bidimensional.
	 * 
	 * @param celulas Celulas a asignar (valores discretos).
	 */
	void setCelulas(byte[][] celulas);
	
	/** Establece el estado de un autómata.
	 * 
	 * @param celulas Celulas a asignar (valores contínuos).
	 */
	void setCelulas(double[][] celulas);

}
