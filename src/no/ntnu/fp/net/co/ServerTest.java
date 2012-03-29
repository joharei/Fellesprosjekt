package no.ntnu.fp.net.co;

import java.io.IOException;
import java.net.SocketTimeoutException;

public class ServerTest {
	
    public static void serverMain(int port) {
    	ConnectionImpl c = new ConnectionImpl(port);
    	try {
    		System.out.println("Listening on port " + port);
    		Connection con = c.accept();
    		System.out.println("Connection established! " + con.toString());
    		while(true){
    			String msg = con.receive();
    			System.out.println("Message: " + msg);
    		}
    	} catch (SocketTimeoutException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (IOException e) {
    		// Do nothing
    	}
    	System.out.println("Closed");
    }
    
    public static void main(String[] args){
    	serverMain(1337);
    }
}
