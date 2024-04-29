/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filestudio;

import java.io.File;
import java.util.prefs.Preferences;

/**
 *
 * @author Admin
 */
public class UserSettings {
    static String unm = System.getProperty("user.name");
    final static Preferences pref = Preferences.userNodeForPackage(UserSettings.class);
    public String mp3dir = pref.get("mp3dir", "C:\\Users\\" + unm + "\\Music\\FileStudio");
    public String mp4dir = pref.get("mp4dir", "C:\\Users\\" + unm + "\\Videos");
    public String picdir = pref.get("picdir", "C:\\Users\\" + unm + "\\Pictures");
    public String archdir = pref.get("archdir", "C:\\Users\\" + unm + "\\Archives\\FileStudio");
    public String appsdir = pref.get("appsdir", "C:\\Users\\" + unm + "\\Apps");
    public String docsdir = pref.get("docsdir", "C:\\Users\\" + unm + "\\Documents");
    public String[] dirs = {mp3dir, mp4dir, picdir, archdir, appsdir, docsdir};
    public UserSettings(){
        //constructor
    }
    public String getDir(String typee){
        createDir(dirs);
        switch(typee){
            case "mp3":
                return mp3dir;
            case "mp4":
                return mp4dir;
            case "pic":
                return picdir;
            case "aar":
                return archdir;
            case "app":
                return appsdir;
            case "doc":
                return docsdir;
            default:
                break;
        }
        return null;
    }
    public void createDir(String[] paths){
        for(String p:paths){
            File f = new File(p);
            if(!f.exists()){
                f.mkdir();
            }else{
                System.out.println("Directory exists!");
            }
        }
    }
}
