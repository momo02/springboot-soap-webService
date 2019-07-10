package com.hiair.hom.ibes.ws.config;

import org.apache.cxf.ext.logging.LoggingInInterceptor;
import org.apache.cxf.ext.logging.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hiair.hom.ibes.ws.server.sample.service.HelloWorldPortType;


@Configuration
public class ClientConfig {
	
	@Value("${client.ticketagent.address}")
	private String address;
	
	@Bean(name="helloWorldProxy")
	public HelloWorldPortType helloWorldProxy() {
		
		// CXF는 지정된 서비스 클래스를 구현하는 웹 서비스 클라이언트를 작성할수 있는 JaxWsProxyFactoryBean 제공
		// CXF provides a JaxWsProxyFactoryBean that will create a Web Service client for you which implements a specified service class.
		JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
		jaxWsProxyFactoryBean.setServiceClass(HelloWorldPortType.class);
		jaxWsProxyFactoryBean.setAddress(address);
		
//		jaxWsProxyFactoryBean.getOutInterceptors().add(loggingOutInterceptor());
//		jaxWsProxyFactoryBean.getInInterceptors().add(loggingInInterceptor());
//		jaxWsProxyFactoryBean.getInFaultInterceptors().add(loggingInInterceptor());
		
		//팩토리(jaxWsProxyFactoryBean)에서 
		//원격 호출을 할 때 사용할 수 있는 JAX-WS proxy를 생성하게하려면 
		//JaxWsProxyFactoryBean에서 create() 메소드를 호출! 
		return (HelloWorldPortType) jaxWsProxyFactoryBean.create();
	}
	
//	@Bean
//	public LoggingOutInterceptor loggingOutInterceptor() {
//		return new LoggingOutInterceptor();
//	}
//	
//	@Bean
//	public LoggingInInterceptor loggingInInterceptor() {
//		return new LoggingInInterceptor();
//	}
	
}
