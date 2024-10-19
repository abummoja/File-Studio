/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package filestudio;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Abraham Moruri (abummoja3@gmail.com)
 */
public class SettingsUIController implements Initializable {

    @FXML
    TextField musicPathField;
    @FXML
    TextField videosPathField;
    @FXML
    TextField documentsPathField;
    @FXML
    TextField appsPathField;
    @FXML
    TextField archivesPathField;
    @FXML
    TextField picturesPathField;
    @FXML
    Button appsBtn;
    @FXML
    Button documentsBtn;
    @FXML
    Button archivesBtn;
    @FXML
    Button videosBtn;
    @FXML
    Button picturesBtn;
    @FXML
    Button musicBtn;
    @FXML
    AnchorPane mainWindowHandle;
    @FXML
    Label aboutLabel;
    @FXML
    ToggleButton themeToggle;
    @FXML
    Label infoLabel;

    UserSettings uss = new UserSettings();
    boolean changesMade = false;
    //buttons array to handle click listener without multiple methods
    //@FXML
    Button[] changeButtons = {appsBtn, documentsBtn, archivesBtn, videosBtn, picturesBtn, musicBtn};
    TextField[] fields = {appsPathField, documentsPathField, archivesPathField, videosPathField, picturesPathField, musicPathField};
    String theme = uss.theme;
    static Stage mStage;//for closing window

    /**
     * Initializes the controller class.
     *
     * @param url the URL?
     * @param rb a resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mainWindowHandle.setOnMousePressed(pressEvent -> {
            mainWindowHandle.setOnMouseDragged(dragEvent -> {
                mainWindowHandle.getScene().getWindow().setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                mainWindowHandle.getScene().getWindow().setY(dragEvent.getScreenY() - pressEvent.getSceneY());
            });
        });
        //set text of inputs to text in prefs
        appsPathField.setText(uss.appsdir);
        musicPathField.setText(uss.mp3dir);
        videosPathField.setText(uss.mp4dir);
        archivesPathField.setText(uss.archdir);
        picturesPathField.setText(uss.picdir);
        documentsPathField.setText(uss.docsdir);
        themeToggle.setTooltip(new Tooltip("The active theme is not written in the theme button."));
        switch (uss.theme) {
            case "light":
                themeToggle.setText("Dark Mode");
                break;
            case "dark":
                themeToggle.setText("Light Theme");
                break;
            default:
                themeToggle.setText("Theme");
        }
        aboutLabel.setText("This Version : " + FXMLDocumentController.ver
                + "\n (c)2024 Abraham Moruri"
                + "\n Project License : Apache 2.0"
                + "\n Project repository : https://github.com/abummoja/File-Studio"
                + "\n SourceForge (Download): https://sourceforge.net/projects/filestudio"
                + "\n This is a free and open-source project and only profits from donations."
                + "\n Consider donating through : " + FXMLDocumentController.pd);
        themeToggle.selectedProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                infoLabel.setText("Restart App For Theme Change");
                if (themeToggle.getText().equals("Light Theme")) {
                    themeToggle.setText("Dark Mode");
                    theme = "light";
                } else if (themeToggle.getText().equals("Dark Mode")) {
                    themeToggle.setText("Light Theme");
                    theme = "dark";
                }
            }
        });
    }

    @FXML
    private void handleClick(MouseEvent event) {
        Button clickedButton = (Button) event.getSource();
        String btnId = clickedButton.getId();
        int j = 0;
        String substr = btnId.length() < 3 ? btnId : btnId.substring(0, 3);
        System.out.println(substr);
        switch (btnId) {
            //open dir chooser and pass the title as the textfields hint and the textfield respectively so the return is automatically pasted
            case "appsBtn":
                openDirChooser(appsPathField.getPromptText(), appsPathField);
                break;
            case "videosBtn":
                openDirChooser(videosPathField.getPromptText(), videosPathField);
                break;
            case "archivesBtn":
                openDirChooser(archivesPathField.getPromptText(), archivesPathField);
                break;
            case "picturesBtn":
                openDirChooser(picturesPathField.getPromptText(), picturesPathField);
                break;
            case "musicBtn":
                openDirChooser(musicPathField.getPromptText(), musicPathField);
                break;
            case "documentsBtn":
                openDirChooser(documentsPathField.getPromptText(), documentsPathField);
                break;
            default:
                System.out.println(btnId + " cannot be used!");
                break;
        }
    }

    @FXML
    void closeWindow() {
        if (mStage != null) {
            if (!changesMade) {
                cancelSettings();//it works! don't touch it
                // uss.isSettingsPageOpen = false;
            } else if (changesMade) {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Save Changes?");
                alert.setHeaderText(null);
                alert.setContentText("Some changes were made. Save new settings?");
                ButtonType yesBtn = new ButtonType("Yes, Save New Settings.");
                ButtonType noBtn = new ButtonType("No, Just Exit.");
                alert.getButtonTypes().setAll(yesBtn, noBtn);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent()) {
                    if (result.get() == yesBtn) {
                        saveSettings();
                        //exit
                        cancelSettings();//cancelSettings() closes the window does not cancel prefs it just doesn't save the changes. That's why i called it here.
                        //uss.isSettingsPageOpen = false;
                    } else if (result.get() == noBtn) {
                        cancelSettings();
                    } else {
                        //return;
                    }
                }
                //boolean ch = alert.getResult().

            }
        }
    }

    @FXML
    void saveSettings() {
        uss.setDir("appsdir", appsPathField.getText());
        uss.setDir("docsdir", documentsPathField.getText());
        uss.setDir("mp3dir", musicPathField.getText());
        uss.setDir("mp4dir", videosPathField.getText());
        uss.setDir("archdir", archivesPathField.getText());
        uss.setDir("picdir", appsPathField.getText());
        uss.setDir("theme", theme);
        uss.saveSettings();
        if (changesMade) {
            changesMade = false;
        }
    }

    @FXML
    void cancelSettings() {
        //does'nt undo anything, just closes the window.
        if (mStage != null) {
            //uss.isSettingsPageOpen = false;
            mStage.close();//it works! don't touch it (buggy)
            //FXMLDocumentController.iUpdateSettingsAccess();
        }
    }

    @FXML
    void resetToDefault() {
        uss.resetAll();
    }

    //open a dir chooser to select a preferred location.
    void openDirChooser(String title, TextField tf) {
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setInitialDirectory(new File(Util.home));
        dirChooser.setTitle(title);
        File selectedFolder = dirChooser.showDialog(musicPathField.getScene().getWindow());
        if (selectedFolder != null && selectedFolder.isDirectory()) {
            tf.setText(selectedFolder.getPath());
            if (!changesMade) {
                changesMade = true;
            }
        }
    }

    public static void setStage(Stage st) {
        mStage = st;//this method receives the stage for closing purposes
    }
}
