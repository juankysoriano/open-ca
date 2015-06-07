package com.openca.bi.continous;

import com.openca.bi.IAutomata2D;

/**
 * Autómata Celular Bidimensional Contínuo.
 *
 * @author juanky
 */
public interface IAutomata2DContinous extends IAutomata2D {
    /**
     * Evoluciona el autómata. Le asigna previamente el estado de las celulasinit.
     *
     * @param celulasinit Estado inicial del autómata que se evoluciona.
     */
    void evolucionar(double[][] celulasinit);

    /**
     * Devuelve la lista de células de un autómata bidimensional contínuo.
     *
     * @return matriz de células.
     */
    double[][] getCelulas();

    /**
     * Establece el estado del autómata celular a partir de una matriz de celulas
     *
     * @param celulas Matriz de células.
     */
    void setCelulas(double[][] celulas);

}
