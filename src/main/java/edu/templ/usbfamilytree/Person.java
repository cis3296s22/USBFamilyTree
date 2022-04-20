package edu.templ.usbfamilytree;

import com.google.gson.JsonSyntaxException;

/**
 * Contains all the variables and methods associated with a Person
 */
public class Person
{
    /**
     * Stores the name of a Person
     */
    public String name;
    /**
     * Stores the DOB of a Person
     */
    public String dateOfBirth;
    /**
     * Stores the occupation of job of a Person
     */
    public String occupation;
    /**
     * Stores the eye color of a Person
     */
    public String eyeColor;
    /**
     * Stores the height of a Person
     */
    public String height;
    /**
     * Stores the file in which all of the images, vidoes, or other files pertaining this Person can be accessed
     */
    public String filePath;

    /**
     * Empty constructor for testing
     */
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
