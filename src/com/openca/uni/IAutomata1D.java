package com.openca.uni;

import com.openca.IAutomata;

/**
 * Autómata Celular Unidimensional.
 *
 * @author juanky
 */
public interface IAutomata1D extends IAutomata {
    void setCells(byte[] cells);

    void setCells(double[] cells);
}
