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
import java.io.EOFException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EchoMultiThreadClient extends Application {
    
    private Socket socket;
    private DataOutputStream send;
    private DataInputStream receive;
    private String username;
    private boolean connectionStatus;
    private boolean logged = false;
    private TextArea chat;

    public static void main(String[] args) {
       launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

            Text title = new Text("Enter username to log in");

            chat = new TextArea();
                chat.setEditable(false);
                chat.setWrapText(true);
            
            TextField enter = new TextField();
            
            Button sendBtn = new Button("Send");
                sendBtn.setOnAction(event -> {
                    try{
                        //Check if the user has logged in by entering username first
                        if(logged){
                            String sendText = enter.getText().trim();
                            if(sendText.length() > 0){
                                //If the user logged in and the message they want to send is not empty
                                //Append message to local chat and send to server
                                chat.appendText(username + ": " + sendText + "\n\n");
                                send.writeUTF(username + ": " + sendText + "\n\n");
                                //Clear message entry
                                enter.clear();
                            }
                        }else{
                            if(connectionStatus){
                                String loginText = enter.getText().trim();
                                if(loginText.length() > 0){
                                    chat.appendText("Logged in as: " + enter.getText()+ "\n\n");
                                    title.setText(enter.getText());
                                    //Set this clients username for easy access on the sent message string
                                    username = enter.getText();
                                    enter.clear();
                                    logged = true;
                                }
                            }else{
                                //Alert Just in case the client hasn't closed after a server failure
                                //This is mostly just leftovers from early testing but better safe than sorry
                                Alert alert = new Alert(AlertType.ERROR);
                                    alert.setTitle("No Connection");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Client failed to connect to the server.");
                                alert.showAndWait();
                                primaryStage.close();
                            }
                        }
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                });

            GridPane root = new GridPane();
                root.setAlignment(Pos.CENTER);
                root.setHgap(10);
                root.setVgap(18);
            
            root.add(title, 0, 0, 1, 1);
            root.add(chat, 0, 1, 2, 1);
            root.add(enter, 0, 2);
            root.add(sendBtn, 1, 2);

            
            Scene scene = new Scene(root, 600, 300);

            primaryStage.setTitle("Chat Room");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();

            try{
                socket = new Socket("localhost", 4000); 
                connectionStatus = true;

                send = new DataOutputStream( socket.getOutputStream() );
                receive = new DataInputStream( socket.getInputStream() );

                //Thread dedicated to listening for responses
                Thread listener = new Thread(()->{
                    try{
                        while(true){
                            String response = receive.readUTF();
                            //Platform.runLater to modify main thread node inside the listener thread
                            Platform.runLater(()->{
                                chat.appendText(response);
                            });
                        }
                    }catch(EOFException e){
                        System.exit(1);
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                });
                listener.setDaemon(true);
                listener.start();

            }catch(ConnectException e){
                //Platform.runLater to create an FX window outside of the main FX thread again
                Platform.runLater(()->{
                    //Alert for trying to launch the client without an active server, closes client
                    Alert alert = new Alert(AlertType.WARNING);
                        alert.setTitle("Connection Failed");
                        alert.setHeaderText(null);
                        alert.setContentText("No server available");
                        alert.showAndWait();
                    System.exit(1);
                });
            }catch(IOException e){
                e.printStackTrace();
            }
    
    }

}
