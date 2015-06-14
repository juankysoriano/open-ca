package com.openca.uni.continous;

import com.openca.utils.DecimalRounder;

import java.util.Arrays;

public class AutomataTotalContinous1D extends AutomataContinous1D {

    /**
     * factor to apply on neighbourhood.
     */
    double ruleFactor;

    /**
     * offset to be sum to neighbourhood
     */
    double ruleOffset;

    @Override
    protected void evolveCellAt(int index) {
        double value = computeNeighbourhoodCodeFor(index) * ruleFactor + ruleOffset;
        if ((int) value >= states - 1) {
            tempCells[index] = value - (int) value;
        } else {
            tempCells[index] = value;
        }

    }

    @Override
    public void setRadius(int radius) {
        super.setRadius(radius);
    }

    @Override
    public void setRule(String rule) {
        String[] reg = new String[2];
        Arrays.fill(reg, "");

        int index = 0;

        for (int i = 0; i < rule.length(); i++) {
            if (rule.charAt(i) == '-') {
                if (i + 1 < rule.length()) {
                    index++;
                }
            } else {
                reg[index] = reg[index].concat((new Character(rule.charAt(i))).toString());
            }
        }

        ruleFactor = Double.parseDouble(reg[0]) / numberOfNeighbours;
        ruleOffset = Double.parseDouble(reg[1]);

    }

    protected double computeNeighbourhoodCodeFor(int index) {
        int newX;
        double code = 0;
        if (index == 0) {
            for (int i = -radius; i <= radius; i++) {
                newX = getWrappedIndex(i, height);
                code += cells[newX];
                code = DecimalRounder.round(code);

            }
        } else {
            newX = getWrappedIndex(index - radius - 1, width);
            double bRes = cells[newX];
            newX = getWrappedIndex(index + radius, width);
            double bSum = cells[newX];
            code = DecimalRounder.round(neighbourCode[index - 1] - DecimalRounder.round(bRes) + DecimalRounder.round(bSum));
        }
        neighbourCode[index] = code;
        return code;
    }

}
