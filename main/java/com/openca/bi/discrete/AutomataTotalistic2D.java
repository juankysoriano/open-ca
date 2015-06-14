package com.openca.bi.discrete;

import java.math.BigInteger;

public class AutomataTotalistic2D extends AutomataDiscrete2D {

    @Override
    protected void evolveCellAt(int x, int y) {
        int index = ruleLookupTable.length - computeNeighbourhoodCode(x, y) - 1;
        cellsTemp[x][y] = index >= 0 ? ruleLookupTable[index] : 0;

    }

    @Override
    protected void setRule(String rule) throws IllegalArgumentException {
        char[] ruleCharacters = (new BigInteger(rule)).toString(states).toCharArray();
        ruleLookupTable = new int[ruleCharacters.length];

        for (int i = 0; i < ruleCharacters.length; i++) {
            ruleLookupTable[i] = ruleCharacters[i] - '0';
        }
    }

    private int computeNeighbourhoodCode(int x, int y) {
        int newX;
        int newY;
        int newXAux;
        int newYAux;
        int code;

        if (y != 0) {
            newYAux = getWrappedIndex(y - radius - 1, height);
            newY = getWrappedIndex(y + radius, height);
            code = neighbourhoodCode[x][y - 1];
            for (int i = -radius; i <= radius; i++) {
                newX = getWrappedIndex(x + i, width);
                code += (cells[newX][newY]) - (cells[newX][newYAux]);
            }
        } else if (x != 0) {
            newXAux = getWrappedIndex(x - radius - 1, width);
            newX = getWrappedIndex(x + radius, width);
            code = neighbourhoodCode[x - 1][y];
            for (int i = -radius; i <= radius; i++) {
                newY = getWrappedIndex(i, height);
                code += (cells[newX][newY]) - (cells[newXAux][newY]);
            }

        } else {
            code = 0;
            for (int i = -radius; i <= radius; i++) {
                newY = getWrappedIndex(i, height);
                for (int j = -radius; j <= radius; j++) {
                    newX = getWrappedIndex(j, width);
                    code += cells[newX][newY];
                }
            }
        }
        neighbourhoodCode[x][y] = code;
        return code;
    }
}
