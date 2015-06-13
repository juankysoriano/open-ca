package com.openca.uni.discrete;

import com.openca.Automata;
import com.openca.uni.OnCellUpdatedCallback1D;

import java.util.Random;

public abstract class AutomataDiscrete1D extends Automata implements CellularAutomataDiscrete1D {
    protected int[] cells;
    protected int[] tempCells;
    protected int[] neighbourhoodCode;

    AutomataDiscrete1D() {
        //no-op
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
    public void evolve() {
        for (int index = 0; index < size; index++) {
            evolveCellAt(index);
        }

        int[] cellsAux = tempCells;
        tempCells = cells;
        cells = cellsAux;
    }

    @Override
    public void evolve(OnCellUpdatedCallback1D onCellUpdatedCallback1D) {
        for (int index = 0; index < size; index++) {
            evolveCellAt(index);
            onCellUpdatedCallback1D.onCellDetected(index, tempCells[index]);
        }

        int[] cellsAux = tempCells;
        tempCells = cells;
        cells = cellsAux;
    }

    protected abstract void evolveCellAt(int index);

    @Override
    public int[] getCells() {
        return cells;
    }

    @Override
    public void clear() {
        for (int i = 0; i < cells.length; i++) {
            cells[i] = 0;
        }
        cells[(int) Math.floor(getSize() / 2)] = 1;
    }

    @Override
    protected void setSize(int size) {
        if (this.size != size) {
            this.size = size;
            cells = new int[size];
            tempCells = new int[size];
            neighbourhoodCode = new int[size];
        }
    }
}
