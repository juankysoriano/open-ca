package com.openca.uni.continous;

import com.openca.uni.Automata1D;

import java.util.Random;

public abstract class AutomataContinous1D extends Automata1D implements IAutomataContinous1D {
    protected double[] cells;
    protected double[] tempCells;
    protected double[] neighbourCode;

    @Override
    public void evolve() {
        resetMetrics();
        for (int index = 0; index < size; index++) {
            evolveCellAt(index);
            updateMetrics(index, (int) tempCells[index], (int) cells[index]);

        }

        double[] cellsaux = tempCells;
        tempCells = cells;
        cells = cellsaux;
    }

    public void evolve(double cells[]) {
        resetMetrics();
        for (int index = 0; index < size; index++) {
            evolveCellAt(index);
            updateMetrics(index, (int) tempCells[index], (int) cells[index]);
        }
        double[] cellsaux = tempCells;
        tempCells = this.cells;
        this.cells = cellsaux;
    }

    @Override
    public double[] getCells() {
        return cells;
    }

    @Override
    public void setCells(double[] cells) {
        if ((cells != null) && (cells.length == size)) {
            this.cells = cells;
        } else {
            try {
                throw new Exception("Distintos tamaños para las células");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setCells(byte[] cells) {
        if ((cells != null) && (cells.length == size)) {
            for (int i = 0; i < size; i++) {
                this.cells[i] = (cells[i]);
            }
        } else {
            try {
                throw new Exception("Distintos tamaños para las células");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setSize(int size) {
        if (this.size != size) {
            this.size = size;
            cells = new double[this.size];
            tempCells = new double[this.size];
            neighbourCode = new double[this.size];
        }
    }

    @Override
    public void randomiseConfiguration() {
        resetMetrics();
        aliveCells = 0;

        boolean hasToBeSymmetric = new Random().nextInt(2) == 0;

        if (hasToBeSymmetric) {
            prepareSymmetricConfiguration();
        } else {
            prepareRandomConfiguration();
        }


        for (int i = 0; i < getNumberOfStates(); i++) {
            hammingDistancePerState[i] = getAliveCellsInState(i);
            leeDistancePerState[i] = getAliveCellsInState(i);
            deltaPotentialPerState[i] = getAliveCellsInState(i);
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
                aliveCellsPerState[state]++;
                aliveCellsPerState[state]++;
                if (state != 0) {
                    aliveCells++;
                    aliveCells++;
                }
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
                aliveCellsPerState[state]++;
                if (state != 0) {
                    aliveCells++;
                }
            }
        }
    }

    @Override
    public void clear() {
        resetMetrics();
        aliveCellsPerState[1] = 1;
        aliveCells = 1;
        for (int i = 0; i < cells.length; i++) {
            cells[i] = 0;
        }
        cells[(int) Math.floor(getSize() / 2)] = 1;

        for (int i = 0; i < getNumberOfStates(); i++) {
            hammingDistancePerState[i] = getAliveCellsInState(i);
            leeDistancePerState[i] = getAliveCellsInState(i);
            deltaPotentialPerState[i] = getAliveCellsInState(i);
        }
    }

    @Override
    public void updateAlive() {
        aliveCells = 0;
        resetMetrics();

        for (int i = 0; i < size; i++) {
            aliveCellsPerState[((int) cells[i])]++;
            if (cells[i] > 0) {
                aliveCells++;
            }
        }
    }
}
