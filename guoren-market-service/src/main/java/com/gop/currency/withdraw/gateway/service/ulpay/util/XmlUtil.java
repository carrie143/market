package com.gop.currency.withdraw.gateway.service.ulpay.util;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XmlUtil {
	
	/**
	 * 对象转化XML
	 * 
	 * @param obj
	 * @return
	 */
	public static String toXML(Object obj) throws Exception {
		JAXBContext context = JAXBContext.newInstance(obj.getClass());
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "GBK");// //编码格式
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);// 是否格式化生成的xml串
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);// 是否省略xm头声明信息
		StringWriter writer = new StringWriter();
		marshaller.marshal(obj, writer);
		String xml = writer.toString();
		return xml.replaceFirst(" standalone=\"yes\"", "");
	}

	/**
	 * XML转化为对象
	 * 
	 * @param xml
	 * @param valueType
	 * @return
	 * @throws JAXBException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromXML(String xml, Class<T> valueType) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(valueType);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		return (T) unmarshaller.unmarshal(new StringReader(xml));
	}

}
