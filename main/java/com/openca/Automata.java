package com.openca;

public abstract class Automata implements CellularAutomata {
    protected int states;
    protected int radius;
    protected int size;
    protected int numberOfNeighbours;
    protected int[] ruleLookupTable;

    @Override
    public int getNumberOfStates() {
        return states;
    }

    @Override
    public int getRadius() {
        return radius;
    }

    @Override
    public int getSize() {
        return size;
    }

    protected int getWrappedIndex(int index) {
        return index < 0 ? index + size : index >= size ? index - size : index;
    }

    protected void setStates(int states) {
        this.states = states;
    }

    protected void setRadius(int radius) {
        this.radius = radius;
        numberOfNeighbours = 2 * this.radius + 1;
    }

    protected abstract void setRule(String rule) throws IllegalArgumentException;

    protected void setSize(int size) {
        this.size = size;
    }

    public enum Dimension {
        UNIDIMENSIONAL,
        BIDIMENSIONAL
    }

    public enum Domain {
        CONTINOUS,
        DISCRETE
    }

    public enum Type {
        CYCLIC,
        TOTALISTIC,
        OUTER_TOTALISTIC,
        ELEMENTARY,
    }

    public static class Builder {
        private static final int NOT_SETTED = -1;
        private int size = NOT_SETTED;
        private int radius = NOT_SETTED;
        private int states = NOT_SETTED;
        private String rule;
        private Dimension dimension;
        private Domain domain;
        private Type type;

        public Builder size(int size) {
            this.size = size;
            return this;
        }

        public Builder radius(int radius) {
            this.radius = radius;
            return this;
        }

        public Builder states(int states) {
            this.states = states;
            return this;
        }

        public Builder dimension(Dimension dimension) {
            this.dimension = dimension;
            return this;
        }

        public Builder domain(Domain domain) {
            this.domain = domain;
            return this;
        }

        public Builder type(Type type) {
            this.type = type;
            return this;
        }

        public Builder rule(String rule) {
            this.rule = rule;
            return this;
        }

        public CellularAutomata build() throws IllegalArgumentException {
            checkArguments();
            return CellularAutomataFactory.createFor(size, radius, states, rule, dimension, domain, type);
        }

        private void checkArguments() throws IllegalArgumentException {
            if (size == NOT_SETTED) {
                throw new IllegalArgumentException("No size has been specified for this automata");
            }
            if (radius == NOT_SETTED) {
                throw new IllegalArgumentException("No radius has been specified for this automata");
            }
            if (states == NOT_SETTED) {
                throw new IllegalArgumentException("No states space has been specified for this automata");
            }
            if (rule == null) {
                throw new IllegalArgumentException("No rule has been specified for this automata");
            }
            if (dimension == null) {
                throw new IllegalArgumentException("No dimension has been specified for this automata");
            }
            if (domain == null) {
                throw new IllegalArgumentException("No domain has been specified for this automata");
            }
            if (type == null) {
                throw new IllegalArgumentException("No type has been specified for this automata");
            }
            if (domain == Domain.CONTINOUS && type != Type.TOTALISTIC) {
                throw new IllegalArgumentException("Continous cellular automata can only be TOTALISTIC");
            }
        }
    }
}