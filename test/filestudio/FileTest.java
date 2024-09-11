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
    void historyTest() {
        FLogger fg = new FLogger(new File(Util.home + "\\test-hist.txt"));
        JsonHandler jh = new JsonHandler();
        List<String> fn = new ArrayList<>();
        fn.add("test1fgf");
        fn.add("anothertest");
        fn.add("last str");
        jh.writeToJson(fn);
        List<String> hist = jh.readFromJson();
        if (hist.isEmpty()) {
            //histList.getItems().add("empty list");
            //throw (new (Exception));
            return;
        }
        for (String s : hist) {
            System.out.println(s);
            fg.Log(s);
            //histList.getItems().add(s);
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
