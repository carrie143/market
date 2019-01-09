//package com.gop.currency.withdraw.gateway.service.ulpay.cert;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.URL;
//import java.security.KeyStore;
//import java.security.KeyStoreException;
//import java.security.NoSuchAlgorithmException;
//import java.security.NoSuchProviderException;
//import java.security.PrivateKey;
//import java.security.UnrecoverableKeyException;
//import java.security.cert.CertificateException;
//import java.security.cert.X509Certificate;
//import java.util.Enumeration;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.gop.currency.withdraw.gateway.service.ulpay.configure.Configurer;
//import com.itrus.cryptorole.CryptoException;
//import com.itrus.cryptorole.NotSupportException;
//import com.itrus.cryptorole.Recipient;
//import com.itrus.cryptorole.Sender;
//import com.itrus.cryptorole.bc.RecipientBcImpl;
//import com.itrus.cryptorole.bc.SenderBcImpl;
//import com.itrus.cvm.CVM;
//import com.itrus.util.Base64;
//
///**
// * @ClassName: UlpayRaTools
// * @Description: 签名验签工具类
// * @author lihr
// * @modify hanmy001@ulic.com.cn
// * @date 2015年8月7日 下午5:38:22
// */
//public class UlpayRaTools {
//
//	public static final String RET_CODE = "retCode";
//	public static final String RET_MSG = "retMsg";
//	public static final String CA_SERIAL_NUMBER = "ca_serial_number";
//	public static final String SUCCESS = "success";
//	public static final String FAILED = "failed";
//
//	public String certPath;
//	public String certPass;
//	public String cvmPath;
//	/**
//	 * 加密字符编码
//	 */
//	private static final String ENCRYPT_CHARSET = "UTF-8";
//
//	private static final Logger LOG = LoggerFactory.getLogger(UlpayRaTools.class);
//
//	private static UlpayRaTools tools = null;
//
//	private UlpayRaTools() {
//
//		// String prePath =
//		// this.getClass().getClassLoader().getResource("").getPath();
//	    String prePath = System.getProperty("user.dir") + "/conf/";
//		/**
//		 * 证书路径
//		 */
//
//		certPath = "conf/"+Configurer.EntCertPath;
//		/**
//		 * 证书密码
//		 */
//		certPass = Configurer.CertPass;
//
//		/**
//		 * 获取根根证书的配置文件及证书吊销列表信息
//		 */
//		cvmPath = prePath + Configurer.CvmPath;
//
//	}
//
//	public static final UlpayRaTools getInstance() {
//		if (null == tools) {
//			synchronized (UlpayRaTools.class) {
//				if (null == tools) {
//					tools = new UlpayRaTools();
//				}
//			}
//		}
//		return tools;
//	}
//
//	/**
//	 * 签名方法
//	 *
//	 * @param oriData原文信息
//	 * @return 签名结果
//	 */
//	public String sign(String oriData) {
//		String signData = "";
//		if (null == certPath || "".equals(certPath)) {
//			throw new IllegalArgumentException("EntCertPath IS NOT NULL");
//		}
//		if (null == certPass || "".equals(certPass)) {
//			throw new IllegalArgumentException("CertPass IS NOT NULL");
//		}
////		Sender s = new SenderBcImpl();
//		Sender s = null;
//		try {
//			s=initCertWithKey(certPath, certPass.toCharArray());
//			byte[] p7dtach = s.signMessage(oriData.getBytes(ENCRYPT_CHARSET));
//			signData = Base64.encode(p7dtach);
//		} catch (Exception e) {
//			LOG.error("数据签名失败：", e);
//			return null;
//		}
//		return signData;
//	}
//
//	private Sender initCertWithKey(String pfxFileName, char[] keyPassword) throws NotSupportException, CryptoException {
//		Sender s = new SenderBcImpl();
//		try {
//			String preString = UlpayRaTools.class.getClassLoader().getResource("").getPath();
//			File file = new File(preString+pfxFileName);
//			LOG.info("证书文件路径为 : "+preString+pfxFileName);
//			InputStream fis = null;
//			if (file.exists()) {
//				fis = new FileInputStream(pfxFileName);
//			} else {
//				Class<? extends UlpayRaTools> clz = UlpayRaTools.class;
//				ClassLoader cl = clz.getClassLoader();
//
//				URL url = cl.getResource(pfxFileName);
//				if (url == null)
//					throw new FileNotFoundException();
//
//				fis = cl.getResourceAsStream(pfxFileName);
//			}
//
//			// InputStream fis = new FileInputStream(pfxFileName);
//			KeyStore keyStore = KeyStore.getInstance("PKCS12", "BC");
//			keyStore.load(fis, keyPassword);
//			fis.close();
//			Enumeration enums = keyStore.aliases();
//			while (enums.hasMoreElements()) {
//				String keyAlias = (String) enums.nextElement();
//				if (keyStore.isKeyEntry(keyAlias)) {
//					s.initCertWithKey((PrivateKey) keyStore.getKey(keyAlias, keyPassword),
//							(X509Certificate) keyStore.getCertificate(keyAlias));
//				}
//			}
//			return s;
//		} catch (FileNotFoundException e) {
//			throw new CryptoException(e);
//		} catch (KeyStoreException e) {
//			throw new CryptoException(e);
//		} catch (NoSuchAlgorithmException e) {
//			throw new CryptoException(e);
//		} catch (CertificateException e) {
//			throw new CryptoException(e);
//		} catch (IOException e) {
//			throw new CryptoException(e);
//		} catch (UnrecoverableKeyException e) {
//			throw new CryptoException(e);
//		} catch (NoSuchProviderException e) {
//			throw new CryptoException(e);
//		}
//	}
//
//	/**
//	 * 验签方法
//	 *
//	 * @param signData签名结果
//	 * @param oriData原文信息
//	 */
//	public Map<String, String> verify(String signData, String oriData) {
//
//		if (cvmPath == null || "".equals(cvmPath)) {
//			throw new IllegalArgumentException("CertPass IS NOT NULL");
//		}
//		Recipient r = new RecipientBcImpl();
//		Map<String, String> res = new HashMap<String, String>();
//		String status = FAILED;
//		String resInfo = "未知";
//		try {
//			/**
//			 * 验证签名结果有效性
//			 */
//			X509Certificate signer = r.verifySignature(oriData.getBytes(ENCRYPT_CHARSET), Base64.decode(signData));
//
//			com.itrus.cert.X509Certificate itrusCert = com.itrus.cert.X509Certificate.getInstance(signer);
//			/**
//			 * 获取证书商户编号与证书用途
//			 */
//			String icaSerialNumber = itrusCert.getICASerialNumber();
//
//			LOG.debug("证书编号为【{}】", icaSerialNumber);
//			res.put(CA_SERIAL_NUMBER, icaSerialNumber);
//			/**
//			 * 初始化证书校验信息
//			 */
//			String preString = UlpayRaTools.class.getClassLoader().getResource("").getPath();
//			LOG.info("证书验签xml文件路径为 : "+preString+"conf/"+Configurer.CvmPath);
////			CVM.config(preString+"conf/"+Configurer.CvmPath);
//			CVM.config("conf/"+Configurer.CvmPath);
//			/**
//			 * 校验证书的颁发机构
//			 */
//			int ret = CVM.verifyCertificate(itrusCert);
//
//			if (ret != CVM.VALID) {
//				switch (ret) {
//				case CVM.CVM_INIT_ERROR:
//					resInfo = "CVM初始化错误，请检查配置文件或给CVM增加支持的CA。";
//					break;
//				case CVM.CRL_UNAVAILABLE:
//					resInfo = "CRL不可用，未知状态。";
//					break;
//				case CVM.EXPIRED:
//					resInfo = "证书已过期。";
//					break;
//				case CVM.ILLEGAL_ISSUER:
//					resInfo = "非法颁发者。";
//					break;
//				case CVM.REVOKED:
//					resInfo = "证书已吊销。";
//					break;
//				case CVM.UNKNOWN_ISSUER:
//					resInfo = "不支持的颁发者。请检查cvm.xml配置文件";
//					break;
//				case CVM.REVOKED_AND_EXPIRED:
//					resInfo = "证书被吊销且已过期。";
//					break;
//				default:
//					resInfo = "验证证书出现未知错误，请联系证书管理人员。";
//				}
//			} else {
//				status = SUCCESS;
//				resInfo = "当前签名证书是有效的！";
//			}
//		} catch (Exception e) {
//			LOG.info("验证报文信息失败。", e);
//			res.put(FAILED, "验签异常");
//		}
//		res.put(RET_CODE, status);
//		res.put(RET_MSG, resInfo);
//		LOG.info("验签结果==>{}", res);
//		return res;
//	}
//	// public static void main(String[] args) {
//	// UlpayRaTools tools = new UlpayRaTools();
//	// System.out.println(tools.certPath);
//	// File file = new File(tools.certPath);
//	// System.out.println(file.exists());
//	// }
//}
