package com.hiair.hom.ibes.webservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.hiair.hom.ibes.ws.client.sample.HelloWorldClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
/**
 * cf) WebEnvironment.DEFINED_PORT 
 * -> By default, the embedded HTTP server will be started on a random port. As we
 *  have defined the URL which the client needs to call with a specific port
 *  number, we need to set the DEFINED_PORT web environment. This will cause
 * 	Spring to use the 'server.port' property from the application.properties file
 * 	instead of a random one.
 */
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class SpringCxfApplicationTests {
	
	static {
        System.setProperty("log.name", "testLog");
    }
	
	@Autowired
	private HelloWorldClient helloWorldClient;
	
	@Test
	public void test() {
		log.debug(helloWorldClient.sayHello("jeongha", "park"));
	}
}
