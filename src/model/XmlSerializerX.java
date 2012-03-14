package model;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import no.ntnu.fp.model.Person;
import no.ntnu.fp.model.XmlSerializer;
import nu.xom.Document;
import nu.xom.Element;

public class XmlSerializerX extends XmlSerializer {
	public Document toXml(Object obj) {
		return null;
	}
	
	public Object toObject(Document xml) {
		return null;
	}
	
	private User assembleUser(Element userElement) throws ParseException {
		String name = null, email = null, password = null, username = null;
		Date date = null;
		int phone = 0;
		Element element = userElement.getFirstChildElement("name");
		if (element != null) {
			name = element.getValue();
		}
		element = userElement.getFirstChildElement("email");
		if (element != null) {
			email = element.getValue();
		}
		element = userElement.getFirstChildElement("date-of-birth");
		if (element != null) {
			date = parseDate(element.getValue());
		}
		element = userElement.getFirstChildElement("username");
		if (element != null) {
			username = element.getValue();
		}
		element = userElement.getFirstChildElement("password");
		if (element != null) {
			password = element.getValue();
		}
		element = userElement.getFirstChildElement("phone");
		if (element != null) {
			phone = Integer.parseInt(element.getValue());
		}
		return new User(name, username, password, email, date, phone);
	}
	
	//can't access the private method in super class
	private Date parseDate(String date) throws ParseException {
		DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM, java.util.Locale.US);
		return format.parse(date);
	}
}
