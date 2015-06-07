package com.openca.bi.discrete;

import com.openca.bi.IAutomata2D;

/** Autómata Celular Bidimensional Discreto.
 * 
 * @author juanky
 *
 */
public interface IAutomata2DDiscrete extends IAutomata2D
{
	/** Evoluciona el autómata. Le asigna previamente el estado de las celulasinit.
	 * 
	 * @param celulasinit Estado inicial del autómata que se evoluciona.
	 */
	void evolucionar(byte[][] celulasinit);
	
	/** Devuelve la lista de células de un autómata bidimensional discreto..
	 * 
	 * @return matriz de células.
	 */
	byte[][] getCelulas();
	
	/** Establece el estado del autómata celular a partir de una matriz de celulas
	 * 
	 * @param celulas Matriz de células.
	 */
	void setCelulas(byte[][] celulas);


}
