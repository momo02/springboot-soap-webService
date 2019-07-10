package com.hiair.hom.ibes.ws.soap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
//import org.apache.ws.security.WSConstants;
//import org.apache.ws.security.WSSecurityException;
//import org.apache.ws.security.handler.RequestData;
//import org.apache.ws.security.handler.WSHandlerConstants;
//import org.apache.xml.security.c14n.CanonicalizationException;
//import org.apache.xml.security.c14n.Canonicalizer;
//import org.apache.xml.security.c14n.InvalidCanonicalizerException;
import org.w3c.dom.Document;

import com.hiair.hom.ibes.comn.EventHandler;
import com.hiair.hom.ibes.comn.JAXBFactory;


public class SoapService<Q, S> {

	private static Log LOG = LogFactory.getLog(SoapService.class);
	
//	@Value("${client.ticketagent.address}")
	private static String soapEndpointUrl = "http://localhost:8002/ibes_test/webserviceTest/helloworld";
//	private static String soapEndpointUrl = "http://localhost:8001/ibes/webservice/helloworld111";
	private static String soapAction = "";

	private final Class<Q> rqType;
	private final Class<S> rsType;
	private List<MessageChangetInterceptor> interceptors;

	public SoapService(Class<Q> rqType, Class<S> rsType) {
		this.rqType = rqType;
		this.rsType = rsType;
	}

	public List<MessageChangetInterceptor> getInInterceptors() {
		if (interceptors == null)
			interceptors = new LinkedList<MessageChangetInterceptor>();
		return interceptors;
	}

	@SuppressWarnings("unchecked")
	public S getResponse(Q request) throws Exception {
		
		String result = callSoapWebService(request);
		JAXBContext jaxbContext = JAXBFactory.getIntance(rsType);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		EventHandler e = new EventHandler();
		unmarshaller.setEventHandler(e);

		for (MessageChangetInterceptor interceptor : getInInterceptors()) {
			result = interceptor.changeName(result);
		}
		StringReader reader = new StringReader(result);
		return (S) unmarshaller.unmarshal(reader);
	}

	@SuppressWarnings("restriction")
	protected void createSoapEnvelope(SOAPMessage soapMessage, Q request) throws Exception {

		SOAPPart soapPart = soapMessage.getSOAPPart();
		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		// SOAP Body
		SOAPBody soapBody = envelope.getBody();

		JAXBContext jaxbContext = JAXBFactory.getIntance(request.getClass());
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.newDocument();

		marshaller.marshal(request, document);
		soapBody.addDocument(document);

	}

//	@SuppressWarnings("restriction")
//	private Document signSOAPMessage(SOAPMessage soapEnvelope)
//			throws SOAPException, TransformerException, WSSecurityException {
//		Source src = soapEnvelope.getSOAPPart().getContent();
//		TransformerFactory transformerFactory = TransformerFactory.newInstance();
//		Transformer transformer = transformerFactory.newTransformer();
//		DOMResult result = new DOMResult();
//		transformer.transform(src, result);
//		Document doc = (Document) result.getNode();
//
//		final RequestData reqData = new RequestData();
//		java.util.Map<String, String> msgContext = new java.util.TreeMap<String, String>();
//		msgContext.put(WSHandlerConstants.ENABLE_SIGNATURE_CONFIRMATION, "false");
//		msgContext.put(WSHandlerConstants.SIG_PROP_FILE, "Client_Encrypt.properties");
//
//		msgContext.put(WSHandlerConstants.SIG_KEY_ID, "DirectReference");
//		msgContext.put("password", "importkey");
//		msgContext.put(WSHandlerConstants.SIG_DIGEST_ALGO, "http://www.w3.org/2000/09/xmldsig#sha1");
//		msgContext.put(WSHandlerConstants.SIG_ALGO, "http://www.w3.org/2000/09/xmldsig#rsa-sha1");
//
//		reqData.setMsgContext(msgContext);
//		reqData.setSignatureUser("importkey");
//		reqData.setUsername("importkey");
//
//		final java.util.List<Integer> actions = new java.util.ArrayList<Integer>();
//		actions.add(new Integer(WSConstants.SIGN));
//		CustomHandler handler = new CustomHandler();
//
//		handler.send(WSConstants.SIGN, doc, reqData, actions, true);
//		return doc;
//	}

	public String toString(Document doc) {
		try {
			StringWriter sw = new StringWriter();
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

			transformer.transform(new DOMSource(doc), new StreamResult(sw));
			return sw.toString();
		} catch (Exception ex) {
			throw new RuntimeException("Error converting to String", ex);
		}
	}

	@SuppressWarnings("restriction")
	private String callSoapWebService(Q request) throws Exception {

		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection soapConnection = soapConnectionFactory.createConnection();
		try {
			SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(request), soapEndpointUrl);
			LOG.debug("Response SOAP Message:");
			if (LOG.isDebugEnabled()) {
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				soapResponse.writeTo(os);
				LOG.debug(new String(os.toByteArray(),"UTF-8"));
				os.close();
			}
			return getString(soapResponse.getSOAPBody().extractContentAsDocument());
		} finally {
			if (soapConnection != null)
				soapConnection.close();
		}
	}

	private String getString(Document document) throws Exception {
		DOMSource domSource = new DOMSource(document);
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.transform(domSource, result);
		return writer.toString();
	}

	@SuppressWarnings("restriction")
	private SOAPMessage createSOAPRequest(Q request) throws Exception {
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		createSoapEnvelope(soapMessage, request);
		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", soapAction);
		soapMessage.saveChanges();

		//인증부분은 일단 주석 처리
//		SOAPMessage signsoapMessage = toMessage(signSOAPMessage(soapMessage));
		SOAPMessage signsoapMessage = soapMessage;
		
		LOG.debug("Request SOAP Message:");
		if (LOG.isDebugEnabled()) {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			signsoapMessage.writeTo(os);
			LOG.debug(new String(os.toByteArray(),"UTF-8"));
			os.close();
		}

		return signsoapMessage;
	}

//	private SOAPMessage toMessage(Document doc)
//			throws IOException, SOAPException, CanonicalizationException, InvalidCanonicalizerException {
//		Canonicalizer c14n = Canonicalizer.getInstance(Canonicalizer.ALGO_ID_C14N_WITH_COMMENTS);
//		byte[] canonicalMessage = c14n.canonicalizeSubtree(doc);
//		ByteArrayInputStream in = new ByteArrayInputStream(canonicalMessage);
//		MessageFactory factory = MessageFactory.newInstance();
//		return factory.createMessage(null, in);
//	}

}
