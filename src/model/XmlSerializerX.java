package model;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import no.ntnu.fp.model.Person;
import no.ntnu.fp.model.Project;
import no.ntnu.fp.model.XmlSerializer;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;

public class XmlSerializerX extends XmlSerializer {
	
	public static void main(String[] args) {
		//test for users
		ArrayList<User> list = new ArrayList<User>();
		list.add(new User("Kalle", "Kanin", "lovebunny666", "b@c.af", Calendar.getInstance().getTime(), 22445588));
		list.add(new User("Donald", "Duck", "ducky", "d@d.ab", Calendar.getInstance().getTime(), 42445588));
		list.add(new User("Onkel", "Skrue", "richie", "s@r.ab", Calendar.getInstance().getTime(), 22445598));
		new XmlSerializerX(list, SaveableClass.User);
	}
	
	//testing constructor
	private XmlSerializerX(ArrayList list, SaveableClass type) {
		Document doc = buildXml(list, type);
		System.out.println("Xml string:\n" + doc.toXML());
		System.out.println("Examine root element: " + doc.getRootElement().getLocalName());
		try {
			ArrayList objects = readXml(doc);
			Iterator it = objects.iterator();
			while (it.hasNext()) {
				System.out.println(it.next());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Read a Xml document and create objects found within.
	 * @param xmlDoc The document
	 * @return Arraylist with the created objects.
	 * @throws ParseException
	 */
	private ArrayList readXml(Document xmlDoc) throws ParseException {
		ArrayList addedObj = new ArrayList();
		Element root = xmlDoc.getRootElement();
		SaveableClass objType = SaveableClass.valueOf(root.getLocalName());
		switch (objType) {
			case User : {
				//Root of Xml file dictates collection of users
				Elements users = root.getChildElements(User.NAME_PROPERTY_CLASSTYPE);
				for (int i = 0; i < users.size(); i++) {
					Element childElement = users.get(i);
					addedObj.add(assembleUser(childElement));
				}
				return addedObj;
			}
			default: {
				throw new ParseException("Unrecognized object type!", 0);
			}
		}
	}
	
	public Document toXml(Object obj) {
		return null;
	}
	
	public Object toObject(Document xml) {
		return null;
	}
	
	/**
	 * Group Xml-supporting objects of the same type in a document.
	 * @param list An arraylist with saveable objects
	 * @param classType The correct SaveableClass enum for the object type
	 * @return Xml document
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
	
	/**
	 * Copied from XmlSerializer and modified for fields; creates a user from the xml element
	 * @param userElement
	 * @return
	 * @throws ParseException
	 */
	private User assembleUser(Element userElement) throws ParseException {
		String firstname = null, surname = null, username = null, password = null, email = null;
		Date date = null;
		int phone = 0;
		Element element = userElement.getFirstChildElement(User.NAME_PROPERTY_FIRSTNAME);
		if (element != null) {
			firstname = element.getValue();
		}
		element = userElement.getFirstChildElement(User.NAME_PROPERTY_SURNAME);
		if (element != null) {
			surname = element.getValue();
		}
		element = userElement.getFirstChildElement(User.NAME_PROPERTY_USERNAME);
		if (element != null) {
			username = element.getValue();
		}
		element = userElement.getFirstChildElement(User.NAME_PROPERTY_PASSWORD);
		if (element != null) {
			password = element.getValue();
		}
		element = userElement.getFirstChildElement(User.NAME_PROPERTY_EMAIL);
		if (element != null) {
			email = element.getValue();
		}
		element = userElement.getFirstChildElement(User.NAME_PROPERTY_DATE_OF_BIRTH);
		if (element != null) {
			date = parseDate(element.getValue());
		}
		element = userElement.getFirstChildElement(User.NAME_PROPERTY_PHONE);
		if (element != null) {
			phone = Integer.parseInt(element.getValue());
		}
		
		return new User(firstname, surname, username, password, email, date, phone);
	}
	
	/**
	 * Turn a user object into a Xml element
	 * @param user
	 * @return
	 */
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
