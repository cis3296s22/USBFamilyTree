package edu.templ.usbfamilytree;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {
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
}