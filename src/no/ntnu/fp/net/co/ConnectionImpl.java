/*
 * Created on Oct 27, 2004
 */
package no.ntnu.fp.net.co;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
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
 * @author Sebj���rn Birkeland and Stein Jakob Nordb���
 * @see no.ntnu.fp.net.co.Connection
 * @see no.ntnu.fp.net.cl.ClSocket
 */
public class ConnectionImpl extends AbstractConnection {

    /** Keeps track of the used ports for each server port. */
    private static Map<Integer, Boolean> usedPorts = Collections.synchronizedMap(new HashMap<Integer, Boolean>());
    
    private final static int INITIAL_PORT = 10000;
    private final static int PORT_RANGE = 100;
    private final static int RETRIES = 1;

    private static boolean shouldInitPortNumbers = true;
    
    /**
     * Initialize initial sequence number and setup state machine.
     * 
     * @param myPort
     *            - the local port to associate with this connection
     */
    public ConnectionImpl(int myPort) {
    	// Set the IP and port
    	this.myAddress = getIPv4Address();
    	this.myPort = myPort;
    }
    
    /**
     * Only to be used by accept() to establish a new connection 
     * 
     * @param packet			The SYN-packet
     * @throws ConnectException
     * @throws IOException
     */
    private ConnectionImpl(KtnDatagram packet) throws ConnectException, IOException {
    	// Set up IPs and ports
    	this.myAddress = getIPv4Address();
    	this.myPort = packet.getDest_port();
    	this.remotePort = packet.getSrc_port();
    	this.remoteAddress = packet.getSrc_addr();
    	this.lastValidPacketReceived = packet;
    	
    	// Wait for client to get ready
    	synchronized (this) {
    		try {
    			wait(500);
    		} catch (InterruptedException e) {
    			e.printStackTrace();
    		}
		}
    	// Send SYN-ACK
    	sendAck(this.lastValidPacketReceived, true);
    	this.state = State.SYN_RCVD;
    	// Wait for ACK
    	this.lastValidPacketReceived = internalReceiveAck(false, this.lastDataPacketSent);
    	this.state = State.ESTABLISHED;
    }

    /**
     * Used to initialize distribution of port numbers
     */
    public static void initPortNumbers() {
    	if(shouldInitPortNumbers) {
	    	for (int i = INITIAL_PORT; i < INITIAL_PORT + PORT_RANGE; i++) {
				usedPorts.put(i, false);
			}
    	}
    }
     /**
      * Finds the IP-address for the (hopefully) active network interface
      * 
      * @return	The IP-address
      */
    public String getIPv4Address() {
        try {
        	// Get all interfaces
        	Enumeration<NetworkInterface> networkInterfaces = java.net.NetworkInterface.getNetworkInterfaces();
        	while(networkInterfaces.hasMoreElements()){
        		// Get the addresses
				Enumeration<InetAddress> networkAddresses = networkInterfaces.nextElement().getInetAddresses();
				while(networkAddresses.hasMoreElements()){
					// Get the address
					String address = networkAddresses.nextElement().getHostAddress();
					// Make sure it's not IPv6 and not local
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

    /**
     * Gives the opportunity to have retries
     * 
     * @param synAck		If we should expect a SYN-ACK
     * @param packetToAck	The packet to ACK
     * @return				The ACK-packet
     * @throws EOFException	If received FIN
     * @throws IOException	If connection failed
     */
    public KtnDatagram internalReceiveAck(boolean synAck, KtnDatagram packetToAck) throws EOFException, IOException {
    	KtnDatagram temp;
    	for (int i = 0; i < RETRIES; i++) {
    		temp = receiveAck();
    		if(temp != null && (synAck && temp.getFlag() == Flag.SYN_ACK || !synAck)) {
    			return temp;
    		} 
    	}
    	throw new SocketTimeoutException();
    }

    /**
     * Gives the opportunity to have retries
     * 
     * @param flag			The flag to expect
     * @param internal		If the receive should give up or not
     * @return				The received packet
     * @throws EOFException	If received FIN
     * @throws IOException	If connection failed
     */
    public KtnDatagram internalReceive(Flag flag, boolean internal) throws EOFException, IOException {
    	KtnDatagram temp = null;
    	for (int i = 0; i < RETRIES; i++) {
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
    	// Set the server IP-address 
    	this.remoteAddress = remoteAddress.getHostAddress();
    	// .. and initial port
    	this.remotePort = remotePort;
    	// Send SYN
        KtnDatagram synPacket = constructInternalPacket(Flag.SYN);
        try {
			simplySendPacket(synPacket);
		} catch (ClException e) {
			// Do nothing
		}
        this.state = State.SYN_SENT;
        // Wait for SYN-ACK
        KtnDatagram ack = internalReceiveAck(true, synPacket);
        if(ack == null){
        	throw new SocketTimeoutException("Did not receive SYN-ACK!");
        }
        this.lastValidPacketReceived = ack;
        // Set the new granted port
        this.remotePort = this.lastValidPacketReceived.getSrc_port();
        // Send ACK
        sendAck(this.lastValidPacketReceived, false);
        this.state = State.ESTABLISHED;
    }
    
    /**
     * Creates the local folder "Log" if it doesn't already exist.
     * Creates a new log file for every session
     */
    public static void fixLogDirectory() {
    	File log = new File("Log");
    	// Create directory if not already existing
    	if(!log.isDirectory()) {
    		log.mkdir();
    	}
    	String name = "logfile";
    	String path = "Log/" + name + ".txt";
    	File logFile = new File(path);
    	int counter = 0;
    	String newName = "";
    	// TODO: Maximum number of logs
    	while(logFile.exists()) {
    		name = path.substring(4, path.length() - 4);
    		newName = "Log/" + name + ++counter + ".txt";
    		logFile = new File(newName);
    	}
    	File ordFile = new File(path);
    	if(ordFile.exists() ) {
    		if(ordFile.length() == 0) {
    			return;
    		}
    		ordFile.renameTo(logFile);
    		ordFile = new File(path);
    	}
    	try {
    		ordFile.createNewFile();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * Finds the next available port number from INITIAL_PORT and up
     * (Does not check if the port is already in use by another program)
     * 
     * @return				The port
     * @throws IOException	If all ports are taken (specified by PORT_RANGE)
     */
    private static int getNextPortNumber() throws IOException {
    	for (int i = INITIAL_PORT; i < INITIAL_PORT + PORT_RANGE; i++) {
			if(!usedPorts.get(i)) {
				usedPorts.put(i, true);
				return i;
			}
		}
    	throw new IOException("Out of ports!");
    }
    
    /**
     * Listen for, and accept, incoming connections.
     * 
     * @return A new ConnectionImpl-object representing the new connection.
     * @see Connection#accept()
     */
    public Connection accept() throws IOException, SocketTimeoutException {
    	while(true) {
	    	// Receive SYN
    		try {
    			this.lastValidPacketReceived = this.internalReceive(Flag.SYN, true);
    		} catch (SocketTimeoutException e) {
    			continue;
    		}
    		// Tell the client wich port to use
		    this.lastValidPacketReceived.setDest_port(getNextPortNumber());
		    return new ConnectionImpl(this.lastValidPacketReceived);
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
    public void send(String msg) throws ConnectException, IOException, SocketTimeoutException {
    	int timeoutCounter = 0;
    	KtnDatagram ack = null;
    	// Construct packet
    	KtnDatagram packet = constructDataPacket(msg);
    	// Calculate checksum
    	packet.setChecksum(packet.calculateChecksum());
    	do {
    		if(timeoutCounter > RETRIES * 2) {
    			throw new SocketTimeoutException();
    		}
    		try {
    			// Send the constructed packet with the given message
				simplySendPacket(packet);
				// Receive ACK
				ack = internalReceiveAck(false, packet);
			} catch (ClException e) {
				ack = null;
				continue;
			} catch (SocketTimeoutException e) {
				ack = null;
			}
			timeoutCounter++;
    	} while(ack == null || ack.getAck() != packet.getSeq_nr());
    	this.lastValidPacketReceived = ack;
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
    	if(this.state != State.ESTABLISHED) {
    		throw new IOException("Connection is closed!");
    	}
    	boolean shouldThrowException = true;
    	while(this.state == State.ESTABLISHED)  {
	    	try{
	    		// Wait for incoming packet
	    		KtnDatagram packet = receivePacket(false);
	    		// Check if the packet is valid
	    		if(packet.getSeq_nr() != this.lastValidPacketReceived.getSeq_nr() + 1 || !isValid(packet)) {
	    			sendAck(this.lastValidPacketReceived, false);
	    		} else {
	    			this.lastValidPacketReceived = packet;
	    			// Wait for sender to get ready
	    			synchronized (this) {
	    				try {
							wait(200);
						} catch (InterruptedException e) {
							// Do nothing
						}
					}
	    			// Send ACK
	    			sendAck(this.lastValidPacketReceived, false);
	    			return packet.getPayload().toString();
	    		}
	    	}catch (EOFException e){
	    		// FIN received, close server
	    		serverClose();
	    		shouldThrowException = false;
	    	}
    	}
    	if(shouldThrowException) {
    		throw new IOException("Connection died while waiting for packet!");
    	}
    	return null;
    }

    /**
     * Close the client's connection.
     * 
     * @see Connection#close()
     */
    public void close() throws IOException {
    	KtnDatagram fin = constructInternalPacket(Flag.FIN);
    	KtnDatagram ack = null;
    	while(true) {
	    	do {
	    		try {
	    			// Send FIN
					simplySendPacket(fin);
				} catch (ClException e) {
					continue;
				}
	    		// Wait for ACK
	    		ack = internalReceiveAck(false, fin);
	    	} while(ack == null || ack.getAck() != fin.getSeq_nr());
	    	this.lastValidPacketReceived = ack;
	    	try{
	    		// Wait for FIN
	    		fin = internalReceive(Flag.FIN, true);
	    	}
	    	catch (EOFException e) {
	    		// FIN received, move on..
	    		break;
	    	}
	    	catch (SocketTimeoutException e){
	    		// Timed out. Try again!
	    		continue;
	    	}
    	}
    	while(true){
    		// Wait for server to get ready
    		synchronized (this) {
	    		try {
	    			wait(200);
	    		} catch (InterruptedException e1) {
	    			e1.printStackTrace();
	    		}
    		}
    		// Send ACK
	    	sendAck(this.disconnectRequest, false);
	    	try{
	    		this.state = State.CLOSE_WAIT;
	    		internalReceive(Flag.FIN, true);
	    	}
	    	catch (EOFException e) {
	    		// Received new FIN. Start over!
	    		continue;
	    	}
	    	catch (SocketTimeoutException e){
	    		this.state = State.CLOSED;
	    		return;
	    	}
    	}
    }
    
    /**
     * Close the server's connection
     */
    private void serverClose(){
    	while(true) {
    		try {
    			// Check the already received FIN
    			if (disconnectRequest.getSeq_nr() != this.lastValidPacketReceived.getSeq_nr() + 1){
    				// Send ACK
    				sendAck(this.lastValidPacketReceived, false);
    				return;
    			}
    			// Wait for client to get ready
    			synchronized (this) {
    				try {
    					wait(200);
    				} catch (InterruptedException e1) {
    					// Do nothing
    				}
    			}
    			// Send ACK
    			sendAck(disconnectRequest, false);
    			KtnDatagram fin = constructInternalPacket(Flag.FIN);
    			KtnDatagram ack = null;
    			while(true){
    				// Validate ACK
    				if (ack != null && fin.getSeq_nr() == ack.getAck()){
    					this.state = State.CLOSED;
    					// TODO: Sjekk at porten faktisk blir frigjort!
    					ConnectionImpl.usedPorts.put(this.myPort, false);
    					return;
    				}
    				// Wait for client to get ready
    				synchronized (this) {
    		    		try {
    		    			wait(200);
    		    		} catch (InterruptedException e1) {
    		    			// Do nothing
    		    		}
    	    		}
    				// Send FIN
    				simplySendPacket(fin);
    				try {
    					// Wait for ACK
    					ack = internalReceiveAck(false, fin);
    					continue;
    				} catch (EOFException e) {
    					// FIN received. Start over!
    					continue;
    				}
    			}
    		} catch (Exception e) {
    			// Do nothing
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

