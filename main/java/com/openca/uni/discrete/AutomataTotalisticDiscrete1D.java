package com.openca.uni.discrete;

import java.math.BigInteger;

public class AutomataTotalisticDiscrete1D extends AutomataDiscrete1D {

    @Override
    protected void evolveCellAt(int cellIndex) {
        int index = ruleLookupTable.length - computeNeighbourhoodCodeFor(cellIndex) - 1;
        tempCells[cellIndex] = index >= 0 ? ruleLookupTable[index] : 0;
    }

    private int computeNeighbourhoodCodeFor(int index) {
        int newX;
        int code = 0;
        int implied = numberOfNeighbours - radius;

        if (index == 0) {
            for (int i = -radius; i < implied; i++) {
                newX = getWrappedIndex(i, width);
                code += (cells[newX]);
            }
        } else {
            int bRes = 0;
            int bSum = 0;
            newX = getWrappedIndex(index - radius - 1, width);
            bRes += (cells[newX]);
            newX = getWrappedIndex(index + radius, width);
            bSum += (cells[newX]);
            code = (neighbourhoodCode[index - 1] - bRes + bSum);
        }
        neighbourhoodCode[index] = code;
        return code;
    }

    @Override
    protected void setRule(String rule) throws IllegalArgumentException {
        char[] ruleCharacters = (new BigInteger(rule)).toString(states).toCharArray();
        ruleLookupTable = new int[ruleCharacters.length];

        for (int i = 0; i < ruleCharacters.length; i++) {
            ruleLookupTable[i] = ruleCharacters[i] - '0';
        }
    }

}