/**********************************************
Workshop #9
Course:JAC444 - Semester 2020
Last Name:Moss
First Name:Tanner
ID: 017896150
Section: NDD
This assignment represents my own work in accordance with Seneca Academic Policy.
Tanner Douglas Moss
Date: 12/2/2020
**********************************************/
package ca.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Echoer extends Thread {
	
	private Socket socket;
	private Set<Socket> contactSockets = new HashSet<Socket>();
	private DataInputStream input;
	private DataOutputStream output;
	private String outMsg = "";

	public Echoer(Socket socket){
		this.socket = socket;
	}

	@Override
	public void run() {
		
		try {
			input = new DataInputStream( socket.getInputStream() );
			
			while(true) {
				setOutMsg(input.readUTF());
				if(contactSockets.size() > 0){
					for(Socket contact: contactSockets){
						output = new DataOutputStream( contact.getOutputStream() );
						output.writeUTF(outMsg);	
					}
				}
				setOutMsg("");
			}

		}catch(Exception e) {
			System.out.println("Client Disconnected");
		}
	
	}

	public String getSocketInfo(){
		return this.socket.toString();
	}

	public Socket getSocket(){
		return this.socket;
	}

	public String getOutMsg() {
		return outMsg;
	}

	public void setOutMsg(String outMsg) {
		this.outMsg = outMsg;
	}

	public void addContact(Socket contact){
		contactSockets.add(contact);
	}

}
