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
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;


public class BookStore extends Application {

    @Override
    public void start(Stage primaryStage) {
        CustomerList.loadCustomers("customers.txt", CustomerList.customers);
        BookList.loadBooks("books.txt", BookList.books);
        
// Create a GridPane layout
        GridPane layout = new GridPane();
        layout.setHgap(10); // horizontal gap between elements
        layout.setVgap(10); // vertical gap between elements
        layout.setStyle("-fx-background-color: #D0F0FF;"); // layout background color
        layout.setAlignment(javafx.geometry.Pos.CENTER); // centering elements

        // Creating labels for the layout
        Label title = new Label("Welcome to the BookStore Application");
        title.setFont(new Font(15));

        Label usernameLabel = new Label("Username:");
        usernameLabel.setFont(new Font(14));

        Label passwordLabel = new Label("Password:");
        passwordLabel.setFont(new Font(14));

        // Creating input fields
        TextField userField = new TextField();
        userField.setMaxWidth(200);
        userField.setPromptText("username");

        PasswordField passField = new PasswordField();
        passField.setMaxWidth(200);
        passField.setPromptText("password");

        // Creating a login button
        Button loginButton = new Button("Login");
        buttonEffects(loginButton);

        // Adding the aforementioned components to the layout - GridPane for positioning elements on the screen
        layout.add(title, 1, 0, 2, 1);
        layout.add(usernameLabel, 1, 1);
        layout.add(userField, 2, 1);
        layout.add(passwordLabel, 1, 2);
        layout.add(passField, 2, 2);
        layout.add(loginButton, 2, 3);

        // Create the scene with the GridPane layout
        Scene loginScene = new Scene(layout, 600, 500);

        primaryStage.setTitle("BookStore App");
        primaryStage.setScene(loginScene);
        primaryStage.show();

        // If the login button is pressed:
        loginButton.setOnAction(e -> {
            // boolean variables to determine if the entered info matched owner or customer
            boolean validCust = false; boolean validBoss = false; 
            
            //storing entered text in local string literals, and reseting fields
            String username = userField.getText();
            String password = passField.getText();
            userField.clear(); 
            passField.clear();
            
            // if the entered info matches owners info, takes us to owner screen
            if (username.equals("admin") && password.equals("admin")) {
                validBoss = true;
                // If admin, go to owner screen
                OwnerScreen.mainScreen(primaryStage, loginScene);                
            }
            
            // otherwise checks if it mataches with customer
            else { 
                int customerID = -1;
                
                // loops through all the customers, checks if info matches with any 
                // of these customers username/password. if so takes us to customer screen. 
                for (Customer customer : CustomerList.customers) { 
                    customerID+=1; // helps track id of customer that logs in.
                    if(username.equals(customer.getUsername()) && customer.checkPassword(password)) {
                        validCust = true;
                        break;
                    }
                }
                
                // if it did match, takes us to the customer screen. 
                if (validCust){
                    CustomerScreen.display(primaryStage, loginScene, customerID);
                }
            }
            
            // if info is not in database, gives error. 
            // notice I've assigned distinct error codes for different types of errors
            // that may be shown to user. 
            if (!validCust && !validBoss){
                errorAlert("ERROR 401", "Invalid ID or Password");            
            }
        });
        
        primaryStage.setOnCloseRequest(event -> {
            saveFiles();
        });
    
    
    }
    
    // method to save files, upon exit. 
    public static void saveFiles(){
        try {
            FileWriter fileCust = new FileWriter(new File("customers.txt"), false);
            for (Customer customer : CustomerList.customers) {
                fileCust.write(customer.getUsername() + ", " + customer.getPassword() + ", " + customer.getPoints() + "\n");
            }
            fileCust.close();
            FileWriter fileBook = new FileWriter(new File("books.txt"), false);
            for (Book book : BookList.books) {
                fileBook.write(book.getBookTitle() + ", " + book.getBookPrice() + "\n");
                }
                fileBook.close();
            } 
        catch (IOException exception) {
                    System.out.println("Cannot write to file");
                }   
    }
    
    // a method to give buttons a background color, and hover effect. 
    // referenced throughout program. 
    public static void buttonEffects(Button button){
        button.setStyle("-fx-border-color: lightblue;; -fx-background-color: white");
        button.setOnMouseEntered(e-> button.setStyle("-fx-background-color: lightblue;"));
        button.setOnMouseExited(e-> button.setStyle("-fx-border-color: lightblue;;-fx-background-color: white;"));
        if (!button.getText().equals("Login")){
            button.setPrefWidth(200);}
    }

    // a method to display error messages to user. Used throughout program. 
    public static void errorAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR, message);
        alert.setTitle(title);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}