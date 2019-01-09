//package com.gop.util;
//
//import org.bouncycastle.crypto.digests.RIPEMD160Digest;
//import org.bouncycastle.util.Arrays;
//
//public class GopAddressUtils{
//	public static boolean isValidAddress(String address){
//		byte[] decodeAddress = Base58.decode(address);
//
//		if(decodeAddress.length != 24){
//			return false;
//		}
//
//		byte[] decodeAddress20 = copyOfRange(decodeAddress, 0, 20);
//		byte[] decodeAddress4 = copyOfRange(decodeAddress, 20, 24);
//		RIPEMD160Digest ripemd160 = new RIPEMD160Digest();
//		ripemd160.update (decodeAddress20, 0, decodeAddress20.length);
//        byte[] digest = new byte[ripemd160.getDigestSize()];
//        ripemd160.doFinal (digest, 0);
//
//        byte[] digest4 = copyOfRange(digest, 0, 4);
//
//		return Arrays.areEqual(decodeAddress4, digest4);
//	}
//
//	private static byte[] copyOfRange(byte[] source, int from, int to) {
//		byte[] range = new byte[to - from];
//		System.arraycopy(source, from, range, 0, range.length);
//
//		return range;
//	}
//
//	public static String getSystemGopAddress(){
//		return ResourceUtils.get("common", "GuoMarket_Address");
//	}
//
//	public static void main(String[] args) {
//		String wallet = null;//"EMCR3qYh3gGAWwPMJWyV2GqPqTgVveB3f";
//		String address = null;//"CjXkC8cNG8LLaUQ9npBsAd2aPrtKYgypL";
//		String fromAddress = "ACTFY2mcDJSryvNi8BMe3FAHiPgca5xKKZM5fffffffffffffffffffffffffffffff1";
//		String toAddress = "ACTFY2mcDJSryvNi8BMe3FAHiPgca5xKKZM5fffffffffffffffffffffffffffffff1";
////		String wallet = "GOPCjXkC8cNG8LLaUQ9npBsAd2aPrtKYgypL";
//		if (fromAddress.length() <= 36) {
//			wallet = fromAddress.substring(3);
//		} else {
//			wallet = fromAddress.substring(3, fromAddress.length() - 32);
//		}
//		if (toAddress.length() <= 36) {
//			address = toAddress.substring(3);
//		} else {
//			address = toAddress.substring(3, toAddress.length() - 32);
//		}
//
//		System.out.println(isValidAddress(address));
//		System.out.println(address.length());
//
//		System.out.println(isValidAddress(wallet));
//		System.out.println(wallet.length());
//	}
//}
//
