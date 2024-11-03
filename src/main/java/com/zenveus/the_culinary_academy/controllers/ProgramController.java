package com.zenveus.the_culinary_academy.controllers;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ProgramController implements Initializable {
    public AnchorPane programRegMainAnchor;
    public ImageView reportLeftRightImage;

    // program Fields
    public TextField programIDField;
    public TextField programDurationField;
    public TextField programNameField;
    public TextField programFeeField;

    // program Side Pane Title
    public Text sidePaneTitle;
    // program Search Field
    public TextField searchEmployee;

    private TranslateTransition sideTransition;
    private boolean isShow = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTransition();
    }
    private void setTransition() {
        sideTransition = new TranslateTransition(Duration.seconds(1.5), programRegMainAnchor);
        sideTransition.setFromX(0);
        sideTransition.setToX(550); // Set initial `toX` based on `isShow`
        updateIcon();
    }

    private void updateIcon() {
        String iconPath = isShow
                ? "src/main/resources/com/zenveus/the_culinary_academy/image/icons/rightArow.png"
                : "src/main/resources/com/zenveus/the_culinary_academy/image/icons/leftArow.png";
        Image image = new Image(new File(iconPath).toURI().toString());
        reportLeftRightImage.setImage(image);
    }
    public void programAddPaneShowHideBtn(ActionEvent actionEvent){
        isShow = !isShow;
        sideTransition.setDuration(Duration.seconds(isShow ? 1.5 : 2));
        sideTransition.setToX(isShow ? 550 : 0);
        updateIcon();
        sideTransition.play();
    }

    //  program back btn (search bar)
    public void programBackBtn(ActionEvent actionEvent) {
        System.out.println("click program page back Btn");
    }
    // program search filed enter click (search bar)
    public void searchProgramClick(ActionEvent actionEvent) {
        System.out.println("click program search Btn");
    }

    // program search clear btn (search bar)
    public void searchProgramClearBtn(ActionEvent actionEvent) {
        System.out.println("click program create Btn");
    }

    
    
    
    // program delete btn
    public void programDeleteBtn(ActionEvent actionEvent) {
        System.out.println("click program delete Btn");
    }
    
    // program save btn
    public void programSaveBtn(ActionEvent actionEvent) {
        System.out.println("click program save Btn");
    }

    // program update btn
    public void programUpdateBtn(ActionEvent actionEvent) {
        System.out.println("click program update Btn");
    }
}
