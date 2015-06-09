package com.openca.uni.continous;

import com.openca.uni.IAutomata1D;

public interface IAutomataContinous1D extends IAutomata1D {
    void evolve(double[] cells);

    double[] getCells();

    void setCells(double[] cells);
}
