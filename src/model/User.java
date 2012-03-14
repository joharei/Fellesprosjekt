package model;

import java.util.ArrayList;

import no.ntnu.fp.model.Person;

public class User extends Person {
	private String username, password;
	private ArrayList<Notification> notifications;
	private ArrayList<Week> weekModels;
	private int phone;
	private boolean isOnline;
}
