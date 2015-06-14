package com.openca.uni.discrete;

public class AutomataCyclicDiscrete1D extends AutomataDiscrete1D {

    private short threshold;

    @Override
    protected void evolveCellAt(int index) {
        int cell = cells[index];
        int nextCell = cell;

        int count = 0;
        int state = cells[index];
        if (state < states - 1) {
            for (int i = index - radius; (count < threshold) && (i <= index + radius); i++) {
                if (cells[getWrappedIndex(i, width)] == (cell + 1)) {
                    count++;
                }
            }
            if (count >= threshold) {
                nextCell = cell;
            }
        } else if (state == states - 1) {
            for (int i = index - radius; (count < threshold) && (i <= index + radius); i++) {

                if (cells[getWrappedIndex(i, width)] == 0) {
                    count++;
                }

            }
            if (count >= threshold) {
                nextCell = 0;
            }
        }
        tempCells[index] = nextCell;

    }

    @Override
    protected void setRule(String rule) {
        threshold = new Short(rule);
    }
}
