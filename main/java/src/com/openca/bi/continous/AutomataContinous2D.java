package com.openca.bi.continous;

import com.openca.bi.Automata2D;

public abstract class AutomataContinous2D extends Automata2D implements IAutomataContinous2D {

    protected double[][] cells;
    protected double[][] tempCells;
    protected double[][] neighbourhoodCode;

    public AutomataContinous2D() {
        resetMetrics();
    }

    public AutomataContinous2D(double[][] cells) {
        super();
        this.cells = cells;
        resetMetrics();
    }

    @Override
    public void evolve() {
        resetMetrics();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                evolveCellAt(i, j);
                updateMetrics(i, (int) tempCells[i][j], (int) cells[i][j]);

            }
        }

        double[][] cellsaux = tempCells;
        tempCells = cells;
        cells = cellsaux;
    }

    @Override
    public void evolve(double[][] celulasinit) {
        resetMetrics();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                evolveCellAt(i, j);
                updateMetrics(i, (int) tempCells[i][j], (int) celulasinit[i][j]);
            }
        }

        double[][] cellsaux = tempCells;
        tempCells = cells;
        cells = cellsaux;
    }

    @Override
    public double[][] getCells() {
        return cells;
    }

    @Override
    public void setCells(byte[][] cellsList) {
        if ((cellsList != null) && (cellsList.length == size)) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    cells[i][j] = ((cellsList[i][j]));
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
    public void setCells(double[][] celulas) {
        if ((celulas != null) && (celulas.length == size)) {
            this.cells = celulas;
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
            cells = new double[this.size][this.size];
            tempCells = new double[this.size][this.size];
            neighbourhoodCode = new double[this.size][this.size];
        }
    }
}

