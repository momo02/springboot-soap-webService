package com.hiair.hom.ibes.ws.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.codenotfound.types.helloworld.Greeting;
import com.codenotfound.types.helloworld.Person;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestServiceTest {
	static {
        System.setProperty("log.name", "testLog");
    }
	
	@Autowired
	TestService testService;
	
	@Test
	public void test() {
		Person req = new Person();
		req.setFirstName("준성");
		req.setLastName("김");
		
		Greeting res = testService.execute(req);
		
		log.debug(">>>>> rs : " + res.getGreeting());
	}

}
