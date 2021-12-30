/**********************************************
Workshop #6
Course:JAC444 - Semester 2020
Last Name: Moss
First Name: Tanner
ID: 017896150
Section: NDD
This assignment represents my own work in accordance with Seneca Academic Policy.
Tanner Douglas Moss
Date: 11/10/2020
**********************************************/
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application {
    
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public void process(Stage primaryStage, String name, String gender, String year) {
        //Values to store the results in for building the results window
        Boolean exactMatch = false;
        String rank = ""; 
        String resultName = "";

        File file = new File("Babynames files/babynamesranking" + year + ".txt");
        try(BufferedReader in = new BufferedReader( new FileReader(file) )){
            String line;
            ArrayList<String> data = new ArrayList<String>();
            while((line = in.readLine()) != null)
                data.add(line);

            ArrayList<String> found = new ArrayList<String>();
            for(String rec : data)
                if( rec.toLowerCase().contains(name.toLowerCase()) )
                    found.add(rec);
            //If we found exactly one match
            if(found.size() == 1){
                //Split the result by whitespace
                //Index [0] = Ranking
                //Index [1] = Boy name
                //Index [2] = Boy name count
                //Index [3] = Girl name
                //Index [4] = Girl name count
                String[] result = found.get(0).split("\\s+");
                rank = result[0];
                if( result[1].equalsIgnoreCase(name) && gender.equalsIgnoreCase("M") ){
                    exactMatch = true;
                    resultName = result[1];
                } else if( result[3].equalsIgnoreCase(name) && gender.equalsIgnoreCase("F") ){
                    exactMatch = true;
                    resultName = result[3];
                }
            //If we found more than one match for the name
            }else if(found.size() > 1){
                String[][] results = new String[found.size()][]; 
                for(int i = 0; i < found.size(); i++)
                    results[i] = found.get(i).split("\\s+");
                    //Split each result in the case that the name belongs to either gender
                for(String[] entry : results){
                    //Check if the boy name in either result is the name we're looking for
                    if( entry[1].equalsIgnoreCase(name) && gender.equalsIgnoreCase("M") ){
                        exactMatch = true;
                        rank = entry[0];
                        resultName = entry[1];
                        break;
                    }else if( entry[3].equalsIgnoreCase(name) && gender.equalsIgnoreCase("F") ){//Check if the girl name in either result is the name we're looking for
                        exactMatch = true;
                        rank = entry[0];
                        resultName =  entry[3];
                        break;
                    }
                }
            }

            if(exactMatch == true){
                //Create and show the result window
                primaryStage.hide();
                Stage resultStage = new Stage();

                //Result HBox containing the result text
                HBox resultBox = new HBox(10);
                    resultBox.setAlignment(Pos.CENTER);
                    resultBox.setPadding(new Insets(0, 0, 50, 0));
                    String genderText = gender.equalsIgnoreCase("f") ? "Female" : "Male";
                    Text resultLabel = new Text(genderText + " name " + resultName + " is ranked #" + rank + " in " + year + " year");
                    resultBox.getChildren().add(resultLabel);
                
                //Prompt HBox containing prompt text
                HBox promptBox = new HBox(10);
                    promptBox.setPadding(new Insets(0, 0, 10, 0));
                    promptBox.setAlignment(Pos.CENTER);
                    Text promptLabel = new Text("Do you want to search for another Name:");
                    promptBox.getChildren().add(promptLabel);

                //Buttons HBox containing "yes" and "no" buttons
                HBox buttons = new HBox(10);
                    buttons.setAlignment(Pos.CENTER);
                    buttons.setSpacing(40);
                    Button yes = new Button("Yes");
                    yes.setMinHeight(25);
                    yes.setMinWidth(80);
                    yes.setOnAction(event -> {
                        resultStage.close();
                        primaryStage.show();
                    });

                    Button no = new Button("No");
                    no.setMinHeight(25);
                    no.setMinWidth(80);
                    no.setOnAction(event -> {
                        resultStage.close();
                        primaryStage.close();
                    });
                    buttons.getChildren().addAll(yes, no);

                GridPane root = new GridPane();
                    root.setAlignment(Pos.CENTER);
                    root.add(resultBox, 0, 0);
                    root.add(promptBox, 0, 3);
                    root.add(buttons, 0, 4);
                
                Scene scene = new Scene(root, 380, 250);
                resultStage.setTitle("Search Name Ranking Application");
                resultStage.setScene(scene);
                resultStage.setResizable(false);
                resultStage.show();
                    
            }else if(exactMatch == false){
                //Account for finding a portion of a name ex. "tay" inside "taylor"
                //where it will register as "found" pulling from the data array but
                //doesn't actually equal the name index of the result array 
                Alert alert = new Alert(AlertType.WARNING);
                    String genderText = gender.equalsIgnoreCase("f") ? "Female" : "Male";
                    alert.setTitle("Name Not Found");
                    alert.setHeaderText(null);
                    alert.setContentText(genderText + " name " + name + " not found within the rankings for " + year + ".");
                alert.showAndWait();
            }
            
        }catch(FileNotFoundException e){
            Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Year Unavailable");
                alert.setHeaderText(null);
                alert.setContentText("The record for "+ year +" is missing.\n"
                + "Available years include 2009-2018.");
            alert.showAndWait();
        }catch(IOException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        Label yearLabel = new Label("Enter the Year:");
        TextField year = new TextField();
        year.setMaxWidth(100);

        Label genderLabel = new Label("Enter the Gender:");
        TextField gender = new TextField();
        gender.setMaxWidth(30);
        
        Label nameLabel = new Label("Enter the Name:");
        TextField name = new TextField();
        name.setMaxWidth(100);
        
        //Buttons HBox containing submit and exit buttons
        HBox buttons = new HBox();
        buttons.setSpacing(60);
            Button submit = new Button("Submit Query");
            submit.setOnAction( event -> {
                if(year.getText().matches("[0-9]{4}")){
                    if(gender.getText().matches("[f,F,m,M]{1}")){
                        process( primaryStage, name.getText(), gender.getText(), year.getText());
                    }else{
                        Alert alert = new Alert(AlertType.WARNING);
                            alert.setTitle("Invalid Gender");
                            alert.setHeaderText(null);
                            alert.setContentText("Please enter a valid gender value.");
                        alert.showAndWait();
                    }
                }else{
                    Alert alert = new Alert(AlertType.WARNING);
                        alert.setTitle("Invalid Year");
                        alert.setHeaderText(null);
                        alert.setContentText("Please enter a valid year value.");
                    alert.showAndWait();
                }
                    
            });

            Button exit = new Button("Exit");
            exit.setOnAction( event -> primaryStage.close() );
        buttons.getChildren().addAll(submit, exit);
        
        ColumnConstraints column1 = new ColumnConstraints();
            column1.setHalignment(HPos.LEFT);
        ColumnConstraints column2 = new ColumnConstraints();
            column2.setHalignment(HPos.LEFT);

        GridPane root = new GridPane();
            root.setAlignment(Pos.CENTER);
            root.setHgap(10);
            root.setVgap(18);
            root.getColumnConstraints().add(column1); 
            root.getColumnConstraints().add(column2);
            root.add(yearLabel, 0, 0);
            root.add(year, 1, 0);
            root.add(genderLabel, 0, 1);
            root.add(gender, 1, 1);
            root.add(nameLabel, 0, 2);
            root.add(name, 1, 2);
            root.add(buttons, 0, 3, 2, 1);

        Scene scene = new Scene(root, 380, 250);
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Search Name Ranking Application");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}