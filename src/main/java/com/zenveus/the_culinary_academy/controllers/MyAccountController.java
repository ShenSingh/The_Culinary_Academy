package com.zenveus.the_culinary_academy.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class MyAccountController implements Initializable {
    public ImageView profileImg;


    // profile details
    public TextField userId;
    public TextField userName;
    public TextField phone;
    public TextField email;
    public TextField address;

    // sec details
    public TextField secDesUserId;
    public PasswordField passwordField;
    public PasswordField comPassField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
        setProfileImg();

    }

    private void setProfileImg() {
        // Load the image from resources
        Image image = new Image(getClass().getResourceAsStream("/com/zenveus/the_culinary_academy/image/icons/profileImg.png"));
        profileImg.setImage(image);

        // Set a circular clip for the profile image with 50% radius
        Circle clip = new Circle(profileImg.getFitWidth() / 2, profileImg.getFitHeight() / 2, profileImg.getFitWidth() / 2);
        profileImg.setClip(clip);
    }

    public void profileDesSaveBtn(ActionEvent actionEvent) {
        System.out.println("Save Button Clicked");
    }

    public void secDesSaveBtn(ActionEvent actionEvent) {
        System.out.println("Save Button Clicked");
    }

}
