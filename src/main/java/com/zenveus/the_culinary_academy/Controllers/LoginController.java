package com.zenveus.the_culinary_academy.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
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
    public void setMainStage(Stage stage) {
        this.mainStage = stage;
    }
    //////////////////////////////////  dashbord  //////////////////////////////////////////////////
    private static Stage DemoStage=new Stage();

    public void dashBord() throws IOException{
        //
        Parent rootNode = FXMLLoader.load(getClass().getResource("/home/shen/Documents/myProject/The_Culinary_Academy/src/main/resources/com/zenveus/the_culinary_academy/view/dashboard.fxml"));
        Scene dashBord=new Scene(rootNode,950,600);


        DemoStage.setScene(dashBord);
        DemoStage.setTitle("DashBord");

        mainStage.hide();
        DemoStage.show();
    }
    static Stage getMainStage(){
        return mainStage;
    }
    static Stage getDemoStage(){
        return DemoStage;
    }
}
