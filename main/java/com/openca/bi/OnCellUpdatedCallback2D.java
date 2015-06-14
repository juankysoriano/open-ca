package com.openca.bi;

import com.openca.OnCellUpdatedCallback;

public interface OnCellUpdatedCallback2D extends OnCellUpdatedCallback {
    void onCellDetected(int x, int y, int cellValue);
}
