import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Optional;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class App extends Application {

    final Insets BOXPAD = new Insets(18, 12, 18, 15);
    final int BTNWIDTH = 95;
    final int NAMETEXTWIDTH = 525;
    final long RECORDSIZE = 99;
    static long pointer = 0; //Used to navigate the records with file.seek(pointer); 
    //It will always be set after reading a record - it will indicate the end of the current record/ the beginning of the next
    //Adding a record sets this to 0 since add clears the fields

    @Override
    public void start(Stage primaryStage) throws IOException {

        FlowPane root = new FlowPane();

        //Contains the first name label and textfield
        HBox box1 = new HBox(10);
        box1.setAlignment(Pos.CENTER);
        box1.setPadding(BOXPAD);
            Label fNameLabel = new Label("First Name:");
            TextField fName = new TextField();
            fName.setMinWidth(NAMETEXTWIDTH);
        box1.getChildren().addAll(fNameLabel, fName);

        //Contains the last name label and textfield
        HBox box2 = new HBox(10);
        box2.setAlignment(Pos.CENTER);
        box2.setPadding(BOXPAD);
            Label lNameLabel = new Label("Last Name:");
            TextField lName = new TextField();
            lName.setMinWidth(NAMETEXTWIDTH);
        box2.getChildren().addAll(lNameLabel, lName);

        //Contains the city, province, and postal code labels and entry nodes
        HBox box3 = new HBox(8);
        box3.setAlignment(Pos.CENTER);
        box3.setPadding(BOXPAD);
            Label cityLabel = new Label("City:");
            TextField city = new TextField();
            city.setMinWidth(180);
            city.setMaxWidth(180);

            Label provLabel = new Label("Province:");
            MenuButton province = new MenuButton("Select Province");
            province.setMinWidth(137);
                MenuItem[] provs = {new MenuItem("AB"),
                                    new MenuItem("BC"), 
                                    new MenuItem("MB"), 
                                    new MenuItem("NB"), 
                                    new MenuItem("NL"), 
                                    new MenuItem("NT"),
                                    new MenuItem("NS"),
                                    new MenuItem("NU"),
                                    new MenuItem("ON"),
                                    new MenuItem("PE"),
                                    new MenuItem("QC"), 
                                    new MenuItem("SK"), 
                                    new MenuItem("YT")
                                    };
                for(MenuItem prov: provs){
                    prov.setOnAction(new EventHandler<ActionEvent>(){
                        @Override
                        public void handle(ActionEvent arg0) {
                            province.setText(prov.getText());
                        } 
                    });
                    province.getItems().add(prov);
                }

            Label postLabel = new Label("Postal Code:");
            TextField postalCode = new TextField();
            postalCode.setMaxWidth(80);
        box3.getChildren().addAll(cityLabel, city, provLabel, province, postLabel, postalCode);

        //Contains all of the control buttons
        HBox box4 = new HBox(8);
        box4.setAlignment(Pos.CENTER);
        box4.setPadding(BOXPAD);
            
            //Add button implementation
            Button btnAdd = new Button("Add");
            btnAdd.setMinWidth(BTNWIDTH);
            btnAdd.setOnAction(new EventHandler<ActionEvent>(){

                @Override
                public void handle(ActionEvent e) {
                    try( RandomAccessFile file = new RandomAccessFile("src/Adresses.dat", "rw") ){
                        //Check to see if any entry fields are empty or only whitespace, and if the dropdown box is still on the default selection
                        if(!fName.getText().trim().isEmpty() && !lName.getText().trim().isEmpty() && !city.getText().trim().isEmpty()
                                && !province.getText().equals("Select Province") && !postalCode.getText().trim().isEmpty() ){

                            writeRecord(file, //File
                                        file.length(), //Write Target (byte)
                                        fName.getText(), //First Name
                                        lName.getText(), //Last Name
                                        city.getText(), //City
                                        province.getText(), //Province
                                        postalCode.getText() //Postal Code
                                        );

                            pointer = 0; //Set the pointer value used for navigation to 0 since we want to clear the entry fields
                            fName.clear(); //Clear the entry fields and set to default
                            lName.clear();
                            city.clear();
                            province.setText("Select Province");
                            postalCode.clear();

                        }else{
                            //Alert window for incomplete entry fields
                            Alert alert = new Alert(AlertType.WARNING);
                                alert.setTitle("Information Incomplete");
                                alert.setHeaderText(null);
                                alert.setContentText("Please fill in each entry field.");

                                alert.showAndWait();
                        }

                    }catch(IOException err){
                        System.err.println(err);
                    }   
                }
            });

            //First button implementation
            Button btnFirst = new Button("First");
            btnFirst.setMinWidth(BTNWIDTH);
            btnFirst.setOnAction(new EventHandler<ActionEvent>(){

                @Override
                public void handle(ActionEvent e) {
                    try( RandomAccessFile file = new RandomAccessFile("src/Adresses.dat", "rw") ){
                        //Seek to index 0 to read the first record
                        file.seek(0);
                        //Read the desired record from the file and trim out the whitespace used to pad the to fixed length
                        fName.setText(file.readUTF().trim());
                        lName.setText(file.readUTF().trim());
                        city.setText(file.readUTF().trim());
                        province.setText(file.readUTF().trim());
                        postalCode.setText(file.readUTF().trim());
                        //Save the pointer as the end of the read record, and the start of the next one
                        pointer = file.getFilePointer();
                    }catch(IOException err){
                        System.err.println(err);
                    }
                }
                
            });
            
            //Previous Button Implementation
            Button btnPrev = new Button("Previous");
            btnPrev.setMinWidth(BTNWIDTH);
            btnPrev.setOnAction(new EventHandler<ActionEvent>(){

                @Override
                public void handle(ActionEvent e) {
                    try( RandomAccessFile file = new RandomAccessFile("src/Adresses.dat", "rw") ){
                        if(pointer >= RECORDSIZE*2){
                            //Move back two records, since we are always at the end of a record after reading we need to move 
                            //past the current one and then go to the start of the desired one
                            file.seek(pointer - RECORDSIZE*2);
                            //Read the desired record from the file and trim out the whitespace used to pad the to fixed length
                            fName.setText(file.readUTF().trim());
                            lName.setText(file.readUTF().trim());
                            city.setText(file.readUTF().trim());
                            province.setText(file.readUTF().trim());
                            postalCode.setText(file.readUTF().trim());
                            //Save the pointer as the end of the read record, and the start of the next one
                            pointer = file.getFilePointer();
                        }
                        
                    }catch(IOException err){
                        System.err.println(err);
                    }
                }
                
            });

            //Next button implementation
            Button btnNext = new Button("Next");
            btnNext.setMinWidth(BTNWIDTH);
            btnNext.setOnAction(new EventHandler<ActionEvent>(){

                @Override
                public void handle(ActionEvent e) {
                    try( RandomAccessFile file = new RandomAccessFile("src/Adresses.dat", "rw") ){
                        if(pointer != file.length()){
                            //Since we are always at the end of a record after reading, next only needs to seek to the saved pointer
                            //and read the record
                            file.seek(pointer);
                            //Read the desired record from the file and trim out the whitespace used to pad the to fixed length
                            fName.setText(file.readUTF().trim());
                            lName.setText(file.readUTF().trim());
                            city.setText(file.readUTF().trim());
                            province.setText(file.readUTF().trim());
                            postalCode.setText(file.readUTF().trim());
                            //Save the pointer as the end of the read record, and the start of the next one
                            pointer = file.getFilePointer();
                        }
                        
                    }catch(IOException err){
                        System.err.println(err);
                    }
                }
                
            });

            //Last Button implementation
            Button btnLast = new Button("Last");
            btnLast.setMinWidth(BTNWIDTH);
            btnLast.setOnAction(new EventHandler<ActionEvent>(){

                @Override
                public void handle(ActionEvent e) {
                    try( RandomAccessFile file = new RandomAccessFile("src/Adresses.dat", "rw") ){
                        //Jump to the end of the file, then move back to the read the last record
                        file.seek(file.length() - RECORDSIZE);
                        //Read the desired record from the file and trim out the whitespace used to pad the to fixed length
                        fName.setText(file.readUTF().trim());
                        lName.setText(file.readUTF().trim());
                        city.setText(file.readUTF().trim());
                        province.setText(file.readUTF().trim());
                        postalCode.setText(file.readUTF().trim());
                        //Save the pointer as the end of the read record, and the start of the next one
                        pointer = file.getFilePointer();

                    }catch(IOException err){
                        System.err.println(err);
                    }
                }
                
            });

            //Update button implementation
            Button btnUpdate = new Button("Update");
            btnUpdate.setMinWidth(BTNWIDTH);
            btnUpdate.setOnAction(new EventHandler<ActionEvent>(){

                @Override
                public void handle(ActionEvent e) {
                    try( RandomAccessFile file = new RandomAccessFile("src/Adresses.dat", "rw") ){
                        //Check to see if the pointer is 0, this means there is no record selected
                        //if a record is selected the pointer = the byte at the end of the record/ start of the next one 
                        if(pointer != 0){
                            //Check to see if any entry fields are empty or only whitespace, and if the dropdown box is still on the default selection
                            if(!fName.getText().trim().isEmpty() && !lName.getText().trim().isEmpty() && !city.getText().trim().isEmpty()
                                && !province.getText().equals("Select Province") && !postalCode.getText().trim().isEmpty() ){
                                
                                //Alert dialog to confirm an update since data will be lost on update we don't want to accidentally update
                                Alert alert = new Alert(AlertType.CONFIRMATION);
                                    alert.setTitle("Confirmation");
                                    alert.setHeaderText("Update: " + fName.getText() + " " + lName.getText());
                                    alert.setContentText("Are you sure you want to update?");
                                    
                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.isPresent() && result.get() == ButtonType.OK){
                                        writeRecord(file, //File
                                            (pointer - RECORDSIZE), //Write Target (byte)
                                            fName.getText(), //First Name
                                            lName.getText(), //Last Name
                                            city.getText(), //City
                                            province.getText(), //Province
                                            postalCode.getText() //Postal Code
                                            );
                                    }
                                
                            }else{
                                //Alert window for incomplete entry fields
                                Alert alert = new Alert(AlertType.WARNING);
                                    alert.setTitle("Information Incomplete");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Please fill in each entry field.");

                                    alert.showAndWait();
                            }
                        }else{
                            //Alert window for incomplete entry fields
                            Alert alert = new Alert(AlertType.WARNING);
                                alert.setTitle("No Record Selected");
                                alert.setHeaderText(null);
                                alert.setContentText("Please select a record to update.");

                                alert.showAndWait();
                        }
                        
                    }catch(IOException err){
                        System.err.println(err);
                    }   
                }
            });
        box4.getChildren().addAll(btnAdd, btnFirst, btnPrev, btnNext, btnLast, btnUpdate);

        root.getChildren().addAll(box1, box2, box3, box4);

        Scene scene = new Scene(root, 660, 235);
        primaryStage.setTitle("Address Book");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public void writeRecord(RandomAccessFile file, Long target, String first, String last, String city, String prov, String postal) throws IOException {
        file.seek(target); //Navigate to the byte target
        file.writeUTF(String.format( "%-25.25s%n", first )); //Write each line fixed to their respective max lengths
        file.writeUTF(String.format( "%-25.25s%n", last ));
        file.writeUTF(String.format( "%-25.25s%n", city ));
        file.writeUTF(String.format( "%-2.2s%n", prov ));
        file.writeUTF(String.format( "%-7.7s%n", postal ));
    }
    public static void main(String[] args){
        launch(args);
    }
}