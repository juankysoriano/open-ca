package com.openca.bi.discrete.vonnewman;

import com.openca.bi.discrete.AutomataDiscrete2D;

public class AutomataCiclicVon2D extends AutomataDiscrete2D {
    private short threshold;

    public AutomataCiclicVon2D(byte[][] celulas) {
        super(celulas);
    }

    @Override
    protected void evolveCellAt(int x, int y) {
        byte cell = cells[x][y];
        byte cellAux = cell;

        int count = 0;
        byte state = cell;
        int newX;
        int newY;
        if (state < states - 1) {
            for (int j = 0; j < numberOfNeighbours; j++) {
                if (j < radius) {
                    newX = getWrappedIndex(x - radius + j);
                    if (cells[newX][y] == (cell + 1)) {
                        count++;
                    }
                    newX = getWrappedIndex(x + j + 1);
                    if (cells[newX][y] == (cell + 1)) {
                        count++;
                    }
                }
                newY = getWrappedIndex(y - radius + j);
                if (cells[x][newY] == (cell + 1)) {
                    count++;
                }
                if (count >= threshold) {
                    cellAux++;
                }
            }
        } else if (state == states - 1) {
            for (int j = 0; j < numberOfNeighbours; j++) {
                if (j < radius) {
                    newX = getWrappedIndex(x - radius + j);
                    if (cells[newX][y] == 0) {
                        count++;
                    }
                    newX = getWrappedIndex(x + j + 1);
                    if (cells[newX][y] == 0) {
                        count++;
                    }
                }
                newY = getWrappedIndex(y - radius + j);
                if (cells[x][newY] == 0) {
                    count++;
                }
                if (count >= threshold) {
                    cellAux = 0;
                }
            }
        }
        cellsTemp[x][y] = cellAux;
    }

    @Override
    public String getMaxRule() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public void setRule(String rule) {
        threshold = (short) Integer.parseInt(rule);
    }
}
