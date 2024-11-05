package com.zenveus.the_culinary_academy.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController  implements Initializable {


    public AnchorPane mainContainer;
    public Text title;
    public LineChart<String, Number> paymentLineChart;
    public Text programCount;
    public Text studentCount;
    public Text codinatorCount;

    public PieChart studentPieChart;
    public PieChart programPieChart;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setChartData();
    }

    private void setChartData() {
        // Create a data series for payments
        XYChart.Series<String, Number> paymentSeries = new XYChart.Series<>();
        paymentSeries.setName("Monthly Payments");

        // Add demo data (Month and Payment Amount)
        paymentSeries.getData().add(new XYChart.Data<>("January", 150));
        paymentSeries.getData().add(new XYChart.Data<>("February", 200));
        paymentSeries.getData().add(new XYChart.Data<>("March", 180));
        paymentSeries.getData().add(new XYChart.Data<>("April", 220));
        paymentSeries.getData().add(new XYChart.Data<>("May", 240));
        paymentSeries.getData().add(new XYChart.Data<>("June", 300));
        paymentSeries.getData().add(new XYChart.Data<>("July", 280));
        paymentSeries.getData().add(new XYChart.Data<>("August", 310));
        paymentSeries.getData().add(new XYChart.Data<>("September", 270));
        paymentSeries.getData().add(new XYChart.Data<>("October", 290));
        paymentSeries.getData().add(new XYChart.Data<>("November", 320));
        paymentSeries.getData().add(new XYChart.Data<>("December", 330));

        // Add the series to the chart
        paymentLineChart.getData().add(paymentSeries);


//        pie chart
        // Add demo data to the PieChart
        PieChart.Data courseA = new PieChart.Data("Course A", 30);
        PieChart.Data courseB = new PieChart.Data("Course B", 25);
        PieChart.Data courseC = new PieChart.Data("Course C", 20);
        PieChart.Data courseD = new PieChart.Data("Course D", 15);
        PieChart.Data courseE = new PieChart.Data("Course E", 10);

        // Add data to the PieChart
        studentPieChart.getData().addAll(courseA, courseB, courseC, courseD, courseE);




        // Add demo data to the PieChart
        PieChart.Data programA = new PieChart.Data("Culinary Arts", 40);
        PieChart.Data programB = new PieChart.Data("Baking & Pastry", 25);
        PieChart.Data programC = new PieChart.Data("Food Science", 15);
        PieChart.Data programD = new PieChart.Data("Hospitality Management", 10);
        PieChart.Data programE = new PieChart.Data("Nutrition", 10);

        // Add data to the PieChart
        programPieChart.getData().addAll(programA, programB, programC, programD, programE);


    }

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
