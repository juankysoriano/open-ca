package com.openca.bi.discrete;

import com.openca.CellularAutomata;
import com.openca.bi.OnCellUpdatedCallback2D;

public interface CellularAutomataDiscrete2D extends CellularAutomata {
    void evolve(OnCellUpdatedCallback2D onCellUpdatedCallback2D);

    int[][] getCells();
}
