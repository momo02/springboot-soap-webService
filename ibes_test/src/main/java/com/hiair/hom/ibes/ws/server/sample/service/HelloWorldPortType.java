package com.hiair.hom.ibes.ws.server.sample.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.hiair.hom.ibes.ws.server.sample.domain.Greeting;
import com.hiair.hom.ibes.ws.server.sample.domain.ObjectFactory;
import com.hiair.hom.ibes.ws.server.sample.domain.Person;

/**
 * This class was generated by Apache CXF 3.2.1
 * 2019-07-10T16:35:46.904+09:00
 * Generated source version: 3.2.1
 * 
 */
@WebService(targetNamespace = "http://codenotfound.com/services/helloworld", name = "HelloWorld_PortType")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface HelloWorldPortType {

    @WebMethod(action = "http://codenotfound.com/services/helloworld/sayHello")
    @WebResult(name = "greeting", targetNamespace = "http://codenotfound.com/types/helloworld", partName = "greeting")
    public Greeting sayHello(
        @WebParam(partName = "person", name = "person", targetNamespace = "http://codenotfound.com/types/helloworld")
        Person person
    );
}
