package com.openca.uni.continous;

import com.openca.Automata;

import java.util.Random;

public abstract class AutomataContinous1D extends Automata implements CellularAutomataContinous1D {
    protected double[] cells;
    protected double[] tempCells;
    protected double[] neighbourCode;

    @Override
    public void evolve() {
        for (int index = 0; index < size; index++) {
            evolveCellAt(index);
        }

        double[] cellsaux = tempCells;
        tempCells = cells;
        cells = cellsaux;
    }

    protected abstract void evolveCellAt(int index);

    @Override
    public double[] getCells() {
        return cells;
    }

    @Override
    protected void setSize(int size) {
        if (this.size != size) {
            this.size = size;
            cells = new double[this.size];
            tempCells = new double[this.size];
            neighbourCode = new double[this.size];
        }
    }

    @Override
    public void randomiseConfiguration() {
        boolean hasToBeSymmetric = new Random().nextInt(2) == 0;

        if (hasToBeSymmetric) {
            prepareSymmetricConfiguration();
        } else {
            prepareRandomConfiguration();
        }
    }

    private void prepareRandomConfiguration() {
        Random random = new Random();
        int density = random.nextInt(101);
        for (int i = 0; i < size / 2; i++) {
            cells[i] = 0;
            cells[size - 1 - i] = 0;
            int value = random.nextInt(101);
            if (value < density) {
                byte state = (byte) random.nextInt(states);
                cells[i] = state;
                cells[size - 1 - i] = state;
            }
        }
    }

    private void prepareSymmetricConfiguration() {
        Random random = new Random();
        int density = random.nextInt(101);
        for (int i = 0; i < size; i++) {
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
        cells[(int) Math.floor(getSize() / 2)] = 1;
    }
}
