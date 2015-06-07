package com.openca.bi.discrete.moore;

import com.openca.bi.discrete.Automata2DDiscrete;

/**
 * Autómata Celular Bidimensional de tipo Cíclico con vecindad de Moore
 *
 * @author juanky
 */
public class Automata2DCyclic extends Automata2DDiscrete {

    /**
     * Regla (Valor del vecindario a superar para cambiar estado de una célula)
     */
    private short umbral;

    /**
     * Constructor
     */
    public Automata2DCyclic() {
        super();
    }

    /**
     * Constructor
     *
     * @param celulas Matriz de células de configuración inicial.
     */
    public Automata2DCyclic(byte[][] celulas) {
        super(celulas);
    }

    @Override
    protected void evolucionarCelula(int x, int y) {
        final byte cell = celulas[x][y];
        byte cellnext = cell;

        if (setVecinos(x, y) >= umbral) {
            cellnext = (byte) ((cell + 1) % estados);
        }
        celulasnext[x][y] = cellnext;

    }

    @Override
    public String getMaxRule() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final void setRule(String regla) {
        umbral = (short) Integer.parseInt(regla);
    }

    /**
     * Devuelve el valor del vecindario.
     * El número de células del vecindario con valor igual al valor de la célula en posición x e y, más uno.
     *
     * @param x Coorenada x de la célula central del vecindario.
     * @param y Coorenada y de la célula central del vecindario.
     * @return Número de células con valor igual a 1 + valor de célula central del vecindario.
     */
    protected final int setVecinos(int x, int y) {
        int contar = 0;
        final byte estado = celulas[x][y];
        final int limitex = x + radio;
        final int limitey = y + radio;
        for (int i = x - radio; i <= limitex; i++) {
            for (int j = y - radio; (contar <= umbral) && (j <= limitey); j++) {
                if (estado != estados - 1) {
                    if (celulas[getRelativeIndex(i)][getRelativeIndex(j)] == (celulas[x][y] + 1)) {
                        contar++;
                    }
                } else {
                    if (celulas[getRelativeIndex(i)][getRelativeIndex(j)] == 0) {
                        contar++;
                    }
                }

            }
        }
        vecinos[x][y] = contar;
        return contar;
    }

}
