package com.gop.util;

public class SequenceUtil {

	private final static Sequence sequence = new Sequence();

	public static String getNextId() {
		return "" + sequence.nextId();
	}
	public static long getNextId(int table) {
		String  orderNo=getNextId();

		return Long.parseLong(orderNo.substring(0, orderNo.length()-1)+table);
	}

	}