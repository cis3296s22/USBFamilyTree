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

    /**
     * A Person can be initialized with just their name if no other data is available
     * @param name takes individual's name
     */
    public Person(String name){
        this.name = name;
    }

    /**
     * If more information is available, this constructor will handle that
     * @param name Person's name
     * @param dateOfBirth Person's date of birth
     * @param occupation Person's job or occupation
     * @param eyeColor Person's eye color
     * @param height Person's height
     * @param filePath Person's path to access their data
     */
    public Person(String name, String dateOfBirth, String occupation, String eyeColor, String height, String filePath){
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.occupation = occupation;
        this.eyeColor = eyeColor;
        this.height = height;
        this.filePath = filePath;
    }
}
