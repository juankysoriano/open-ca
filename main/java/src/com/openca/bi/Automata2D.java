package com.openca.bi;

import com.openca.Automata;

public abstract class Automata2D extends Automata implements IAutomata2D {
    protected abstract void evolveCellAt(int x, int y);

    @Override
    public void setRadius(int radius) {
        this.radius = radius;
        numberOfNeighbours = 2 * this.radius + 1;
    }

}


