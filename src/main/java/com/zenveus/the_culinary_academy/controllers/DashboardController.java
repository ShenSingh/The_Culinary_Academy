package com.zenveus.the_culinary_academy.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {


    public AnchorPane mainContainer;
    public Text title;

    //
    public void logOutBtn(ActionEvent actionEvent) {
        System.out.println("LogOut");
    }

    public void dashboardBtn(ActionEvent actionEvent) throws IOException {
        System.out.println("click dashboard");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zenveus/the_culinary_academy/view/dashboard.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, Color.TRANSPARENT);

            Stage stage = (Stage)this.mainContainer.getScene().getWindow();

            stage.setScene(scene);



        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void studentBtn(ActionEvent actionEvent) {
        System.out.println("click student");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zenveus/the_culinary_academy/view/student.fxml"));
            Parent root = loader.load();

            // Clear the mainContainer and add the loaded FXML as the new content
            mainContainer.getChildren().clear();
            mainContainer.getChildren().add(root);

            // Anchor the new content to fit the mainContainer
            AnchorPane.setTopAnchor(root, 0.0);
            AnchorPane.setBottomAnchor(root, 0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
            AnchorPane.setRightAnchor(root, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void employeeBtn(ActionEvent actionEvent) {
        System.out.println("click employee");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zenveus/the_culinary_academy/view/employee.fxml"));
            Parent root = loader.load();

            // Clear the mainContainer and add the loaded FXML as the new content
            mainContainer.getChildren().clear();
            mainContainer.getChildren().add(root);

            // Anchor the new content to fit the mainContainer
            AnchorPane.setTopAnchor(root, 0.0);
            AnchorPane.setBottomAnchor(root, 0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
            AnchorPane.setRightAnchor(root, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void programBtn(ActionEvent actionEvent) {
        System.out.println("click program");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zenveus/the_culinary_academy/view/program.fxml"));
            Parent root = loader.load();

            // Clear the mainContainer and add the loaded FXML as the new content
            mainContainer.getChildren().clear();
            mainContainer.getChildren().add(root);

            // Anchor the new content to fit the mainContainer
            AnchorPane.setTopAnchor(root, 0.0);
            AnchorPane.setBottomAnchor(root, 0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
            AnchorPane.setRightAnchor(root, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void myAccountBtn(ActionEvent actionEvent) {
        System.out.println("click my account");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zenveus/the_culinary_academy/view/myAccount.fxml"));
            Parent root = loader.load();

            // Clear the mainContainer and add the loaded FXML as the new content
            mainContainer.getChildren().clear();
            mainContainer.getChildren().add(root);

            // Anchor the new content to fit the mainContainer
            AnchorPane.setTopAnchor(root, 0.0);
            AnchorPane.setBottomAnchor(root, 0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
            AnchorPane.setRightAnchor(root, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void systemMiniBtn(ActionEvent actionEvent) {
        // Minimize the application
        Stage stage = (Stage) mainContainer.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void systemExitBtn(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }
}
