package com.openca.bi.continous;

import com.openca.utils.DecimalRounder;

import java.util.Arrays;
import java.util.Random;

public class AutomataTotalisticContinous2DMoore extends AutomataContinous2D {
    private double ruleFactor;

    private double ruleOffset;

    @Override
    protected void evolveCellAt(int x, int y) {

        double value = computeNeighbourhoodCode(x, y) * ruleFactor + ruleOffset;
        if ((int) value >= states - 1) {
            tempCells[x][y] = value - (int) value;
        } else {
            tempCells[x][y] = value;
        }
    }

    @Override
    public void randomiseConfiguration() {
        Random rand = new Random();
        boolean hasToBeSymmetric = rand.nextInt(2) == 0;

        if (hasToBeSymmetric) {
            prepareSymmetricConfiguration();
        } else {
            prepareRandomConfiguration();
        }
    }

    private void prepareRandomConfiguration() {
        Random rand = new Random();
        int density = rand.nextInt(101);

        for (int i = 0; i < size / 2; i++) {
            for (int j = 0; j < size / 2; j++) {
                cells[i][j] = 0;
                cells[i][size - 1 - j] = 0;
                cells[size - 1 - i][j] = 0;
                cells[size - 1 - i][size - 1 - j] = 0;
                int value = rand.nextInt(101);
                if (value < density) {
                    byte state = (byte) rand.nextInt(states);
                    cells[i][j] = state;
                    cells[i][size - 1 - j] = state;
                    cells[size - 1 - i][j] = state;
                    cells[size - 1 - i][size - 1 - j] = state;
                }
            }

        }
    }

    private void prepareSymmetricConfiguration() {
        Random rand = new Random();
        int density = rand.nextInt(101);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = 0;
                int value = rand.nextInt(101);
                if (value < density) {
                    cells[i][j] = rand.nextInt(states);
                }
            }

        }
    }

    @Override
    protected void setRule(String rule) {
        String[] reg = new String[2];
        Arrays.fill(reg, "");

        int index = 0;

        for (int i = 0; i < rule.length(); i++) {
            if (rule.charAt(i) == '-') {
                if (i + 1 < rule.length()) {
                    index++;
                }
            } else {
                reg[index] = reg[index].concat((Character.valueOf(rule.charAt(i))).toString());
            }
        }

        ruleFactor = Double.parseDouble(reg[0]) / Math.pow(numberOfNeighbours, 2);

        ruleOffset = Double.parseDouble(reg[1]);

    }

    protected double computeNeighbourhoodCode(int x, int y) {
        int newX;
        int newY;
        int newXAux;
        int newYAux;
        double code;

        if (y != 0) {
            newYAux = getWrappedIndex(y - radius - 1);
            newY = getWrappedIndex(y + radius);
            code = neighbourhoodCode[x][y - 1];
            for (int i = -radius; i <= radius; i++) {
                newX = getWrappedIndex(x + i);
                code += DecimalRounder.round(DecimalRounder.round(cells[newX][newY]) - DecimalRounder.round(cells[newX][newYAux]));
                code = DecimalRounder.round(code);
            }
        } else if (x != 0) {
            newXAux = getWrappedIndex(x - radius - 1);
            newX = getWrappedIndex(x + radius);
            code = neighbourhoodCode[x - 1][y];
            for (int i = -radius; i <= radius; i++) {
                newY = getWrappedIndex(i);
                code += DecimalRounder.round(DecimalRounder.round(cells[newX][newY]) - DecimalRounder.round(cells[newXAux][newY]));
                code = DecimalRounder.round(code);

            }

        } else {
            code = 0;
            for (int i = -radius; i <= radius; i++) {
                newY = getWrappedIndex(i);
                for (int j = -radius; j <= radius; j++) {
                    newX = getWrappedIndex(j);
                    code += cells[newX][newY];
                    code = DecimalRounder.round(code);
                }
            }
        }
        neighbourhoodCode[x][y] = code;
        return code;
    }

    @Override
    public void clear() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells.length; j++) {
                cells[i][j] = 0;
            }
        }

        int half = (int) Math.floor(getSize() / 2);

        getCells()[half][half] = 1;
        getCells()[half - 1][half - 1] = 1;
        getCells()[half - 1][half] = 1;
        getCells()[half][half - 1] = 1;
        getCells()[half + 1][half] = 1;
        getCells()[half + 1][half + 1] = 1;
        getCells()[half + 1][half - 1] = 1;
        getCells()[half - 1][half + 1] = 1;
        getCells()[half][half + 1] = 1;
    }
}
