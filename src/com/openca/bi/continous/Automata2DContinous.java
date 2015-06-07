package com.openca.bi.continous;

import com.openca.bi.Automata2D;

import java.util.Random;

/**
 * Autómata Celular Bidimensional Contínuo
 *
 * @author juanky
 */
public abstract class Automata2DContinous extends Automata2D implements IAutomata2DContinous {
    /**
     * Matriz de células del autómata
     */
    protected double[][] celulas;
    /**
     * Matriz de células del autómata tras evolve
     */
    protected double[][] celulasnext;
    /**
     * Matriz con el valor del vecindario calculado para cada célula
     */
    protected double[][] vecinos;

    protected double[] estadosvivas;
    /**
     * Diferencia hamming para cada posible valor de célula
     */
    protected double[] estadodiferenciashamming;
    /**
     * Diferencia lee para cada posible valor de célula
     */
    protected double[] estadodiferenciaslee;
    /**
     * Diferencia potencial para cada posible valor de célula
     */
    protected double[] estadodiferenciaspotencial;
    /**
     * Diferencia hamming para un sector
     */
    protected double[] sectordiferenciashamming;
    /**
     * Diferencia lee para cada posible sector
     */
    protected double[] sectordiferenciaslee;
    /**
     * Diferecia potencial para cada posible sector
     */
    protected double[] sectordiferenciaspotencial;
    /**
     * Número de vivas de cada posible sector
     */
    protected double[] sectorvivas;

    /**
     * Constructor
     */
    public Automata2DContinous() {
        resetMetricas();
    }

    /**
     * Constructor
     *
     * @param celulas Matriz de células del estado inicial del autómata.
     */
    public Automata2DContinous(double[][] celulas) {
        super();
        this.celulas = celulas;
        resetMetricas();
    }

    @Override
    public void RandomIC()//Calcula una Configuraci�n inicial aleatoria
    {
        final Random rand = new Random();
        resetMetricas();
        vivas = 0;

        final int simetrico = rand.nextInt(2);
        final int densidad = rand.nextInt(101);

        if (simetrico == 0) {
            for (int i = 0; i < tamano; i++) {
                for (int j = 0; j < tamano; j++) {
                    celulas[i][j] = 0;
                    final int numero = rand.nextInt(101);
                    if (numero < densidad) {
                        final double estado = rand.nextDouble() + rand.nextInt(estados - 1);
                        celulas[i][j] = estado;
                        final int state = (int) Math.round(celulas[i][j]);
                        estadosvivas[state]++;
                        ;
                        if (state > 0) {
                            vivas++;
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < tamano / 2; i++) {
                for (int j = 0; j < tamano / 2; j++) {
                    celulas[i][j] = 0;
                    celulas[i][tamano - 1 - j] = 0;
                    celulas[tamano - 1 - i][j] = 0;
                    celulas[tamano - 1 - i][tamano - 1 - j] = 0;
                    final int numero = rand.nextInt(101);
                    if (numero < densidad) {
                        final double estado = rand.nextDouble() + rand.nextInt(estados - 1);
                        celulas[i][j] = estado;
                        celulas[tamano - i - 1][tamano - j - 1] = estado;
                        final int state = (int) Math.round(celulas[i][j]);
                        estadosvivas[state]++;
                        estadosvivas[state]++;

                        if (state > 0) {
                            vivas++;
                            vivas++;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < getNumStates(); i++) {
            estadodiferenciashamming[i] = getAliveState(i);
            estadodiferenciaslee[i] = getAliveState(i);
            estadodiferenciaspotencial[i] = getAliveState(i);
        }

    }

    @Override
    public void evolve() { //Evolucionamos todas las clulas
        resetMetricas();

        for (int i = 0; i < tamano; i++) {
            for (int j = 0; j < tamano; j++) {
                evolucionarCelula(i, j);
                this.updateMetricas(i, celulasnext[i][j], celulas[i][j]);

            }
        }

        final double[][] cellsaux = celulasnext;
        celulasnext = celulas;
        celulas = cellsaux;

    }

    @Override
    public void evolucionar(double[][] celulasinit) { //Evolucionamos todas las clulas
        resetMetricas();

        for (int i = 0; i < tamano; i++) {
            for (int j = 0; j < tamano; j++) {
                evolucionarCelula(i, j);
                this.updateMetricas(i, celulasnext[i][j], celulasinit[i][j]);
            }
        }

        final double[][] cellsaux = celulasnext;
        celulasnext = celulas;
        celulas = cellsaux;

    }

    @Override
    public double[][] getCelulas() {
        return celulas;
    }

    @Override
    public int getHammingDiffState(int i) {
        return (int) estadodiferenciashamming[i];
    }

    @Override
    public int getHammingDiffSector(int sector) {
        return (int) sectordiferenciashamming[sector];
    }

    @Override
    public int getDiffLeeState(int i) {
        return (int) estadodiferenciaslee[i];
    }

    @Override
    public int getDiffLeeSector(int sector) {
        return (int) sectordiferenciaslee[sector];
    }

    @Override
    public int getDeltaPotState(int i) {
        return (int) estadodiferenciaspotencial[i];
    }

    @Override
    public int getDeltaPotSector(int sector) {
        return (int) sectordiferenciaspotencial[sector];
    }

    protected double[][] getVecinos() {
        return vecinos;
    }

    @Override
    public int getAliveState(int i) {
        return (int) estadosvivas[i];
    }

    @Override
    public int getAliveSector(int sector) {
        return (int) sectorvivas[sector];
    }

    @Override
    public void clean() {
        resetMetricas();
        estadosvivas[1] = 9;
        vivas = 4;

        for (int i = 0; i < celulas.length; i++) {
            for (int j = 0; j < celulas.length; j++) {
                celulas[i][j] = 0;
            }
        }

        final int mitad = (int) Math.floor(getSize() / 2);

        getCelulas()[mitad][mitad] = 1;
        getCelulas()[mitad - 1][mitad - 1] = 1;
        getCelulas()[mitad - 1][mitad] = 1;
        getCelulas()[mitad][mitad - 1] = 1;
        getCelulas()[mitad + 1][mitad] = 1;
        getCelulas()[mitad + 1][mitad + 1] = 1;
        getCelulas()[mitad + 1][mitad - 1] = 1;
        getCelulas()[mitad - 1][mitad + 1] = 1;
        getCelulas()[mitad][mitad + 1] = 1;

        for (int i = 0; i < getNumStates(); i++) {
            estadodiferenciashamming[i] = getAliveState(i);
            estadodiferenciaslee[i] = getAliveState(i);
            estadodiferenciaspotencial[i] = getAliveState(i);
        }
    }

    @Override
    public void setCelulas(byte[][] celulalist) {
        if ((celulalist != null) && (celulalist.length == tamano)) {
            for (int i = 0; i < tamano; i++) {
                for (int j = 0; j < tamano; j++) {
                    celulas[i][j] = ((celulalist[i][j]));
                }
            }
        } else {
            try {
                throw new Exception("Distintos tamaños para las células");
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setCelulas(double[][] celulas) {
        if ((celulas != null) && (celulas.length == tamano)) {
            this.celulas = celulas;
        } else {
            try {
                throw new Exception("Distintos tamaños para las células");
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setStates(byte estados) {
        this.estados = estados;
        estadosvivas = new double[estados];
        estadodiferenciashamming = new double[estados];
        estadodiferenciaslee = new double[estados];
        estadodiferenciaspotencial = new double[estados];

    }

    @Override
    public void setSectors(byte secciones) {
        this.secciones = secciones;
        sectordiferenciashamming = new double[secciones];
        sectordiferenciaslee = new double[secciones];
        sectordiferenciaspotencial = new double[secciones];
        sectorvivas = new double[secciones];
    }

    @Override
    public void setSize(int tamano) {
        if (this.tamano != tamano) {
            this.tamano = tamano;
            celulas = new double[this.tamano][this.tamano];
            celulasnext = new double[this.tamano][this.tamano];
            vecinos = new double[this.tamano][this.tamano];
        }
    }

    /**
     * Actualiza el valor de las métricas.
     *
     * @param index          Índice de la célula.
     * @param estado         Valor actual de la célula.
     * @param estadoanterior Valor anterior de la célula.
     */
    protected void updateMetricas(int index, double estado, double estadoanterior) {
        final int sector = (int) (index / ((double) getSize() / getNumSections()));
        double diferencia = (estado - estadoanterior);
        final double intensidad = estado - (int) estado;
        int state = (int) estado + 1;
        if (state == getNumStates()) {
            state--;
        }
        estadodiferenciaspotencial[state] = estadodiferenciaspotencial[state] + diferencia;

        if (estadoanterior != 0) {
            sectordiferenciaspotencial[sector] = sectordiferenciaspotencial[sector] + diferencia;
        }

        estadosvivas[state] += intensidad;
        sectorvivas[sector] = sectorvivas[sector] + intensidad;

        if (estadoanterior != estado) {
            diferencia = Math.abs(diferencia);
            sectordiferenciashamming[sector] = sectordiferenciashamming[sector] + diferencia;
            estadodiferenciashamming[state] = estadodiferenciashamming[state] + diferencia;
            estadodiferenciaslee[state] = estadodiferenciaslee[state] + Math.min(diferencia, (estados - 1) - diferencia);
            sectordiferenciaslee[sector] = sectordiferenciaslee[sector] + Math.min(diferencia, (estados - 1) - diferencia);
        }
    }

    @Override
    public void updateAlive() {
        vivas = 0;
        resetMetricas();
        for (int i = 0; i < tamano; i++) {
            for (int j = 0; j < tamano; j++) {
                final int state = (int) Math.round(celulas[i][j]);
                estadosvivas[state]++;
                if (celulas[i][j] > 0) {
                    vivas++;
                }
            }
        }
    }

    @Override
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
}

