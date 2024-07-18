/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filestudio;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressBar;

/**
 *
 * @author Admin
 */
public class DiskInfoCell extends ListCell<DiskInfo>{
    @FXML Label diskNameLabel;
    @FXML Label diskFillLabel;
    @FXML ProgressBar diskFillProgress;
    FXMLLoader fxmlLoader;
    public DiskInfoCell(){
        //h
    }

    @Override
    protected void updateItem(DiskInfo item, boolean empty) {
        super.updateItem(item, empty); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        if(empty || item == null){
            setText(null);
            setGraphic(null);
        }else{
            if(fxmlLoader == null){
                fxmlLoader = new FXMLLoader(getClass().getResource("DiskListItem.fxml"));
                fxmlLoader.setController(this);
                try{
                    fxmlLoader.load();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        diskNameLabel.setText("C:");
        diskFillLabel.setText("GB OF BG");
        diskFillProgress.setProgress(item.getFreeSpace()-item.getTotalSpace()/100);
        setText(null);
        setGraphic(null);
    }
    
}
