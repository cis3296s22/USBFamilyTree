package edu.templ.usbfamilytree;

import com.google.gson.JsonSyntaxException;

public class Person {
    public String name;
    public String dateOfBirth;
    public String occupation;
    public String eyeColor;
    public String height;
    public String filePath;

    public Person(){

    }
    public Person(String name, String dateOfBirth, String occupation, String eyeColor, String height, String filePath){
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.occupation = occupation;
        this.eyeColor = eyeColor;
        this.height = height;
        this.filePath = filePath;
    }
}
