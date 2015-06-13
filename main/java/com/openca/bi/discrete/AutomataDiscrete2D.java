package com.openca.bi.discrete;

import com.openca.Automata;
import com.openca.bi.OnCellUpdatedCallback2D;

import java.util.Random;

public abstract class AutomataDiscrete2D extends Automata implements CellularAutomataDiscrete2D {
    protected int[][] cells;
    protected int[][] cellsTemp;
    protected int[][] neighbourhoodCode;

    @Override
    public void randomiseConfiguration() {
        Random rand = new Random();
        boolean hasToBeSymmetric = rand.nextInt(2) == 0;

        if (hasToBeSymmetric) {
            prepareSymmetricConfiguration();
        } else {
            prepareRandomConfiguration();
        }
    }

    private void prepareRandomConfiguration() {
        Random rand = new Random();
        int density = rand.nextInt(101);

        for (int i = 0; i < size / 2; i++) {
            for (int j = 0; j < size / 2; j++) {
                cells[i][j] = 0;
                cells[i][size - 1 - j] = 0;
                cells[size - 1 - i][j] = 0;
                cells[size - 1 - i][size - 1 - j] = 0;
                int value = rand.nextInt(101);
                if (value < density) {
                    byte state = (byte) rand.nextInt(states);
                    cells[i][j] = state;
                    cells[i][size - 1 - j] = state;
                    cells[size - 1 - i][j] = state;
                    cells[size - 1 - i][size - 1 - j] = state;
                }
            }

        }
    }

    private void prepareSymmetricConfiguration() {
        Random rand = new Random();
        int density = rand.nextInt(101);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = 0;
                int value = rand.nextInt(101);
                if (value < density) {
                    cells[i][j] = rand.nextInt(states);
                }
            }

        }
    }

    @Override
    public void evolve() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                evolveCellAt(i, j);
            }
        }

        int[][] cellAux = cellsTemp;
        cellsTemp = cells;
        cells = cellAux;
    }

    @Override
    public void evolve(OnCellUpdatedCallback2D onCellUpdatedCallback2D) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                evolveCellAt(i, j);
                onCellUpdatedCallback2D.onCellDetected(i, j, cellsTemp[i][j]);
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
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells.length; j++) {
                cells[i][j] = 0;
            }
        }

        int half = (int) Math.floor(getSize() / 2);

        getCells()[half][half] = 1;
        getCells()[half - 1][half - 1] = 1;
        getCells()[half - 1][half] = 1;
        getCells()[half][half - 1] = 1;
        getCells()[half + 1][half] = 1;
        getCells()[half + 1][half + 1] = 1;
        getCells()[half + 1][half - 1] = 1;
        getCells()[half - 1][half + 1] = 1;
        getCells()[half][half + 1] = 1;
    }

    @Override
    public void setSize(int size) {
        if (this.size != size) {
            this.size = size;
            cells = new int[size][size];
            cellsTemp = new int[size][size];
            neighbourhoodCode = new int[size][size];
        }
    }
}
