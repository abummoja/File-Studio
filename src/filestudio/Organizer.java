/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filestudio;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Admin
 */
public class Organizer {

    static String sts;
    static String files[] = {""};
    public static List<String> fileList = new ArrayList<>(Arrays.asList(files));

    //get files by extension. Not recursive for subfolders (TODO)
    public String[] iterateAndFilter(String mpath, String ext) throws IOException {
        Path dir = Paths.get(mpath);
        File checkIfExists = new File(mpath);
        File[] iCheck = checkIfExists.listFiles();
        if(iCheck.length <= 0){
            return null;
        }
        PathMatcher imageFileMatcher
                = FileSystems.getDefault().getPathMatcher(
                        ext);
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir,
                entry -> imageFileMatcher.matches(entry.getFileName()))) {
            for (Path path : stream) {
                System.out.println(path.getFileName());
                System.out.println(path.toString());
                fileList.add(path.toString());
                // sts = "Moved "+ext+" files from "+mpath;
                //sts = "\nFound: "+fflist.size()+" files of type "+ext;
            }
        }
        files = fileList.toArray(new String[fileList.size()]);
        System.out.println(files.length);
        //fss should be a variable to receive the directory path due to current file extension
        //fss = "C:\\Users\\User\\Music";
        // moveF(fss);
        return files;
    }

    //same as above, but i dont wanna break by removing since i dont know where it is used
    public void listFiles(String type, String dir) throws IOException {
        iterateAndFilter(dir, type);
    }

    //useage: to clear the list of files of a certain extension and call the iterateFilter method again to get files
    public static void clearList() {
        fileList.clear();
        files = fileList.toArray(new String[fileList.size()]);
    }

    //move files from one dir to another
    public static void moveFiles(String morig, String mdest) throws IOException {

        File orig = new File(morig);
        File dest = new File(mdest);
        //String orr = orig.getAbsolutePath();
        String nf = dest.getAbsolutePath();
        String nm = orig.getName();
        //nf is dir and nm is original file name the\\ take the file into the dir not at dir level
        File fin = new File(nf + "\\" + nm);
        System.out.println("\nnf is " + nf + " nm is " + nm);
        System.out.println("\n==> " + fin.getAbsolutePath());
        if (dest.isDirectory()) {
            orig.renameTo(fin);
            sts = fin.getAbsolutePath();
        } else {
            System.err.println("not a directory" + dest.getAbsolutePath());
        }
    }

    //just a quick write up to move string[] of files to new destination
    public void moveFi(String destina, String[] ff) throws IOException {
        int i = 0;
        for (String t : ff) {
            i += 1;
            System.out.println("\nmoving from " + t + " to " + destina);
            moveFiles(t, destina);
            sts += "\nmoved " + i + " files.";
        }
    }
}
