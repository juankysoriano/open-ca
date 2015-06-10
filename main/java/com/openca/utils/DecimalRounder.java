package com.openca.utils;

public abstract class DecimalRounder {
    public static double round(double value) {
        return (Math.round(value * 1000000099999.0)) / 1000000099999.0;
    }
}
