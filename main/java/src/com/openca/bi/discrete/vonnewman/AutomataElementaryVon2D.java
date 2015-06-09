package com.openca.bi.discrete.vonnewman;

import com.openca.bi.discrete.AutomataDiscrete2D;

import java.math.BigInteger;

public class AutomataElementaryVon2D extends AutomataDiscrete2D {
    private int firstFactor;
    private int secondFactor;

    public AutomataElementaryVon2D(byte[][] celulas) {
        super(celulas);
    }

    @Override
    protected void evolveCellAt(int x, int y) {
        int index = ruleLookupTable.length - computeNeighbourhoodCode(x, y) - 1;
        cellsTemp[x][y] = (index >= 0) && (index < ruleLookupTable.length) ? ruleLookupTable[index] : 0;
    }

    @Override
    public String getMaxRule() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setRadius(int radius) {
        super.setRadius(radius);
        firstFactor = (int) Math.pow(states, radius);
        secondFactor = (int) Math.pow(states, numberOfNeighbours + radius);
    }

    @Override
    public void setRule(String rule) throws Exception {
        char[] ruleChars = (new BigInteger(rule)).toString(states).toCharArray();
        ruleLookupTable = new byte[ruleChars.length];

        for (int i = 0; i < ruleChars.length; i++) {
            if (Character.isDigit(ruleChars[i])) {
                ruleLookupTable[i] = (byte) (ruleChars[i] - '0');
            } else {
                this.ruleLookupTable[i] = (byte) (ruleChars[i] - 'W');
            }
        }
    }

    protected int computeNeighbourhoodCode(int x, int y) //Calcula el vecindario del que forma parte una clula (Von Neumman)
    {
        int code = 0;
        int newX;
        int newY;
        int exp = 1;
        int exp2 = firstFactor;
        int exp3 = secondFactor;
        for (int j = 0; j < numberOfNeighbours; j++) {
            if (j < radius) {
                newX = getWrappedIndex(x - radius + j);
                code += exp * (cells[newX][y]);
                exp *= states;
                newX = getWrappedIndex(x + j + 1);
                code += exp3 * (cells[newX][y]);
                exp3 *= states;
            }
            newY = getWrappedIndex(y - radius + j);
            code += exp2 * (cells[x][newY]);
            exp2 *= states;
        }
        neighbourhoodCode[x][y] = code;
        return code;
    }

}
