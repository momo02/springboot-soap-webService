package com.hiair.hom.ibes.comn;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

public class EventHandler implements ValidationEventHandler {

	public boolean handleEvent(ValidationEvent event) {
		return false;
	}

}
