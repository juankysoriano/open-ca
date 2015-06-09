package com.openca.bi.discrete;

import com.openca.bi.IAutomata2D;

public interface IAutomataDiscrete2D extends IAutomata2D {
    void evolve(byte[][] cells);

    byte[][] getCells();

    void setCells(byte[][] cells);
}
