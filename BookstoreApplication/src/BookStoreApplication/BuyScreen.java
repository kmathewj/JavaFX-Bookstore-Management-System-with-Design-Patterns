/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BookStoreApplication;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.geometry.Insets;

/**
 *
 * @author dabrahmb
 */
public class BuyScreen extends CustomerScreen {
    static Scene buyScene;
    
    public static void buydisplay(Stage applicationStage, Scene primaryScene, int index, Double totalPrice, boolean redeemOrNot){
        
        Customer loginCustomer = CustomerList.customers.get(index);

        // defining gridpane container to position elements on screen. 
        GridPane layout = new GridPane();        
        layout.setAlignment(javafx.geometry.Pos.CENTER);
        layout.setStyle("-fx-background-color: #D0F0FF;;");
        layout.setHgap(10); // horizontal gap between elements
        layout.setVgap(10); // vertical gap between elements
        
        
        // setting behaviour and style for the logout button
        Button exitButton = new Button();
        exitButton.setText("Logout");
        buttonEffects(exitButton);
        exitButton.setOnAction(e-> applicationStage.setScene(primaryScene));// changes scene to login screen, when logout button is pressed
        exitButton.setPrefWidth(300);
        
        // creating a label to display TC message. 
        Label totalCostMessage = new Label();
        totalCostMessage.setStyle("-fx-background-color: lightblue; -fx-border-width: 2px;");;
        totalCostMessage.setPrefWidth(300);
        totalCostMessage.setMinHeight(30);
        totalCostMessage.setPadding(new Insets(10,10,10,10));

        
        int pointsEarnedFromTransaction = 0; // tracking points earned from transaction. 
        
        // if the customer opted for the redeem points option
        if(redeemOrNot){            
            double cashFromPoints = (double) loginCustomer.getPoints() / 100;
            double newCost;
            
            // ensures that we dont end up with a negative newCost. Aids in setting points up. 
            // checks for the case that cash from points exceeds cost. 
            if (cashFromPoints > buySum){
                loginCustomer.setPoints(loginCustomer.getPoints() - (int)(buySum*100));
                newCost = 0;
            }
            else {
                newCost = buySum - cashFromPoints;
                loginCustomer.setPoints(0);
            }
            
            pointsEarnedFromTransaction = (int)(newCost*10);
            totalCostMessage.setText("The total price after using your \n points is: $" + newCost);
        } 
        
        // otherwise
        else{               
            totalCostMessage.setText("The total price is: $" + buySum);
            pointsEarnedFromTransaction = (int)(buySum*10);
        }
        
        // setting custoemrs points post transaction. 
        loginCustomer.setPoints(loginCustomer.getPoints()+pointsEarnedFromTransaction);        
        
        // defining a label to display customer points and status. 
        CustomerList.setCustStatus(loginCustomer); // checks the status, before displaying it. 
        Label pointsAndStat = new Label("You Currently have: " + loginCustomer.getPoints() + " points.\n And your status is: " +loginCustomer.getStat());
        pointsAndStat.setStyle("-fx-background-color: lightblue; -fx-border-width: 2px;");
        pointsAndStat.setPrefWidth(300);
        pointsAndStat.setMinHeight(30);
        pointsAndStat.setPadding(new Insets(10,10,10,10));
        
        // formatting all three menu options onto layout. 
        layout.add(totalCostMessage, 1, 1);
        layout.add(pointsAndStat,1,2);
        layout.add(exitButton, 1, 3);       
       
        // setting the scene
        buyScene = new Scene(layout, primaryScene.getWidth(), primaryScene.getHeight());
        applicationStage.setScene(buyScene); // setting the scene.
                
    
    }
}
