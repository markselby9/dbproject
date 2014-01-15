/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bll;

/**
 *
 * @author fengchaoyi
 */
public class Account {
    private String username;
    private String password;
    private Account next;

    public Account(){
        
    }
    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the next
     */
    public Account getNext() {
        return next;
    }

    /**
     * @param next the next to set
     */
    public void setNext(Account next) {
        this.next = next;
    }
    
}
