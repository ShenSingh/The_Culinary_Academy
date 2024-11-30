package com.zenveus.the_culinary_academy.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.zenveus.the_culinary_academy.bo.BOFactory;
import com.zenveus.the_culinary_academy.bo.custom.UserBO;
import com.zenveus.the_culinary_academy.dto.UserDto;
import com.zenveus.the_culinary_academy.tm.UserTm;
import com.zenveus.the_culinary_academy.util.BCryptHasher;
import com.zenveus.the_culinary_academy.util.Regex;
import com.zenveus.the_culinary_academy.util.TextFields;
import com.zenveus.the_culinary_academy.Exception.RegistrationException;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
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
    public TableView<UserTm> userTable;
    public TableColumn<?,?> colUsrId;
    public TableColumn<?,?> colUsrName;
    public TableColumn<?,?> colUsrEmail;
    public TableColumn<?,?> colUsrPhone;
    public TableColumn<?, ?> colUsrAddress;
    public JFXComboBox<String> userJob;
    public UserTm selectedItem;
    private TranslateTransition sideTransition;
    private boolean isShow = false;

    ObservableList<UserTm> obList = FXCollections.observableArrayList();
    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTransition();
        setEmployeeID();
        setCellValueFactories();
        loadAllEmployees();
        setComboBox();
    }

    private void setComboBox() {
        userJob.getItems().addAll("Admin", "Coordinator");
    }

    private void setEmployeeID() {
        String lastUser = getLastUserId();
        employeeIDField.setText(lastUser);
        employeeIDField.setDisable(true);
    }

    private String getLastUserId() {
        List<UserDto> allUsers = userBO.getAllUsers();

        if (allUsers.isEmpty()) {
            return "U001";
        }

        String lastUserId = allUsers.get(allUsers.size() - 1).getUserId();
        if (lastUserId == null || lastUserId.isEmpty() || !lastUserId.matches("U\\d+")) {
            return "U001";
        }

        int id = Integer.parseInt(lastUserId.substring(1));
        id++;

        return String.format("U%03d", id);
    }


    //    side menu transition
    private void setTransition() {
        sideTransition = new TranslateTransition(Duration.seconds(1.5), employeeRegMainAnchor);
        sideTransition.setFromX(0);
        sideTransition.setToX(250); // Set initial `toX` based on `isShow`
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
        sideTransition.stop();  // Stop any ongoing transition before starting a new one

        // Set starting and ending points dynamically based on isShow
        sideTransition.setFromX(isShow ? 250 : 0);
        sideTransition.setToX(isShow ? 0 : 250);
        sideTransition.setDuration(Duration.seconds(1.5));

        isShow = !isShow;  // Toggle the state

        sideTransition.setOnFinished(e -> updateIcon()); // Update icon after the transition completes
        sideTransition.play();
    }
    //  employee back btn (search bar)
    public void employeeBackBtn(ActionEvent actionEvent) {
        System.out.println("click employee page back Btn");

        DashboardController dashboardController = new DashboardController();
        dashboardController.loadDashboard(employeeRegMainAnchor);

    }
    // employee search filed enter click (search bar)
    public void searchEmployeeClick(ActionEvent actionEvent) {
        System.out.println("click employee search filed");

        String searchText = searchEmployee.getText();

        if(searchText.isEmpty()){
            loadAllEmployees();
            return;
        }

        List<UserDto> allUsers = userBO.getAllUsers();
        obList.clear();
        for (UserDto userDto : allUsers) {
            if(userDto.getUserId().contains(searchText) || userDto.getFullName().contains(searchText) || userDto.getEmail().contains(searchText) || userDto.getPhoneNumber().contains(searchText) || userDto.getAddress().contains(searchText)){
                obList.add(new UserTm(userDto.getUserId(), userDto.getFullName(), userDto.getEmail(), userDto.getPhoneNumber(), userDto.getAddress()));
            }
        }
        userTable.setItems(obList);

    }
    // employee search clear btn (search bar)
    public void searchEmployeeClearBtn(ActionEvent actionEvent) {
        System.out.println("click employee create Btn");

        searchEmployee.setText("");
    }

    // employee delete btn
    public void employeeDeleteBtn(ActionEvent actionEvent) throws SQLException {
        System.out.println("click employee delete Btn");

        try {
            UserDto user = new UserDto();
            user.setUserId(employeeIDField.getText());
            user.setFullName(employeeNameField.getText());
            user.setEmail(employeeEmailField.getText());
            user.setPhoneNumber(employeePhoneField.getText());
            user.setAddress(employeeAddressField.getText());
            user.setJobRole(userJob.getValue());

            UserDto userDto = userBO.isUserExist(user);

            if(userDto == null){
                new Alert(Alert.AlertType.ERROR, "Employee Not Found!").showAndWait();
                return;
            }

            boolean isDeleted = userBO.deleteUser(userDto);

            if(isDeleted){
                new Alert(Alert.AlertType.INFORMATION, "Employee Deleted Successfully!").showAndWait();
                setEmployeeID();
                clearAllFields();
                loadAllEmployees();
            }else{
                new Alert(Alert.AlertType.ERROR, "Failed to Delete Employee!").showAndWait();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
            throw new SQLException(e);
        }
    }
    // employee save btn
    public void employeeSaveBtn(ActionEvent actionEvent) {
        System.out.println("click employee save Btn");

        // all employee fields must be filled
        if(employeeIDField.getText().isEmpty() || employeeEmailField.getText().isEmpty() || employeeNameField.getText().isEmpty() || employeePhoneField.getText().isEmpty() || employeeAddressField.getText().isEmpty() || userJob.getValue() == null){
            new Alert(Alert.AlertType.WARNING, "All Fields Must Be Filled!").showAndWait();
            return;
        }

        String employeeID = employeeIDField.getText();
        String employeeEmail = employeeEmailField.getText();
        String employeeName = employeeNameField.getText();
        String employeePhone = employeePhoneField.getText();
        String employeeAddress = employeeAddressField.getText();
        String username = generateUsername(employeeName);
        String password = generatePassword(employeeName);
        String jobRole = userJob.getValue();


        UserDto userDTO = new UserDto();

        userDTO.setUserId(employeeID);
        userDTO.setFullName(employeeName);
        userDTO.setEmail(employeeEmail);
        userDTO.setPhoneNumber(employeePhone);
        userDTO.setAddress(employeeAddress);
        userDTO.setJobRole(jobRole);

        userDTO.setUsername(username);
        userDTO.setPassword(password);

        System.out.println(userDTO);

        if(!Regex.isTextFieldValid(TextFields.EMAIL, employeeEmail)){
            new Alert(Alert.AlertType.WARNING, "Invalid Email!").showAndWait();
            employeeEmailField.requestFocus();
            return;
        }

        if(!Regex.isTextFieldValid(TextFields.CONTACT, employeePhone)){
            new Alert(Alert.AlertType.WARNING, "Invalid Phone Number!").showAndWait();
            employeePhoneField.requestFocus();
            return;
        }

        // search and make sure no duplicate email
        boolean isEmailExist = isEmailExist(employeeEmail);
        if(isEmailExist){
            new Alert(Alert.AlertType.WARNING, "Email Already Exist!").showAndWait();
            employeeEmailField.requestFocus();
            return;
        }

        // search and make sure no duplicate phone number
        boolean isPhoneExist = isPhoneExist(employeePhone);
        if(isPhoneExist){
            new Alert(Alert.AlertType.WARNING, "Phone Number Already Exist!").showAndWait();
            employeePhoneField.requestFocus();
            return;
        }


        try {
            boolean isAdded = userBO.addUser(userDTO);
            if (isAdded) {
                Platform.runLater(() -> {
                    new Alert(Alert.AlertType.INFORMATION, "Employee Added Successfully!").showAndWait();
                });

                setEmployeeID();
                clearAllFields();
                loadAllEmployees();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to Add Employee!").showAndWait();
            }
        } catch (RegistrationException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generatePassword(String employeeName) {
        String base = employeeName.split("\\s+")[0].toLowerCase();
        int randomNum = (int) (Math.random() * 10000);
        String specialChars = "!@#$%^&*";
        char specialChar = specialChars.charAt((int) (Math.random() * specialChars.length()));

        String password = base + randomNum + specialChar;
        System.out.println("password: " + password);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Generated Password");
        alert.setHeaderText(null);
        alert.setContentText("Generated Password: " + password);
        alert.showAndWait();

        setEmployeeID();
        clearAllFields();
        loadAllEmployees();

        return BCryptHasher.hashPassword(password);
    }

    private String generateUsername(String employeeName) {
        // username is the first word of the full name
        return employeeName.split("\\s+")[0].toLowerCase();
    }

    private boolean isPhoneExist(String employeePhone) {
        // search and make sure no duplicate phone number
        List<UserDto> existingUsers = userBO.getAllUsers();

        for (UserDto userDto : existingUsers) {
            if(userDto.getPhoneNumber().equals(employeePhone)){
                return true;
            }
        }

        return false;
    }

    private boolean isEmailExist(String employeeEmail) {
        // search and make sure no duplicate email
        List<UserDto> existingUsers = userBO.getAllUsers();

        for (UserDto userDto : existingUsers) {
            if(userDto.getEmail().equals(employeeEmail)){
                return true;
            }
        }

        return false;
    }

    //clear all fields
    public void clearAllFields(){
        employeeEmailField.clear();
        employeeNameField.clear();
        employeePhoneField.clear();
        employeeAddressField.clear();
    }

    // employee update btn
    public void employeeUpdateBtn(ActionEvent actionEvent) {
        System.out.println("click employee update Btn");

        // no duplicate employees can be added
        List<UserDto> allUsers = userBO.getAllUsers();

        // id is already existing do not add
        for (UserDto user : allUsers) {
            if (user.getUserId().equals(employeeIDField.getText())) {
                new Alert(Alert.AlertType.WARNING, "Employee ID already exists!").showAndWait();
                return;
            }
        }


        UserDto user = new UserDto();
        user.setUserId(employeeIDField.getText());
        user.setFullName(employeeNameField.getText());
        user.setEmail(employeeEmailField.getText());
        user.setPhoneNumber(employeePhoneField.getText());
        user.setAddress(employeeAddressField.getText());
        user.setJobRole(userJob.getValue());



        if(!Regex.isTextFieldValid(TextFields.EMAIL, user.getEmail())){
            new Alert(Alert.AlertType.WARNING, "Invalid Email!").showAndWait();
            employeeEmailField.requestFocus();
            return;
        }

        if(!Regex.isTextFieldValid(TextFields.CONTACT, user.getPhoneNumber())){
            new Alert(Alert.AlertType.WARNING, "Invalid Phone Number!").showAndWait();
            employeePhoneField.requestFocus();
            return;
        }

        // search and make sure no duplicate email
        boolean isEmailExist = isEmailExist(user.getEmail());
        if(isEmailExist){
            new Alert(Alert.AlertType.WARNING, "Email Already Exist!").showAndWait();
            employeeEmailField.requestFocus();
            return;
        }

        // search and make sure no duplicate phone number
        boolean isPhoneExist = isPhoneExist(user.getPhoneNumber());
        if(isPhoneExist){
            new Alert(Alert.AlertType.WARNING, "Phone Number Already Exist!").showAndWait();
            employeePhoneField.requestFocus();
            return;
        }

        UserDto userExist = userBO.isUserExist(user);


        boolean isUpdated = userBO.updateUser(userExist);
        if(isUpdated){
            new Alert(Alert.AlertType.INFORMATION, "Employee Updated Successfully!").showAndWait();
            setEmployeeID();
            clearAllFields();
            loadAllEmployees();
        }else{
            new Alert(Alert.AlertType.ERROR, "Failed to Update Employee!").showAndWait();
        }

    }

    public void loadAllEmployees(){
        try {
            List<UserDto> allUsers = userBO.getAllUsers();
            obList.clear();
            for (UserDto userDto : allUsers) {
                obList.add(new UserTm(userDto.getUserId(), userDto.getFullName(), userDto.getEmail(), userDto.getPhoneNumber(), userDto.getAddress()));
            }
            userTable.setItems(obList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCellValueFactories(){
        colUsrId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colUsrName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colUsrEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colUsrPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colUsrAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    public void rowClick(MouseEvent mouseEvent) {
        System.out.println("click row");

        List<UserDto> userDTOList = userBO.getAllUsers();
        UserDto selectUser = null;

        if (userTable.getSelectionModel().getSelectedItem() != null) {
            selectedItem = userTable.getSelectionModel().getSelectedItem();
            employeeIDField.setText(selectedItem.getUserId());

            for (UserDto userDTO : userDTOList){
                if (userDTO.getUserId().equals(selectedItem.getUserId())){
                    selectUser = userDTO;
                }
            }
            employeeNameField.setText(selectedItem.getFullName());
            employeeEmailField.setText(selectedItem.getEmail());
            employeePhoneField.setText(selectedItem.getPhoneNumber());
            employeeAddressField.setText(selectedItem.getAddress());
            assert selectUser != null;
            userJob.setValue(selectUser.getJobRole());

            if(isShow){
                isShow = false;
                sideTransition.setDuration(Duration.seconds(2));
                sideTransition.setToX(isShow ? 250 : 0);
                updateIcon();
                sideTransition.play();
            }
        }
    }
}
