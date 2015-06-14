package com.openca;

public abstract class Automata implements CellularAutomata {
    protected int states;
    protected int radius;
    protected int width;
    protected int height;
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
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    protected int getWrappedIndex(int index, int size) {
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

    protected void setWidth(int width) {
        this.width = width;
    }

    protected void setHeight(int height) {
        this.height = height;
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
        private int radius = NOT_SETTED;
        private int states = NOT_SETTED;
        private int width = NOT_SETTED;
        private int height = NOT_SETTED;
        private String rule;
        private Dimension dimension;
        private Domain domain;
        private Type type;

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
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

        public <T extends CellularAutomata> T build() throws IllegalArgumentException {
            checkArguments();
            return (T) CellularAutomataFactory.createFor(width, height, radius, states, rule, dimension, domain, type);
        }

        private void checkArguments() throws IllegalArgumentException {
            if (width == NOT_SETTED) {
                throw new IllegalArgumentException("No width has been specified for this automata");
            }
            if (height == NOT_SETTED && dimension == Dimension.BIDIMENSIONAL) {
                throw new IllegalArgumentException("No height has been specified for this automata");
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