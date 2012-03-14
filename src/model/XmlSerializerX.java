package model;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

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
	
	/**
	 * Group Xml-supporting objects in a document
	 */
	private Document buildXml(ArrayList list, SaveableClass classType) {
		Element root = new Element("" + classType);
		switch (classType) {
			case User : {
				@SuppressWarnings("unchecked")
				Iterator it = list.iterator();
				while (it.hasNext()) {
					Element element = userToXmlElement((User) it.next());
					root.appendChild(element);
				}
				break;
			}
			default : {
				throw new IllegalArgumentException("Unhandled class type");
			}
		}
		return new Document(root);
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
	
	private Element userToXmlElement(User user) {
		Element element = new Element(User.NAME_PROPERTY_CLASSTYPE);
		
		Element firstname = new Element(User.NAME_PROPERTY_FIRSTNAME);
		Element surname = new Element(User.NAME_PROPERTY_SURNAME);
		firstname.appendChild(user.getFirstname());
		surname.appendChild(user.getSurname());
		
		Element usern = new Element(User.NAME_PROPERTY_USERNAME);
		usern.appendChild(user.getUsername());
		
		Element pwd = new Element(User.NAME_PROPERTY_PASSWORD);
		pwd.appendChild(user.getPassword());
		
		Element email = new Element(User.NAME_PROPERTY_EMAIL);
		email.appendChild(user.getEmail());
		
		DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM, java.util.Locale.US);
		Element dateOfBirth = new Element(User.NAME_PROPERTY_DATE_OF_BIRTH);
		dateOfBirth.appendChild(format.format(user.getDateOfBirth()));

		Element phone = new Element (User.NAME_PROPERTY_PHONE);
		phone.appendChild("" + user.getPhone());
		
		//link fields to object
		element.appendChild(firstname);
		element.appendChild(surname);
		element.appendChild(usern);
		element.appendChild(pwd);
		element.appendChild(email);
		element.appendChild(dateOfBirth);
		element.appendChild(phone);
		return element;
	}
	
	//can't access the private method in super class
	private Date parseDate(String date) throws ParseException {
		DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM, java.util.Locale.US);
		return format.parse(date);
	}
}
