package com.openca;

/**
 * IOrganismo representa un autómata celular.
 *
 * @author juanky
 */
public interface IAutomata {

    void randomiseConfiguration();

    void evolve();

    int getHammingDistanceForState(int state);

    int getHammingDistanceForSector(int sector);

    int getLeeDistanceForState(int state);

    int getLeeDistanceForSector(int sector);

    int getDeltaPotentialForState(int state);

    int getDeltaPotentialForSector(int sector);

    byte getNumberOfStates();

    byte getNumberOfSections();

    String getMaxRule();

    int getRadius();

    void setRadius(int radius);

    byte[] getRuleLookup();

    int getSize();

    void setSize(int size);

    int getAliveCells();

    int getAliveCellsInState(int state);

    int getAliveCellsInSector(int sector);

    void clear();

    void setStates(byte states);

    void setRule(String rule) throws Exception;

    void setSectors(byte sectors);

    void updateAlive();
}
