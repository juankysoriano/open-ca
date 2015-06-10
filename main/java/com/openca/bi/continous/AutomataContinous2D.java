package com.openca.bi.continous;

import com.openca.Automata;

public abstract class AutomataContinous2D extends Automata implements CellularAutomataContinous2D {

    protected double[][] cells;
    protected double[][] tempCells;
    protected double[][] neighbourhoodCode;

    @Override
    public void evolve() {
        double[][] cellAux = tempCells;
        tempCells = cells;
        cells = cellAux;
    }

    protected abstract void evolveCellAt(int x, int y);

    @Override
    public double[][] getCells() {
        return cells;
    }

    @Override
    public void setSize(int size) {
        if (this.size != size) {
            this.size = size;
            cells = new double[size][size];
            tempCells = new double[size][size];
            neighbourhoodCode = new double[size][size];
        }
    }
}

