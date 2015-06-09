package com.openca.bi;

import com.openca.IAutomata;

/**
 * Autómata Celular Bidimensional.
 *
 * @author juanky
 */
public interface IAutomata2D extends IAutomata {
    void setCells(byte[][] celulas);

    void setCells(double[][] celulas);
}
