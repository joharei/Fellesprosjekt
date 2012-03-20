package model;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import synclogic.LoginRequest;
import synclogic.SynchronizationUnit;

import no.ntnu.fp.model.XmlSerializer;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;

public class XmlSerializerX extends XmlSerializer {
	static SynchronizationUnit syncUnit;//TODO: Add to class diagram
	
	public static void main(String[] args) throws IOException, ParseException, ParsingException {
		//test for users
//		list.add(new User("Kalle", "Kanin", "lovebunny666", "b@c.af", Calendar.getInstance().getTime(), 22445588));
//		list.add(new User("Donald", "Duck", "ducky", "d@d.ab", Calendar.getInstance().getTime(), 42445588));
		User user = new User("Onkel", "Skrue", "richie", "1234a", "s@r.ab", Calendar.getInstance().getTime(), 22445598);
		String xml = toXml(user, SaveableClass.User);
		System.out.println(xml);
		User user2 = (User) toObject(xml);
		System.out.println(user2.toString());
		
		LoginRequest lr = new LoginRequest(user.getUsername(), user.getPassword());
		lr.setLoginAccepted(true);
		xml = toXml(lr, SaveableClass.LoginRequest);
		System.out.println(xml);
		LoginRequest lr2 = (LoginRequest) toObject(xml);
		System.out.println("Uname: " + lr2.getUsername());
		System.out.println("Password: " + lr2.getPassword());
		System.out.println("Accepted: " + lr2.getLoginAccepted());
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
	private static ArrayList readXml(Document xmlDoc) throws ParseException {
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
			case Week : {
				Elements weeks = root.getChildElements(Week.NAME_PROPERTY_CLASSTYPE);
				for (int i = 0; i < weeks.size(); i++) {
					Element weekElement = weeks.get(i);
					addedObj.add(assembleWeek(weekElement));
				}
			}
			default: {
				throw new ParseException("Unrecognized object type!", 0);
			}
		}
	}
	
	public static String toXml(Object obj, SaveableClass type) {
		if (obj instanceof List) {
			//handle list
			throw new IllegalArgumentException("List handling not yet supported");
		} else {
			//handle single object
			switch (type) {
				case User : {
					Element userE = userToXmlElement((User) obj);
					return userE.toXML();
				}
				case LoginRequest : {
					Element loginE = loginRequestToXmlElement((LoginRequest) obj);
					return loginE.toXML();
				}
				default : {
					throw new IllegalArgumentException("Unsupported object type (see SaveableClass, may be around the corner)!");
				}
			}
		}
	}
	
	public static Object toObject(String xml) throws IOException, ParseException, ParsingException {
		Document xmldoc = stringToDocument(xml);
		Element root = xmldoc.getRootElement();
		SaveableClass objType = SaveableClass.valueOf(root.getLocalName());
		switch(objType) {
			case User : {
				return assembleUser(root);
			}
			case LoginRequest : {
				return assembleLoginRequest(root);
			}
			default : {
				throw new ParsingException("Unidentified object type met during parsing");
			}
		}
	}
	
	private static Document stringToDocument(String xml) throws java.io.IOException, java.text.ParseException, nu.xom.ParsingException {
		nu.xom.Builder parser = new nu.xom.Builder(false);
		nu.xom.Document doc = parser.build(xml, "");
		return doc;
	}
	
	/**
	 * Group Xml-supporting objects of the same type in a document.
	 * @param list An arraylist with saveable objects
	 * @param classType The correct SaveableClass enum for the object type
	 * @return Xml document
	 */
	@SuppressWarnings("rawtypes")
	public static Document buildXml(ArrayList list, SaveableClass classType) {
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
	 * Create a login request from a Xml element
	 */
	private static LoginRequest assembleLoginRequest(Element lrElement) {
		String username = null, password = null;
		boolean accepted = false;

		Element e = lrElement.getFirstChildElement(LoginRequest.NAME_PROPERTY_USERNAME);
		if (e != null) {
			username = e.getValue();
		}
		
		e = lrElement.getFirstChildElement(LoginRequest.NAME_PROPERTY_PASSWORD);
		if (e != null) {
			password = e.getValue();
		}
		
		e = lrElement.getFirstChildElement(LoginRequest.NAME_PROPERTY_LOGIN_ACCEPTED);
		if (e != null) {
			accepted = Boolean.parseBoolean(e.getValue());
		}
		LoginRequest lr = new LoginRequest(username, password);
		lr.setLoginAccepted(accepted);
		return lr;
	}

	/**
	 * Turn a LoginRequest into a Xml element.
	 */
	private static Element loginRequestToXmlElement(LoginRequest logreq) {
		Element lrElement = new Element("" + SaveableClass.LoginRequest);
		
		Element username = new Element(LoginRequest.NAME_PROPERTY_USERNAME);
		username.appendChild(logreq.getUsername());
		lrElement.appendChild(username);
		
		Element password = new Element(LoginRequest.NAME_PROPERTY_PASSWORD);
		password.appendChild(logreq.getPassword());
		lrElement.appendChild(password);
		
		Element accepted = new Element(LoginRequest.NAME_PROPERTY_LOGIN_ACCEPTED);
		accepted.appendChild("" + logreq.getLoginAccepted());
		lrElement.appendChild(accepted);
		return lrElement;
	}

	/**
	 * Copied from XmlSerializer and modified for fields; creates a user from the xml element
	 * @throws ParseException
	 */
	private static User assembleUser(Element userElement) throws ParseException {
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
			System.out.println("Date: " + element.getValue());
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
	 */
	private static Element userToXmlElement(User user) {
		Element element = new Element("" + SaveableClass.User);
		
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
		
		DateFormat dformat = User.getDateFormat();
		Element dateOfBirth = new Element(User.NAME_PROPERTY_DATE_OF_BIRTH);
		dateOfBirth.appendChild(dformat.format(user.getDateOfBirth()));

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
	
	/**
	 * Turn a week object into a Xml element
	 */
	private static Element weekToXmlElement(Week week) {
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
		return weekElement;
	}
	
	/**
	 * Create a week model from the Xml element
	 * @throws ParseException
	 */
	private static Week assembleWeek(Element weekElement) throws ParseException {
		Date start = null, end = null;
		ArrayList<Appointment> appointments = new ArrayList<Appointment>();
		
		DateFormat dformat = Week.getDateFormat();
		Element e = weekElement.getFirstChildElement(Week.NAME_PROPERTY_START_DATE);
		if (e != null) {
			start = dformat.parse(e.getValue());
		}
		
		e = weekElement.getFirstChildElement(Week.NAME_PROPERTY_END_DATE);
		if (e != null) {
			end = dformat.parse(e.getValue());
		}
				
		e = weekElement.getFirstChildElement(Week.NAME_PROPERTY_APPOINTMENTS);
		if (e != null) {
			Elements apps = e.getChildElements();
			for (int i = 0; i < e.getChildCount(); i++) {
				appointments.add(assembleAppointment(apps.get(i)));
			}
		}
		return new Week(start, end, appointments);
	}
	
	/**
	 * Turn a room into a Xml element
	 */
	private static Element roomToXmlElement(Room room) {
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
	
	/**
	 * Turn a room in Xml form into an object
	 */
	private static Room assembleRoom(Element roomElement) {
		int id = 0, capacity = 0;
		String name = null;
		Element e = roomElement.getFirstChildElement(Room.NAME_PROPERTY_ID);
		if (e != null) {
			id = Integer.parseInt(e.getValue());
		}
		e = roomElement.getFirstChildElement(Room.NAME_PROPERTY_NAME);
		if (e != null) {
			name = e.getValue();
		}
		e = roomElement.getFirstChildElement(Room.NAME_PROPERTY_CAPACITY);
		if (e != null) {
			capacity = Integer.parseInt(e.getValue());
		}
		return new Room(id, name, capacity);
	}
	
	/**
	 * Turn an appointment or meeting into a Xml element
	 */
	private static Element appointmentToXmlElement(Appointment event) {
		DateFormat dformat = Appointment.getDateFormat();
		DateFormat tformat = Appointment.getTimeformat();
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
		owner.appendChild(userToXmlElement(event.getOwner()));
		
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
		
		//Handle meetings
		if (event instanceof Meeting) {
			Meeting meeting = (Meeting) event;
			Element participantGrouping = new Element(Meeting.NAME_PROPERTY_PARTICIPANTS);
			ArrayList<User> users = meeting.getParticipants();
			Iterator<User> it = users.iterator();
			while (it.hasNext()) {
				participantGrouping.appendChild(userToXmlElement(it.next()));
			}
			appElement.appendChild(participantGrouping);
			
			Element invitationGrouping = new Element(Meeting.NAME_PROPERTY_INVITATIONS);
			ArrayList<Invitation> invs = meeting.getInvitations();
			Iterator<Invitation> it2 = invs.iterator();
			while (it2.hasNext()) {
				invitationGrouping.appendChild(invitationToXmlElement(it2.next()));
			}
			appElement.appendChild(invitationGrouping);
		}
		return appElement;
	}
	
	/**
	 * Create a meeting or appointment from their respective elements
	 * @throws ParseException
	 */
	private static Appointment assembleAppointment(Element appElement) throws ParseException {
		Date date = null, start = null, end = null;
		String desc = null, loc = null;
		Room room = null;
		User owner = null;
		int id = 0;
		boolean delFlag = false;
		
		Element e = appElement.getFirstChildElement(Appointment.NAME_PROPERTY_DATE);
		if (e != null) {
			DateFormat dFormat = Appointment.getDateFormat();
			date = dFormat.parse(e.getValue());
		}
		
		DateFormat tFormat = Appointment.getTimeformat();
		e = appElement.getFirstChildElement(Appointment.NAME_PROPERTY_START_TIME);
		if (e != null) {
			start = tFormat.parse(e.getValue());
		}
		
		e = appElement.getFirstChildElement(Appointment.NAME_PROPERTY_END_TIME);
		if (e != null) {
			end = tFormat.parse(e.getValue());
		}
		
		e = appElement.getFirstChildElement(Appointment.NAME_PROPERTY_DESCRIPTION);
		if (e != null) {
			desc = e.getValue();
		}
		
		e = appElement.getFirstChildElement(Appointment.NAME_PROPERTY_LOCATION);
		if (e != null) {
			loc = e.getValue();
		}
		
		e = appElement.getFirstChildElement(Appointment.NAME_PROPERTY_ROOM);
		if (e != null) {
			room = assembleRoom(e);
		}
		
		e = appElement.getFirstChildElement(Appointment.NAME_PROPERTY_OWNER);
		if (e != null) {
			owner = assembleUser(e);
		}
		
		e = appElement.getFirstChildElement(Appointment.NAME_PROPERTY_ID);
		if (e != null) {
			id = Integer.parseInt(e.getValue());
		}
		
		e = appElement.getFirstChildElement(Appointment.NAME_PROPERTY_DELETED);
		if (e != null) {
			delFlag = Boolean.parseBoolean(e.getValue());
		}
		
		//Handle meetings
		if (SaveableClass.valueOf(appElement.getLocalName()) == SaveableClass.Meeting) {
			ArrayList<User> participants = new ArrayList<User>();
			ArrayList<Invitation> invs = new ArrayList<Invitation>();
			
			e = appElement.getFirstChildElement(Meeting.NAME_PROPERTY_PARTICIPANTS);
			if (e != null) {
				int count = e.getChildCount();
				Elements users = e.getChildElements();
				for (int i = 0; i < count; i++) {
					participants.add(assembleUser(users.get(i)));
				}
			}
			
			e = appElement.getFirstChildElement(Meeting.NAME_PROPERTY_INVITATIONS);
			if (e != null) {
				int count = e.getChildCount();
				Elements invites = e.getChildElements();
				for (int i = 0; i < count; i++) {
					invs.add(assembleInvitation(invites.get(i)));
				}
			}
			Meeting m = new Meeting(date, start, end, desc, loc, room, id, owner, delFlag);
			m.setParticipants(participants);
			m.setInvitations(invs);
			return m;
		}
		return new Appointment(date, start, end, desc, loc, room, id, owner, delFlag);
	}
	
	/**
	 * Turns an invitation into a Xml element
	 * @param inv
	 */
	private static Element invitationToXmlElement(Invitation inv) {
		Element invElement = new Element(Invitation.NAME_PROPERTY_CLASSTYPE);
		
		Element status = new Element(Invitation.NAME_PROPERTY_STATUS);
		status.appendChild("" + inv.getStatus());
		invElement.appendChild(status);
		
		//meeting id only
		Element meeting = new Element(Invitation.NAME_PROPERTY_MEETING);
		meeting.appendChild("" + inv.getMeeting().getId());
		invElement.appendChild(meeting);
		
		return invElement;
	}
	
	/**
	 * Create an invitation object from a Xml element.
	 * Uses the synchronization unit to retrieve the meeting
	 * based on the meeting id.
	 */
	private static Invitation assembleInvitation(Element invElement) {
		InvitationStatus status = null;
		Meeting meeting = null;
		
		Element e = invElement.getFirstChildElement(Invitation.NAME_PROPERTY_STATUS);
		if (e != null) {
			status = InvitationStatus.valueOf(e.getValue());
		}
		
		e = invElement.getFirstChildElement(Invitation.NAME_PROPERTY_MEETING);
		if (e != null) {
			String meetingID = e.getValue();
			meeting = (Meeting) syncUnit.getObjectFromID(SaveableClass.Meeting, meetingID);
		}
		
		return new Invitation(status, meeting);
	}
}
