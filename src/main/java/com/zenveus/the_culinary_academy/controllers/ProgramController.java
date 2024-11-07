package com.zenveus.the_culinary_academy.controllers;

import com.zenveus.the_culinary_academy.bo.BOFactory;
import com.zenveus.the_culinary_academy.bo.custom.ProgramBO;
import com.zenveus.the_culinary_academy.bo.custom.UserBO;
import com.zenveus.the_culinary_academy.dto.ProgramDTO;
import com.zenveus.the_culinary_academy.dto.UserDTO;
import com.zenveus.the_culinary_academy.entity.User;
import com.zenveus.the_culinary_academy.tm.ProgramTm;
import com.zenveus.the_culinary_academy.util.Regex;
import com.zenveus.the_culinary_academy.util.TextFields;
import javafx.animation.TranslateTransition;
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

    ProgramBO programBO = (ProgramBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PROGRAM);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTransition();
        setProgramID();
        setCellValueFactories();
        loadAllPrograms();
    }

    private void loadAllPrograms() {
        try {
            List<ProgramDTO> allProgram = programBO.getAllProgram();
            programTable.getItems().clear();
            for (ProgramDTO programDTO : allProgram) {
                programTable.getItems().add(new ProgramTm(
                        programDTO.getProgramId(),
                        programDTO.getProgramName(),
                        programDTO.getDuration(),
                        programDTO.getFee()
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setCellValueFactories() {
        colProId.setCellValueFactory(new PropertyValueFactory<>("programId"));
        colProName.setCellValueFactory(new PropertyValueFactory<>("programName"));
        colProDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colProFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
    }

    private void setProgramID() {
        String lastId = getLastUserId();
        programIDField.setText(lastId);
        programIDField.setDisable(true);
    }

    private String getLastUserId() {
        List<ProgramDTO> allProgram = programBO.getAllProgram();

        if (allProgram.isEmpty()) {
            return "P001";
        }

        String lastProgramId = allProgram.get(allProgram.size() - 1).getProgramId();
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
        sideTransition.setToX(548); // Set initial `toX` based on `isShow`
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
        isShow = !isShow;
        sideTransition.setDuration(Duration.seconds(isShow ? 1.5 : 2));
        sideTransition.setToX(isShow ? 548 : 0);
        updateIcon();
        sideTransition.play();
    }

    //  program back btn (search bar)
    public void programBackBtn(ActionEvent actionEvent) {
        System.out.println("click program page back Btn");
    }
    // program search filed enter click (search bar)
    public void searchProgramClick(ActionEvent actionEvent) {
        System.out.println("click program search Btn");
    }

    // program search clear btn (search bar)
    public void searchProgramClearBtn(ActionEvent actionEvent) {
        System.out.println("click program create Btn");
    }

    
    
    
    // program delete btn
    public void programDeleteBtn(ActionEvent actionEvent) {
        System.out.println("click program delete Btn");
        if (selectedProgram != null) {
            try {
                boolean isDeleted = programBO.deleteProgram(selectedProgram.getProgramId());
                System.out.println(isDeleted);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Program Deleted Successfully!").showAndWait();
                    loadAllPrograms();
                    setProgramID();
                    clearFields();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Delete Program!").showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    // program save btn
    public void programSaveBtn(ActionEvent actionEvent) {
        System.out.println("click program save Btn");

        String programId = programIDField.getText();
        String programName = programNameField.getText();
        String programDuration = programDurationField.getText();
        double programFee = Double.parseDouble(programFeeField.getText());

        ProgramDTO programDTO = new ProgramDTO(programId, programName, programDuration, programFee);


        if(!Regex.isTextFieldValid(TextFields.PRICE, programFeeField.getText())){
            new Alert(Alert.AlertType.WARNING, "Invalid Fee").showAndWait();
            programFeeField.requestFocus();
            return;
        }
        try {
            boolean isAdded = programBO.addProgram(programDTO);
            if (isAdded) {
                new Alert(Alert.AlertType.INFORMATION, "Program Added Successfully!").showAndWait();
                loadAllPrograms();
                setProgramID();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to Add Program!").showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void clearFields() {
        programNameField.clear();
        programDurationField.clear();
        programFeeField.clear();
    }

    // program update btn
    public void programUpdateBtn(ActionEvent actionEvent) {
        System.out.println("click program update Btn");

        ProgramDTO programDto = new ProgramDTO();
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
            boolean isUpdated = programBO.updateProgram(programDto);
            System.out.println(isUpdated);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Program Updated Successfully!").showAndWait();
                loadAllPrograms();
                clearFields();
                setProgramID();
            }else{
                new Alert(Alert.AlertType.ERROR, "Failed to Update Program!").showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void rowClick(MouseEvent mouseEvent) {
        System.out.println("click program row");

        if (programTable.getSelectionModel().getSelectedItem() != null) {
            sidePaneTitle.setText("Update Program");
            selectedProgram = programTable.getSelectionModel().getSelectedItem();
            programIDField.setText(selectedProgram.getProgramId());
            programNameField.setText(selectedProgram.getProgramName());
            programDurationField.setText(selectedProgram.getDuration());
            programFeeField.setText(String.valueOf(selectedProgram.getFee()));

            if (isShow) {
                isShow = false;
                sideTransition.setDuration(Duration.seconds(2));
                sideTransition.setToX(isShow ? 550 : 0);
                updateIcon();
                sideTransition.play();
            }
        }
    }
}
