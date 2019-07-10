package com.hiair.hom.ibes.ws.soap;

import com.hiair.hom.ibes.comn.utils.XmlUtils;
import com.hiair.hom.ibes.ws.server.sample.domain.Greeting;
import com.hiair.hom.ibes.ws.server.sample.domain.Person;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SoapTest {
	
	private static SoapService<Person, Greeting> SERIVCE = new SoapService<Person, Greeting>(
			Person.class, Greeting.class);
	
	public static Greeting execute(Person req) throws Exception {

		log.debug(XmlUtils.toXml(req.getClass(), req));

		Greeting res;
		res = SERIVCE.getResponse(req);

		log.debug(XmlUtils.toXml(res.getClass(), res));

		return res;
	}
	
	public static void main(String[] args) {
		try {
			
			Person person = new Person();
			person.setFirstName("박");
			person.setLastName("정하");
			
			Greeting res = execute(person);
			System.out.println(res);
			
		} catch (Exception e) {
			// TODOJ Auto-generated catch block
			e.printStackTrace();
		}
	}
}
