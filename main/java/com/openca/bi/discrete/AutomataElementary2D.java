package com.openca.bi.discrete;

import com.openca.OnCellUpdatedCallback;

import java.math.BigInteger;

public class AutomataElementary2D extends AutomataDiscrete2D {
    private int factor;
    private int secondFactor;
    private int thirdFactor;

    @Override
    protected void evolveCellAt(int x, int y) {
        int index = ruleLookupTable.length - computeNeighbourhoodCode(x, y) - 1;
        cellsTemp[x][y] = (index >= 0) && (index < ruleLookupTable.length) ? ruleLookupTable[index] : 0;
    }

    @Override
    protected void setRadius(int radius) {
        super.setRadius(radius);
        factor = (int) Math.pow(states, numberOfNeighbours);
        secondFactor = factor / states;
        thirdFactor = (int) Math.pow(factor, numberOfNeighbours - 1);
    }

    @Override
    protected void setRule(String rule) throws IllegalArgumentException {
        char[] ruleCharacters = (new BigInteger(rule)).toString(states).toCharArray();
        ruleLookupTable = new int[ruleCharacters.length];

        for (int i = 0; i < ruleCharacters.length; i++) {
            if (Character.isDigit(ruleCharacters[i])) {
                ruleLookupTable[i] = ruleCharacters[i] - '0';
            } else {
                this.ruleLookupTable[i] = ruleCharacters[i] - 'W';
            }
        }
    }

    private int computeNeighbourhoodCode(int x, int y) {
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
            newY = getWrappedIndex(y - radius - 1, height);
            newYAux = getWrappedIndex(y + radius, height);
            for (int i = -radius; i <= radius; i++) {
                newX = getWrappedIndex(x + i, width);
                bRes += exp * (cells[newX][newY]);
                exp *= factor;
                bSum += secondExp * (cells[newX][newYAux]);
                secondExp *= factor;
            }
            code = (neighbourhoodCode[x][y - 1] - bRes) / 2 + bSum;
        } else if (x != 0) {
            newX = getWrappedIndex(x - radius - 1, width);
            newXAux = getWrappedIndex(x + radius, width);

            for (int i = -radius; i <= radius; i++) {
                newY = getWrappedIndex(i, height);
                bRes += exp * (cells[newX][newY]);
                bSum += exp * (cells[newXAux][newY]);
                exp *= states;
            }
            code = (neighbourhoodCode[x - 1][y] - bRes) / factor + bSum * thirdFactor;
        } else {
            for (int i = -radius; i <= radius; i++) {
                newX = getWrappedIndex(i, width);
                for (int j = -radius; j <= radius; j++) {
                    newY = getWrappedIndex(j, height);
                    code += exp * (cells[newX][newY]);
                    exp *= states;
                }
            }

        }
        neighbourhoodCode[x][y] = code;
        return code;
    }
}