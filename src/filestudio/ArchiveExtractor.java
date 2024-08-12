/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filestudio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

/**
 *
 * @author Admin
 */
public class ArchiveExtractor {

    String path, outputPath;

    public ArchiveExtractor() {
        //empty constructor
    }

    public ArchiveExtractor(String path, String outputPath) {
        this.path = path;
        this.outputPath = outputPath;
    }

    public void setSelectedDir(String newDest) {
        this.path = newDest;//the selected folder to compress (can not be changed when compressing)
    }

    public void setOutputPath(String outDir) {
        this.outputPath = outDir;//the output zip file (can not be changed when compressing)
    }

    public void processFiles() throws IOException {
        File outputZip = new File(outputPath);
        ZipArchiveOutputStream zipOut = new ZipArchiveOutputStream(outputZip);
        createZip(zipOut, path);
        zipOut.close();
    }

    public void createZip(ZipArchiveOutputStream outStream, String srcPath) throws IOException {
        try (Stream<Path> walk = Files.walk(Paths.get(srcPath))) {
            List<File> fileList = (List<File>) walk.filter(Files::isRegularFile).map(x -> x.toFile()).collect(Collectors.toList());
            for (File file : fileList) {
                int index = file.getAbsoluteFile().getParent().lastIndexOf(File.separator);
                String parentDir = file.getAbsoluteFile().getParent().substring(index + 1);
                ZipArchiveEntry zaEntry = new ZipArchiveEntry(parentDir + File.separator + file.getName());
                zaEntry.setMethod(ZipEntry.DEFLATED);
                outStream.putArchiveEntry(zaEntry);
                IOUtils.copy(new FileInputStream(file), outStream);
                outStream.closeArchiveEntry();
            }
        }
    }
}
