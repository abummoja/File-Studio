/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filestudio;

import java.io.File;
import java.io.FileWriter;
import jdk.nashorn.internal.runtime.Context;

/**
 * Logger implementation
 *
 * @author Abraham Moruri
 */
public class FLogger {

    String fileName = "fs-logs.txt";
    private File logFile = new File(Util.home + "\\" + fileName);

    public FLogger() {
        //empty constructor
    }

    public FLogger(File logFilem) {
        this.logFile = logFilem;
    }

    public void Log(String... data) {
        //Context.getContext().getClass()
        FileWriter fw = null;
        try {
            fw = new FileWriter(logFile, true);
            for (String s : data) {
                fw.write(s + "\n");
            }
        } catch (Exception e) {
            return;
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (Exception e) {
                    return;
                }
            }
        }
    }

    public File getLogFile() {
        return this.logFile;
    }

    public static interface FSLogger {

//        public abstract void log(String str){
//
//        }
    }
}
