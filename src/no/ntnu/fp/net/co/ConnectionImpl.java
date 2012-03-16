/*
 * Created on Oct 27, 2004
 */
package no.ntnu.fp.net.co;

import java.io.EOFException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import sun.net.InetAddressCachePolicy;

import no.ntnu.fp.net.admin.Log;
import no.ntnu.fp.net.cl.ClException;
import no.ntnu.fp.net.cl.ClSocket;
import no.ntnu.fp.net.cl.KtnDatagram;
import no.ntnu.fp.net.cl.KtnDatagram.Flag;

/**
 * Implementation of the Connection-interface. <br>
 * <br>
 * This class implements the behaviour in the methods specified in the interface
 * {@link Connection} over the unreliable, connectionless network realised in
 * {@link ClSocket}. The base class, {@link AbstractConnection} implements some
 * of the functionality, leaving message passing and error handling to this
 * implementation.
 * 
 * @author Sebj�rn Birkeland and Stein Jakob Nordb�
 * @see no.ntnu.fp.net.co.Connection
 * @see no.ntnu.fp.net.cl.ClSocket
 */
public class ConnectionImpl extends AbstractConnection {

    /** Keeps track of the used ports for each server port. */
    private static Map<Integer, Boolean> usedPorts = Collections.synchronizedMap(new HashMap<Integer, Boolean>());
    
    private final static int INITIAL_PORT = 10000;
    private final static int PORT_RANGE = 100;
    private final static int RETRIES = 5;

    private static boolean shouldInitPortNumbers = true;
    
    //Testing the A2 framework
    private KtnDatagram datagram;
    /**
     * Initialise initial sequence number and setup state machine.
     * 
     * @param myPort
     *            - the local port to associate with this connection
     */
    public ConnectionImpl(int myPort) {
    	initPortNumbers();
    	this.myAddress = getIPv4Address();
    	datagram = new KtnDatagram();
    	this.myPort = myPort;
//    	throw new RuntimeException("NOT IMPLEMENTED");
    }
    
    /**
     * 
     * 
     * @param packet
     * @throws ConnectException
     * @throws IOException
     */
    public ConnectionImpl(KtnDatagram packet) throws ConnectException, IOException {
    	initPortNumbers();
    	this.myAddress = getIPv4Address();
    	this.myPort = packet.getDest_port();
    	this.remotePort = packet.getSrc_port();
    	this.remoteAddress = packet.getSrc_addr();
    	this.lastValidPacketReceived = packet;
    	
    	// Send SYN-ACK
    	sendAck(this.lastValidPacketReceived, true);
    	this.state = State.SYN_RCVD;
    	// Wait for ACK
    	this.lastValidPacketReceived = internalReceiveAck(false, this.lastValidPacketReceived);
    	this.state = State.ESTABLISHED;
    	
//    	this.lastValidPacketReceived = receiveAck();
//    	if(this.lastValidPacketReceived.getFlag() != Flag.ACK) {
//    		this.state = State.CLOSED;
//    		throw new ConnectException("Did not receive ACK for sent SYN-ACK");
//    	}
//    	this.state = State.ESTABLISHED;
    }

    public static void initPortNumbers() {
    	if(shouldInitPortNumbers) {
	    	for (int i = INITIAL_PORT; i < INITIAL_PORT + PORT_RANGE; i++) {
				usedPorts.put(i, false);
			}
    	}
    }
    
    public String getIPv4Address() {
        try {
        	Enumeration<NetworkInterface> networkInterfaces = java.net.NetworkInterface.getNetworkInterfaces();
        	while(networkInterfaces.hasMoreElements()){
				Enumeration<InetAddress> networkAddresses = networkInterfaces.nextElement().getInetAddresses();
				while(networkAddresses.hasMoreElements()){
					String address = networkAddresses.nextElement().getHostAddress();
					if(address.contains(".") && !address.equals("127.0.0.1")){
						return address;
					}
				}
        	}
        	return InetAddress.getLocalHost().getHostAddress();
        }
        catch (UnknownHostException e) {
        	return "127.0.0.1";
        } catch (SocketException e) {
        	return "127.0.0.1";
		}
    }

    public KtnDatagram internalReceiveAck(boolean synAck, KtnDatagram packetToAck) throws EOFException, IOException {
    	KtnDatagram temp;
    	for (int i = 0; i < RETRIES; i++) {
    		System.out.println("Waiting for ACK");
    		temp = receiveAck();
    		if(temp == null) {
    			System.out.println("ACK was null");
    		} else {
    			System.out.println("Received packet with flag: " + temp.getFlag().toString() + " and seq.number: " + temp.getSeq_nr());
    		}
    		if(temp != null && (synAck && temp.getFlag() == Flag.SYN_ACK || !synAck)) {
    			return temp;
    		} 
//				else if (packetToAck.getSeq_nr() != temp.getSeq_nr()){
//					
//				}
    	}
    	throw new SocketTimeoutException();
    }
    
    public KtnDatagram internalReceive(Flag flag, boolean internal) throws EOFException, IOException {
    	KtnDatagram temp = null;
    	for (int i = 0; i < RETRIES; i++) {
    		if(flag == Flag.FIN) {
    			System.out.println("Waiting for FIN");
    		}
    		temp = receivePacket(internal);
			if(temp != null && temp.getFlag() == flag) {
				return temp;
			} 
    	}
    	throw new SocketTimeoutException();
    }
    
    /**
     * Establish a connection to a remote location.
     * 
     * @param remoteAddress
     *            - the remote IP-address to connect to
     * @param remotePort
     *            - the remote portnumber to connect to
     * @throws IOException
     *             If there's an I/O error.
     * @throws java.net.SocketTimeoutException
     *             If timeout expires before connection is completed.
     * @see Connection#connect(InetAddress, int)
     */
    public void connect(InetAddress remoteAddress, int remotePort) throws IOException,
    SocketTimeoutException {
    	this.remoteAddress = remoteAddress.getHostAddress();
    	this.remotePort = remotePort;
        KtnDatagram synPacket = constructInternalPacket(Flag.SYN);
//        synPacket.setSrc_addr(getIPv4Address());
        // TODO: Should we check if packet is corrupted??
        try {
			simplySendPacket(synPacket);
		} catch (ClException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        this.state = State.SYN_SENT;
        this.lastValidPacketReceived = internalReceiveAck(true, synPacket);
        this.remotePort = this.lastValidPacketReceived.getSrc_port();
        sendAck(this.lastValidPacketReceived, false);
        this.state = State.ESTABLISHED;
        
        
        
        if(this.state != State.ESTABLISHED) {
        	throw new SocketTimeoutException("Did not receive SYN-ACK!");
        }
        
        
//        this.lastValidPacketReceived = sendDataPacketWithRetransmit(synPacket);
//        sendAck(this.lastValidPacketReceived, false);
    }
    
    /**
     * Creates the local folder "Log" if it doesn't already exist.
     * Creates the file "Log/logfile.txt" if it doesn't already exist.
     */
    public static void fixLogDirectory() {
    	File log = new File("Log");
    	if(!log.isDirectory()) {
    		log.mkdir();
    	}
    	File logFile = new File("Log/logfile.txt");
    	if(!logFile.exists()) {
    		try {
				logFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }
    
    private static int getNextPortNumber() throws IOException {
    	// TODO: Change exception type
    	for (int i = INITIAL_PORT; i < INITIAL_PORT + PORT_RANGE; i++) {
			if(!usedPorts.get(i)) {
				usedPorts.put(i, true);
				return i;
			}
		}
    	throw new IOException("Out of ports!");
    }
    
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
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	System.out.println("Closed");
    }
    
    public static void clientMain(String address, int port) {
    	ConnectionImpl c = new ConnectionImpl(INITIAL_PORT - 1);
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
	    		c.send(msg);
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
    
    public static void main(String[] args) {
//    	fixLogDirectory();
//    	serverMain(1337);
    	// Stian IP
//    	clientMain("78.91.13.73", 1337);
    	// Bj�rn Arve IP
//    	clientMain("78.91.36.121", 1337);
    	
//    	ConnectionImpl c = new ConnectionImpl(1338);
//    	c.testSimplySend("78.91.36.121", 1337);
	}
    
    public void testSimplySend(String ip, int port) {
    	KtnDatagram packet = constructInternalPacket(Flag.SYN);
//    	packet.setDest_addr(ip);
//    	packet.setDest_port(port);
    	try {
			simplySendPacket(packet);
		} catch (ClException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * Listen for, and accept, incoming connections.
     * 
     * @return A new ConnectionImpl-object representing the new connection.
     * @see Connection#accept()
     */
    public Connection accept() throws IOException, SocketTimeoutException {
    	while(true) {
	    	// TODO: Should we check if packet is corrupted??
	    	// Receive SYN
    		try {
    			this.lastValidPacketReceived = this.internalReceive(Flag.SYN, true);
    		} catch (SocketTimeoutException e) {
    			continue;
    		}
		    this.lastValidPacketReceived.setDest_port(getNextPortNumber());
		    ConnectionImpl conn = new ConnectionImpl(this.lastValidPacketReceived);
		    return conn;
    	}
    }

    /**
     * Send a message from the application.
     * 
     * @param msg
     *            - the String to be sent.
     * @throws ConnectException
     *             If no connection exists.
     * @throws IOException
     *             If no ACK was received.
     * @see AbstractConnection#sendDataPacketWithRetransmit(KtnDatagram)
     * @see no.ntnu.fp.net.co.Connection#send(String)
     */
    public void send(String msg) throws ConnectException, IOException {
    	KtnDatagram packet = constructDataPacket(msg);
    	packet.setChecksum(packet.calculateChecksum());
    	KtnDatagram ack;
    	do {
    		ack = sendDataPacketWithRetransmit(packet);
    	} while(ack == null || ack.getAck() != packet.getSeq_nr());
    }

    /**
     * Wait for incoming data.
     * 
     * @return The received data's payload as a String.
     * @see Connection#receive()
     * @see AbstractConnection#receivePacket(boolean)
     * @see AbstractConnection#sendAck(KtnDatagram, boolean)
     */
    public String receive() throws ConnectException, IOException {
    	while(this.state == State.ESTABLISHED) {
	    	try{
	    		KtnDatagram packet = receivePacket(false);
	    		if(packet.getSeq_nr() != this.lastValidPacketReceived.getSeq_nr() + 1 || !isValid(packet)) {
	    			System.out.println("Corrupted or unexpected package!");
	    			sendAck(this.lastValidPacketReceived, false);
	    		} else {
	    			this.lastValidPacketReceived = packet;
	    			sendAck(this.lastValidPacketReceived, false);
	    			return packet.getPayload().toString();
	    		}
	    	}catch (EOFException e){
	    		serverClose();
	    		return null;
	    	}
    	}
    	
    	throw new IOException("Connection died while waiting for packet!");
    }

    /**
     * Close the connection.
     * 
     * @see Connection#close()
     */
    public void close() throws IOException {
    	KtnDatagram fin = constructInternalPacket(Flag.FIN);
    	KtnDatagram ack = null;
    	while(true) {
	    	do {
	    		try {
					simplySendPacket(fin);
				} catch (ClException e) {
					// TODO Auto-generated catch block
					continue;
				}
				System.out.println("Waiting for ACK with seq.number = " + fin.getSeq_nr());
	    		ack = internalReceiveAck(false, fin);
	    	} while(ack == null || ack.getAck() != fin.getSeq_nr());
	    	this.lastValidPacketReceived = ack;
	    	try{
	    		fin = internalReceive(Flag.FIN, true);
	    	}
	    	catch (EOFException e) {
	    		System.out.println("RECEIVED FIN!");
	    		break;
	    	}
	    	catch (SocketTimeoutException e){
	    		continue;
	    	}
    	}
    	while(true){
    		boolean b = false;
//    		synchronized (this) {
//	    		try {
//	    			System.out.println("STARTING WAIT");
//	    			wait(1000);
//	    			System.out.println("ENDING WAIT");
//	    		} catch (InterruptedException e1) {
//	    			// TODO Auto-generated catch block
//	    			e1.printStackTrace();
//	    		}
//    		}
	    	sendAck(this.disconnectRequest, false);
	    	try{
	    		fin = internalReceive(Flag.FIN, false);
	    	}
	    	catch (EOFException e) {
	    		continue;
	    	}
	    	catch (SocketTimeoutException e){
	    		if(!b) {
	    			b = true;
	    			continue;
	    		}
	    		this.state = State.CLOSED;
	    		break;
	    	}
    	}
//        throw new RuntimeException("NOT IMPLEMENTED");
    }
    
    public void serverClose(){
    	while(true) {
    		try {
    			if (disconnectRequest.getSeq_nr() != this.lastValidPacketReceived.getSeq_nr() + 1){
    				sendAck(this.lastValidPacketReceived, false);
    				return;
    			}
    			sendAck(disconnectRequest, false);
    			KtnDatagram fin = constructInternalPacket(Flag.FIN);
    			KtnDatagram ack = null;
    			while(true){
    				if (ack != null && fin.getSeq_nr() == ack.getSeq_nr()){
    					this.state = State.CLOSED;
    					break;
    				}
    				simplySendPacket(fin);
    				try {
    					ack = internalReceiveAck(false, fin);
    				} catch (EOFException e) {
    					continue;
    				}
    			}
    		} catch (ConnectException e) {
    			// TODO Auto-generated catch block
    			System.out.println("Could not send FIN!");;
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (ClException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    }

    /**
     * Test a packet for transmission errors. This function should only called
     * with data or ACK packets in the ESTABLISHED state.
     * 
     * @param packet
     *            Packet to test.
     * @return true if packet is free of errors, false otherwise.
     */
    protected boolean isValid(KtnDatagram packet) {
    	return packet.getChecksum() == packet.calculateChecksum();
    }

}

