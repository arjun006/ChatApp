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

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ChatServer extends Application {

	public static Label logs = new Label("[Chat Server Logs]");
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		Thread startServer = new Thread(new StartServer());
		startServer.start();
		
		ScrollPane layout = new ScrollPane();
		layout.setPrefSize(400, 600);
		layout.setContent(logs);
		
		primaryStage.setTitle("Multi-threaded Server");
		primaryStage.setScene(new Scene(layout));
		primaryStage.show();
	}

}
