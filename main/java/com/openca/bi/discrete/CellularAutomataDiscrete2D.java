package com.openca.bi.discrete;

import com.openca.CellularAutomata;
import com.openca.OnCellUpdatedCallback;
import com.openca.bi.OnCellUpdatedCallback2D;

public interface CellularAutomataDiscrete2D extends CellularAutomata {
    int[][] getCells();
}
