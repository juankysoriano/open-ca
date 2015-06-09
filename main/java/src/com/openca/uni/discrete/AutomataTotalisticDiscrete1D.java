package com.openca.uni.discrete;

import java.math.BigInteger;

public class AutomataTotalisticDiscrete1D extends AutomataDiscrete1D {
    public AutomataTotalisticDiscrete1D(byte[] cells) {
        super(cells);
    }

    @Override
    protected void evolveCellAt(int indice) {
        int index = ruleLookupTable.length - computeNeighbourhoodCodeFor(indice) - 1;
        tempCells[indice] = index >= 0 ? ruleLookupTable[index] : 0;
    }

    @Override
    public String getMaxRule() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setRule(String rule) throws Exception {
        char[] ruleCharacters = (new BigInteger(rule)).toString(states).toCharArray();
        ruleLookupTable = new byte[ruleCharacters.length];

        for (int i = 0; i < ruleCharacters.length; i++) {
            ruleLookupTable[i] = (byte) (ruleCharacters[i] - '0');
        }
    }

    protected int computeNeighbourhoodCodeFor(int index) {
        int newX;
        int code = 0;
        int implied = numberOfNeighbours - radius;

        if (index == 0) {
            for (int i = -radius; i < implied; i++) {
                newX = getWrappedIndex(i);
                code += (cells[newX]);
            }
        } else {
            int bRes = 0;
            int bSum = 0;
            newX = getWrappedIndex(index - radius - 1);
            bRes += (cells[newX]);
            newX = getWrappedIndex(index + radius);
            bSum += (cells[newX]);
            code = (neighbourhoodCode[index - 1] - bRes + bSum);
        }
        neighbourhoodCode[index] = code;
        return code;
    }

}