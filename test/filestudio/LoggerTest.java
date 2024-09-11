/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filestudio;

import org.testng.annotations.Test;

/**
 *
 * @author Admin
 */
public class LoggerTest {

    @Test
    void logic() {
        FLogger fl = new FLogger();
        fl.Log("testing...");
        fl.Log("another...");
        fl.Log("ting...");
    }
}
