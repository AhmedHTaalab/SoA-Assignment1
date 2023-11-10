package com.fox.Assignment1;
import java.lang.String;
public class StudentModel {
    private String ID;
    private String FirstName;
    private String LastName;
    private String Gender;
    private double GPA;
    private int Level;
    private String Address;

    public StudentModel(String ID, String firstname, String lastname, String gender, double GPA, int level, String address) {
        this.ID = ID;
        this.FirstName = firstname;
        this.LastName = lastname;
        this.Gender = gender;
        this.GPA = GPA;
        this.Level = level;
        this.Address = address;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return this.FirstName;
    }

    public void setFirstName(String firstname) {
        this.FirstName = firstname;
    }

    public String getLastName() {
        return this.LastName;
    }

    public void setLastName(String lastname) {
        this.LastName = lastname;
    }

    public String getGender() {
        return this.Gender;
    }

    public void setGender(String gender) {
        this.Gender = gender;
    }

    public double getGPA() {
        return this.GPA;
    }

    public void setGPA(double GPA) {
        this.GPA = GPA;
    }

    public int getLevel() {
        return this.Level;
    }

    public void setLevel(int level) {
        this.Level = level;
    }

    public String getAddress() {
        return this.Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }
}
