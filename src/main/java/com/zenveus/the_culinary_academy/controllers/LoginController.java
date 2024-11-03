package com.zenveus.the_culinary_academy.controllers;

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

public class LoginController {
    public AnchorPane rootNode;
    private String name="admin";
    private String pass="admin";

    @FXML
    private TextField uNameText;

    @FXML
    private PasswordField uPassText;

    @FXML
    private static Stage mainStage;

    @FXML
    private void logBtn(ActionEvent event) throws IOException {
        String uName=uNameText.getText();
        String uPass=uPassText.getText();

        if (true /*!uName.isEmpty() && !uPass.isEmpty()*/){

            if (true /*uName.equals(this.name) && uPass.equals(this.pass)*/ ) {

                System.out.println("Go to dashBord");

                uNameText.setText("");
                uPassText.setText("");
                dashBord();



            }else{
                printAlert("Invalied UserName or Password!");

            }
        }else{
            printAlert("Not fill UserName or Password!");
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

}
