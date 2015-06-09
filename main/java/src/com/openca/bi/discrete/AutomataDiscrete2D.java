package com.openca.bi.discrete;

import com.openca.bi.Automata2D;

import java.util.Random;


public abstract class AutomataDiscrete2D extends Automata2D implements IAutomataDiscrete2D {
    protected byte[][] cells;
    protected byte[][] cellsTemp;
    protected int[][] neighbourhoodCode;

    public AutomataDiscrete2D(byte[][] cells) {
        super();
        this.cells = cells;
    }

    @Override
    public void randomiseConfiguration() {
        Random rand = new Random();
        resetMetrics();
        aliveCells = 0;

        boolean hasToBeSymmetric = rand.nextInt(2) == 0;

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

                    aliveCellsPerState[state]++;
                    aliveCellsPerState[state]++;
                    aliveCellsPerState[state]++;
                    aliveCellsPerState[state]++;

                    if (state > 0) {
                        aliveCells++;
                        aliveCells++;
                        aliveCells++;
                        aliveCells++;
                    }
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
                    byte estado = (byte) rand.nextInt(states);
                    cells[i][j] = estado;
                    aliveCellsPerState[estado]++;
                    if (estado > 0) {
                        aliveCells++;
                    }
                }
            }

        }
    }

    @Override
    public void evolve() { //Evolucionamos todas las clulas
        resetMetrics();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                evolveCellAt(i, j);
                updateMetrics(i, cellsTemp[i][j], cells[i][j]);

            }
        }

        byte[][] cellsaux = cellsTemp;
        cellsTemp = cells;
        cells = cellsaux;

    }

    @Override
    public void evolve(byte[][] cells) { //Evolucionamos todas las clulas
        resetMetrics();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                evolveCellAt(i, j);
                updateMetrics(i, cellsTemp[i][j], cells[i][j]);
            }
        }

        byte[][] cellsaux = cellsTemp;
        cellsTemp = this.cells;
        this.cells = cellsaux;
    }

    @Override
    public byte[][] getCells() {
        return cells;
    }

    @Override
    public void setCells(double[][] celulalist) {
        if ((celulalist != null) && (celulalist.length == size)) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    cells[i][j] = (byte) Math.abs(celulalist[i][j]);
                    if (cells[i][j] == states) {
                        cells[i][j] = (byte) (states - 1);
                    }
                }
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
    public int getHammingDistanceForState(int state) {
        return hammingDistancePerState[state];
    }

    @Override
    public int getLeeDistanceForState(int state) {
        return leeDistancePerState[state];
    }

    @Override
    public int getDeltaPotentialForState(int state) {
        return deltaPotentialPerState[state];
    }

    @Override
    public int getAliveCellsInState(int state) {
        return aliveCellsPerState[state];
    }

    @Override
    public void clear() {
        resetMetrics();

        aliveCells = 9;
        aliveCellsPerState[1] = 9;

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


        for (int i = 0; i < getNumberOfStates(); i++) {
            hammingDistancePerState[i] = getAliveCellsInState(i);
            leeDistancePerState[i] = getAliveCellsInState(i);
            deltaPotentialPerState[i] = getAliveCellsInState(i);
        }
    }

    @Override
    public void setCells(byte[][] cells) {
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
    public void setSize(int size) {
        if (this.size != size) {
            this.size = size;
            cells = new byte[this.size][this.size];
            cellsTemp = new byte[this.size][this.size];
            neighbourhoodCode = new int[this.size][this.size];
        }
    }

    @Override
    public void updateAlive() {
        aliveCells = 0;
        resetMetrics();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                aliveCellsPerState[cells[i][j]]++;
                if (cells[i][j] > 0) {
                    aliveCells++;
                }
            }
        }
    }
}
