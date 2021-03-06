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

import java.io.*;
import java.net.*;
import java.util.Scanner;

//import chat.server.ChatServer;
//import chatprogram.server.StartServer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Client extends Application {

	public static Label logs = new Label("Client Chat:");
	private TextField enterName = new TextField();
	public static TextField enterMessage = new TextField();
	private Scene scene1, scene2;
	private Button submitName = new Button("Enter Chat");

	public static String name = "";

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		GridPane root1 = new GridPane();
		root1.setPrefSize(400, 200);
		root1.setPadding(new Insets(0, 20, 20, 20)); 
		root1.setVgap(15);
		root1.setHgap(5); 
		root1.setAlignment(Pos.CENTER);
		root1.add(new Label("Enter your name here: "), 0, 0);
		root1.add(enterName, 0, 1);
		root1.add(submitName, 1, 1);

		scene1 = new Scene(root1);
		primaryStage.setScene(scene1);
		primaryStage.setTitle("Chat Room");
		primaryStage.show();

		ConnectServer connectServer = new ConnectServer();

	
		submitName.setOnAction(e -> {
		
			name = enterName.getText();
			Thread connectServerThread = new Thread(connectServer);
			connectServerThread.start();

		
			ScrollPane layout = new ScrollPane();
			layout.setPrefSize(400, 600);
			layout.setContent(logs);

			BorderPane root2 = new BorderPane();
			root2.setPrefSize(350, 400);
			root2.setCenter(layout);
			root2.setBottom(enterMessage);

			scene2 = new Scene(root2);
			primaryStage.setScene(scene2);
		});

		
		Client.enterMessage.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				DataOutputStream out = connectServer.getDataOuputStream();
				String msg = Client.enterMessage.getText();
				try {
					out.writeUTF(msg); 
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				enterMessage.setText(""); 
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
}

class ConnectServer implements Runnable {
	Socket socket = null; 
	DataInputStream in = null; 
	DataOutputStream out = null; 

	public DataOutputStream getDataOuputStream() {
		return out;
	}

	@Override
	public void run() {

		try {
			socket = new Socket("localhost", 8080);

			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());

			out.writeUTF(Client.name);
			System.out.println(Client.name + " : succesfully joined the chat room: ");

		} catch (IOException e) {
		}

		try {
			while (true) {
				String str = in.readUTF();
				Platform.runLater(() -> {
					Client.logs.setText(Client.logs.getText() + "\n" + str);
				});
			}
		} catch (IOException e) {
		}

	}
}