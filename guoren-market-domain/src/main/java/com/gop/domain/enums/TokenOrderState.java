package com.gop.domain.enums;

public enum TokenOrderState {

	WAITING, CANCEL, PAID, SHIPPED, REFUND, COMPLETE;

	public static TokenOrderState getTokenOrderStateByName(String name) {
		switch (name) {
		case "WAITING":
			return WAITING;

		case "CANCEL":
			return CANCEL;

		case "PAID":
			return PAID;

		case "SHIPPED":
			return SHIPPED;

		case "REFUND":
			return REFUND;

		case "COMPLETE":
			return COMPLETE;

		default:
			return null;

		}

	}

}
