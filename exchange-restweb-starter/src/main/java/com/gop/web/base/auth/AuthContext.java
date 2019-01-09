package com.gop.web.base.auth;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Properties;

import org.apache.xmlbeans.impl.xsd2inst.SchemaInstanceGenerator;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;

import com.google.common.base.Optional;
import com.gop.code.consts.CommonCodeConst;
import com.gop.exception.AppException;
import com.gop.web.base.model.LoginSession;

import javax.servlet.http.HttpServletRequest;

public class AuthContext {
	private static final String FILED_SEPARATOR = ",";
	private static final String VALUE_SEPARATOR = "=";
	private Properties properties;
	private static final String PAY_PASSWORD = "pay-password";
	private static final String LOGIN_PASSWORD = "login-password";
	private static final String CAPTCHAR_CODE = "captchar-code";
	private static final String SERVICE_CODE = "service-code";
	private static final String USER_ACCOUNT_NO = "account-no";
	private static final String PASSPORT = "passport-no";
	private static final String CAPTCHAR_NO = "captchar-no";
	private static final String GOOGLE_CODE = "google-code";
	private static final String TOKEN = "token";
	private static final String DOWNLOAD = "download";
	private static final String SIGN = "sign";
	private static final String ACCESSKEY = "accessKey";

	private static final String LOGIN = "loginsession";

	NativeWebRequest webRequest;

	public static AuthContext build(String headerStr, NativeWebRequest webRequest) {
		AuthContext authContext = new AuthContext();
		String[] fields = StringUtils.tokenizeToStringArray(headerStr, FILED_SEPARATOR, true, true);

		try {
			authContext.properties = Optional
					.fromNullable(splitArrayElementsIntoProperties(fields, VALUE_SEPARATOR, null)).or(new Properties());
		} catch (UnsupportedEncodingException e) {
			throw new AppException(CommonCodeConst.FIELD_ERROR);
		}

		authContext.webRequest = webRequest;
		return authContext;
	}

	public String getSign() {
		return properties.getProperty(SIGN);
	}

	public String getAccesskey() {
		return properties.getProperty(ACCESSKEY);
	}

	public String getDownload(){
		return properties.getProperty(DOWNLOAD);
	}

	public String getUri(){return webRequest.getNativeRequest(HttpServletRequest.class).getRequestURI();}

	public String getField(String field) {
		return properties.getProperty(field);
	}

	public String getCaptcharCode() {
		return properties.getProperty(CAPTCHAR_CODE);
	}

	public String getcaptcharNo() {
		return properties.getProperty(CAPTCHAR_NO);
	}

	public String getpayPassword() {
		return properties.getProperty(PAY_PASSWORD);
	}

	public String getLoginPassword() {
		return properties.getProperty(LOGIN_PASSWORD);
	}

	public String getServiceCode() {
		return properties.getProperty(SERVICE_CODE);
	}

	public void setUserAccount(String userAccount) {
		properties.setProperty(USER_ACCOUNT_NO, userAccount);
	}

	public String getUserAccount() {
		return properties.getProperty(USER_ACCOUNT_NO);
	}

	public String getpassort() {
		return properties.getProperty(PASSPORT);
	}

	public String getGoogleCode() {
		return properties.getProperty(GOOGLE_CODE);
	}

	public LoginSession getLoginSession() {
		Object o = webRequest.getAttribute(LOGIN, RequestAttributes.SCOPE_REQUEST);
		if (null == o)
			return null;
		return (LoginSession) o;
	}

	public void setLoginSession(LoginSession loginSession) {
		webRequest.setAttribute(LOGIN, loginSession, RequestAttributes.SCOPE_REQUEST);
	}

	public String getToke() {
		return properties.getProperty(TOKEN);
	}

	@Override
	public String toString() {
		return "AuthContext [properties=" + properties + "]";
	}

	public static Properties splitArrayElementsIntoProperties(String[] array, String delimiter, String charsToDelete)
			throws UnsupportedEncodingException {

		if (ObjectUtils.isEmpty(array)) {
			return null;
		}
		Properties result = new Properties();
		for (String element : array) {
			if (charsToDelete != null) {
				element = StringUtils.deleteAny(element, charsToDelete);
			}
			String[] splittedElement = StringUtils.split(element, delimiter);
			if (splittedElement == null) {
				continue;
			}
			result.setProperty(splittedElement[0].trim(), URLDecoder.decode(splittedElement[1].trim(), "UTF-8"));
		}
		return result;
	}

}
