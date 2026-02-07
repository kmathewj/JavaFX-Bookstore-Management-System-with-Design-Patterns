/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BookStoreApplication;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author dabrahmb
 */

// This is the Book class. It establishes basic properties for the Book object
// and provides access to these properties. 
public class Book {
    private final String title;
    private final double price;
    private BooleanProperty isChecked; // simple Boolean Used to track checklist in customer Screen.
    public boolean bookSelected; 
    
    public Book (String title, double price){
        this.title = title;
        this.price = price;
        this.isChecked = new SimpleBooleanProperty(false); // checklist defaulted to off 
    }
    public String getBookTitle() { 
        return title;
    }
    
    public double getBookPrice(){
        return price;
    }
    
    public BooleanProperty isChecked(){
        return isChecked;
                       
    }

    
}
