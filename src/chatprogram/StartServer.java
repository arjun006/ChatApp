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

import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.application.Platform;

public class StartServer implements Runnable {
	Socket socket = null; 
	User user = new User(); 
	ServerSocket server_socket = null; 

	int count = 0;
	Thread thread[] = new Thread[5];


	@Override
	public void run() {
		try {
			server_socket = new ServerSocket(8080);
			while (true) {
				socket = server_socket.accept(); 
				Date d1 = new Date();
				SimpleDateFormat df = new SimpleDateFormat("MM/dd/YYYY HH:mm a");
				String formattedDate = df.format(d1);
				
				Platform.runLater(() -> {
					ChatServer.logs.setText(ChatServer.logs.getText() + "\nNew client connected. " 
							+ formattedDate);
				});

				thread[count] = new Thread(new ChatService(user, socket));
				thread[count].start();
				count++;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}