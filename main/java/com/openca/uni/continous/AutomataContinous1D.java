package com.openca.uni.continous;

import com.openca.Automata;
import com.openca.OnCellUpdatedCallback;
import com.openca.uni.OnCellUpdatedCallback1D;

import java.util.Random;

public abstract class AutomataContinous1D extends Automata implements CellularAutomataContinous1D {
    protected double[] cells;
    protected double[] tempCells;
    protected double[] neighbourCode;

    @Override
    public void evolve() {
        for (int index = 0; index < width; index++) {
            evolveCellAt(index);
        }

        double[] cellsaux = tempCells;
        tempCells = cells;
        cells = cellsaux;
    }

    @Override
    public void evolve(OnCellUpdatedCallback onCellUpdatedCallback1D) {
        for (int index = 0; index < width; index++) {
            evolveCellAt(index);
            ((OnCellUpdatedCallback1D) onCellUpdatedCallback1D).onCellDetected(index, (int) tempCells[index]);
        }

        double[] cellsAux = tempCells;
        tempCells = cells;
        cells = cellsAux;
    }


    protected abstract void evolveCellAt(int index);

    @Override
    public double[] getCells() {
        return cells;
    }

    @Override
    protected void setWidth(int size) {
        if (this.width != size) {
            this.width = size;
            cells = new double[this.width];
            tempCells = new double[this.width];
            neighbourCode = new double[this.width];
        }
    }

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
        Random random = new Random();
        int density = random.nextInt(101);
        for (int i = 0; i < width / 2; i++) {
            cells[i] = 0;
            cells[width - 1 - i] = 0;
            int value = random.nextInt(101);
            if (value < density) {
                byte state = (byte) random.nextInt(states);
                cells[i] = state;
                cells[width - 1 - i] = state;
            }
        }
    }

    private void prepareSymmetricConfiguration() {
        Random random = new Random();
        int density = random.nextInt(101);
        for (int i = 0; i < width; i++) {
            cells[i] = 0;
            int value = random.nextInt(101);
            if (value < density) {
                byte state = (byte) random.nextInt(states);
                cells[i] = state;
            }
        }
    }

    @Override
    public void clear() {
        for (int i = 0; i < cells.length; i++) {
            cells[i] = 0;
        }
        cells[(int) Math.floor(getWidth() / 2)] = 1;
    }
}
