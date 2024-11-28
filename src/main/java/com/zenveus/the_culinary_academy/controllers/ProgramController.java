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
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

        if (allPrograms.isEmpty()) {
            return "P001";
        }

        String lastProgramId = allPrograms.get(allPrograms.size() - 1).getProgramId();
        if (lastProgramId == null || lastProgramId.isEmpty() || !lastProgramId.matches("P\\d+")) {
            return "P001";
        }

        int id = Integer.parseInt(lastProgramId.substring(1));
        id++;

        return String.format("P%03d", id);
    }

    private void setTransition() {
        sideTransition = new TranslateTransition(Duration.seconds(1.5), programRegMainAnchor);
        sideTransition.setFromX(0);
        sideTransition.setToX(550); // Set initial `toX` based on `isShow`
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
        sideTransition.setFromX(isShow ? 548 : 0);
        sideTransition.setToX(isShow ? 0 : 548);
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
            try {
                boolean isDeleted = programBo.deleteProgram(selectedProgram.getProgramId());
                System.out.println(isDeleted);
                if (isDeleted) {
                    loadAllPrograms();
                    setProgramID();
                    clearFields();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    // program save btn
    public void programSaveBtn(ActionEvent actionEvent) {
        System.out.println("click program save Btn");

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // program update btn
    public void programUpdateBtn(ActionEvent actionEvent) {
        System.out.println("click program update Btn");

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
        } catch (Exception e) {
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
                sideTransition.setToX(isShow ? 550 : 0);
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
