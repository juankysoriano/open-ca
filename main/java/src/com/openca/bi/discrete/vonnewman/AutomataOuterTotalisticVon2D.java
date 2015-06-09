package com.openca.bi.discrete.vonnewman;

import com.openca.bi.discrete.AutomataDiscrete2D;

import java.math.BigInteger;
import java.util.Arrays;

public class AutomataOuterTotalisticVon2D extends AutomataDiscrete2D {
    private byte lookUpTable[][];

    public AutomataOuterTotalisticVon2D(byte[][] celulas) {
        super(celulas);
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
        int longitude = (numberOfNeighbours * 2 - 1) * (states - 1);
        return (state.pow(longitude + 1).subtract(new BigInteger("1"))).toString();
    }


    @Override
    public void setRule(String rule) throws Exception {
        int longitude = (numberOfNeighbours * 2 - 1) * (states - 1);
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
        int code = 0;
        for (int j = 0; j < numberOfNeighbours; j++) {
            if (j < radius) {
                newX = getWrappedIndex(x - radius + j);
                code += (cells[newX][y]);
                newX = getWrappedIndex(x + j + 1);
                code += (cells[newX][y]);
            }
            newY = getWrappedIndex(y - radius + j);
            code += (cells[x][newY]);
        }

        neighbourhoodCode[x][y] = code;
        return code;
    }
}
