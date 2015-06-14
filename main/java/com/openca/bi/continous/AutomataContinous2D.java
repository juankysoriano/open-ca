package com.openca.bi.continous;

import com.openca.Automata;
import com.openca.OnCellUpdatedCallback;
import com.openca.bi.OnCellUpdatedCallback2D;

public abstract class AutomataContinous2D extends Automata implements CellularAutomataContinous2D {

    protected double[][] cells;
    protected double[][] cellsTemp;
    protected double[][] neighbourhoodCode;

    @Override
    public void evolve() {
        double[][] cellAux = cellsTemp;
        cellsTemp = cells;
        cells = cellAux;
    }

    @Override
    public void evolve(OnCellUpdatedCallback onCellUpdatedCallback2D) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                evolveCellAt(i, j);
                ((OnCellUpdatedCallback2D) onCellUpdatedCallback2D).onCellDetected(i, j, (int) cellsTemp[i][j]);
            }
        }

        double[][] cellAux = cellsTemp;
        cellsTemp = cells;
        cells = cellAux;
    }

    protected abstract void evolveCellAt(int x, int y);

    @Override
    public double[][] getCells() {
        return cells;
    }

    @Override
    public void setWidth(int width) {
        if (this.width != width) {
            this.width = width;
            cells = new double[width][height];
            cellsTemp = new double[width][height];
            neighbourhoodCode = new double[width][height];
        }
    }

    @Override
    public void setHeight(int height) {
        if (this.height != height) {
            this.height = height;
            cells = new double[width][height];
            cellsTemp = new double[width][height];
            neighbourhoodCode = new double[width][height];
        }
    }
}

