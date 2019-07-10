package com.hiair.hom.ibes.ws.server.sample.service;

import com.hiair.hom.ibes.ws.server.sample.domain.Greeting;
import com.hiair.hom.ibes.ws.server.sample.domain.ObjectFactory;
import com.hiair.hom.ibes.ws.server.sample.domain.Person;

public class HelloWorldImpl implements HelloWorldPortType {

	@Override
	public Greeting sayHello(Person request) {
		String greeting = "Hello " + request.getFirstName() + " " + request.getLastName() + "~~~~!";

		ObjectFactory factory = new ObjectFactory();
		
		Greeting response = factory.createGreeting();
		response.setGreeting(greeting);

		return response;
	}

}
