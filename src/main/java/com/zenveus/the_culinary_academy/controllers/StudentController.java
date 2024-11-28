package com.zenveus.the_culinary_academy.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.zenveus.the_culinary_academy.bo.BOFactory;
import com.zenveus.the_culinary_academy.bo.custom.ProgramBO;
import com.zenveus.the_culinary_academy.bo.custom.StudentBO;
import com.zenveus.the_culinary_academy.dto.ProgramDto;
import com.zenveus.the_culinary_academy.dto.StudentDto;
import com.zenveus.the_culinary_academy.tm.ProgramTm;
import com.zenveus.the_culinary_academy.tm.StudentTm;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    public JFXComboBox programCombo;
    public JFXTextArea selectProgramShow;
    public JFXComboBox payOptionCombo;
    public JFXTextArea paymentDetailsTxtArea;
    public TableColumn<?,?> colStuId;
    public TableColumn<?,?> colStuName;
    public TableColumn<?,?> colStuNic;
    public TableColumn<?,?> colStuAge;
    public TableColumn<?,?> colStuEmail;
    public TableColumn<?,?> colStuPhone;
    public TableColumn<?,?> colStuAddress;
    public TableView<StudentTm> studentTable;
    public TableColumn<?,?> colAction;
    public JFXComboBox paymentCombo;

    private TranslateTransition sideTransition;
    private boolean isShow = false;

    ObservableList<StudentTm> observableList = FXCollections.observableArrayList();


    StudentBO studentbo = (StudentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STUDENT);
    ProgramBO programbo = (ProgramBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PROGRAM);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTransition();
        setStudentID();
        setCellValueFactories();
        loadAllStudents();
        setProgramComboBox();
        setPaymentComboBox();
        setProgramComboAction();
        disableFields();
    }

    private void disableFields() {
        selectProgramShow.setDisable(false);
        paymentDetailsTxtArea.setDisable(false);
    }

    private void setProgramComboAction() {
        paymentDetailsTxtArea.setText("Programs\t:\n");
        payOptionCombo.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                String selectedProgram = programCombo.getValue().toString();
                // get first word of the selected program
                selectedProgram = selectedProgram.split("-")[0].trim();

                String selectedPaymentOption = payOptionCombo.getValue().toString();
                String currentText = selectProgramShow.getText();

                // General check for the selected program ID in any format
                if (!currentText.contains("Program ID: " + selectedProgram) &&
                        !currentText.matches("(?m)^" + selectedProgram + " - .*")) {

                    // Append the new selection to the existing text
                    selectProgramShow.setText(currentText + (currentText.isEmpty() ? "" : "\n")
                            + selectedProgram + " - " + selectedPaymentOption);

                    // Reset the combo boxes
                    programCombo.setValue(null);
                    payOptionCombo.setValue(null);

                    setPaymentDetails(selectedPaymentOption, selectedProgram);
                    setTotalPayments();
                } else {
                    // Show warning if the program is already added
                    new Alert(Alert.AlertType.WARNING, "The selected program is already added.").show();
                    System.out.println("The selected program is already added.");

                    // Reset the combo boxes
                    programCombo.setValue(null);
                    payOptionCombo.setValue(null);
                }
            }
        });
    }


    private void setTotalPayments() {
        String[] lines = paymentDetailsTxtArea.getText().split("\n");
        double total = 0;
        StringBuilder newText = new StringBuilder();

        for (String line : lines) {
            // Skip unwanted lines: "Total", "Programs :", or empty lines
            if (line.trim().startsWith("Total") || line.trim().startsWith("Programs :") || line.trim().isEmpty()) {
                continue;
            }

            // Check if the line contains "Rs." and parse the amount
            if (line.contains("Rs.")) {
                try {
                    String[] parts = line.split("Rs\\.");
                    double payment = Double.parseDouble(parts[1].trim());
                    total += payment;
                } catch (NumberFormatException e) {
                    e.printStackTrace();  // Handle parsing error gracefully
                }
            }

            // Append each valid line to the new text
            newText.append(line).append("\n");
        }

        // Append the correctly calculated total to the new text
        newText.append("\nTotal\t: Rs.").append(total);
        paymentDetailsTxtArea.setText(newText.toString());
    }

    private void setPaymentDetails(String selectedPaymentOption, String selectedProgram) {
        // Split the selectedProgram string at the first "-" to separate ID and name
        String[] programParts = selectedProgram.split(" - ", 2);
        String programName = programParts.length > 1 ? programParts[1].trim() : selectedProgram;

        String currentText = paymentDetailsTxtArea.getText();
        paymentDetailsTxtArea.setText(currentText + (currentText.isEmpty() ? "" : "\n")
                + programName + " - " + selectedPaymentOption + " (Advance)\t:\n");

        double advancePay = getProgramAdvancePay(selectedProgram, selectedPaymentOption);
        paymentDetailsTxtArea.setText(paymentDetailsTxtArea.getText() + "Rs." + advancePay);
    }

    private double getProgramAdvancePay(String selectedProgram, String selectedPaymentOption) {
        System.out.println("Selected Program: " + selectedProgram);
        // get first word of the selected program
        String programId = selectedProgram.split("-")[0].trim();
        System.out.println("Program ID: " + programId);
        ProgramDto programDto = programbo.getProgramDetails(programId);
        double fee = programDto.getFee();

        // get the advance payment based on the selected program
        double advancePay = 0;
        switch (selectedPaymentOption) {
            case "3 inst":
                advancePay = fee * 0.03;
                break;
            case "6 inst":
                advancePay = fee * 0.05;
                break;
            case "12 inst":
                advancePay = fee * 0.07;
                break;
        }

        return advancePay;
    }

    private void setPaymentComboBox() {
        payOptionCombo.getItems().add("3 inst");
        payOptionCombo.getItems().add("6 inst");
        payOptionCombo.getItems().add("12 inst");
    }

    private void setProgramComboBox() {
        List<ProgramDto> allPrograms = programbo.getAllPrograms();
        for (ProgramDto program : allPrograms) {
            programCombo.getItems().add(program.getProgramId() + " - " + program.getProgramName());
        }
    }

    private void setCellValueFactories() {
        // Set the cell value factories for the student table
        colStuId.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        colStuName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colStuNic.setCellValueFactory(new PropertyValueFactory<>("studentNIC"));
        colStuAge.setCellValueFactory(new PropertyValueFactory<>("studentAge"));
        colStuEmail.setCellValueFactory(new PropertyValueFactory<>("studentEmail"));
        colStuPhone.setCellValueFactory(new PropertyValueFactory<>("studentPhone"));
        colStuAddress.setCellValueFactory(new PropertyValueFactory<>("studentAddress"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void setStudentID() {
        String lastStudent = getLastStudentID();
        studentIDField.setText(lastStudent);
        studentIDField.setDisable(true);
    }

    private String getLastStudentID() {
        List<StudentDto> allStudents = studentbo.getAllStudents();

        if (allStudents.isEmpty()) {
            return "S0001";
        }

        String lastStudentId = allStudents.get(allStudents.size() - 1).getStudentId();
        if (lastStudentId == null || lastStudentId.isEmpty() || !lastStudentId.matches("S\\d+")) {
            return "S0001";
        }

        int id = Integer.parseInt(lastStudentId.substring(1));
        id++;

        return String.format("S%04d", id);
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
        sideTransition.stop();  // Stop any ongoing transition before starting a new one

        // Set starting and ending points dynamically based on isShow
        sideTransition.setFromX(isShow ? 870 : 0);
        sideTransition.setToX(isShow ? 0 : 870);
        sideTransition.setDuration(Duration.seconds(1.5));

        isShow = !isShow;  // Toggle the state

        sideTransition.setOnFinished(e -> updateIcon()); // Update icon after the transition completes
        sideTransition.play();
    }

    //  student back btn (search bar)
    public void studentBackBtn(ActionEvent actionEvent) {
        System.out.println("click student page back Btn");

        System.out.println("click employee page back Btn");

        DashboardController dashboardController = new DashboardController();
        dashboardController.loadDashboard(reportMainAnchor);
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
    // student save btn
    public void studentSaveBtn(ActionEvent actionEvent) {
        System.out.println("click student save Btn");

        String studentId = studentIDField.getText();
        String studentName = studentNameField.getText();
        String studentNIC = studentNICField.getText();
        String studentDOB = studentDOBField.getValue().toString();
        String studentPhone = studentPhoneField.getText();
        String studentEmail = studentEmailField.getText();
        String studentAddress = studentAddressField.getText();
        LocalDate registrationDate = LocalDate.now();
        LocalTime registrationTime = LocalTime.now();

        StudentDto studentDto = new StudentDto(studentId, studentNIC, studentDOB, studentName, studentAddress, studentEmail, studentPhone, registrationDate, registrationTime);

        // Get the selected programs from the selectProgramShow text area
        String[] programs = selectProgramShow.getText().split("\n");
        List<String[]> programDetailsList = new ArrayList<>();

        for (String program : programs) {
            if (program.contains("-")) {
                String[] parts = program.split("-");
                if (parts.length >= 2) {
                    String programId = parts[0].trim();
                    String programInfo = parts[1].trim();

                    programDetailsList.add(new String[]{programId, programInfo});
                }
            }
        }

        String[][] programDetailsArray = programDetailsList.toArray(new String[0][]);
        String[][] paymentDetailsArray = getPaymentDetailsArray();

        // Debugging Output
        System.out.println("Program Details:");
        for (String[] details : programDetailsArray) {
            for (String part : details) {
                System.out.println(part);
            }
        }

        System.out.println("Payment Details:");
        for (String[] payment : paymentDetailsArray) {
            System.out.println("Program: " + payment[0] + ", Amount: " + payment[1]);
        }


        boolean isSaved = studentbo.saveStudentAndPrograms(studentDto, programDetailsArray, paymentDetailsArray);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Student saved successfully.").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save student.").show();
        }
    }

    private String[][] getPaymentDetailsArray() {
        String[] paymentDetails = paymentDetailsTxtArea.getText().split("\n");
        List<String[]> paymentDetailsList = new ArrayList<>();

        String programDetails = null;
        String paymentAmount = null;

        for (String payment : paymentDetails) {
            // Skip lines that contain "Total", "Programs :", or empty lines
            if (payment.contains("Total") || payment.contains("Programs") || payment.trim().isEmpty()) {
                continue;
            }

            // Capture the program details (assuming it's on a separate line)
            if (programDetails == null) {
                programDetails = payment.trim();
                // Remove extra details after the "-" (if present) and trailing colon if any
                int indexOfSeparator = programDetails.indexOf("-");
                if (indexOfSeparator != -1) {
                    programDetails = programDetails.substring(0, indexOfSeparator).trim();
                }
                programDetails = programDetails.replaceAll(":$", "").trim();
            } else if (payment.contains("Rs.")) {
                // Capture the payment amount (from the line containing "Rs.")
                paymentAmount = payment.split("Rs\\.")[1].trim().replaceAll("[^\\d.]", "");

                // Add both the program and its corresponding payment to the list
                paymentDetailsList.add(new String[]{programDetails, paymentAmount});

                // Reset programDetails and paymentAmount for the next program
                programDetails = null;
                paymentAmount = null;
            }
        }

        // Convert the list to a 2D array and return it
        return paymentDetailsList.toArray(new String[0][]);
    }


    // student update btn
    public void studentUpdateBtn(ActionEvent actionEvent) {
        System.out.println("click student update Btn");

        String studentId = studentIDField.getText();
        String studentName = studentNameField.getText();
        String studentNIC = studentNICField.getText();
        String studentDOB = studentDOBField.getValue().toString();
        String studentPhone = studentPhoneField.getText();
        String studentEmail = studentEmailField.getText();
        String studentAddress = studentAddressField.getText();

        StudentDto studentDto = new StudentDto();
        studentDto.setStudentId(studentId);
        studentDto.setFullName(studentName);
        studentDto.setStudentNic(studentNIC);
        studentDto.setDob(studentDOB);
        studentDto.setPhone(studentPhone);
        studentDto.setEmail(studentEmail);
        studentDto.setAddress(studentAddress);

        // Get the payment details from the paymentDetailsTxtArea
        String[] paymentDetails = paymentDetailsTxtArea.getText().split("\n");

        List<String[]> programDetailsList = new ArrayList<>();

        double Total = 0.0;

        String lastPaymentAmount = ""; // Temporary storage for the last payment amount
        for (String payment : paymentDetails) {
//            System.out.println("Processing payment: " + payment); // Log the payment string
            if (payment.contains("Total") || payment.contains("Programs")) {
                // store the total amount with out Rs.
                if (payment.contains("Total")) {
                    Total = Double.parseDouble(payment.split("Rs.")[1].trim());
                    System.out.println("Total: " + Total);

                }

                continue; // Skip lines containing "Total" or "Programs"
            }
            if (payment.contains("-") && !payment.contains("Rs.")) {
                String[] parts = payment.split("-");
//                System.out.println("Parts length: " + parts.length); // Log the length of the parts array
//                for (String part : parts) {
//                    System.out.println("Part: " + part); // Log each part
//                }
                if (parts.length >= 3) {
                    String programId = parts[0].trim();
                    String programInfo = parts[1].trim();
                    String installment = parts[2].trim();
                    programDetailsList.add(new String[]{programId, programInfo, installment});
//                    System.out.println("Program ID: " + programId + ", Program Info: " + programInfo + ", Installment: " + installment);
                } else if (parts.length>=2) {
                    String programId = parts[0].trim();
                    String programInfo = parts[1].trim();

                    programDetailsList.add(new String[]{programId,null,programInfo});
                }

            } else if (payment.contains("Rs.")) {
                lastPaymentAmount = payment.split("Rs.")[1].trim();
                if (!programDetailsList.isEmpty()) {
                    String[] lastProgramDetails = programDetailsList.get(programDetailsList.size() - 1);
                    lastProgramDetails = Arrays.copyOf(lastProgramDetails, lastProgramDetails.length + 1);
                    lastProgramDetails[lastProgramDetails.length - 1] = lastPaymentAmount;
                    programDetailsList.set(programDetailsList.size() - 1, lastProgramDetails);
                }
            }

        }

        try {
            boolean isUpdated = studentbo.updateStudentAndPrograms(studentDto, programDetailsList, Total);
            // Reinitialize the controller
            initialize(null, null);

            new Alert(Alert.AlertType.INFORMATION, "Student updated successfully.").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to update student.").show();
            throw new RuntimeException(e);
        }


        // Debugging: Print extracted details
//        System.out.println("Extracted Details:");
//        for (String[] details : programDetailsList) {
//            System.out.printf("Program ID: %s, Info: %s, Installment: %s, Payment: Rs. %s%n",
//                    details[0], details[1], details[2], details[3]);
//        }


    }

    public void rowClick(MouseEvent mouseEvent) {
        System.out.println("click row");

        StudentTm selectedItem = studentTable.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return;
        }

        StudentDto student = studentbo.searchStudentByStudentId(selectedItem.getStudentID());

        studentIDField.setText(student.getStudentId());
        studentNameField.setText(student.getFullName());
        studentNICField.setText(student.getStudentNic());
        studentDOBField.setValue(LocalDate.parse(student.getDob()));
        studentPhoneField.setText(student.getPhone());
        studentEmailField.setText(student.getEmail());
        studentAddressField.setText(student.getAddress());

        // Load the programs and payments for the selected student
        loadProgramsAndPayments(student.getStudentId());

    }

    private void loadProgramsAndPayments(String studentId) {
        // Retrieve programs and payments for the student
        List<String[]> programs = studentbo.getProgramsForStudent(studentId);

        // Build a display text for programs
        StringBuilder programText = new StringBuilder();
        for (String[] program : programs) {
            programText.append("Program ID: ").append(program[0])
                    .append("\nName: ").append(program[1])
                    .append("\nDuration: ").append(program[2])
                    .append("\nFee: ").append(program[3])
                    .append("\nPayment Option: ").append(program[4])
                    .append("\nInstallment Fee: ").append(program[5])
                    .append("\nTotal Due: ").append(program[6])
                    .append("\nPayment Status: ").append(program[7])
                    .append("\n\n");
        }
        selectProgramShow.setText(programText.toString());

        // Set programs to payment combo box (exclude already paid programs)
        for (String[] program : programs) {
            if (program[7].equals("Not Paid")) { // Payment status is "Not Paid"
                paymentCombo.getItems().add(program[0] + " - " + program[1]);
            }
        }

        // Handle combo box selection for payment
        paymentCombo.setOnAction(event -> {
            String selectedProgram = String.valueOf(paymentCombo.getValue()); // Get selected value
            if (selectedProgram == null) return;

            // Extract program ID and name
            String[] parts = selectedProgram.split(" - ");
            String programId = parts[0].trim();
            String programName = parts[1].trim();

            // Find program details
            String[] programDetails = programText.toString().split("Program ID: " + programId);
            if (programDetails.length < 2) return; // If program not found, exit

            String[] programDetailsParts = programDetails[1].split("\n");
            String paymentOption = programDetailsParts[4].split(":")[1].trim();
            String installmentFee = programDetailsParts[5].split(":")[1].trim();

            // Extract current payment details and total
            String paymentDetails = paymentDetailsTxtArea.getText();
            String[] paymentTextLines = paymentDetails.split("\n");
            double total = 0;
            StringBuilder updatedPaymentText = new StringBuilder();

            // Rebuild the payment details while extracting the existing total
            for (String line : paymentTextLines) {
                if (line.contains("Total")) {
                    total = Double.parseDouble(line.split("Rs\\.")[1].trim());
                } else {
                    updatedPaymentText.append(line).append("\n");
                }
            }

            // Check if the program is already added
            if (paymentDetails.contains(programName)) {
                new Alert(Alert.AlertType.WARNING, "The selected program is already added.").show();
                return;
            }

            // Add new program payment details
            updatedPaymentText.append(programId).append(" - ").append(programName).append(" - ").append(paymentOption).append(" (Installment):\n");
            updatedPaymentText.append("Rs. ").append(installmentFee).append("\n\n");

            // Update the total
            total += Double.parseDouble(installmentFee);
            updatedPaymentText.append("Total: Rs. ").append(total).append("\n");

            // Update the payment details text area
            paymentDetailsTxtArea.setText(updatedPaymentText.toString());
        });

    }

    public void loadAllStudents() {
        List<StudentDto> allStudents = studentbo.getAllStudents();
        observableList.clear();

        for (StudentDto studentDto : allStudents) {
            JFXButton btn = new JFXButton("Delete");
            observableList.add(new StudentTm(studentDto.getStudentId(), studentDto.getFullName(), studentDto.getStudentNic(), getAge(studentDto.getDob()), studentDto.getEmail(), studentDto.getPhone(), studentDto.getAddress(), btn));
        }
        studentTable.setItems(observableList);
    }

    public String getAge(String dob) {
        LocalDate birthDate = LocalDate.parse(dob);
        LocalDate currentDate = LocalDate.now();
        return String.valueOf(currentDate.getYear() - birthDate.getYear());
    }
}
