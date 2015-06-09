package com.openca.bi.discrete.vonnewman;

import com.openca.bi.discrete.AutomataDiscrete2D;

import java.math.BigInteger;

public class AutomataTotalisticVon2D extends AutomataDiscrete2D {
    public AutomataTotalisticVon2D(byte[][] celulas) {
        super(celulas);
    }

    @Override
    protected void evolveCellAt(int x, int y) {
        int index = ruleLookupTable.length - computeNeighbourhoodCode(x, y) - 1;
        cellsTemp[x][y] = index >= 0 ? ruleLookupTable[index] : 0;
    }

    @Override
    public String getMaxRule() {
        return null;
    }


    @Override
    public void setRule(String rule) throws Exception {
        char[] ruleChars = (new BigInteger(rule)).toString(states).toCharArray();
        ruleLookupTable = new byte[ruleChars.length];

        for (int i = 0; i < ruleChars.length; i++) {
            ruleLookupTable[i] = (byte) (ruleChars[i] - '0');
        }
    }

    protected int computeNeighbourhoodCode(int x, int y) //Calcula el vecindario del que forma parte una clula (Von Neumman)
    {
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
