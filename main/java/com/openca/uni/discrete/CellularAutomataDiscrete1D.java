package com.openca.uni.discrete;

import com.openca.CellularAutomata;
import com.openca.uni.OnCellUpdatedCallback1D;

public interface CellularAutomataDiscrete1D extends CellularAutomata {
    int[] getCells();

    void evolve(OnCellUpdatedCallback1D onCellUpdatedCallback1D);
}
