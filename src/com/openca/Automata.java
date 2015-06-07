package com.openca;

/**
 * Clase que implementa un autómata celular
 *
 * @author juanky
 */
public abstract class Automata implements IAutomata {
    /**
     * Espacio de estados
     */
    protected byte estados;
    /**
     * Radio
     */
    protected int radio;
    /**
     * Tamaño
     */
    protected int tamano;
    /**
     * Nº de células del vecindario
     */
    protected int implicadas;
    /**
     * LookUpTable de la Regla
     */
    protected byte[] reglastring;
    /**
     * Nºvivas de cada posible valor de la célula
     */
    protected int[] estadosvivas;
    /**
     * Diferencia hamming para cada posible valor de célula
     */
    protected int[] estadodiferenciashamming;
    /**
     * Diferencia lee para cada posible valor de célula
     */
    protected int[] estadodiferenciaslee;
    /**
     * Diferencia potencial para cada posible valor de célula
     */
    protected int[] estadodiferenciaspotencial;
    /**
     * Diferencia hamming para un sector
     */
    protected int[] sectordiferenciashamming;
    /**
     * Diferencia lee para cada posible sector
     */
    protected int[] sectordiferenciaslee;
    /**
     * Diferecia potencial para cada posible sector
     */
    protected int[] sectordiferenciaspotencial;
    /**
     * Número de vivas de cada posible sector
     */
    protected int[] sectorvivas;
    /**
     * Número de células vivas (valor > 0)
     */
    protected int vivas;
    /**
     * Número de secciones
     */
    protected byte secciones;

    /**
     * Constructor
     */
    public Automata() {
    }

    @Override
    public final int getRelativeIndex(int origen) {
        return origen < 0 ? origen + tamano : origen >= tamano ? origen - tamano : origen;
    }

    @Override
    public abstract void RandomIC();

    @Override
    public abstract void evolve();

    @Override
    public int getHammingDiffState(int estado) {
        return estado < estadodiferenciashamming.length ? estadodiferenciashamming[estado] : 0;

    }

    @Override
    public int getHammingDiffSector(int sector) {
        return sector < sectordiferenciashamming.length ? sectordiferenciashamming[sector] : 0;
    }

    @Override
    public int getDiffLeeState(int estado) {
        return estado < estadodiferenciaslee.length ? estadodiferenciaslee[estado] : 0;
    }

    @Override
    public int getDiffLeeSector(int sector) {
        return sector < sectordiferenciaslee.length ? sectordiferenciaslee[sector] : 0;
    }

    @Override
    public int getDeltaPotState(int estado) {
        return estado < estadodiferenciaspotencial.length ? estadodiferenciaspotencial[estado] : 0;
    }

    @Override
    public int getDeltaPotSector(int sector) {
        return sector < sectordiferenciaspotencial.length ? sectordiferenciaspotencial[sector] : 0;
    }

    @Override
    public final byte getNumStates() {
        return estados;
    }

    public abstract String getMaxRule();

    @Override
    public final int getRadious() {
        return radio;
    }

    @Override
    public byte[] getRuleLookup() {
        return reglastring;
    }

    @Override
    public final byte getNumSections() {
        return secciones;
    }

    @Override
    public final int getSize() {
        return tamano;
    }

    public int getAlive() {
        return vivas;
    }

    @Override
    public int getAliveState(int estado) {
        return estado < estadosvivas.length ? estadosvivas[estado] : 0;
    }

    @Override
    public int getAliveSector(int sector) {
        return sector < sectorvivas.length ? sectorvivas[sector] : 0;
    }

    @Override
    public abstract void clean();

    /**
     * Resetea el valor de las métricas
     */
    protected void resetMetricas() {
        for (int i = 0; i < getNumStates(); i++) {
            estadodiferenciashamming[i] = 0;
            estadodiferenciaslee[i] = 0;
            estadosvivas[i] = 0;
        }

        for (int i = 0; i < getNumSections(); i++) {
            sectordiferenciashamming[i] = 0;
            sectordiferenciaslee[i] = 0;
            sectorvivas[i] = 0;
        }
    }

    /**
     * Asigna el valor de la métrica diferencia Hamming para cada posible valor de célula
     *
     * @param diferenciasestados
     */
    protected void setDiferenciasHammingEstado(int[] diferenciasestados) {
        estadodiferenciashamming = diferenciasestados;
    }

    ;


    /**
     * Asigna el valor de la métrica diferencia Lee para cada posible valor de célula
     *
     * @param diferenciasestados
     */
    protected void setDiferenciasLeeEstado(int[] diferenciasestados) {
        estadodiferenciaslee = diferenciasestados;
    }

    /**
     * Asigna el valor de la métrica diferencia Potencial para cada posible valor de célula
     *
     * @param diferenciasestados
     */
    protected void setDiferenciasPotencialEstado(int[] diferenciasestados) {
        estadodiferenciaspotencial = diferenciasestados;
    }

    @Override
    public void setStates(byte estados) {
        this.estados = estados;
        estadosvivas = new int[estados];
        estadodiferenciashamming = new int[estados];
        estadodiferenciaslee = new int[estados];
        estadodiferenciaspotencial = new int[estados];

    }

    @Override
    public void setRadious(int radio) {
        this.radio = radio;
    }

    @Override
    public abstract void setRule(String regla) throws Exception;

    @Override
    public void setSectors(byte secciones) {
        this.secciones = secciones;
        sectordiferenciashamming = new int[secciones];
        sectordiferenciaslee = new int[secciones];
        sectordiferenciaspotencial = new int[secciones];
        sectorvivas = new int[secciones];
    }

    @Override
    public void setSize(int tamano) {
        this.tamano = tamano;
    }

    /**
     * Establece el número de células con valor > 0
     *
     * @param vivas Número de células con valor > 0
     */
    public void setVivas(int vivas) {
        this.vivas = vivas;
    }

    /**
     * Asigna el valor de la métrica número de vivas para cada posible valor de célula
     *
     * @param vivasestados Lista con el número de células con cada posible valor de célula
     */
    protected void setVivasEstado(int[] vivasestados) {
        estadosvivas = vivasestados;
    }

    /**
     * Asigna el valor de la métrica número de vivas para un posible estado de una célula del autómata
     *
     * @param estado Posible valor de un estado del autómata.
     * @param vivas  Valor de la métrica para ese posible estado.
     */
    protected void setVivasEstado(short estado, short vivas) {
        estadosvivas[estado] = vivas;
    }

    /**
     * Actualiza el valor de las métricas
     *
     * @param index          Indice de la célula.
     * @param estado         Valor de la célula.
     * @param estadoanterior Valor anterior de la célula.
     */
    protected void updateMetricas(int index, int estado, int estadoanterior) {
        final int sector = (int) (index / ((double) getSize() / getNumSections()));
        int diferencia = (estado - estadoanterior);
        estadodiferenciaspotencial[estado] = estadodiferenciaspotencial[estado] + diferencia;

        if (estadoanterior == 0) {
            sectordiferenciaspotencial[sector] = sectordiferenciaspotencial[sector] + diferencia;
        }

        estadosvivas[estado]++;
        if (estado > 0) {
            sectorvivas[sector]++;
        }

        if (estadoanterior != estado) {
            diferencia = Math.abs(estado - estadoanterior);

            if (estado != 0) {
                sectordiferenciashamming[sector]++;
                sectordiferenciaslee[sector] = sectordiferenciaslee[sector] + Math.min(diferencia, estados - diferencia);
            }
            estadodiferenciashamming[estado]++;
            estadodiferenciaslee[estado] = estadodiferenciaslee[estado] + Math.min(diferencia, estados - diferencia);
        }
    }

    @Override
    public abstract void updateAlive();
}