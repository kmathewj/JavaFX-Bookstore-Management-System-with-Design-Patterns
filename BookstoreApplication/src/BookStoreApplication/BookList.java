/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BookStoreApplication;
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
public class BookList extends OwnerScreen{
    
    static ObservableList<Book> books = FXCollections.observableArrayList();
    static Scene bookListMenu;

    
    public static void display(Stage application, Scene back) {
        // creating table to display books, and Vbox to store top,middle,bottom parts of screen
        TableView table = new TableView();
        VBox layout = new VBox();
        
        // declaring a text field to store new book Name.
        TextField bookNameField = new TextField();
        bookNameField.setPromptText("Title");
        bookNameField.setPrefWidth(200); // Set width to 200 pixels
        bookNameField.setMaxWidth(200);  // Ensures finite max width, for UI purposes
        
        // declaring price Field to store new book price.
        TextField bookPriceField = new TextField();
        bookPriceField.setPromptText("Price");
        bookPriceField.setPrefWidth(200); // Set width to 200 pixels
        bookPriceField.setMaxWidth(200);  // Ensures finite max width, for UI purposes
        
        // creating the three screen buttons
        Button goBackButton = new Button("Back");  
        Button deleteButton = new Button("Delete");
        Button addBookButton = new Button("Add");  
        
        // defining behaviour of the buttons        
        // clicking back button takes user to owner screen 'back'
        goBackButton.setOnAction(e -> application.setScene(back));
        
        // clicking delete button deletes selected book. 
        deleteButton.setOnAction(e -> {
            Book bookToDel = (Book)table.getSelectionModel().getSelectedItem();
            deleteBook(books, bookToDel);
            table.refresh(); // reprints table in real time
        });
        
        
        // clicking add button adds a new book to the book list if
        // both the title and price fields are non-empty. 
        // if either or both username password fields are empty, error is presented. 
        addBookButton.setOnAction(e -> {
            try {
            
            if (!((bookNameField.getText().equals("")|| bookPriceField.getText().equals("")))){
                Double enteredPrice = Double.parseDouble(bookPriceField.getText());
                Book newBook = new Book(bookNameField.getText(), enteredPrice);
                addBook(newBook);              
            }
            else{
                errorAlert("ERROR 406", "You Must Enter a Book name and price");
            }            
            }
            catch(NumberFormatException c){
                errorAlert("ERROR 407", "The price must be a double" );            
            }
            bookNameField.clear();
            bookPriceField.clear();
            
        });
        
        
        // function defined in parent class, used for hover and color effects on buttons.
        buttonEffects(goBackButton);
        buttonEffects(deleteButton);
        buttonEffects(addBookButton);

        // creating seperate VBoxes for the middle and bottom parts of main Vbox Layout. 
        // helps distinguish between the three parts of screen.
        VBox middlePart = new VBox(bookNameField, bookPriceField, addBookButton);
        middlePart.setAlignment(Pos.CENTER); // Center the fields
        VBox bottomPart = new VBox(deleteButton, goBackButton);
        bottomPart.setAlignment(Pos.CENTER);
        
        // adding padding between table and username field. and after back button. For UI purposes. 
        VBox.setMargin(middlePart, new Insets(20, 0, 0, 0));
        VBox.setMargin(bottomPart, new Insets(0, 0, 20, 0));


        table.setItems(books); // gives the table the observable list of books. 

        // creates three different table columns, of type book and return type string. 
        TableColumn<Book, String> bookTitleColumn = new TableColumn<>("Title");
        TableColumn<Book, String> bookPriceColumn = new TableColumn<>("Price $");
        
        // populates the newly craeted column with its coressponding getter method. (parses through each book in list to find following properties. ) 
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        bookPriceColumn.setCellValueFactory(new PropertyValueFactory<>("bookPrice")); 
        
        bookTitleColumn.setPrefWidth(450);
        bookPriceColumn.setPrefWidth(150);
                
        table.getColumns().addAll(bookTitleColumn, bookPriceColumn); // adds the two columns to table. 
        
 
        layout.setStyle( "-fx-background-color: white");  // background color 
        layout.getChildren().addAll(table, middlePart, bottomPart);
        bookListMenu = new Scene(layout, back.getWidth(), back.getHeight());
        application.setScene(bookListMenu); // sets the scene of the stage to bookListMenu. 
    }
    
    public static void loadBooks(String file, ObservableList<Book> books) {
        try {
            Scanner in = new Scanner(new File(file));
            String[] line;
            books.clear(); // list is re-written each time. 
            while (in.hasNextLine()) {
                line = in.nextLine().split(", ");
                books.add(new Book(line[0], Double.parseDouble(line[1])));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    public static void deleteBook(ObservableList<Book> books, Book book) {
        books.remove(book);
    }
    
    public static void addBook(Book book) {
        boolean bookInStore = false;
        
        for (Book bookInList: books) {
            if(bookInList.getBookTitle().equals(book.getBookTitle())) {
                bookInStore = true;
                break;
            }
        }
                
        if(bookInStore) {
            errorAlert("ERROR 405", "The Store already has this Book");
        } else {
            books.add(book);
        }
    }


}

