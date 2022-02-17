/**********************************************
Workshop #10
Course:JAC444 - Semester
Last Name:Devakumar	
First Name:Arjun
ID:159076199	
Section:NFF
This assignment represents my own work in accordance with Seneca Academic Policy.
Signature
Date:April 12,2021
**********************************************/
package chatprogram;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

import javafx.application.Platform;

public class User {
	HashMap<String, DataOutputStream> clientmap = new HashMap<String, DataOutputStream>();

	String str; 
	public synchronized void AddClient(String name, Socket socket) {
		try {
			clientmap.put(name, new DataOutputStream(socket.getOutputStream()));
			sendMsg(name + " joined.", "Server");
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public synchronized void RemoveClient(String name) {
		try {
			clientmap.remove(name);
			sendMsg(name + " exit.", "Server");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public synchronized void sendMsg(String msg, String name) throws Exception {
		str = name + " : " + msg;
		Platform.runLater(() -> { 
			ChatServer.logs.setText(ChatServer.logs.getText() + "\n" + str);
		});
		
		Iterator iterator = clientmap.keySet().iterator();
		while (iterator.hasNext()) {
			String clientname = (String) iterator.next();
			clientmap.get(clientname).writeUTF(name + " : " + msg);
		}
	}
}
