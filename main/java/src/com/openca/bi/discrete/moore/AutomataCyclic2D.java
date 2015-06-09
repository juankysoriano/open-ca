package com.openca.bi.discrete.moore;

import com.openca.bi.discrete.AutomataDiscrete2D;

public class AutomataCyclic2D extends AutomataDiscrete2D {

    private short threshold;

    public AutomataCyclic2D(byte[][] celulas) {
        super(celulas);
    }

    @Override
    protected void evolveCellAt(int x, int y) {
        byte cell = cells[x][y];
        byte nextCell = cell;

        if (computeNeighbourhoodCode(x, y) >= threshold) {
            nextCell = (byte) ((cell + 1) % states);
        }
        cellsTemp[x][y] = nextCell;

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

    protected int computeNeighbourhoodCode(int x, int y) {
        int count = 0;
        byte state = cells[x][y];
        int limitX = x + radius;
        int limitY = y + radius;
        for (int i = x - radius; i <= limitX; i++) {
            for (int j = y - radius; (count <= threshold) && (j <= limitY); j++) {
                if (state != states - 1) {
                    if (cells[getWrappedIndex(i)][getWrappedIndex(j)] == (cells[x][y] + 1)) {
                        count++;
                    }
                } else {
                    if (cells[getWrappedIndex(i)][getWrappedIndex(j)] == 0) {
                        count++;
                    }
                }

            }
        }
        neighbourhoodCode[x][y] = count;
        return count;
    }

}
