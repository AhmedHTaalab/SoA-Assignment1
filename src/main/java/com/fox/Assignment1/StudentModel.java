package com.fox.Assignment1;

public class StudentModel {
    private String ID;
    private String Firstname;
    private String Lastname;

    private String Gender;

    private double GPA;

    private int Level;

    private String Address;

    public StudentModel(String ID, String firstname, String lastname, String gender, double GPA, int level, String address) {
        this.ID = ID;
        this.Firstname = firstname;
        this.Lastname = lastname;
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

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        this.Firstname = firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        this.Lastname = lastname;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        this.Gender = gender;
    }

    public double getGPA() {
        return GPA;
    }

    public void setGPA(double GPA) {
        this.GPA = GPA;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        this.Level = level;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }
}
