package no.ntnu.fp.net.co;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class ClientTest {
    
    public static void clientMain(String address, int port) {
    	ConnectionImpl c = new ConnectionImpl(1337);
    	System.out.println("Your IP-address is: " + c.getIPv4Address());
    	try {
    		System.out.println("Trying to connect to " + address + " on port " + port);
    		c.connect(Inet4Address.getByName(address), port);
    		System.out.println("Connection established!");
    		Scanner scanner = new Scanner(System.in);
    		while(true){
    			System.out.print("Type something to send: ");
	    		String msg = scanner.nextLine();
	    		if (msg.equals("quit")){
	    			break;
	    		}
	    		try {
	    			c.send(msg);
	    		} catch(SocketTimeoutException e) {
	    			e.printStackTrace();
	    			System.out.println("Could not send packet, please reconnect!");
	    		}
    		}
    		System.out.println("Closing...");
    		c.close();
    	} catch (SocketTimeoutException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	System.out.println("Finished!");
    }
    
	public static void main(String[] args){
		clientMain("localhost", 1337);
	}
}
