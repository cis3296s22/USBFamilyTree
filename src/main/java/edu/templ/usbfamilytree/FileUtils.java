package edu.templ.usbfamilytree;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
/**
 * FileUtils contains static methods that help handling files
 * for reading and saving objects from different sessions
 */
public class FileUtils {
    /**
     * A reusable Gson instance
     * Google's implementation of JSON serializer/deserializer
     */
    public static Gson gson = new Gson();
    /**
     * Reads file as string, from the given path
     * @param path is the path to a file, that has to be read and returned as String
     * @return returns contents of the file as String
     */
    public static String ReadFile(String path){
        String content = null;
        File file = new File(path);
        FileReader reader = null;
        try
        {
            reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return content;
    }
    /**
     * Writes content to a file
     * @param path is the path to the file
     * @param content data that has to be written to the file
     */
    public static void WriteFile(String path, String content){
        try {
            File myObj = new File(path);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            }
            FileWriter myWriter = new FileWriter(path);
            myWriter.write(content);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    /**
     * Reusable method that converts any object into JSON String
     * @param o any object that has to be serialized
     * @return returns JSON string that represents object
     */
    public static String toJson(Object o) {
        return gson.toJson(o);
    }
    /**
     * Reusable method that converts JSON string into a specified Class instance
     * @param <T> Object type to be returned
     * @param json JSON String to be deserialized
     * @param type Class instance type
     * @return returns deserialized object of the specified type
     * @throws JsonSyntaxException
     */
    public static <T> T fromJson(String json, Class<T> type) throws JsonSyntaxException {
        return gson.fromJson(json, type);
    }
}