package com.openca.bi.discrete.moore;

import com.openca.bi.discrete.Automata2DDiscrete;

import java.math.BigInteger;

/**
 * Autómata Celular Bidimensional con tipo de regla Dinámica y vecindad de Moore
 *
 * @author juanky
 */
public final class Automata2DElementary extends Automata2DDiscrete {
    /**
     * Factor de escalado. Se usará para calcular el valor del vecindario de una célula.
     */
    private int factor;
    /**
     * Factor de escalado. Se usará para calcular el valor del vecindario de una célula.
     */
    private int factor2;
    /**
     * Factor de escalado. Se usará para calcular el valor del vecindario de una célula.
     */
    private int factor3;

    /**
     * Constructor
     */
    public Automata2DElementary() {
        super();
    }

    /**
     * Constructor
     *
     * @param celulas Matriz con las células iniciales del autómata.
     */
    public Automata2DElementary(byte[][] celulas) {
        super(celulas);
    }

    @Override
    protected void evolucionarCelula(int x, int y) {
        final int index = reglastring.length - setVecinos(x, y) - 1;
        celulasnext[x][y] = (index >= 0) && (index < reglastring.length) ? reglastring[index] : 0;
    }

    @Override
    public String getMaxRule() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final void setRadious(int radio) {
        super.setRadious(radio);
        factor = (int) Math.pow(estados, implicadas);
        factor2 = factor / estados;
        factor3 = (int) Math.pow(factor, implicadas - 1);
    }

    @Override
    public void setRule(String regla) throws Exception {
        // TODO Auto-generated method stub
        final char[] reglacharstring = (new BigInteger(regla)).toString(estados).toCharArray();
        reglastring = new byte[reglacharstring.length];

        for (int i = 0; i < reglacharstring.length; i++) {
            //if(Character.isDigit(reglacharstring[i]))
            //{
            reglastring[i] = (byte) (reglacharstring[i] - '0');
            //}
            //else ////OJITO CON ESTO -> Podría ser necesario para más de 10 estados
            //{
            //	this.reglastring[i] = (byte) (reglacharstring[i]-'W');
            //}
        }
    }

    /**
     * Calcula el valor del vecindario de una célula(Moore)
     * Se usará como índice para acceder a la lookUpTable
     *
     * @param x Coordenada x de la célula central
     * @param y Coordenada y de la célula central
     * @return Índice de la lookUpTable que se usará para acceder a la célula
     */
    protected final int setVecinos(int x, int y) {
        int nuevax;
        int nuevay;
        int nuevax1;
        int nuevay1;
        int code = 0;
        int bres = 0;
        int bsum = 0;
        int exp = 1;
        int exp1 = factor2;

        if (y != 0) {
            nuevay = getRelativeIndex(y - radio - 1);
            nuevay1 = getRelativeIndex(y + radio);
            for (int i = -radio; i <= radio; i++) {
                nuevax = getRelativeIndex(x + i);
                bres += exp * (celulas[nuevax][nuevay]);
                exp *= factor;
                bsum += exp1 * (celulas[nuevax][nuevay1]);
                exp1 *= factor;
            }
            code = (vecinos[x][y - 1] - bres) / 2 + bsum;
        } else if ((y == 0) && (x != 0)) {
            nuevax = getRelativeIndex(x - radio - 1);
            nuevax1 = getRelativeIndex(x + radio);

            for (int i = -radio; i <= radio; i++) {
                nuevay = getRelativeIndex(i);
                bres += exp * (celulas[nuevax][nuevay]);
                bsum += exp * (celulas[nuevax1][nuevay]);
                exp *= estados;
            }
            code = (vecinos[x - 1][y] - bres) / factor + bsum * factor3;
        } else {
            for (int i = -radio; i <= radio; i++) {
                nuevax = getRelativeIndex(i);
                for (int j = -radio; j <= radio; j++) {
                    nuevay = getRelativeIndex(j);
                    code += exp * (celulas[nuevax][nuevay]);
                    exp *= estados;
                }
            }

        }
        vecinos[x][y] = code;
        return code;
    }

}