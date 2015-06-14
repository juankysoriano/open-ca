package com.openca.uni.discrete;

import java.math.BigInteger;
import java.util.Arrays;

public class AutomataDiscreteOuterTotalisticDiscrete1D extends AutomataDiscrete1D {
    private int ruleLookUpTable[][];

    @Override
    protected void evolveCellAt(int index) {
        int cell = cells[index];
        int neighboursCode = computeNeighbourhoodCodeFor(index);
        tempCells[index] = neighboursCode >= 0 ? ruleLookUpTable[cell][neighboursCode] : 0;
    }

    //I think I was drunk when I wrote this code.
    @Override
    public void setRule(String rule) throws IllegalArgumentException {
        int longitude = numberOfNeighbours * (states - 1);
        ruleLookUpTable = new int[states][longitude + 1];

        String[] rules = new String[states];

        Arrays.fill(rules, "");
        try {
            for (int i = 0; i < states; i++) {
                for (int j = 0; j < longitude; j++) {
                    ruleLookUpTable[i][j] = 0;
                }
            }

            int index = 0;
            for (int i = 0; i < rule.length(); i++) {
                if (rule.charAt(i) == '-') {
                    if (i + 1 < rule.length()) {
                        index++;
                    }
                } else {
                    rules[index] = rules[index].concat(String.valueOf(rule.charAt(i)));
                }
            }

            for (int i = 0; i < states; i++) {
                String aux = (new BigInteger(rules[i])).toString(states);
                for (int j = 0; j <= longitude; j++) {
                    if (j < aux.length()) {
                        ruleLookUpTable[i][aux.length() - 1 - j] = (byte) (aux.charAt(j) - '0');
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            throw (e);
        }
    }

    // And probably here I was in a very high delirium state
    protected int computeNeighbourhoodCodeFor(int index) {
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

}
