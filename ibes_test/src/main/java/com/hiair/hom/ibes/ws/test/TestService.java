package com.hiair.hom.ibes.ws.test;

import org.springframework.stereotype.Service;

import com.codenotfound.types.helloworld.Greeting;
import com.codenotfound.types.helloworld.Person;
import com.hiair.hom.ibes.ws.soap.SoapService;

@Service
public class TestService {
	
	private static SoapService<Person, Greeting> SERIVCE = new SoapService<Person, Greeting>(
			Person.class, Greeting.class);
	
	public static Greeting execute(Person req) {
		
		Greeting res = null;
		try {
			res = SERIVCE.getResponse(req);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
}