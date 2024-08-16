/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filestudio;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;
//import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import org.junit.jupiter.api.Test;
//import filsetudio.Status;
//import static Status;

/**
 *
 * @author Admin
 */
public class ArchiveExtractorTest {

    ArchiveExtractor ext = new ArchiveExtractor();

    @Test
    void TestCompressor() throws IOException {
        //ext = new ArchiveExtractor();
        String outputPath = "C:\\Users\\Admin\\Downloads\\test.zip";
        String path = "C:\\Users\\Admin\\Downloads\\gnome";
        File outputZip = new File(outputPath);
        ZipArchiveOutputStream zipOut = new ZipArchiveOutputStream(outputZip);
        ext.createZip(zipOut, path);
        zipOut.close();
        assertTrue(Files.exists(Paths.get(outputZip.getPath())));
    }

    @Test
    void TestUnArchiver() {
        //ext = new ArchiveExtractor();
        String theFile, oDir;
        theFile = "C:\\Users\\Admin\\Downloads\\leetcode-solutions-master.zip";
        oDir = "C:\\Users\\Admin\\Downloads\\leetsoln";
        ext.unarchive(theFile, oDir);
    }

    @Test
    void TestArchiver() {
        String theDir, newFile;
        theDir = "C:\\Users\\Admin\\Downloads\\ABRAHAM 32101";
        //creating 7z fails (org.tukaani.xz lib required) also rar cannot be made from this.
        //only single formats such as .tar, .zip (not .tar.gz or .tar.xz)
        newFile = "C:\\Users\\Admin\\Downloads\\testarch.tar";
        ext.archive(theDir, newFile);
    }
}
