package com.fox.Assignment1;
import java.lang.String;
public class StudentModel {
    private String ID;
    private String FirstName;
    private String LastName;
    private String Gender;
    private Double GPA;
    private Integer Level;
    private String Address;

    public StudentModel(String ID, String FirstName, String LastName, String Gender, Double GPA, Integer Level, String Address) {
        this.ID = ID;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Gender = Gender;
        this.GPA = GPA;
        this.Level = Level;
        this.Address = Address;
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

    public Double getGPA() {
        return this.GPA;
    }

    public void setGPA(Double GPA) {
        this.GPA = GPA;
    }

    public Integer getLevel() {
        return this.Level;
    }

    public void setLevel(Integer level) {
        this.Level = level;
    }

    public String getAddress() {
        return this.Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    @Override
    public String toString() {
        return "StudentModel{" +
                "ID='" + ID + '\'' +
                ", FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", Gender='" + Gender + '\'' +
                ", GPA=" + GPA +
                ", Level=" + Level +
                ", Address='" + Address + '\'' +
                '}';
    }
}
