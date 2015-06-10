package com.openca;

public interface CellularAutomata {
    void randomiseConfiguration();

    void evolve();

    int getNumberOfStates();

    int getRadius();

    int getSize();

    void clear();
}
