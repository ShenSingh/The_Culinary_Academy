package com.zenveus.the_culinary_academy.controllers;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentController implements Initializable {

    public AnchorPane reportMainAnchor;
    public ImageView reportLeftRightImage;

    // Student Fields
    public TextField studentAddressField;
    public DatePicker studentDOBField;
    public TextField studentPhoneField;
    public TextField studentNICField;
    public TextField studentNameField;
    public TextField studentEmailField;
    public TextField studentIDField;
    // Student Search Field
    public TextField searchStudent;
    // Student Side Pane Title
    public Text sidePaneTitle;

    private TranslateTransition sideTransition;
    private boolean isShow = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTransition();
    }

    private void setTransition() {
        sideTransition = new TranslateTransition(Duration.seconds(1.5), reportMainAnchor);
        sideTransition.setFromX(0);
        sideTransition.setToX(1055); // Set initial `toX` based on `isShow`
        updateIcon();
    }

    private void updateIcon() {
        String iconPath = isShow
                ? "src/main/resources/com/zenveus/the_culinary_academy/image/icons/rightArow.png"
                : "src/main/resources/com/zenveus/the_culinary_academy/image/icons/leftArow.png";
        Image image = new Image(new File(iconPath).toURI().toString());
        reportLeftRightImage.setImage(image);
    }

    public void reportPaneShowHideBtn(ActionEvent actionEvent) {
        isShow = !isShow;
        sideTransition.setDuration(Duration.seconds(isShow ? 1.5 : 2));
        sideTransition.setToX(isShow ? 1055 : 0);
        updateIcon();
        sideTransition.play();
    }

    //  student back btn (search bar)
    public void studentBackBtn(ActionEvent actionEvent) {
        System.out.println("click student page back Btn");
    }
    // student search filed enter click (search bar)
    public void searchStudentClick(ActionEvent actionEvent) {
        System.out.println("click student search filed");
    }
    // student search clear btn (search bar)
    public void searchStudentClearBtn(ActionEvent actionEvent) {
        System.out.println("click student create Btn");
    }




    // student delete btn
    public void studentDeleteBtn(ActionEvent actionEvent) {
        System.out.println("click student delete Btn");
    }
    // student save btn
    public void studentSaveBtn(ActionEvent actionEvent) {
        System.out.println("click student save Btn");
    }
    // student update btn
    public void studentUpdateBtn(ActionEvent actionEvent) {
        System.out.println("click student update Btn");
    }
}
