package com.openca.bi.discrete;

import com.openca.Automata;
import com.openca.OnCellUpdatedCallback;
import com.openca.bi.OnCellUpdatedCallback2D;

import java.util.Random;

public abstract class AutomataDiscrete2D extends Automata implements CellularAutomataDiscrete2D {
    protected int[][] cells;
    protected int[][] cellsTemp;
    protected int[][] neighbourhoodCode;

    @Override
    public void randomiseConfiguration() {
        boolean hasToBeSymmetric = new Random().nextInt(100) > 90;

        if (hasToBeSymmetric) {
            prepareSymmetricConfiguration();
        } else {
            prepareRandomConfiguration();
        }
    }

    private void prepareRandomConfiguration() {
        Random rand = new Random();
        int density = rand.nextInt(101);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells[i][j] = 0;
                int value = rand.nextInt(101);
                if (value < density) {
                    cells[i][j] = rand.nextInt(states);
                }
            }
        }
    }

    private void prepareSymmetricConfiguration() {
        Random rand = new Random();
        int density = rand.nextInt(101);

        for (int i = 0; i < width / 2; i++) {
            for (int j = 0; j < height / 2; j++) {
                cells[i][j] = 0;
                cells[i][height - 1 - j] = 0;
                cells[width - 1 - i][j] = 0;
                cells[width - 1 - i][height - 1 - j] = 0;
                int value = rand.nextInt(101);
                if (value < density) {
                    byte state = (byte) rand.nextInt(states);
                    cells[i][j] = state;
                    cells[i][height - 1 - j] = state;
                    cells[width - 1 - i][j] = state;
                    cells[width - 1 - i][height - 1 - j] = state;
                }
            }
        }
    }

    @Override
    public void evolve() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                evolveCellAt(i, j);
            }
        }

        int[][] cellAux = cellsTemp;
        cellsTemp = cells;
        cells = cellAux;
    }

    @Override
    public void evolve(OnCellUpdatedCallback onCellUpdatedCallback2D) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                evolveCellAt(i, j);
                ((OnCellUpdatedCallback2D)onCellUpdatedCallback2D).onCellDetected(i, j, cellsTemp[i][j]);
            }
        }

        int[][] cellAux = cellsTemp;
        cellsTemp = cells;
        cells = cellAux;
    }

    protected abstract void evolveCellAt(int x, int y);

    @Override
    public int[][] getCells() {
        return cells;
    }

    @Override
    public void clear() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells[i][j] = 0;
            }
        }
    }

    @Override
    public void setWidth(int width) {
        if (this.width != width) {
            this.width = width;
            cells = new int[width][height];
            cellsTemp = new int[width][height];
            neighbourhoodCode = new int[width][height];
        }
    }

    @Override
    public void setHeight(int height) {
        if (this.height != height) {
            this.height = height;
            cells = new int[width][height];
            cellsTemp = new int[width][height];
            neighbourhoodCode = new int[width][height];
        }
    }
}
