package com.openca.uni.discrete;

import java.math.BigInteger;

public class AutomataElementaryDiscrete1D extends AutomataDiscrete1D {

    /**
     * Used to calculate the neighbourhood code for a cell
     */
    private int scaleFactor;

    @Override
    protected void evolveCellAt(int index) {
        int neighbourhoodCode = ruleLookupTable.length - computeNeighbourhoodCodeFor(index) - 1;
        tempCells[index] = neighbourhoodCode >= 0 ? ruleLookupTable[neighbourhoodCode] : 0;
    }

    private int computeNeighbourhoodCodeFor(int index) {
        int exponential = scaleFactor;
        int newX;
        int code = 0;
        if (index == 0) {
            for (int i = -radius; i <= radius; i++) {
                newX = getWrappedIndex(i);
                code += exponential * (cells[newX]);
                exponential /= states;
            }
        } else {
            newX = getWrappedIndex(index - radius - 1);
            int bRes = scaleFactor * (cells[newX]);
            newX = getWrappedIndex(index + radius);
            int bSum = (cells[newX]);
            code = (neighbourhoodCode[index - 1] - bRes) * states + bSum;
        }
        neighbourhoodCode[index] = code;
        return code;
    }

    @Override
    protected void setRadius(int radius) {
        super.setRadius(radius);
        scaleFactor = (int) Math.pow(states, numberOfNeighbours - 1);
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

}
