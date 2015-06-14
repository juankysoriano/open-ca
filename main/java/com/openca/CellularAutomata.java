package com.openca;

public interface CellularAutomata {
    void randomiseConfiguration();

    void evolve(OnCellUpdatedCallback callback);

    void evolve();

    int getNumberOfStates();

    int getRadius();

    int getWidth();

    int getHeight();

    void clear();
}
