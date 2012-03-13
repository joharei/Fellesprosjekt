/*
 * Created on Oct 27, 2004
 */
package no.ntnu.fp.net.co;

import java.io.EOFException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

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
public class ConnectionImpl extends AbstractConnection implements Runnable {

    /** Keeps track of the used ports for each server port. */
    private static Map<Integer, Boolean> usedPorts = Collections.synchronizedMap(new HashMap<Integer, Boolean>());
    
    private final static int INITIAL_PORT = 10000;
    private final static int PORT_RANGE = 100;
    
    //Testing the A2 framework
    private KtnDatagram datagram;
    /**
     * Initialise initial sequence number and setup state machine.
     * 
     * @param myPort
     *            - the local port to associate with this connection
     */
    public ConnectionImpl(int myPort) {
    	datagram = new KtnDatagram();
    	this.myPort = myPort;
//    	throw new RuntimeException("NOT IMPLEMENTED");
    }
    
    public ConnectionImpl(KtnDatagram packet) {
    	this.myPort = packet.getDest_port();
    	this.remotePort = packet.getSrc_port();
    	this.remoteAddress = packet.getSrc_addr();
    	this.lastValidPacketReceived = packet;
    }

    private String getIPv4Address() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        }
        catch (UnknownHostException e) {
            return "127.0.0.1";
        }
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
        // TODO: Should we check if packet is corrupted??
        this.state = State.SYN_SENT;
        this.lastValidPacketReceived = sendDataPacketWithRetransmit(synPacket);
        sendAck(this.lastValidPacketReceived, false);
        this.state = State.ESTABLISHED;
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
    
    public static void main(String[] args) {
		Connection c = new ConnectionImpl(1337);
		try {
			Connection con = c.accept();
			con.send("test");
			con.receive();
		} catch (Exception e) {
			
		}
	}
    
    /**
     * Listen for, and accept, incoming connections.
     * 
     * @return A new ConnectionImpl-object representing the new connection.
     * @see Connection#accept()
     */
    public Connection accept() throws IOException, SocketTimeoutException {
    	// TODO: Should we check if packet is corrupted??
    	// Receive SYN
    	this.lastValidPacketReceived = this.receivePacket(true);
    	if(this.lastValidPacketReceived.getFlag() != Flag.SYN) {
    		throw new IOException("Received packet did not contain SYN flag");
    	}
    	this.lastValidPacketReceived.setSrc_port(getNextPortNumber());
    	ConnectionImpl conn = new ConnectionImpl(this.lastValidPacketReceived);
    	Thread t = new Thread(conn);
    	t.start();
    	return conn;
    	//throw new RuntimeException("NOT IMPLEMENTED");
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
        throw new RuntimeException("NOT IMPLEMENTED");
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
        return receivePacket(false).getPayload().toString();
    	//return datagram.getPayload().toString();
    }

    /**
     * Close the connection.
     * 
     * @see Connection#close()
     */
    public void close() throws IOException {
        throw new RuntimeException("NOT IMPLEMENTED");
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
        throw new RuntimeException("NOT IMPLEMENTED");
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// Send SYN-ACK
		try {
			sendAck(this.lastValidPacketReceived, true);
			this.lastValidPacketReceived = receiveAck();
		} catch(Exception e) {
			this.state = State.CLOSED;
		}
		// Wait for ACK
	}
}
