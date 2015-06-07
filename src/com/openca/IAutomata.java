package com.openca;

/**
 * IOrganismo representa un autómata celular.
 *
 * @author juanky
 */
public interface IAutomata {
    /**
     * Ajusta el índice, del autóamta celular. Recordemos que el autómata tiene forma de toroide.
     * Las células de los extremos son vecinas.
     *
     * @param indice Indice de la célula.
     * @return nuevo indice ajustado
     */
    int getRelativeIndex(int indice);

    /**
     * Establece configuración inicial aleatoria.
     */
    void RandomIC();

    /**
     * Evoluciona el estado del autómata celular.
     */
    void evolve();

    /**
     * Devuelve el valor de la métrica distancia Hamming para las células con valor.
     *
     * @param valor Valor de célula para el que se recoge la métrica.
     * @return distancia hamming para valor
     */
    int getHammingDiffState(int valor);

    /**
     * Devuelve el valor de la métrica distancia Hamming para el sector
     *
     * @param sector Sector para el que se recoge la métrica.
     * @return distancia hamming para sector
     */
    int getHammingDiffSector(int sector);

    /**
     * Devuelve el valor de la métrica distancia Lee para las células con valor.
     *
     * @param valor Valor de célula para el que se recoge la métrica.
     * @return distancia lee para valor
     */
    int getDiffLeeState(int valor);

    /**
     * Devuelve el valor de la métrica distancia Lee para el sector
     *
     * @param sector Sector para el que se recoge la métrica.
     * @return distancia lee para sector
     */
    int getDiffLeeSector(int sector);

    /**
     * Devuelve el valor de la métrica Diferencia Potencial para las células con valor.
     *
     * @param valor Valor de célula para el que se recoge la métrica.
     * @return diferencia potencial para valor
     */
    int getDeltaPotState(int valor);

    /**
     * Devuelve el valor de la métrica Diferencia Potencial para el sector
     *
     * @param sector Sector para el que se recoge la métrica.
     * @return diferencia potencial para sector
     */
    int getDeltaPotSector(int sector);

    /**
     * Devuelve el tamaño del espacio de estados del autómata.
     *
     * @return Tamaño del espacio de estados.
     */
    byte getNumStates();

    /**
     * Devuelve el máximo valor posible de una regla para ese autómata.
     *
     * @return cadena con el valor máximo posible de regla.
     */
    String getMaxRule();

    /**
     * Devuelve el radio del autómata.
     *
     * @return radio del autómata.
     */
    int getRadious();

    /**
     * Devuelve una LookupTable de la regla. Se accede a esa tabla para buscar siguiente estado.
     *
     * @return LookupTable de la regla.
     */
    byte[] getRuleLookup();

    /**
     * Devuelve el número de secciones del autómata.
     *
     * @return número de secciones del autómata.
     */
    byte getNumSections();

    /**
     * Devuelve el tamaño del autómata.
     *
     * @return tamaño del autómata.
     */
    int getSize();

    /**
     * Devuelve el número de células con valor mayor 0
     *
     * @return número células valor > 0
     */
    int getAlive();

    /**
     * Devuelve el valor de la métrica número de vivas de las células con valor.
     *
     * @param valor Valor de célula para el que se recoge la métrica.
     * @return número de células con ese valor
     */
    int getAliveState(int valor);

    /**
     * Devuelve el valor de la métrica número de vivas para el sector
     *
     * @param sector Sector para el que se recoge la métrica.
     * @return número de células con valor > 0 de ese sector
     */
    int getAliveSector(int sector);

    /**
     * Devuelve una configuración inicial simple.
     */
    void clean();

    /**
     * Asigna el tamaño del espacio de estados del autómata.
     *
     * @param estados tamaño del espacio de estados.
     */
    void setStates(byte estados);

    /**
     * Asigna el radio de vecindad del autómata
     *
     * @param radio Radio de vecindad.
     */
    void setRadious(int radio);

    /**
     * Asigna regla al autómata.
     *
     * @param regla Regla del autómata.
     * @throws Exception
     */
    void setRule(String regla) throws Exception;

    /**
     * Asigna número de secciones del autómata.
     *
     * @param secciones Número de secciones del autómata.
     */
    void setSectors(byte secciones);

    /**
     * Asigna tamaño del autómata.
     *
     * @param tamano Tamaño del autómata.
     */
    void setSize(int tamano);

    /**
     * Recorre el estado del autómata y cuenta en número de vivas.
     */
    void updateAlive();
}
