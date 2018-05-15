package com.ums.common.util;

import java.math.BigDecimal;

import com.ums.exception.TestException;
public class NumberUtil {
	
	private static final String zeroMsg = "The scale must be a positive integer or zero";
	/*
	 * 提供精确加法运算
	 */
	public static double  add(double num1, double... num2) {
		BigDecimal d1 = new BigDecimal(Double.toString(num1));
		for(int i = 0; i< num2.length; i++) {
			BigDecimal d2 = new BigDecimal(Double.toString(num2[i]));
			d1 = d1.add(d2);
		}
		return d1.doubleValue();
	}
	
	 public static float add(float num1, float num2) {
	        BigDecimal b1 = new BigDecimal(Double.toString(num1));
	        BigDecimal b2 = new BigDecimal(Double.toString(num2));
	        return b1.add(b2).floatValue();
	 }
	
	/**
	 * 提供精确地减法运算
	 */
	
	public static double subduct(double num1, double num2) {
		BigDecimal d1 = new BigDecimal(Double.toString(num1));
		BigDecimal d2 = new BigDecimal(Double.toString(num2));
		return d1.subtract(d2).doubleValue();
	}
	
	public static float subtract(float num1, float num2) {
	        BigDecimal b1 = new BigDecimal(Double.toString(num1));
	        BigDecimal b2 = new BigDecimal(Double.toString(num2));
	        return b1.subtract(b2).floatValue();
	}
	
	/**
	 * 精确的乘法运算
	 */
	
	public static double multiply(double num1, double num2) {
        BigDecimal b1 = new BigDecimal(Double.toString(num1));
        BigDecimal b2 = new BigDecimal(Double.toString(num2));
        return b1.multiply(b2).doubleValue();
    }
	
	public static float multiply(float num1, float num2) {
	        BigDecimal b1 = new BigDecimal(Double.toString(num1));
	        BigDecimal b2 = new BigDecimal(Double.toString(num2));
	        return b1.multiply(b2).floatValue();
	}
	
	/**
	 * 精确的除法运算
	 */
	
	public static double divide(double num1, double num2, int scale) {
        if (scale < 0) {
            throw new TestException(zeroMsg);
        }
        BigDecimal b1 = new BigDecimal(Double.toString(num1));
        BigDecimal b2 = new BigDecimal(Double.toString(num2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
	
	public static float divide(float num1, float num2, int scale) {
        if (scale < 0) {
            throw new TestException(zeroMsg);
        }
        BigDecimal b1 = new BigDecimal(Double.toString(num1));
        BigDecimal b2 = new BigDecimal(Double.toString(num2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }
	
	/**
	 * 精确小数位处理（四舍五入）
	 */
	 public static double round(double num, int scale) {
	        if (scale < 0) {
	            throw new IllegalArgumentException(zeroMsg);
	        }
	        BigDecimal b = new BigDecimal(Double.toString(num));
	        BigDecimal one = new BigDecimal("1");
	        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	   }
	 
	 public static float round(float num, int scale) {
	        if (scale < 0) {
	            throw new IllegalArgumentException(zeroMsg);
	        }
	        BigDecimal b = new BigDecimal(Float.toString(num));
	        BigDecimal one = new BigDecimal("1");
	        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).floatValue();
	  }
}
