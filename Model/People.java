/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author REMMY
 */
public class People {
    private String first_name;
    private String last_name;
    private String email;
    private String phone;
    
    public People(){
        super();
    }
    
    public String getFirstName(){
        return this.first_name ;
    }
    public String getLastName(){
        return this.last_name;
    }
    public String getEmail(){
        return this.email;
    }
    public String getPhone(){
        return this.phone;
    }
    
    public void setFirstName(String a){
         this.first_name = a ;
    }
    public void setLastName(String a){
         this.last_name = a;
    }
    public void setEmail(String a){
         this.email = a;
    }
    public void setPhone(String a){
         this.phone = a;
    }
}
