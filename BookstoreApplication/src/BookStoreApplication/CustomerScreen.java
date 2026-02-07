/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BookStoreApplication;

/**
 *
 * @author dabrahmb
 */


import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Point2D;
import javafx.scene.control.CheckBox;
import javafx.scene.control.cell.CheckBoxTableCell;

/**
 *
 * @author dabrahmb
 */
public class CustomerScreen extends BookStore{
    static Scene customerMenu;
    public static double buySum;

    public static void display(Stage application, Scene mainScene, int index) {
        
        
        
   
        Customer loginCustomer = CustomerList.customers.get(index);
        
        // creats the table to display books. 
        TableView table = new TableView();
        VBox layout = new VBox();
        
        // logout button. 
        Button exitButton = new Button("Logout");
        exitButton.setOnAction(e-> application.setScene(mainScene));
        exitButton.setPrefHeight(200);        
        
        Button buyButton = new Button("Buy");
        buyButton.setPrefHeight(200);
        buyButton.setOnAction(e-> {
            boolean somethingIsSelected = false;
            for (Book book : BookList.books) {
                if ((book.isChecked().get())) { // if some book is selected. 
                    somethingIsSelected = true;                   
                }
            }            
            if (somethingIsSelected){
                
                // call to buyScreen
                getSelectedBooks();
                BuyScreen.buydisplay(application, mainScene, index, buySum, false);
                buySum = 0;
            }           
            else {
                errorAlert("ERROR 408", "Select A Book First");
            }  
        });
   
       
        Button RedeemPointsBuyButton = new Button("Redeem Points and Buy");
        RedeemPointsBuyButton.setPrefHeight(200);
        RedeemPointsBuyButton.setOnAction(e-> {
            if(loginCustomer.getPoints()>0){
                boolean somethingIsSelected = false;
                for (Book book : BookList.books) {
                    if ((book.isChecked().get())) {
                        somethingIsSelected = true;                   
                    }
                }
                if (somethingIsSelected){
                    // call to buyScreen
                    getSelectedBooks();
                    BuyScreen.buydisplay(application, mainScene, index, buySum, true);
                }

                else {
                    errorAlert("ERROR 408", "Select A Book First");
                }  
            }
            else{
                errorAlert("ERROR 409", "No Redeemable Points");
            }
        });
        

        // hover and color effects for buttons. 
        buttonEffects(buyButton);
        buttonEffects(RedeemPointsBuyButton);
        buttonEffects(exitButton);
        
        HBox cartOptions = new HBox();
        cartOptions.getChildren().addAll(buyButton, RedeemPointsBuyButton, exitButton);
        
        CustomerList.setCustStatus(loginCustomer); // checks the status, before displaying it. 
        
        // label to display top part of customer screen 
        Label topElementIntro = new Label();
        topElementIntro.setText("Welcome " + loginCustomer.getUsername() + ". You have " + Integer.toString(loginCustomer.getPoints()) + " points, and your status is: " + loginCustomer.getStat());
        topElementIntro.setStyle("-fx-background-color: lightblue; -fx-padding: 10px; -fx-text-fill: white;-fx-font-weight: bold;");
        topElementIntro.setPrefWidth(600);
        
        
        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        titleColumn.setPrefWidth(450);  // Set preferred width for the title column

        
        TableColumn<Book, String> priceColumn = new TableColumn<>("Price$");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("bookPrice"));
        priceColumn.setPrefWidth(75);
        
        
        TableColumn<Book, Boolean> selectColumn = new TableColumn<>("Select");
        selectColumn.setCellValueFactory(cd -> cd.getValue().isChecked());
        selectColumn.setCellFactory(CheckBoxTableCell.forTableColumn(selectColumn));
        selectColumn.setPrefWidth(75);
        
        table.setEditable(true);
        table.setItems(BookList.books);
        table.getColumns().addAll(titleColumn, priceColumn,selectColumn);
        layout.setStyle("-fx-background-color: white;");
        layout.getChildren().addAll(topElementIntro, table, cartOptions);
        
        customerMenu = new Scene(layout, mainScene.getWidth(), mainScene.getHeight());
        
        application.setScene(customerMenu);
    }
    
    public static void getSelectedBooks() {
    buySum = 0;
    
    // Create a separate list to store books to be removed
    ObservableList<Book> booksToRemove = FXCollections.observableArrayList();
    
    for (Book book : BookList.books) {
        if (book.isChecked().get()) {
            buySum += book.getBookPrice();           
            booksToRemove.add(book); // Collect books to be removed
        }
    }

    // Remove the books after iteration
    BookList.books.removeAll(booksToRemove);
    }
}

