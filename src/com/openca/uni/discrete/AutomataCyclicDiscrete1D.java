package com.openca.uni.discrete;

public class AutomataCyclicDiscrete1D extends AutomataDiscrete1D {

    private short threshold;

    public AutomataCyclicDiscrete1D(byte[] celulas) {
        super(celulas);
    }

    @Override
    protected void evolveCellAt(int index) {
         byte cell = cells[index];
        byte cellnext = cell;


        int contar = 0;
         byte estado = cells[index];
        if (estado < states - 1) {
            for (int i = index - radius; (contar < threshold) && (i <= index + radius); i++) {
                if (cells[getWrappedIndex(i)] == (cell + 1)) {
                    contar++;
                }
            }
            if (contar >= threshold) {
                cellnext = cell;
            }
        } else if (estado == states - 1) {
            for (int i = index - radius; (contar < threshold) && (i <= index + radius); i++) {

                if (cells[getWrappedIndex(i)] == 0) {
                    contar++;
                }

            }
            if (contar >= threshold) {
                cellnext = 0;
            }
        }
        tempCells[index] = cellnext;

    }

    @Override
    public String getMaxRule() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public  void setRule(String rule) {
        threshold = new Short(rule);
    }
}
