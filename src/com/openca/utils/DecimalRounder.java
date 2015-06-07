package com.openca.utils;

/** Devuelve número redondeado a 13 decimales */
public abstract class DecimalRounder
{
	public static double round4(double numero)
	{
		return (Math.round(numero *  1000000099999.0)) / 1000000099999.0;
	}
}
