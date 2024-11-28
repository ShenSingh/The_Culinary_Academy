package com.zenveus.the_culinary_academy.controllers;

import com.zenveus.the_culinary_academy.bo.BOFactory;
import com.zenveus.the_culinary_academy.bo.custom.UserBO;
import com.zenveus.the_culinary_academy.dto.UserDto;
import com.zenveus.the_culinary_academy.util.BCryptHasher;
import com.zenveus.the_culinary_academy.util.Regex;
import com.zenveus.the_culinary_academy.util.TextFields;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.List;
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

    public PasswordField passwordField;
    public PasswordField comPassField;
    public TextField secDesUserName;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    String updatedPassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setProfileImg();
        setUserDetails();

    }

    private void setUserDetails() {
        UserDto user = LoginController.getLoginUser();
        System.out.println("User: " + user);

        if (user == null) {
            System.out.println("User is null");
            return;
        }

        UserDto user1 = new UserDto();

        user1.setUserId(user.getUserId());


        UserDto updatedUser=userBO.isUserExist(user1);

        userId.setText(updatedUser.getUserId());
        userName.setText(updatedUser.getFullName());
        phone.setText(updatedUser.getPhoneNumber());
        email.setText(updatedUser.getEmail());
        address.setText(updatedUser.getAddress());

        secDesUserName.setText(updatedUser.getUsername());
        passwordField.setText(LoginController.getUserPassword());

        userId.setEditable(false);
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

        if (userName.getText().isEmpty() || phone.getText().isEmpty() || email.getText().isEmpty() || address.getText().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Fields Cannot Be Empty!").showAndWait();
            return;
        }


        UserDto updateUser = null;
        List<UserDto> userDTOList = userBO.getAllUsers();

        // Find the existing user by ID
        for (UserDto userDTO : userDTOList) {
            if (userId.getText().equals(userDTO.getUserId())) {
                updateUser = userDTO;
                break;
            }
        }

        if (updateUser == null) {
            new Alert(Alert.AlertType.ERROR, "User not found!").showAndWait();
            return;
        }

        // Update fields directly on updateUser
        updateUser.setFullName(userName.getText());
        updateUser.setEmail(email.getText());
        updateUser.setPhoneNumber(phone.getText());
        updateUser.setAddress(address.getText());

        // Validation checks
        if (!Regex.isTextFieldValid(TextFields.EMAIL, updateUser.getEmail())) {
            new Alert(Alert.AlertType.WARNING, "Invalid Email!").showAndWait();
            email.requestFocus();
            return;
        }

        if (!Regex.isTextFieldValid(TextFields.CONTACT, updateUser.getPhoneNumber())) {
            new Alert(Alert.AlertType.WARNING, "Invalid Phone Number!").showAndWait();
            phone.requestFocus();
            return;
        }

        // Check for duplicate email
        if (isEmailExist(updateUser.getEmail(), updateUser.getUserId())) {
            new Alert(Alert.AlertType.WARNING, "Email Already Exists!").showAndWait();
            email.requestFocus();
            return;
        }

        // Check for duplicate phone number
        if (isPhoneExist(updateUser.getPhoneNumber(), updateUser.getUserId())) {
            new Alert(Alert.AlertType.WARNING, "Phone Number Already Exists!").showAndWait();
            phone.requestFocus();
            return;
        }

        // Update the user
        boolean isUpdated = userBO.updateUser(updateUser);
        if (isUpdated) {
            new Alert(Alert.AlertType.INFORMATION, "User Updated Successfully!").showAndWait();
            setUserDetails();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to Update User!").showAndWait();
        }


    }

    private boolean isPhoneExist(String phoneNumber, String userId) {
        // Retrieve all users except the one being updated
        List<UserDto> existingUsers = userBO.getAllUsers();

        for (UserDto userDto : existingUsers) {
            // Check if the phone number exists and if it belongs to a different user
            if (userDto.getPhoneNumber().equals(phone) && !userDto.getUserId().equals(userId)) {
                return true; // Duplicate phone number found for another user
            }
        }
        return false;
    }

    private boolean isEmailExist(String email, String userId) {
        // Retrieve all users except the one being updated
        List<UserDto> existingUsers = userBO.getAllUsers();

        for (UserDto userDto : existingUsers) {
            // Check if the email exists and if it belongs to a different user
            if (userDto.getEmail().equals(email) && !userDto.getUserId().equals(userId)) {
                return true; // Duplicate email found for another user
            }
        }
        return false;

    }

    public void secDesSaveBtn(ActionEvent actionEvent) {
        System.out.println("Save Button Clicked");

        if (secDesUserName.getText().isEmpty() || passwordField.getText().isEmpty() || comPassField.getText().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Fields Cannot Be Empty!").showAndWait();
            return;
        }

        if (!passwordField.getText().equals(comPassField.getText())) {
            new Alert(Alert.AlertType.WARNING, "Passwords Do Not Match!").showAndWait();
            return;
        }

        UserDto updateUser = null;
        List<UserDto> userDTOList = userBO.getAllUsers();

        for (UserDto userDTO : userDTOList) {
            if (userId.getText().equals(userDTO.getUserId())) {
                updateUser = userDTO;
                break;
            }
        }
        updateUser.setUsername(secDesUserName.getText());
        updateUser.setPassword(BCryptHasher.hashPassword(passwordField.getText()));

        boolean isUpdated = userBO.updateUser(updateUser);
        if (isUpdated) {
            new Alert(Alert.AlertType.INFORMATION, "sec User Updated Successfully!").showAndWait();
            updatedPassword = passwordField.getText();
            LoginController.setUserPassword(updatedPassword);
            comPassField.clear();
            setUserDetails();
        } else {
            new Alert(Alert.AlertType.ERROR, "sec Failed to Update User!").showAndWait();
        }


    }

}
