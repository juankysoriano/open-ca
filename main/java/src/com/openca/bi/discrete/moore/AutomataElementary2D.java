package com.openca.bi.discrete.moore;

import com.openca.bi.discrete.AutomataDiscrete2D;

import java.math.BigInteger;

/**
 * Autómata Celular Bidimensional con tipo de regla Dinámica y vecindad de Moore
 *
 * @author juanky
 */
public class AutomataElementary2D extends AutomataDiscrete2D {
    private int factor;
    private int secondFactor;
    private int thirdFactor;

    public AutomataElementary2D(byte[][] celulas) {
        super(celulas);
    }

    @Override
    protected void evolveCellAt(int x, int y) {
        int index = ruleLookupTable.length - computeNeighbourhoodCode(x, y) - 1;
        cellsTemp[x][y] = (index >= 0) && (index < ruleLookupTable.length) ? ruleLookupTable[index] : 0;
    }

    @Override
    public String getMaxRule() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setRadius(int radius) {
        super.setRadius(radius);
        factor = (int) Math.pow(states, numberOfNeighbours);
        secondFactor = factor / states;
        thirdFactor = (int) Math.pow(factor, numberOfNeighbours - 1);
    }

    @Override
    public void setRule(String rule) throws Exception {
        // TODO Auto-generated method stub
        char[] reglacharstring = (new BigInteger(rule)).toString(states).toCharArray();
        ruleLookupTable = new byte[reglacharstring.length];

        for (int i = 0; i < reglacharstring.length; i++) {
            if (Character.isDigit(reglacharstring[i])) {
                ruleLookupTable[i] = (byte) (reglacharstring[i] - '0');
            } else {
                this.ruleLookupTable[i] = (byte) (reglacharstring[i] - 'W');
            }
        }
    }

    protected int computeNeighbourhoodCode(int x, int y) {
        int newX;
        int newY;
        int newXAux;
        int newYAux;
        int code = 0;
        int bRes = 0;
        int bSum = 0;
        int exp = 1;
        int secondExp = secondFactor;

        if (y != 0) {
            newY = getWrappedIndex(y - radius - 1);
            newYAux = getWrappedIndex(y + radius);
            for (int i = -radius; i <= radius; i++) {
                newX = getWrappedIndex(x + i);
                bRes += exp * (cells[newX][newY]);
                exp *= factor;
                bSum += secondExp * (cells[newX][newYAux]);
                secondExp *= factor;
            }
            code = (neighbourhoodCode[x][y - 1] - bRes) / 2 + bSum;
        } else if ((y == 0) && (x != 0)) {
            newX = getWrappedIndex(x - radius - 1);
            newXAux = getWrappedIndex(x + radius);

            for (int i = -radius; i <= radius; i++) {
                newY = getWrappedIndex(i);
                bRes += exp * (cells[newX][newY]);
                bSum += exp * (cells[newXAux][newY]);
                exp *= states;
            }
            code = (neighbourhoodCode[x - 1][y] - bRes) / factor + bSum * thirdFactor;
        } else {
            for (int i = -radius; i <= radius; i++) {
                newX = getWrappedIndex(i);
                for (int j = -radius; j <= radius; j++) {
                    newY = getWrappedIndex(j);
                    code += exp * (cells[newX][newY]);
                    exp *= states;
                }
            }

        }
        neighbourhoodCode[x][y] = code;
        return code;
    }

}