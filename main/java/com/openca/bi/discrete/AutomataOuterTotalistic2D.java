package com.openca.bi.discrete;

import java.math.BigInteger;
import java.util.Arrays;

public class AutomataOuterTotalistic2D extends AutomataDiscrete2D {

    private int lookUpTable[][];

    @Override
    protected void evolveCellAt(int x, int y) {
        int cell = cells[x][y];
        int index = computeNeighbourhoodCode(x, y);
        cellsTemp[x][y] = index >= 0 ? lookUpTable[cell][index] : 0;
    }

    @Override
    protected void setRule(String rule) throws IllegalArgumentException {
        int longitude = numberOfNeighbours * numberOfNeighbours * (states - 1);
        lookUpTable = new int[states][longitude + 1];
        String[] reg = new String[states];

        Arrays.fill(reg, "");
        try {
            for (int i = 0; i < states; i++) {
                for (int j = 0; j < longitude; j++) {
                    lookUpTable[i][j] = 0;
                }
            }

            int index = 0;
            for (int i = 0; i < rule.length(); i++) {
                if (rule.charAt(i) == '-') {
                    if (i + 1 < rule.length()) {
                        index++;
                    }
                } else {
                    reg[index] = reg[index].concat(String.valueOf(rule.charAt(i)));
                }
            }

            for (int i = 0; i < states; i++) {
                String aux = (new BigInteger(reg[i])).toString(states);
                for (int j = 0; j <= longitude; j++) {
                    if (j < aux.length()) {
                        lookUpTable[i][aux.length() - 1 - j] = aux.charAt(j) - '0';
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            throw (e);
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
