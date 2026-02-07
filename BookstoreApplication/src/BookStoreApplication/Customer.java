/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BookStoreApplication;

/**
 *
 * @author dabrahmb
 */
public class Customer {
    private final String  username;
    private final String password;
    public String stat;
    public int points;

    public Customer(String username, String password, int points) {
        this.username = username;
        this.password = password;
        this.points = points;
        this.stat = "Silver";
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String inputPassword) {  
        return this.password.equals(inputPassword);
    }
    
    public int getPoints(){
        return points;
    }
    
    public String getStat(){
        return stat;
    }
    
    public void setPoints(int points) {
        this.points = points;
    }
    public String getPassword() { // used in customer list.
    return password;
}

    public void setStat(String stat) {
        this.stat = stat;
    }

}
