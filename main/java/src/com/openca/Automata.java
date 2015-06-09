package com.openca;

/**
 * Clase que implementa un autómata celular
 *
 * @author juanky
 */
public abstract class Automata implements IAutomata {
    protected byte states;
    protected int radius;
    protected int size;
    protected int numberOfNeighbours;
    protected byte[] ruleLookupTable;
    protected int[] aliveCellsPerState;
    protected int[] hammingDistancePerState;
    protected int[] leeDistancePerState;
    protected int[] deltaPotentialPerState;
    protected int[] aliveCellsPerSector;
    protected int[] hammingDistancePerSector;
    protected int[] leeDistancePerSector;
    protected int[] deltaPotentialPerSector;
    protected int aliveCells;
    protected byte sections;

    protected
    int getWrappedIndex(int index) {
        return index < 0 ? index + size : index >= size ? index - size : index;
    }

    @Override
    public abstract void randomiseConfiguration();

    @Override
    public abstract void evolve();

    @Override
    public int getHammingDistanceForState(int state) {
        return state < hammingDistancePerState.length ? hammingDistancePerState[state] : 0;

    }

    @Override
    public int getHammingDistanceForSector(int sector) {
        return sector < hammingDistancePerSector.length ? hammingDistancePerSector[sector] : 0;
    }

    @Override
    public int getLeeDistanceForState(int state) {
        return state < leeDistancePerState.length ? leeDistancePerState[state] : 0;
    }

    @Override
    public int getLeeDistanceForSector(int sector) {
        return sector < leeDistancePerSector.length ? leeDistancePerSector[sector] : 0;
    }

    @Override
    public int getDeltaPotentialForState(int state) {
        return state < deltaPotentialPerState.length ? deltaPotentialPerState[state] : 0;
    }

    @Override
    public int getDeltaPotentialForSector(int sector) {
        return sector < deltaPotentialPerSector.length ? deltaPotentialPerSector[sector] : 0;
    }

    @Override
    public byte getNumberOfStates() {
        return states;
    }

    public abstract String getMaxRule();

    @Override
    public int getRadius() {
        return radius;
    }

    @Override
    public byte[] getRuleLookup() {
        return ruleLookupTable;
    }

    @Override
    public byte getNumberOfSections() {
        return sections;
    }

    @Override
    public int getSize() {
        return size;
    }

    public int getAliveCells() {
        return aliveCells;
    }

    @Override
    public int getAliveCellsInState(int state) {
        return state < aliveCellsPerState.length ? aliveCellsPerState[state] : 0;
    }

    @Override
    public int getAliveCellsInSector(int sector) {
        return sector < aliveCellsPerSector.length ? aliveCellsPerSector[sector] : 0;
    }

    @Override
    public abstract void clear();

    protected void resetMetrics() {
        for (int i = 0; i < states; i++) {
            hammingDistancePerState[i] = 0;
            leeDistancePerState[i] = 0;
            aliveCellsPerState[i] = 0;
        }

        for (int i = 0; i < sections; i++) {
            hammingDistancePerSector[i] = 0;
            leeDistancePerSector[i] = 0;
            aliveCellsPerSector[i] = 0;
        }
    }

    @Override
    public void setStates(byte states) {
        this.states = states;
        aliveCellsPerState = new int[states];
        hammingDistancePerState = new int[states];
        leeDistancePerState = new int[states];
        deltaPotentialPerState = new int[states];
    }

    @Override
    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public abstract void setRule(String rule) throws Exception;

    @Override
    public void setSectors(byte sectors) {
        this.sections = sectors;
        hammingDistancePerSector = new int[sectors];
        leeDistancePerSector = new int[sectors];
        deltaPotentialPerSector = new int[sectors];
        aliveCellsPerSector = new int[sectors];
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }

    protected void updateMetrics(int index, int state, int previousState) {
        updateAlive(index, state);
        updateDeltaPotential(index, state, previousState);
        updateHammingDistance(index, state, previousState);
        updateLeeDistance(index, state, previousState);
    }

    private int getSectionFor(int index) {
        return index / (size / sections);
    }

    private void updateHammingDistance(int index, int state, int previousState) {
        if (previousState != state) {
            if (state != 0) {
                int sector = getSectionFor(index);
                hammingDistancePerSector[sector]++;
            }
            hammingDistancePerState[state]++;
        }
    }

    private void updateLeeDistance(int index, int state, int previousState) {
        if (previousState != state) {
            int distance = Math.abs(state - previousState);
            if (state != 0) {
                int sector = getSectionFor(index);
                leeDistancePerSector[sector] = leeDistancePerSector[sector] + Math.min(distance, states - distance);
            }
            leeDistancePerState[state] = leeDistancePerState[state] + Math.min(distance, states - distance);
        }
    }

    private void updateAlive(int index, int state) {
        aliveCellsPerState[state]++;
        if (state > 0) {
            int sector = getSectionFor(index);
            aliveCellsPerSector[sector]++;
        }
    }

    private void updateDeltaPotential(int index, int state, int previousState) {
        int distance = state - previousState;
        deltaPotentialPerState[state] = deltaPotentialPerState[state] + distance;

        if (previousState == 0) {
            int sector = getSectionFor(index);
            deltaPotentialPerSector[sector] = deltaPotentialPerSector[sector] + distance;
        }
    }

    @Override
    public abstract void updateAlive();
}