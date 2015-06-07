package com.openca.uni.discrete;

import com.openca.uni.IAutomata1D;

/** Autómata Celular Unidimensional Discreto.
 * 
 * @author juanky
 *
 */
public interface IAutomata1DDiscreto extends IAutomata1D
{
	/** Evoluciona el autómata. Le asigna previamente el estado de las celulasinit.
	 * 
	 * @param celulasinit Estado inicial del autómata que se evoluciona.
	 */
	void evolucionar(byte[] celulasinit);
	
	/** Devuelve la lista de células de un autómata bidimensional discreto.
	 * 
	 * @return matriz de células.
	 */
	byte[] getCelulas();
	
	/** Establece el estado del autómata celular a partir de un vector de celulas
	 * 
	 * @param celulas vector de células.
	 */
	void setCells(byte[] celulas);

}
