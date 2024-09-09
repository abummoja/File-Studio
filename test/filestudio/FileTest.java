/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filestudio;

import com.google.gson.JsonArray;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.testng.annotations.Test;

/**
 *
 * @author Admin
 */
public class FileTest {

    private static final String FILE_NAME = "names.json";
    File dir = new File(Util.home + "\\" + FILE_NAME);

    @Test
    void checkFile() {
        try {
            dir.createNewFile();
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(FileTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (dir.exists()) {
            System.out.println(dir.getPath());
        } else {
            System.out.println("Does not exist!");
        }
    }

    @Test
    void canWriteJson() {
        List<String> nm = new ArrayList<>();
        nm.add("good-first");
        nm.add("new test");
        writeToJson(nm);
        //nm.notify();
    }

    public void writeToJson(List<String> names) {
        JsonArray jsonArray = new JsonArray();
        for (String name : names) {
            jsonArray.add(name);
            System.out.println("ADDED: " + name);
        }

        FileWriter fileWriter = null;
        try {
            File file = new File(dir.getPath());
            fileWriter = new FileWriter(file);
            fileWriter.write(jsonArray.toString());

            //fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
