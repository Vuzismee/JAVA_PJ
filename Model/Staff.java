/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author REMMY
 */
public class Staff {
    private int staffId;
    private int active;
    private int storeID;
    private int managerId;

    public Staff() {
        super();
    }

    public int getStaffID() {
        return staffId;
    }

    public void setStaffID(int staffID) {
        this.staffId = staffID;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getStoreID() {
        return storeID;
    }

    public void setStoreID(int storeID) {
        this.storeID = storeID;
    }

    public int getManagerID() {
        return managerId;
    }

    public void setManagerID(int managerID) {
        this.managerId = managerID;
    }
}