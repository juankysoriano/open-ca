package com.openca.uni.discrete;

import com.openca.uni.IAutomata1D;

public interface IAutomataDiscrete1D extends IAutomata1D {
    void evolve(byte[] cells);

    byte[] getCells();

    void setCells(byte[] cells);

}
