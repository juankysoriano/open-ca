package com.openca.bi.discrete.moore;

import com.openca.bi.discrete.AutomataDiscrete2D;

import java.math.BigInteger;
import java.util.Arrays;

public class AutomataOuterTotalistic2D extends AutomataDiscrete2D {

    private byte lookUpTable[][];

    public AutomataOuterTotalistic2D(byte[][] cells) {
        super(cells);
    }

    @Override
    protected void evolveCellAt(int x, int y) {
        byte cell = cells[x][y];
        int index = computeNeighbourhoodCode(x, y);
        cellsTemp[x][y] = index >= 0 ? lookUpTable[cell][index] : 0;

    }

    @Override
    public String getMaxRule() {
        BigInteger state = new BigInteger(Integer.toString(states));
        int longitude = numberOfNeighbours * numberOfNeighbours * (states - 1);
        return (state.pow(longitude + 1).subtract(new BigInteger("1"))).toString();
    }

    @Override
    public void setRule(String rule) throws Exception {
        int longitude = numberOfNeighbours * numberOfNeighbours * (states - 1);
        lookUpTable = new byte[states][longitude + 1];
        String[] reg = new String[states];

        Arrays.fill(reg, "");
        try {
            for (int i = 0; i < states; i++) {
                for (int j = 0; j < longitude; j++) {
                    lookUpTable[i][j] = 0;
                }
            }

            int index = 0;
            for (int i = 0; i < rule.length(); i++)  //Si cumple la condici�n de vida
            {
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
                        lookUpTable[i][aux.length() - 1 - j] = (byte) (aux.charAt(j) - '0');
                    }
                }
            }
        } catch (Exception e) {
            throw (e);
        }
    }

    protected int computeNeighbourhoodCode(int x, int y) {
        int newX;
        int newY;
        int newXAux;
        int newYAux;
        int code;

        if (y != 0) {
            newYAux = getWrappedIndex(y - radius - 1);
            newY = getWrappedIndex(y + radius);
            code = neighbourhoodCode[x][y - 1];
            for (int i = -radius; i <= radius; i++) {
                newX = getWrappedIndex(x + i);
                code += (cells[newX][newY]) - (cells[newX][newYAux]);
            }
        } else if ((y == 0) && (x != 0)) {
            newXAux = getWrappedIndex(x - radius - 1);
            newX = getWrappedIndex(x + radius);
            code = neighbourhoodCode[x - 1][y];
            for (int i = -radius; i <= radius; i++) {
                newY = getWrappedIndex(i);
                code += (cells[newX][newY]) - (cells[newXAux][newY]);
            }
        } else {
            code = 0;
            for (int i = -radius; i <= radius; i++) {
                newY = getWrappedIndex(i);
                for (int j = -radius; j <= radius; j++) {
                    newX = getWrappedIndex(j);
                    code += cells[newX][newY];
                }
            }
        }
        neighbourhoodCode[x][y] = code;
        return code;
    }

}
