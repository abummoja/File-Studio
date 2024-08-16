/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package filestudio;

//import filetool.FileTool;
import com.google.gson.Gson;
import java.awt.Desktop;
//import filetool.FileMultiplier;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
//import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.lang.Runtime;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;
import javafx.beans.Observable;
//<<<<<<< HEAD
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
//=======
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventType;
import javafx.scene.control.ComboBox;
//>>>>>>> refs/remotes/origin/master
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SelectionMode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;

/**
 * This is the entry point/main class
 *
 * @author Abraham Moruri : abummoja3@gmail.com||custombeats365@gmail.com
 */
public class FXMLDocumentController implements Initializable {
    //@FXML Button closeButton;

    @FXML
    Label userTitle;
    @FXML
    Label dirProperties;
    @FXML
    TextField wordToRemoveLabel;
    @FXML
    TextField wordToReplaceLabel;
    @FXML
    Label successOrNotRenamerLabel;
    @FXML
    ListView<String> diskList;
    @FXML
    ListView<String> histList;
    @FXML
    ListView<String> wordRemoverList;
    //@FXML
    //Pane mainWindowHandleMini;
    @FXML
    AnchorPane mainWindowHandle;
    @FXML
    Button selectFileDuplicatorBtn;
    @FXML
    TextField fileToDuplicateTextBox;
    @FXML
    ListView duplicatorListView;
    @FXML
    TextField numberOfCopiesTextBox;
    @FXML
    Button redoBtn;
    @FXML
    Button undoBtn;
    @FXML
    TextField organizerDirTextField;
    @FXML
    Label organizerPreviewText;
    @FXML
    Button extractorSelectFileBtn;
    @FXML
    Button extractorDestinationBtn;
    @FXML
    Button extractBtn;
    @FXML
    TextField extractorFilePath;
    @FXML
    TextField extractorDestinationLabel;
    @FXML
    Button imageUpscaleBtn;
    @FXML
    TextField imageUpscaleTextField;
    @FXML
    ProgressBar diskProgress;
    @FXML
    ImageView imageUpscaleImageView;
    @FXML
    Button settingsButton;
    @FXML
    TextField compressorPath;
    @FXML
    TextField compressorDest;
    @FXML
    ComboBox compressorType;
    String[] types = {".zip", ".tar", ".tar.sz", ".tar.gz", ".tar.deflate", ".tar.xz", ".tar.bz2"};
    String archFolder = "";
    String pd = "https://paypal.com/donate/?hosted_button_id=A88GCN8R382B6";
    String sfUrl = "https://sourceforge.net/projects/filestudio";//source forge update url
    //@FXML Button autoGenerateWordToRemove;
    //@FXML ListView<DiskInfo> diskList;
    ObservableList<DiskInfo> disksListObservable = FXCollections.observableArrayList();
    ObservableList<String> diskStringList = FXCollections.observableArrayList();
    File history;
    String mdata = "";
    String activeDir = "";
    String ext = ".zip";
    List<File> wordRemoverFileList = new ArrayList<>();
    List<File> generatedList = new ArrayList<>();
    String[] docs, pics, vids, auds, exes, archs;
    Organizer iOrganizer = new Organizer();
    UserSettings uss = new UserSettings();
    static String audex = "regex:.*(?i:mp3|ogg|avi|wav|flacc|aud|m4a|m3u)";
    static String videx = "regex:.*(?i:mp4|mkv|webm|ts|wmp|mov)";
    static String picex = "regex:.*(?i:jpg|jpeg|png|gif|bmp|jpe|jfif|ico)";
    static String docex = "regex:.*(?i:pdf|doc|txt|pptx|xls|mhtml|html|ppt|mdb|accdb|docx)";
    static String archex = "regex:.*(?i:zip|rar|7z|aar|jar|gz|tar|xz|iso)";
    static String appex = "regex:.*(?i:exe|com|apk|bat|msi|iso|app|sh)";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //checkHistory();

        userTitle.setText(Util.user + " | " + Util.os);
        File[] rootDrive = File.listRoots();
        for (File diskDrive : rootDrive) {
            disksListObservable.add(new DiskInfo(diskDrive.getAbsolutePath()));
        }
        for (DiskInfo mdiskInfo : disksListObservable) {
            diskStringList.add((mdiskInfo.getName() + "-" + Math.round((mdiskInfo.getFreeSpace() / 1024 / 1024) * 100) / 100 + " GB Free"));
        }
        diskList.setItems(diskStringList);
        diskList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        diskList.getSelectionModel().select(0);
        //diskList.addEventHandler(EventType.ROOT, eventHandler);
        for (String archType : types) {
            compressorType.getItems().add(archType);
        }

        compressorType.getSelectionModel().selectedItemProperty().addListener((Observable ov) -> {
            int selectedIndex = compressorType.getSelectionModel().getSelectedIndex();
            ext = types[selectedIndex];
            System.out.println(ext);
            //String ext = (String)compressorType.getItems()[compressorType.getSelectionModel().getSelectedIndex()]
        });
        diskList.getSelectionModel().selectedItemProperty().addListener(ov -> {
            DiskInfo di = disksListObservable.get(diskList.getSelectionModel().getSelectedIndex());
            System.out.println(di.path);
//            int space = (int) di.disk.getTotalSpace();
//            int used = (int) di.getUsableSpace() - space;
            //in work
            dirProperties.setText(di.path + "\nName: " + di.getName() + "\nTotal Space: " + Math.round((di.getTotalSpace() / 1024 / 1024) * 100) / 100 + " GB\nFree Space: " + Math.round((di.getFreeSpace() / 1024 / 1024) * 100) / 100 + " GB");
            double p = (double) ((Math.round((di.getTotalSpace() / 1024 / 1024) * 100) / 100) - (Math.round((di.getFreeSpace() / 1024 / 1024) * 100) / 100)) / 1000;
            diskProgress.setProgress(p);

            System.out.println((Math.round((di.getUsableSpace() / 1024 / 1024) * 100)));
            //end
        });

        /*diskList.setCellFactory(new Callback<ListView<DiskInfo>, ListCell<DiskInfo>>(){
            @Override
            public ListCell<DiskInfo> call(ListView<DiskInfo> diskList) {
                return new DiskInfoCell();
                //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });*/
        mainWindowHandle.setOnMousePressed(pressEvent -> {
            mainWindowHandle.setOnMouseDragged(dragEvent -> {
                mainWindowHandle.getScene().getWindow().setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                mainWindowHandle.getScene().getWindow().setY(dragEvent.getScreenY() - pressEvent.getSceneY());
            });
        });

    }

    @FXML
    void updateFunc() {
        openBrowser(sfUrl);
    }

    @FXML
    void donateFunc() {
        //launch browser with paypal link to donation page.
        openBrowser(pd);
    }

    private void openBrowser(String url) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (URISyntaxException ex) {
                donateAlert();
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                donateAlert();
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            donateAlert();
        }
    }

    void donateAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Browser Error.");
        alert.setHeaderText("Couldn't Open Browser To: " + pd);
        alert.setContentText("Thank you for your support, however an error is preventing you from completing donation.");
        ButtonType yesBtn = new ButtonType("Copy Donation Link To Clipboard");
        ButtonType noBtn = new ButtonType("Cancel Donation.");
        alert.getButtonTypes().setAll(yesBtn, noBtn);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == yesBtn) {
                //copy to clipboard
            } else if (result.get() == noBtn) {
                //clode dlg
            } else {
                //return;
            }
        }
    }

    @FXML
    void launchSettings() {
        //if (!uss.isSettingsPageOpen) {
        Stage stage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("SettingsUI.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            Image i = new Image(getClass().getResourceAsStream("FileStudioOtherIcon.png"));
            stage.getIcons().add(i);
            //stage.setOnCloseRequest((evt) -> {
            //updateSettingsAccess();
            //});
            //stage.setAlwaysOnTop(true);
            //stage.show();
            stage.show();
            SettingsUIController.setStage(stage);
            //updateSettingsAccess();
            //uss.isSettingsPageOpen = true;
            //TO-DO: Hide Settings Button to prevent user from launching two instances of setings since it will cause a bug.
            //also create public static func to allow settings page to send commend to re enable button on clode
            //stage.setOnCloseRequest((evt) -> {
            //Enable settings btn.
            //System.out.println("Closing settings");
            //});
        } catch (IOException e) {
            System.out.println("Fxml settings err Abu, " + e.getMessage() + e.getCause().toString());
        }
        //}
    }

    public void updateSettingsAccess() {
        boolean isSettingsButtonViewable = settingsButton.isVisible();
        if (isSettingsButtonViewable == true) {
            settingsButton.setVisible(!isSettingsButtonViewable);
            settingsButton.setManaged(!isSettingsButtonViewable);
        } else {
            settingsButton.setVisible(isSettingsButtonViewable);
            settingsButton.setManaged(isSettingsButtonViewable);
        }
    }

    public static void iUpdateSettingsAccess() {
        FXMLDocumentController fxd = new FXMLDocumentController();
        fxd.updateSettingsAccess();
    }

    // ported from HTML Edit IDE
    public String readFile(String filePathRead) throws IOException {
        // Path p1 = Paths.get(filePathRead);
        // BufferedReader reader = new BufferedReader(new FileReader(filePathRead));
        String fileContent = "";
        try {
            java.util.Scanner scanner = new java.util.Scanner(new File(filePathRead));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                fileContent = fileContent + "\n" + line;
                //do stuff
            }
        } catch (FileNotFoundException e) {
        }
        return fileContent;
    }

    public void close() {
        //check first if operations are running before closing.
        Platform.exit();
    }

    public void minimize() {
        //mINIMIZE THE WINDOW
        try {
            Stage st = (Stage) userTitle.getScene().getWindow();
            st.setIconified(true);
        } catch (Exception e) {
        }
    }
    //dir chooser not file chooser

    public void showFileChooser() {
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setInitialDirectory(new File("C://Users/" + Util.user));
        File selectedFolder = dirChooser.showDialog(userTitle.getScene().getWindow());
        dirProperties.setText(selectedFolder.getAbsolutePath());
        activeDir = selectedFolder.getAbsolutePath();
        organizerDirTextField.setText(activeDir);
        compressorPath.setText(activeDir);
        compressorDest.setText(selectedFolder.getParent());
    }

    //[UNFINISHED&BUGGY] Method to get 'recent directories' list on Welcome Screen
    public void checkHistory() {
        File folder = new File("C://" + Util.user + "/Documents/FileStudio/");
        if (!folder.exists()) {
            try {
                folder.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        history = new File(folder.getAbsolutePath() + "/history.json");
        if (!history.exists()) {
            try {
                history.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (history.exists()) {
            try {
                //load json data into listview.
                mdata = readFile(history.getAbsolutePath());
                Gson gson = new Gson();
                HistoryItem[] historyArr = gson.fromJson(mdata, HistoryItem[].class);
                for (HistoryItem item : historyArr) {
                    histList.getItems().add(item.path);
                }
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void removeWord() {
        try {
            FileRenamer renamer = new FileRenamer();
            String status = renamer.removeWordFromList(wordRemoverFileList, wordToRemoveLabel.getText(), wordToReplaceLabel.getText());
            successOrNotRenamerLabel.setText(status);
            wordRemoverList.getItems().clear();
        } catch (Exception f) {
        }
    }

    public void search() {
        //check if the String containing path to active dir is empty or null
        if (activeDir != null && !activeDir.equals(" ")) {
            String keyWord = wordToRemoveLabel.getText();
            /*@reoved check if the search text is longer than 3 chars to start searching*/
            File directory = new File(activeDir);
            File[] directoryToSearch = directory.listFiles();
            //clear the list and listview as user types
            wordRemoverFileList.clear();
            wordRemoverList.getItems().clear();
            //then update the list and listview with dir contents matching search criteria
            for (File file : directoryToSearch) {
                if (file.getName().contains(keyWord)) {
                    wordRemoverFileList.add(file);
                    wordRemoverList.getItems().add(file.getName());
                }
            }
        }
    }

    //UNFINISHED&BUGGY method to automatically detect the most common word in a list of files
    public void autoGenerateSearchPhrase() {
        //WordPredictor predictor = new WordPredictor();
        if (activeDir != null && !" ".equals(activeDir)) {
            try {
                System.out.println(WordPredictor.findMostRepeatedWord(activeDir));
                wordToRemoveLabel.setText(WordPredictor.findMostRepeatedWord(activeDir));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void selectFile() {
        File iDir;
        if (activeDir != null) {
            iDir = new File(activeDir);
        } else {
            iDir = new File(System.getProperty("user.home"));
        }
        FileChooser filePicker = new FileChooser();
        filePicker.setTitle("Select A File To Duplicate");
        filePicker.setInitialDirectory(iDir);
        File toDuplicate = filePicker.showOpenDialog(selectFileDuplicatorBtn.getScene().getWindow());
        //if file is selected
        if (toDuplicate != null) {
            //open file
            fileToDuplicateTextBox.setText(toDuplicate.getAbsolutePath());
            duplicatorListView.getItems().add(toDuplicate.getName());
        }
    }

    public void generateCopies() {
        File toMultiply = new File(fileToDuplicateTextBox.getText());
        File parentDir = new File(toMultiply.getParent());
        FileMultiplier multiplier = new FileMultiplier();
        try {
            generatedList = multiplier.multiply(toMultiply.getAbsolutePath(), parentDir.getAbsolutePath(), Integer.parseInt(numberOfCopiesTextBox.getText()));
            for (File f : generatedList) {
                duplicatorListView.getItems().add(f.getName());
            }
            undoBtn.setDisable(false);
            redoBtn.setDisable(true);
        } catch (IOException ex) {
            System.out.println("Err Abu, " + ex.getMessage());
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void undoDuplicator() {
        File original = new File(fileToDuplicateTextBox.getText());
        for (File f : generatedList) {
            try {
                f.delete();
                duplicatorListView.getItems().clear();
                duplicatorListView.getItems().add(original.getName());
                redoBtn.setDisable(false);
                undoBtn.setDisable(true);
            } catch (Exception e) {
                //show dialog (failed)
            }
        }
    }

    public void redoDuplicator() {
        generateCopies();
    }

    public void processDir() {
        String dirToOrganize = organizerDirTextField.getText();
        if (dirToOrganize.equals("") || dirToOrganize.equals(null)) {
            //warn user
            return;
        }
        try {
            auds = iOrganizer.iterateAndFilter(dirToOrganize, audex);
            organizerPreviewText.setText("Found: \n" + auds.length + " audio files\n");
            Organizer.clearList();
            vids = iOrganizer.iterateAndFilter(dirToOrganize, videx);
            organizerPreviewText.setText(organizerPreviewText.getText() + vids.length + " videos\n");
            Organizer.clearList();
            pics = iOrganizer.iterateAndFilter(dirToOrganize, picex);
            organizerPreviewText.setText(organizerPreviewText.getText() + pics.length + " pictures\n");
            Organizer.clearList();
            docs = iOrganizer.iterateAndFilter(dirToOrganize, docex);
            organizerPreviewText.setText(organizerPreviewText.getText() + docs.length + " documents\n");
            Organizer.clearList();
            exes = iOrganizer.iterateAndFilter(dirToOrganize, appex);
            organizerPreviewText.setText(organizerPreviewText.getText() + exes.length + " apps\n");
            Organizer.clearList();
            archs = iOrganizer.iterateAndFilter(dirToOrganize, archex);
            organizerPreviewText.setText(organizerPreviewText.getText() + archs.length + " compressed (archived) files.");
            Organizer.clearList();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void organizeDir() {
        try {
            iOrganizer.moveFi(uss.getDir("aar"), archs);
            //orgCmplete.setText("Moved: " + zips.length + " archives");
            iOrganizer.moveFi(uss.getDir("mp4"), vids);
            //iOrganizerorgCmplete.setText(orgCmplete.getText() + "\nMoved: " + vids.length + " videos");
            iOrganizer.moveFi(uss.getDir("mp3"), auds);
            //orgCmplete.setText(orgCmplete.getText() + "\nMoved: " + auds.length + " Music");
            iOrganizer.moveFi(uss.getDir("doc"), docs);
            //iOrganizerorgCmplete.setText(orgCmplete.getText() + "\nMoved: " + docs.length + " Documents");
            iOrganizer.moveFi(uss.getDir("app"), exes);
            //iOrganizerorgCmplete.setText(orgCmplete.getText() + "\nMoved: " + iapps.length + " executables");
            iOrganizer.moveFi(uss.getDir("pic"), pics);
            //orgCmplete.setText(orgCmplete.getText() + "\nMoved: " + pics.length + " pics");
            //orgCmplete.setText("Success!");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            //orgCmplete.setText(ex.getMessage());
            //Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void readContents(String zPath) throws ZipException, FileNotFoundException, IOException {
        //THIS FUNC IS UNDER CONSTRUCTION (ERROR PRONE) AND UNFUNCTIONAL
        File fl = new File(zPath);
        ZipFile zipFile = new ZipFile(fl);
        ZipInputStream zipInput = new ZipInputStream(new FileInputStream(fl));
        ZipArchiveEntry entry = new ZipArchiveEntry((ZipEntry) zipFile.getEntries());
        byte[] content = new byte[(int) entry.getSize()];
        for (int i = 0; i < entry.getSize(); i++) {
            zipInput.read(content, i, content.length - i);
            System.out.println("Read file...");
        }
    }

    public void selectArchive() {
        FileChooser filePicker = new FileChooser();
        filePicker.setTitle("Select Archive To Extract");
        //filePicker.setInitialDirectory(new File(activeDir));
        FileChooser.ExtensionFilter archiveFilter = new FileChooser.ExtensionFilter("Archives", "*.zip", "*.rar", "*.tar", "*.gz", "*.7z", "*.bz2", "*.xz", "*.lzma", "*.cab", "*.iso", "*.dmg");
        filePicker.getExtensionFilters().add(archiveFilter);
        File toExtract = filePicker.showOpenDialog(userTitle.getScene().getWindow());
        String archName = "extracted";
        if (toExtract != null) {
            extractorFilePath.setText(toExtract.getPath());
            archName = toExtract.getName();
            int extIndex = archName.lastIndexOf(".");
            if (extIndex != -1) {
                archName = archName.substring(0, extIndex);
            }
            archName = archName.replaceAll("[\\\\/:*?\"<>|]", "_");
        } else {
            return;
        }
        archFolder = archName;
        extractorDestinationLabel.setText(toExtract.getParent() + "\\" + archFolder);
//        try {
//            //readContents(toExtract.getPath().toString());
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public void selectFolderToExtractTo() {
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File selectedFolder = dirChooser.showDialog(userTitle.getScene().getWindow());
        if (selectedFolder != null) {
            extractorDestinationLabel.setText(selectedFolder.getPath());
        } else {
            return;
        }
    }

    public void extractFile() {
        //NOTE: this method only calls the extractor class but does not actually extract files.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("layout_archiver.fxml"));
        try {
            Parent root = loader.load();
            ArchiverController archController = loader.getController();
            archController.setCurrentPathAndDest(extractorFilePath.getText(), extractorDestinationLabel.getText());
            Stage extractorStage = new Stage();
            extractorStage.setScene(new Scene(root));
            extractorStage.initStyle(StageStyle.UNDECORATED);
            extractorStage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void selectImage() {
        FileChooser imagePicker = new FileChooser();
        imagePicker.setTitle("Select Image to Upscale");
        //filePicker.setInitialDirectory(new File(activeDir));
        FileChooser.ExtensionFilter archiveFilter = new FileChooser.ExtensionFilter("Pictures", "*.png", "*.jpg", "*.gif", "*.jpeg", "*.ico", "*.jfif");
        imagePicker.getExtensionFilters().add(archiveFilter);
        File toExtract = imagePicker.showOpenDialog(userTitle.getScene().getWindow());
        if (toExtract != null && toExtract.exists()) {
            imageUpscaleTextField.setText(toExtract.getPath());
            try {
                Image i = new Image(new FileInputStream(toExtract.getPath()));
                imageUpscaleImageView.setImage(i);
                //[Fri,Mar-8-2024]Last run failed with --> Can't open image: java.lang.IllegalArgumentException: Invalid URL: unknown protocol: c
            } catch (Exception e) {
                System.out.println("Can't open image: " + e);
            }
        }
    }

    public void openGallery() {
        String imagePath = imageUpscaleTextField.getText();
        String command = "explorer.exe \"" + imagePath + "\"";
        Process proc;
        int exitcode;
        if (imagePath != null)
            try {
            proc = Runtime.getRuntime().exec(command);
            exitcode = proc.waitFor();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return;
    }

    public void revealImageInExplorer() {
        File theImage = new File(imageUpscaleTextField.getText());
        String command = "explorer.exe \"" + theImage.getParent() + "\"";
        Process proc;
        int exitcode;
        if (theImage != null)
            try {
            proc = Runtime.getRuntime().exec(command);
            exitcode = proc.waitFor();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return;
    }

    public void compressorStart() throws IOException {
        ArchiveExtractor aext = new ArchiveExtractor();
        String theDir = compressorPath.getText();
        File theDirObj = new File(theDir);
        theDir = theDirObj.getAbsolutePath();
        String outputDir = compressorDest.getText() + "\\" + theDirObj.getName() + ext;
        System.out.println("TheDir: " + theDir);
        System.out.println("Compressing to: " + outputDir);
        if (new File(compressorPath.getText()).exists()) {
            switch (ext) {
                case ".zip":
//                    ArchiveExtractor bext = new ArchiveExtractor();
//                    String path = compressorPath.getText();
//                    File tDir = new File(path);
//                    String outputPath = tDir.getParent() + "\\" + tDir.getName() + ".zip";
//                    System.out.println(outputPath);
//                    File outputZip = new File(outputPath);
//                    ZipArchiveOutputStream zipOut = new ZipArchiveOutputStream(outputZip);
//                    bext.createZip(zipOut, path);
//                    zipOut.close();
                case ".tar":
                    aext.archive(theDir, outputDir);
                    break;
                case ".tar.xz":
                    try {
                        aext.createTarXZFile(theDir, outputDir);
                    } catch (Exception ex) {
                        System.out.println("ABU-XZ: " + ex.getMessage());
                        ex.printStackTrace();
                    }
                    break;
                //TODO: the ".tar.---" should be fed a tar file in place of "theDir" since they take tar and archive to second format
                case ".tar.gz":
                    aext.createTarGZipFile(theDir, outputDir);
                    break;
                case ".tar.deflate":
                    aext.createTarDeflateFile(theDir, outputDir);
                    break;
                case ".tar.sz":
                    aext.createTarSnappyFile(theDir, outputDir);
                    break;
                case ".tar.bz2":
                    aext.createTarBZip2File(theDir, outputDir);
            }

        }
    }

    public void compressorChangeDir() {
        showFileChooser();
    }

    public void compressorChangeDest() {
        //todo: change the path to output zip
        //show file chooser with correct file type
        showFileChooser();
    }
}
