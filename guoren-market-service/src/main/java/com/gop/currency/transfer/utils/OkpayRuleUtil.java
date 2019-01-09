package com.gop.currency.transfer.utils;

import java.util.ArrayList;
import java.util.List;

public class OkpayRuleUtil {

	private static String pattren1 = "\\d+-\\d+";
	private static String pattren2 = "\\d+";

	private static boolean checkSingleRule(String rule) {

		if (rule.matches(pattren1) || rule.matches(pattren2))
			return true;
		return false;
	}

	public static boolean checkRule(String rule) {

		if (null == rule || "" == rule.trim())
			return false;
		String[] rules = rule.split(",");
		for (String s : rules) {
			if (!checkSingleRule(s))
				return false;
		}
		return true;
	}

	public static List<Entry> parasRule(String rule) {

		if (!checkRule(rule))
			return null;
		String[] rules = rule.split(",");
		List<Entry> lst = new ArrayList<>();
		for (String r : rules) {

			String[] pair = r.split("-");

			if (pair.length == 2) {
			}
			Entry entry = new Entry();
			if (pair.length == 2) {
				entry.setKey(Integer.valueOf(pair[0]));
				entry.setValue(Integer.valueOf(pair[1]));
			} else {
				entry.setKey(Integer.valueOf(pair[0]));
				entry.setValue(Integer.valueOf(pair[0]));
			}
			lst.add(entry);
		}

		return lst;
	}

	public static boolean checkIsConformRule(Integer uid, String rule) {
		if (!checkRule(rule))
			return false;

		List<Entry> lst = parasRule(rule);
		if (null == lst || lst.isEmpty())
			return false;

		for (Entry entry : lst) {
			if (entry.isInside(uid))
				return true;
		}
		return false;
	}

	private static class Entry {

		Integer key;
		Integer value;

		public void setKey(Integer key) {
			this.key = key;
		}

		public void setValue(Integer value) {
			this.value = value;
		}

		public boolean isInside(Integer index) {

			if ((index <= value && index >= key) || (index <= key && index >= value))
				return true;
			return false;
		}
	}
}
