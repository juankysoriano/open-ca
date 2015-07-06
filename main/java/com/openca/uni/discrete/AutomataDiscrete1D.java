package com.openca.uni.discrete;

import com.openca.Automata;
import com.openca.OnCellUpdatedCallback;
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
        boolean hasToBeSymmetric = new Random().nextInt(100) > 90;

        if (hasToBeSymmetric) {
            prepareSymmetricConfiguration();
        } else {
            prepareRandomConfiguration();
        }
    }

    private void prepareRandomConfiguration() {
        Random random = new Random();
        int density = 30;
        for (int i = 0; i < width; i++) {
            cells[i] = 0;
            int value = random.nextInt(101);
            if (value < density) {
                byte state = (byte) random.nextInt(states);
                cells[i] = state;
            }
        }
    }

    private void prepareSymmetricConfiguration() {
        Random random = new Random();
        int density = 30;
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

    @Override
    public void evolve() {
        for (int index = 0; index < width; index++) {
            evolveCellAt(index);
        }

        int[] cellsAux = tempCells;
        tempCells = cells;
        cells = cellsAux;
    }

    @Override
    public void evolve(OnCellUpdatedCallback onCellUpdatedCallback1D) {
        for (int index = 0; index < width; index++) {
            evolveCellAt(index);
            ((OnCellUpdatedCallback1D) onCellUpdatedCallback1D).onCellDetected(index, tempCells[index]);
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
        cells[(int) Math.floor(getWidth() / 2)] = 1;
    }

    @Override
    protected void setWidth(int wi) {
        if (this.width != wi) {
            this.width = wi;
            cells = new int[wi];
            tempCells = new int[wi];
            neighbourhoodCode = new int[wi];
        }
    }
}
