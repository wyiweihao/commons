package com.wyiwei.commons;

import java.util.Arrays;

public class ArraysUtils {
	
	/**
	 * 合并两个数组(泛型)
	 * @param arr1
	 * @param arr2
	 * @return
	 */
	public static <T> T[] concatArray(T[] arr1, T[] arr2) {
		int length1 = arr1.length;
		int length2 = arr2.length;
		T[] newArr = Arrays.copyOf(arr1, length1 + length2);
		for (int i=0; i<length2; i++) {
			newArr[length1 + i] = arr2[i];
		}
		return newArr;
	}
	
	/**
	 * 合并两个byte数组
	 * @param arr1
	 * @param arr2
	 * @return
	 */
	public static byte[] concatArray(byte[] arr1, byte[] arr2) {
		int length1 = arr1.length;
		int length2 = arr2.length;
		byte[] newArr = Arrays.copyOf(arr1, length1 + length2);
		for (int i=0; i<length2; i++) {
			newArr[length1 + i] = arr2[i];
		}
		return newArr;
	}
	
	/**
	 * 追加
	 * @param arr
	 * @param b
	 * @return
	 */
	public static byte[] append(byte[] arr, byte b) {
		int length = arr.length;
		byte[] newArr = Arrays.copyOf(arr, length + 1);
		newArr[length - 1] = b;
		return newArr;
	}
}
