package com.hiair.hom.ibes.comn;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

public class JAXBFactory {
	
	private static Map<Class,JAXBContext> instances = new HashMap<Class,JAXBContext>();

	public static JAXBContext getIntance(Class clazz) throws JAXBException {
		if( !instances.containsKey(clazz)) {
			instances.put(clazz, JAXBContext.newInstance(clazz));
		} 
		return  instances.get(clazz);
	}
}
