/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BookStoreApplication;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;
/**
 *
 * @author dabrahmb
 */
public class OwnerScreen extends BookStore{
    
    static Scene OwnerScene;
    
    public static void mainScreen(Stage applicationStage, Scene primaryScene){
        
        // layout container gridpane used to position elements on screen
        GridPane layout = new GridPane();        
        layout.setAlignment(javafx.geometry.Pos.CENTER);
        layout.setStyle("-fx-background-color: #D0F0FF;;");
        layout.setHgap(10); // horizontal gap between elements
        layout.setVgap(10); // vertical gap between elements
        
        OwnerScene = new Scene(layout, primaryScene.getWidth(), primaryScene.getHeight());
        
        // defining new buttons     
        Button bookButton = new Button();
        Button customerButton = new Button();
        Button exitButton = new Button();
        
        bookButton.setText("Books");
        customerButton.setText("Customers");
        exitButton.setText("Logout");
        
        bookButton.setPrefWidth(150);
        customerButton.setPrefWidth(150);
        exitButton.setPrefWidth(150);
        
        // calling parent method to use hover effect
        buttonEffects(bookButton);
        buttonEffects(customerButton);
        buttonEffects(exitButton);
        
        // defining behaviour of buttons when clicked
        bookButton.setOnAction(e -> BookList.display(applicationStage, OwnerScene));
        customerButton.setOnAction(e->CustomerList.display(applicationStage, OwnerScene));
        exitButton.setOnAction(e-> applicationStage.setScene(primaryScene));// changes scene to login screen, when logout button is pressed
        
        // adding buttons to the layout, positioning them in the center as shown in project manual. 
        layout.add(bookButton, 1, 2);
        layout.add(customerButton, 1, 3);
        layout.add(exitButton, 1, 4);
        
        applicationStage.setScene(OwnerScene); // setting the scene.
                
    
    }
    
}
