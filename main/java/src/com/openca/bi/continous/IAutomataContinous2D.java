package com.openca.bi.continous;

import com.openca.bi.IAutomata2D;

public interface IAutomataContinous2D extends IAutomata2D {

    void evolve(double[][] celulasinit);

    double[][] getCells();

    void setCells(double[][] celulas);

}
