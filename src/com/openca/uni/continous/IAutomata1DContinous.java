package com.openca.uni.continous;

import com.openca.uni.IAutomata1D;

/** Autómata Celular Unidimensional Contínuo.
 * 
 * @author juanky
 *
 */
public interface IAutomata1DContinous extends IAutomata1D
{
	/** Evoluciona el autómata. Le asigna previamente el estado de las celulasinit.
	 * 
	 * @param celulasinit Estado inicial del autómata que se evoluciona.
	 */
	void evolve(double[] celulasinit);
	
	/** Devuelve la lista de células de un autómata bidimensional contínuo.
	 * 
	 * @return matriz de células.
	 */
	double[] getCells();
	
	/** Establece el estado del autómata celular a partir de un vector de celulas
	 * 
	 * @param celulas vector de células.
	 */
	void setCells(double[] celulas);
}
