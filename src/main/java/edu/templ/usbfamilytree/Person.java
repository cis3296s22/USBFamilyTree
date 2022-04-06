package edu.templ.usbfamilytree;

import com.google.gson.JsonSyntaxException;

public class Person {
    public String name;
    public String dateOfBirth;
    public String occupation;
    public Person(){

    }
    public Person(String name, String dateOfBirth, String occupation){
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.occupation = occupation;
    }
}
