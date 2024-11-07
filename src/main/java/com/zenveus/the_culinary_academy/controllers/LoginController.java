package com.zenveus.the_culinary_academy.controllers;

import com.zenveus.the_culinary_academy.bo.BOFactory;
import com.zenveus.the_culinary_academy.bo.custom.UserBO;
import com.zenveus.the_culinary_academy.dto.UserDTO;
import com.zenveus.the_culinary_academy.util.BCryptHasher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;

public class LoginController {
    public AnchorPane rootNode;

    static UserDTO loginUser;

    public static UserDTO getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(UserDTO loginUser) {
        this.loginUser = loginUser;
    }

    private boolean isPasswordVisible = false;  // Track visibility state

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);


    @FXML
    private TextField uNameText;

    @FXML
    private PasswordField uPassText;
    @FXML
    public TextField uPassVisibleText;
    @FXML
    private static Stage mainStage;

    @FXML
    private void logBtn(ActionEvent event) throws IOException {
//        dashBord();
        List<UserDTO> userDTOList = userBO.getAllUsers();



        String uName=uNameText.getText();
        String uPass=uPassText.getText();

        for(UserDTO userDTO : userDTOList){
            System.out.println(userDTO);

            if (uName.equals(userDTO.getUsername())){

                if (BCryptHasher.verifyPassword(uPass, userDTO.getPassword())){
                    System.out.println("Go to dashBord");

                    uNameText.clear();
                    uPassText.clear();
                    loginUser = userDTO;
                    dashBord();
                }else {
                    System.out.println("not password");
                    new Alert(Alert.AlertType.WARNING, "wrong User Password ");

                }

            }else {
                System.out.println("not id");
                new Alert(Alert.AlertType.WARNING, "wrong User ID ");

            }
        }

    }
    void printAlert(String content){
        uNameText.setText("");
        uPassText.setText("");
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("WARNING");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    //////////////////////////////////  dashbord  //////////////////////////////////////////////////

    public void dashBord() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zenveus/the_culinary_academy/view/dashboard.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, Color.TRANSPARENT);

        Stage stage = (Stage)this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();

    }

    public void passwordShowBtn(ActionEvent actionEvent) {
        if (isPasswordVisible) {
            // Hide password
            uPassText.setText(uPassVisibleText.getText());
            uPassText.setVisible(true);
            uPassVisibleText.setVisible(false);
        } else {
            // Show password
            uPassVisibleText.setText(uPassText.getText());
            uPassVisibleText.setVisible(true);
            uPassText.setVisible(false);

        }
        isPasswordVisible = !isPasswordVisible;
    }
}