/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package filestudio;

import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Admin
 */
public class FileStudio extends Application {

    UserSettings uss = new UserSettings();

    @Override
    public void start(Stage stage) throws Exception {
        //TO-DO: Read prefs to check if user has selected newUI or just launch old Ui
        //if prefs newui(...try(load newui))else (try...old ui)
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            Scene scene = new Scene(root);
            //stylesheets="@style.css"
            switch (uss.theme) {
                case "dark":
                    scene.getStylesheets().add("filestudio/style.css");
                    break;
                case "light":
                    scene.getStylesheets().add("filestudio/light.css");
                    break;
                default:
                    scene.getStylesheets().add("filestudio/style.css");
            }

            //getClass().getResource("style.css").getFile()
            stage.setScene(scene);
            //stage.setIconified(true); //launches the app in minimized state
            //File f = new File(getClass().getResource("FileStudioMain.ico").getFile());
            //.ico files don't work, use .png or .jpg
            Image i = new Image(getClass().getResourceAsStream("FileStudioMainIcon.png"));
            stage.getIcons().add(i);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
            stage.maximizedProperty().addListener((obs, oldv, newv) -> {
                if (newv) {
                    stage.setMaximized(false);
                }
            });
            stage.show();
        } catch (IOException e) {
            System.out.println("Fxml err Abu, " + e.getMessage() + e.getCause().toString());
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
