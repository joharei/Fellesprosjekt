package model;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

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
	@SuppressWarnings("rawtypes")
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
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
	@SuppressWarnings("rawtypes")
	public Document buildXml(ArrayList list, SaveableClass classType) {
		Iterator it = list.iterator();
		Element root = new Element("" + classType);
		switch (classType) {
			case User : {
				while (it.hasNext()) {
					Element element = userToXmlElement((User) it.next());
					root.appendChild(element);
				}
				break;
			}
			case Week : {
				while (it.hasNext()) {
					Element element = weekToXmlElement((Week) it.next());
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
			DateFormat format = User.getDateFormat();
			date = format.parse(element.getValue());
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
	
	private Element weekToXmlElement(Week week) {
		DateFormat dformat = Week.getDateFormat();
		Element weekElement = new Element(Week.NAME_PROPERTY_CLASSTYPE);
		Element start = new Element(Week.NAME_PROPERTY_START_DATE);
		start.appendChild(dformat.format(week.getStartDate()));
		
		Element end = new Element(Week.NAME_PROPERTY_END_DATE);
		end.appendChild(dformat.format(week.getEndDate()));
		
		//add meetings/appointments
		Element appGrouping = new Element("" + SaveableClass.Appointment);
		ArrayList<Appointment> appointments = week.getAppointments();
		if (!appointments.isEmpty()) {
			Iterator<Appointment> it = appointments.iterator();
			while (it.hasNext()) {
				appGrouping.appendChild(appointmentToXmlElement(it.next()));
			}
		}
		
		weekElement.appendChild(start);
		weekElement.appendChild(end);
		weekElement.appendChild(appGrouping);
		return null;
	}
	
	/**
	 * Turn a room into a Xml element
	 */
	private Element roomToXmlElement(Room room) {
		Element roomElement = new Element(Room.NAME_PROPERTY_CLASSTYPE);
		
		Element id = new Element(Room.NAME_PROPERTY_ID);
		id.appendChild("" + room.getId());
		
		Element name = new Element(Room.NAME_PROPERTY_NAME);
		name.appendChild(room.getName());
		
		Element capacity = new Element(Room.NAME_PROPERTY_CAPACITY);
		capacity.appendChild("" + room.getCapacity());
		
		roomElement.appendChild(id);
		roomElement.appendChild(name);
		roomElement.appendChild(capacity);
		
		return roomElement;
	}
	
	private Element appointmentToXmlElement(Appointment event) {
		// TODO: Håndter møter
		DateFormat dformat = event.getDateFormat();
		DateFormat tformat = event.getTimeformat();
		Element appElement = new Element(Appointment.NAME_PROPERTY_CLASSTYPE);
		
		Element date = new Element(Appointment.NAME_PROPERTY_DATE); 
		date.appendChild(dformat.format(event.getDate()));
		
		Element start = new Element(Appointment.NAME_PROPERTY_START_TIME);
		start.appendChild(tformat.format(event.getStartTime()));
		
		Element end = new Element(Appointment.NAME_PROPERTY_END_TIME);
		end.appendChild(tformat.format(event.getEndTime()));
		
		Element desc = new Element(Appointment.NAME_PROPERTY_DESCRIPTION);
		desc.appendChild(event.getDescription());
		
		Element loc = new Element(Appointment.NAME_PROPERTY_LOCATION);
		loc.appendChild(event.getLocation());
		
		Element room = new Element(Appointment.NAME_PROPERTY_ROOM);
		room.appendChild(roomToXmlElement(event.getRoom()));
		
		Element id = new Element(Appointment.NAME_PROPERTY_ID);
		id.appendChild("" + event.getId());
		
		Element owner = new Element(Appointment.NAME_PROPERTY_OWNER);
		//TODO: Legg til selve brukeren, evt en forenklet versjon?
		owner.appendChild(event.getOwner().getName());
		
		Element delFlag = new Element(Appointment.NAME_PROPERTY_DELETED);
		delFlag.appendChild("" + event.isDeleted());
		
		appElement.appendChild(date);
		appElement.appendChild(start);
		appElement.appendChild(end);
		appElement.appendChild(desc);
		appElement.appendChild(loc);
		appElement.appendChild(room);
		appElement.appendChild(id);
		appElement.appendChild(owner);
		appElement.appendChild(delFlag);
		
		return appElement;
	}
}
