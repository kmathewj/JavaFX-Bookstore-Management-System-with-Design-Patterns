package BookStoreApplication;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author dabrahmb
 */
public class CustomerList extends OwnerScreen{
    
    static ObservableList<Customer> customers = FXCollections.observableArrayList();
    static Scene customerListMenu;

    
    public static void display(Stage application, Scene back) {
        // creating table to display customers, and Vbox to store top,middle,bottom parts of screen
        TableView table = new TableView();
        VBox layout = new VBox();
        
        // declaring a text field to store username of new customer
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setPrefWidth(200); // Set width to 200 pixels
        usernameField.setMaxWidth(200);  // Ensures finite max width, for UI purposes
        
        // declaring passwordField to store password of new customer. 
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setPrefWidth(200); // Set width to 200 pixels
        passwordField.setMaxWidth(200);  // Ensures finite max width, for UI purposes
        
        // creating the three screen buttons
        Button goBackButton = new Button("Back");  
        Button deleteButton = new Button("Delete");
        Button addCustButton = new Button("Add");  
        
        // defining behaviour of the buttons
        
        // clicking back button takes user to owner screen 'back'
        goBackButton.setOnAction(e -> application.setScene(back));
        
        // clicking delete button deletes selected customer. 
        deleteButton.setOnAction(e -> {
            Customer customerDelete = (Customer)table.getSelectionModel().getSelectedItem();
            deleteCust(customers, customerDelete);
            table.refresh(); // reprints table in real time
        });
        
        
        // clicking add button adds a new customer to the customer list if
        // both the username and password fields are non-empty. Points are defaulted to 0.
        // if either or both username password fields are empty, error 404 is presented. 
        addCustButton.setOnAction(e -> {
            if (!(usernameField.getText().equals("") || passwordField.getText().equals(""))) {
                Customer customerAdd = new Customer(usernameField.getText(), passwordField.getText(), 0);
                addCust(customers, customerAdd);
                usernameField.clear(); passwordField.clear(); // text fields are reset
            }
            else{
                errorAlert("ERROR 404", "You must enter a username and password first.");            
            }
            table.refresh(); // updates table in real time
        });
        
        
        // function defined below, used for hover and color effects on buttons.
        buttonEffects(goBackButton);
        buttonEffects(deleteButton);
        buttonEffects(addCustButton);

        // creating seperate Vboxes for the middle and bottom parts of main Vbox Layout. 
        // helps distinguish between the three parts of screen.
        VBox middlePart = new VBox(usernameField, passwordField, addCustButton);
        middlePart.setAlignment(Pos.CENTER); // Center the fields
        VBox bottomPart = new VBox(deleteButton, goBackButton);
        bottomPart.setAlignment(Pos.CENTER);
        
        // adding padding between table and username field. and after back button. For UI purposes. 
        VBox.setMargin(middlePart, new Insets(20, 0, 0, 0));
        VBox.setMargin(bottomPart, new Insets(0, 0, 20, 0));


         table.setItems(customers); // gives the table the observable list of customers. 

        // creates three different table columns, of type customer and return type string. 
        TableColumn<Customer, String> userColumn = new TableColumn<>("Username");
        TableColumn<Customer, String> pointColumn = new TableColumn<>("Points");
        TableColumn<Customer, String> passColumn = new TableColumn<>("Password");        
        
        // populates the newly craeted column with its coressponding getter method. (parses through each customer in list to find following properties. ) 
        userColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passColumn.setCellValueFactory(new PropertyValueFactory<>("password"));       
        pointColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
        
        userColumn.setPrefWidth(445);
        passColumn.setPrefWidth(100);
        pointColumn.setPrefWidth(50);
                
        table.getColumns().addAll(userColumn, passColumn, pointColumn); // adds the three columns to table. 
        
 
        layout.setStyle( "-fx-background-color: white");  // background color 
        layout.getChildren().addAll(table, middlePart, bottomPart);
        customerListMenu = new Scene(layout, back.getWidth(), back.getHeight());
        application.setScene(customerListMenu); // sets the scene of the stage to customerListMenu. 
    }
    
    public static void loadCustomers(String file, ObservableList<Customer> customerList) {
        try {
            Scanner in = new Scanner(new File(file));
            String[] line;
            customerList.clear();
            while (in.hasNextLine()) {
                line = in.nextLine().split(", ");
                customerList.add(new Customer(line[0], line[1], Integer.parseUnsignedInt(line[2]))); // points must be positive. 
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
    
    public static void setCustStatus(Customer customer) {
        if (customer.getPoints() >= 1000) {
            customer.setStat("Gold");
        } else {
            customer.setStat("Silver");
        }
    }
    

    
    public static void deleteCust(ObservableList<Customer> customerList, Customer customer) {
        customerList.remove(customer);
    }
    
    public static void addCust(ObservableList<Customer> customerList, Customer customer) {
        boolean existingCustomer = false;
        
        for (Customer customerInList: customerList) {
            if(customerInList.getUsername().equals(customer.getUsername())) {
                existingCustomer = true;
                break;
            }
        }
                
        if(existingCustomer) {
            errorAlert("ERROR 402", "User already exists");
        } else if (customer.getUsername().toLowerCase().equals("admin")) {
            errorAlert("ERROR 403", "There is only one admin");
        } else {
            customerList.add(customer);
        }
    }


}


