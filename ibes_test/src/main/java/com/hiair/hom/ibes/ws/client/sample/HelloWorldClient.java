package com.hiair.hom.ibes.ws.client.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hiair.hom.ibes.ws.server.sample.domain.Greeting;
import com.hiair.hom.ibes.ws.server.sample.domain.ObjectFactory;
import com.hiair.hom.ibes.ws.server.sample.domain.Person;
import com.hiair.hom.ibes.ws.server.sample.service.HelloWorldPortType;

@Component
public class HelloWorldClient {
	
	  @Autowired
	  private HelloWorldPortType helloWorldProxy;

	  public String sayHello(String firstName, String lastName) {

	    ObjectFactory factory = new ObjectFactory();
	    Person person = factory.createPerson();
	    person.setFirstName(firstName);
	    person.setLastName(lastName);

	    Greeting response = helloWorldProxy.sayHello(person);

	    return response.getGreeting();
	  }
}
