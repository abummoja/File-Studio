/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filestudio;

/**
 *
 * @author Admin
 */
public class ArchiveExtractor {
    String path;
    public ArchiveExtractor(){
        //empty constructor
    }
    public ArchiveExtractor(String path){
        this.path = path;
    }
    public void setActiveFile(String newPath){
        this.path = newPath;
    }
    public void extractZip(){
        
    }
}
