package com.bisa.cashflow;

import java.util.Random;

public class CashFlowUtil {
	/**
	 * 
	 * @param cadenas
	 * @return
	 */
	public static boolean isNotEmpty(Object... cadenas) {
		if (cadenas == null)
			return false;
		for (Object cadena : cadenas) {
			if (cadena == null)
				return false;
			if (cadena.toString().trim().isEmpty())
				return false;
		}
		return true;
	}

	/**
	 * 
	 * @return
	 */
	public static String randomAccountCode() {
		Random rnd = new Random();
		int number = rnd.nextInt(999999);
		return String.format("%06d", number);
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public static double toDouble(String value) {
		value = value == null ? "" : value.trim();
		double result = 0D;
		if (value.isEmpty())
			return result;
		try {
			result = Double.valueOf(value);
		} catch (Exception e) {
			System.err.println("problemas al convertir el numero " + value);
			result = 0D;
		}
		return result;
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public static long toLong(String value) {
		value = value == null ? "" : value.trim();
		long result = 0;
		if (value.isEmpty())
			return result;
		try {
			result = Long.valueOf(value);
		} catch (Exception e) {
			System.err.println("problemas al convertir el numero " + value);
			result = 0;
		}
		return result;
	}
	
	public static int toInteger(String value) {
		value = value == null ? "" : value.trim();
		int result = 0;
		if (value.isEmpty())
			return result;
		try {
			result = Integer.valueOf(value);
		} catch (Exception e) {
			System.err.println("problemas al convertir el numero " + value);
			result = 0;
		}
		return result;
	}
}
