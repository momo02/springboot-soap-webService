package com.hiair.hom.ibes.comn.utils;

import java.io.StringWriter;

import javax.xml.bind.Marshaller;

import com.hiair.hom.ibes.comn.JAXBFactory;


public class XmlUtils {
	
	public static String toXml(Class<?> clazz,Object object) throws Exception {
		Marshaller marshaller = JAXBFactory.getIntance(clazz).createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		StringWriter sw = new StringWriter();
		marshaller.marshal(object, sw);
		return sw.toString();
	}
}
