package com.zenveus.the_culinary_academy.controllers;

import com.zenveus.the_culinary_academy.bo.BOFactory;
import com.zenveus.the_culinary_academy.bo.custom.ProgramBO;
import com.zenveus.the_culinary_academy.dto.ProgramDto;
import com.zenveus.the_culinary_academy.dto.UserDto;
import com.zenveus.the_culinary_academy.entity.Program;
import com.zenveus.the_culinary_academy.tm.ProgramTm;
import com.zenveus.the_culinary_academy.util.Regex;
import com.zenveus.the_culinary_academy.util.TextFields;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
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
import java.util.List;
import java.util.ResourceBundle;

public class ProgramController implements Initializable {
    public AnchorPane programRegMainAnchor;
    public ImageView reportLeftRightImage;

    // program Fields
    public TextField programIDField;
    public TextField programDurationField;
    public TextField programNameField;
    public TextField programFeeField;

    // program Side Pane Title
    public Text sidePaneTitle;
    // program Search Field
    public TextField searchEmployee;

    // table
    public TableView<ProgramTm> programTable;
    // collunm
    public TableColumn<?,?> colProId;
    public TableColumn<?,?> colProName;
    public TableColumn<?,?> colProDuration;
    public TableColumn<?,?> colProFee;


    private TranslateTransition sideTransition;
    private boolean isShow = false;

    public ProgramTm selectedProgram;


    ObservableList<ProgramTm> observableList = FXCollections.observableArrayList();
    ProgramBO programBo = (ProgramBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PROGRAM);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTransition();
        setProgramID();
        setCellValueFactories();
        loadAllPrograms();
    }

    private void loadAllPrograms() {
        try {
            List<ProgramDto> allPrograms = programBo.getAllPrograms();
            observableList.clear();
            for (ProgramDto programDto : allPrograms) {
                observableList.add(new ProgramTm(programDto.getProgramId(), programDto.getProgramName(), programDto.getDuration(), String.valueOf(programDto.getFee())));
            }
            programTable.setItems(observableList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactories() {
        colProId.setCellValueFactory(new PropertyValueFactory<>("programId"));
        colProName.setCellValueFactory(new PropertyValueFactory<>("programName"));
        colProDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colProFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
    }

    private void setProgramID() {
        String lastProgramId = getLastProgramId();
        programIDField.setText(lastProgramId);
        programIDField.setEditable(true);
    }

    private String getLastProgramId() {
        List<ProgramDto> allPrograms = programBo.getAllPrograms();
        if (allPrograms == null || allPrograms.isEmpty()) {
            return "CA1001";
        }

        // Get the last program ID
        String lastProgramId = allPrograms.get(allPrograms.size() - 1).getProgramId();
        if (lastProgramId == null || !lastProgramId.matches("CA1\\d+")) {
            return "CA1001";
        }

        try {
            // Extract numeric part and increment
            int id = Integer.parseInt(lastProgramId.substring(3));
            id++;
            return String.format("CA1%03d", id); // Keep 3-digit padding
        } catch (NumberFormatException e) {
            // Log error and return default ID
            System.err.println("Invalid program ID format: " + lastProgramId);
            return "CA1001";
        }
    }


    private void setTransition() {
        sideTransition = new TranslateTransition(Duration.seconds(1.5), programRegMainAnchor);
        sideTransition.setFromX(0);
        sideTransition.setToX(220); // Set initial `toX` based on `isShow`
        updateIcon();
    }

    private void updateIcon() {
        String iconPath = isShow
                ? "src/main/resources/com/zenveus/the_culinary_academy/image/icons/rightArow.png"
                : "src/main/resources/com/zenveus/the_culinary_academy/image/icons/leftArow.png";
        Image image = new Image(new File(iconPath).toURI().toString());
        reportLeftRightImage.setImage(image);
    }
    public void programAddPaneShowHideBtn(ActionEvent actionEvent){
        sideTransition.stop();  // Stop any ongoing transition before starting a new one

        // Set starting and ending points dynamically based on isShow
        sideTransition.setFromX(isShow ? 220 : 0);
        sideTransition.setToX(isShow ? 0 : 220);
        sideTransition.setDuration(Duration.seconds(1.5));

        isShow = !isShow;  // Toggle the state

        sideTransition.setOnFinished(e -> updateIcon()); // Update icon after the transition completes
        sideTransition.play();
    }

    //  program back btn (search bar)
    public void programBackBtn(ActionEvent actionEvent) {
        System.out.println("click program page back Btn");

        DashboardController dashboardController = new DashboardController();
        dashboardController.loadDashboard(programRegMainAnchor);
    }
    // program search filed enter click (search bar)
    public void searchProgramClick(ActionEvent actionEvent) {
        System.out.println("click program search Btn");

        String programName = searchEmployee.getText();

        if (programName.isEmpty()) {
            loadAllPrograms();
            return;
        }

        List<ProgramDto> allPrograms = programBo.getAllPrograms();
        observableList.clear();

        for (ProgramDto programDto : allPrograms) {
            if (programDto.getProgramName().contains(programName)) {
                observableList.add(new ProgramTm(programDto.getProgramId(), programDto.getProgramName(), programDto.getDuration(), String.valueOf(programDto.getFee())));
            }
        }

        programTable.setItems(observableList);

    }

    // program search clear btn (search bar)
    public void searchProgramClearBtn(ActionEvent actionEvent) {
        System.out.println("click program create Btn");

        searchEmployee.clear();
    }

    
    
    
    // program delete btn
    public void programDeleteBtn(ActionEvent actionEvent) {
        System.out.println("click program delete Btn");

        if (selectedProgram != null) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this program?", ButtonType.YES, ButtonType.NO);
            confirmationAlert.showAndWait();

            if (confirmationAlert.getResult() == ButtonType.YES) {
                try {
                    boolean isDeleted = programBo.deleteProgram(selectedProgram.getProgramId());
                    System.out.println(isDeleted);
                    if (isDeleted) {
                        loadAllPrograms();
                        setProgramID();
                        clearFields();
                    }
                    new Alert(Alert.AlertType.INFORMATION, "Program deleted successfully!").showAndWait();
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete program!").showAndWait();
                    e.printStackTrace();
                }
            }
        }
    }
    
    // program save btn
    public void programSaveBtn(ActionEvent actionEvent) {
        System.out.println("click program save Btn");

        // all fields must be filled
        if (programIDField.getText().isEmpty() || programNameField.getText().isEmpty() || programDurationField.getText().isEmpty() || programFeeField.getText().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "All fields must be filled!").showAndWait();
            return;
        }

        String programID = programIDField.getText();
        String programName = programNameField.getText();
        String programDuration = programDurationField.getText();
        String programFee = programFeeField.getText();

        // validate fee using regex
        if (!Regex.isTextFieldValid(TextFields.PRICE, programFee)) {
            new Alert(Alert.AlertType.WARNING, "Invalid Fee!").showAndWait();
            return;
        }

        ProgramDto programDto = new ProgramDto();

        programDto.setProgramId(programID);
        programDto.setProgramName(programName);
        programDto.setDuration(programDuration);
        programDto.setFee(Double.parseDouble(programFee));

        System.out.println(programDto);

        try {
            boolean isAdded = programBo.addProgram(programDto);
            System.out.println(isAdded);
            if (isAdded) {
                loadAllPrograms();
                setProgramID();
                clearFields();
            }
            new Alert(Alert.AlertType.INFORMATION, "Program added successfully!").showAndWait();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to add program!").showAndWait();
            e.printStackTrace();
        }
    }

    // program update btn
    public void programUpdateBtn(ActionEvent actionEvent) {
        System.out.println("click program update Btn");

        /// do not add if user id is already existing
        List<ProgramDto> allPrograms = programBo.getAllPrograms();

        for (ProgramDto program : allPrograms) {
            if (program.getProgramId().equals(programIDField.getText())) {
                new Alert(Alert.AlertType.WARNING, "Program ID already exists!").showAndWait();
                return;
            }
        }


        ProgramDto programDto = new ProgramDto();
        programDto.setProgramId(programIDField.getText());
        programDto.setProgramName(programNameField.getText());
        programDto.setDuration(programDurationField.getText());
        programDto.setFee(Double.parseDouble(programFeeField.getText()));

        // validate fee using regex
        if (!Regex.isTextFieldValid(TextFields.PRICE, programFeeField.getText())) {
            System.out.println("Invalid Fee");
            return;
        }

        try {
            boolean isUpdated = programBo.updateProgram(programDto);
            System.out.println(isUpdated);
            if (isUpdated) {
                loadAllPrograms();
                clearFields();
                setProgramID();
            }

            new Alert(Alert.AlertType.INFORMATION, "Program updated successfully!").showAndWait();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to update program!").showAndWait();
            e.printStackTrace();
        }
    }

    public void rowClick(MouseEvent mouseEvent) {
        System.out.println("click program row");

        if (programTable.getSelectionModel().getSelectedItem() != null) {
            selectedProgram = programTable.getSelectionModel().getSelectedItem();
            programIDField.setText(selectedProgram.getProgramId());
            programNameField.setText(selectedProgram.getProgramName());
            programDurationField.setText(selectedProgram.getDuration());
            programFeeField.setText(selectedProgram.getFee());

            if (isShow) {
                isShow = !isShow;
                sideTransition.setDuration(Duration.seconds(isShow ? 1.5 : 2));
                sideTransition.setToX(isShow ? 220 : 0);
                updateIcon();
                sideTransition.play();
            }
        }
    }

    public void clearFields() {
        programNameField.clear();
        programDurationField.clear();
        programFeeField.clear();
    }
}
