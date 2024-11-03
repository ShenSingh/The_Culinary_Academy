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

public class EmployeeController implements Initializable {

    public AnchorPane employeeRegMainAnchor;
    public ImageView reportLeftRightImage;

    // Employee Fields
    public TextField employeeIDField;
    public TextField employeeEmailField;
    public TextField employeeNameField;
    public TextField employeePhoneField;
    public TextField employeeAddressField;
    // Employee Search Field
    public TextField searchEmployee;
    // Employee Side Pane Title
    public Text sidePaneTitle;

    private TranslateTransition sideTransition;
    private boolean isShow = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTransition();
    }
    
    
//    side menu transition
    private void setTransition() {
        sideTransition = new TranslateTransition(Duration.seconds(1.5), employeeRegMainAnchor);
        sideTransition.setFromX(0);
        sideTransition.setToX(550); // Set initial `toX` based on `isShow`
        updateIcon();
    }
    //   update side menu icon
    private void updateIcon() {
        String iconPath = isShow
                ? "src/main/resources/com/zenveus/the_culinary_academy/image/icons/rightArow.png"
                : "src/main/resources/com/zenveus/the_culinary_academy/image/icons/leftArow.png";
        Image image = new Image(new File(iconPath).toURI().toString());
        reportLeftRightImage.setImage(image);
    }
    //   show hide side menu
    public void employeeAddPaneShowHideBtn(ActionEvent actionEvent) {
        isShow = !isShow;
        sideTransition.setDuration(Duration.seconds(isShow ? 1.5 : 2));
        sideTransition.setToX(isShow ? 550 : 0);
        updateIcon();
        sideTransition.play();
    }
    //  employee back btn (search bar)
    public void employeeBackBtn(ActionEvent actionEvent) {
        System.out.println("click employee page back Btn");
    }
    // employee search filed enter click (search bar)
    public void searchEmployeeClick(ActionEvent actionEvent) {
        System.out.println("click employee search filed");
    }
    // employee search clear btn (search bar)
    public void searchEmployeeClearBtn(ActionEvent actionEvent) {
        System.out.println("click employee create Btn");
    }

    
    
    // employee delete btn
    public void employeeDeleteBtn(ActionEvent actionEvent) {
        System.out.println("click employee delete Btn");
    }
    // employee save btn
    public void employeeSaveBtn(ActionEvent actionEvent) {
        System.out.println("click employee save Btn");
    }
    // employee update btn
    public void employeeUpdateBtn(ActionEvent actionEvent) {
        System.out.println("click employee update Btn");
    }
}
