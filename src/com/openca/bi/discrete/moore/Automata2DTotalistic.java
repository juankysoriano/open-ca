package com.openca.bi.discrete.moore;

import com.openca.bi.discrete.Automata2DDiscrete;

import java.math.BigInteger;

/**
 * Organismo Bidimensional con tipo de regla Total y vecindad de Moore
 *
 * @author juanky
 */
public final class Automata2DTotalistic extends Automata2DDiscrete {
    /**
     * Constructor
     */
    public Automata2DTotalistic() {
        super();
    }

    /**
     * Constructor
     *
     * @param celulas Matriz con las células iniciales del autómata.
     */
    public Automata2DTotalistic(byte[][] celulas) {
        super(celulas);
    }

    @Override
    protected void evolucionarCelula(int x, int y) {
        final int index = reglastring.length - setVecinos(x, y) - 1;
        celulasnext[x][y] = index >= 0 ? reglastring[index] : 0;

    }

    @Override
    public String getMaxRule() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setRule(String regla) throws Exception {
        // TODO Auto-generated method stub
        final char[] reglacharstring = (new BigInteger(regla)).toString(estados).toCharArray();
        reglastring = new byte[reglacharstring.length];

        for (int i = 0; i < reglacharstring.length; i++) {
            reglastring[i] = (byte) (reglacharstring[i] - '0');
        }
    }

    /**
     * Calcula el valor del vecindario de una célula (Moore)
     * Se usará como índice para acceder a la lookUpTable
     *
     * @param x Coordenada x de la célula central
     * @param y Coordenada y de la célula central
     * @return Índice de la lookUpTable que se usará para acceder a la célula
     */
    protected final int setVecinos(int x, int y) //Calcula el vecindario del que forma parte una clula (Moore)
    {
        int nuevax;
        int nuevay;
        int nuevax1;
        int nuevay1;
        int code;

        if (y != 0) {
            nuevay1 = getRelativeIndex(y - radio - 1);
            nuevay = getRelativeIndex(y + radio);
            code = vecinos[x][y - 1];
            for (int i = -radio; i <= radio; i++) {
                nuevax = getRelativeIndex(x + i);
                code += (celulas[nuevax][nuevay]) - (celulas[nuevax][nuevay1]);
            }
        } else if ((y == 0) && (x != 0)) {
            nuevax1 = getRelativeIndex(x - radio - 1);
            nuevax = getRelativeIndex(x + radio);
            code = vecinos[x - 1][y];
            for (int i = -radio; i <= radio; i++) {
                nuevay = getRelativeIndex(i);
                code += (celulas[nuevax][nuevay]) - (celulas[nuevax1][nuevay]);
            }
            //+ bsum - bres;

        } else //if x== && y == 0
        {
            code = 0;
            for (int i = -radio; i <= radio; i++) {
                nuevay = getRelativeIndex(i);
                for (int j = -radio; j <= radio; j++) {
                    nuevax = getRelativeIndex(j);
                    code += celulas[nuevax][nuevay];
                }
            }
        }
        vecinos[x][y] = code;
        return code;
    }
}
