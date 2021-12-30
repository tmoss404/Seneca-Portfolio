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

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ChatServer extends Application {

	private Set<Echoer> clients = new HashSet<Echoer>();

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		TextArea serverLog = new TextArea();
			serverLog.setEditable(false);
			serverLog.setWrapText(true);
		Scene scene = new Scene(serverLog, 500, 500);

		primaryStage.setTitle("Multi-threaded Server");
        primaryStage.setScene(scene);
		primaryStage.show();
		
		Thread serverStart = new Thread(()->{
			try(ServerSocket serverSocket = new ServerSocket(4000)){
				//Log server start
				serverLog.appendText("Server started at " + new Date() + "\n\n");
				while(true) {
					//Accept client connection, start thread
					Echoer client = new Echoer(serverSocket.accept());
					//Terminate client connections on server shutdown
					client.setDaemon(true);
					client.start();
					serverLog.appendText("Connection From " + client.getSocketInfo() + " at " + new Date() + "\n\n");
					
					//We don't try to interate through an empty array before there are any clients
					if(clients.size() > 0){
						for(Echoer c: clients){
							//Make sure there's no way we give a client it's own socket as a contact
							if(c.getSocket().getPort() != client.getSocket().getPort() ){
								//Have the new client trade contact information with every existing client
								client.addContact(c.getSocket());
								c.addContact(client.getSocket());
							}
						}
					}
					System.out.println( client.getSocket() );
					clients.add(client);
					
				} 
			}catch(IOException e) {
				System.out.println("Server Exception" + e.getMessage());
			}
		});
		//Terminate the thread when the application closes
		serverStart.setDaemon(true);
		serverStart.start();
	}
}
