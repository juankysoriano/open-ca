package com.openca.uni;

import com.openca.Automata;

/**
 * Organismo Unidimensional
 *
 * @author juanky
 */
public abstract class Automata1D extends Automata implements IAutomata1D {
    protected abstract void evolveCellAt(int index);

    @Override
    public void setRadius(int radius) {
        this.radius = radius;
        numberOfNeighbours = this.radius * 2 + 1;
    }
}
