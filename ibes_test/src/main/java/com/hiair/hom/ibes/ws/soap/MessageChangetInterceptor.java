package com.hiair.hom.ibes.ws.soap;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.phase.Phase;

@SuppressWarnings("deprecation")
public abstract class MessageChangetInterceptor extends AbstractSoapInterceptor{

	
	public MessageChangetInterceptor() {
		super(Phase.RECEIVE);
	}

	public void handleMessage(SoapMessage message) throws Fault {
		message.put(SoapMessage.ENCODING, "UTF-8");
		InputStream is = message.getContent(InputStream.class);

		if (is != null) {
			CachedOutputStream bos = new CachedOutputStream();
			try {
				IOUtils.copy(is, bos);
				String soapMessage = new String(bos.getBytes());
				bos.flush();
				message.setContent(InputStream.class, is);

				is.close();
				InputStream inputStream = new ByteArrayInputStream(changeName(soapMessage).getBytes());
				message.setContent(InputStream.class, inputStream);
				bos.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	public abstract String changeName(String soapMessage);
}
