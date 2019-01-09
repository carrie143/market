package com.gop.util;

import java.util.Random;
import java.util.UUID;

/**
 * Created by zhangjinyang on 2018/7/6.
 */
public class IDGenerateUtil {

  public static String generateClientId() {

    StringBuilder clientId = new StringBuilder();
    for (int i = 0; i < 8; i++) {
      clientId.append(new Random().nextInt(10));
    }
    return clientId.toString();

  }

  public static String getUUID() {

    return UUID.randomUUID().toString().replaceAll("-", "");

  }

  public static String generateClientOrderNo() {
    StringBuilder clientId = new StringBuilder();
    for (int i = 0; i < 16; i++) {
      clientId.append(new Random().nextInt(10));
    }
    return clientId.toString();
  }

  public static void main(String[] args) {
    System.out.println(generateClientOrderNo());
  }
}
