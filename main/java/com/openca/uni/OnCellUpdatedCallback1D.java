package com.openca.uni;

import com.openca.OnCellUpdatedCallback;

public interface OnCellUpdatedCallback1D extends OnCellUpdatedCallback {
    void onCellDetected(int x, int cellValue);
}
