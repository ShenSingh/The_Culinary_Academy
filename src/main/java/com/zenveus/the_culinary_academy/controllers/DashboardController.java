package com.zenveus.the_culinary_academy.controllers;

import com.zenveus.the_culinary_academy.Launcher;
import com.zenveus.the_culinary_academy.bo.BOFactory;
import com.zenveus.the_culinary_academy.bo.custom.ChartBO;
import com.zenveus.the_culinary_academy.dto.UserDto;
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
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController  implements Initializable {


    public AnchorPane mainContainer;
    public LineChart<String, Number> paymentLineChart;
    public Text programCount;
    public Text studentCount;
    public Text codinatorCount;

    public PieChart studentPieChart;
    public PieChart programPieChart;
    public Text wellcomeText;
    public Text date;

    public Button dashboardBtn;
    public Button studentBtn;
    public Button employeeBtn;
    public Button programBtn;
    public Button myAccountBtn;

    Stage dashStage;


    ChartBO chartBO = (ChartBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CHART);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setWellcomeAndDate();
        setChartData();
        setCounts();
        setToolTip();
        getLoginUser();
    }

    private void getLoginUser() {
        UserDto userDTO = LoginController.getLoginUser();

        if (userDTO != null){
            if (userDTO.getJobRole().equals("Admin")){
                wellcomeText.setText("Welcome, Admin - "+LoginController.getLoginUser().getUsername());
            }else if (userDTO.getJobRole().equals("Coordinator")){
                wellcomeText.setText("Welcome, Coordinator - "+LoginController.getLoginUser().getUsername());
                employeeBtn.setOnAction(event -> {
                    // No action or show a message indicating restricted access
                });

                employeeBtn.setOpacity(0.5); // Appears disabled
                Tooltip.install(employeeBtn, new Tooltip("No access for Coordinator"));
            }else{
                System.out.println("user not found !!");
            }
        }else {
            wellcomeText.setText("Welcome, admin");
        }



    }

    private void setToolTip() {
        Tooltip.install(dashboardBtn,new Tooltip("Dashboard"));
        Tooltip.install(studentBtn, new Tooltip("Student"));
        Tooltip.install(employeeBtn, new Tooltip("Employee"));
        Tooltip.install(programBtn, new Tooltip("Program"));
        Tooltip.install(myAccountBtn, new Tooltip("My Account"));
    }

    private void setWellcomeAndDate() {
        wellcomeText.setText("Welcome, Admin");
        date.setText(String.valueOf(java.time.LocalDate.now()));
    }

    private void setCounts() {
        int proCount = chartBO.getProgramCount();
        programCount.setText(proCount+" Programs");

        int stuCount = chartBO.getStudentCount();
        studentCount.setText(stuCount+" Students");

        int coCount = chartBO.getCoordinatorCount();
        codinatorCount.setText(coCount+" Coordinators");
    }

    private void setChartData() {
        // Create a data series for payments
        XYChart.Series<String, Number> paymentSeries = new XYChart.Series<>();
        paymentSeries.setName("Monthly Payments");

        // Add demo data (Month and Payment Amount)
        List<Object[]> monthlyTotalPayments = chartBO.getMonthlyTotalPayments();

        // Sort the data by month (ascending order)
        Collections.sort(monthlyTotalPayments, (row1, row2) -> {
            int month1 = (int) row1[0];
            int month2 = (int) row2[0];
            return Integer.compare(month1, month2);
        });


        // Add the sorted data to the chart series
        for (Object[] row : monthlyTotalPayments) {
            int month = (int) row[0];
            double amount = (double) row[1];
            paymentSeries.getData().add(new XYChart.Data<>(String.valueOf(month), amount));
        }

        paymentLineChart.getData().add(paymentSeries);

//        pie chart
        // Add demo data to the PieChart

        // Get the student count for each course
        List<Object[]> studentCourseCount = chartBO.getStudentCourseCount();

        for (Object[] row : studentCourseCount) {
            String courseName = (String) row[0];
            System.out.println("courseName = " + courseName);
            long count = (long) row[1];
            System.out.println("count = " + count);
            studentPieChart.getData().add(new PieChart.Data(courseName, count));
        }



        // Add demo data to the PieChart
        List<Object[]> programsCountByDuration = chartBO.getProgramsCountByDuration();

        for (Object[] row : programsCountByDuration) {
            String duration = (String) row[0];
            long count = (long) row[1];
            programPieChart.getData().add(new PieChart.Data(duration, count));
        }

    }

    public void logOutBtn(ActionEvent actionEvent) {
        System.out.println("LogOut");
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("view/loginPage.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), Color.TRANSPARENT);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);

            dashStage = (Stage)mainContainer.getScene().getWindow();
            dashStage.close();


            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dashboardBtn(ActionEvent actionEvent) throws IOException {
        System.out.println("click dashboard");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zenveus/the_culinary_academy/view/dashboard.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, Color.TRANSPARENT);

            Stage stage = (Stage)this.mainContainer.getScene().getWindow();
            dashStage = stage;

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


    public void loadDashboard(AnchorPane employeeRegMainAnchor) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/zenveus/the_culinary_academy/view/dashboard.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, Color.TRANSPARENT);

            Stage stage = (Stage)employeeRegMainAnchor.getScene().getWindow();

            stage.setScene(scene);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
