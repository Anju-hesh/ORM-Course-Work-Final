package lk.ijse.gdse72.ormfinalcoursework.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse72.ormfinalcoursework.bo.BOFactory;
import lk.ijse.gdse72.ormfinalcoursework.bo.custom.PatientBO;
import lk.ijse.gdse72.ormfinalcoursework.bo.custom.TherapyProgramBO;
import lk.ijse.gdse72.ormfinalcoursework.dao.custom.TherapistDAO;
import lk.ijse.gdse72.ormfinalcoursework.dao.custom.impl.TherapistDAOImpl;
import lk.ijse.gdse72.ormfinalcoursework.dto.PatientDTO;
import lk.ijse.gdse72.ormfinalcoursework.dto.TherapyProgramDTO;
import lk.ijse.gdse72.ormfinalcoursework.dto.tm.PatientTM;
import lk.ijse.gdse72.ormfinalcoursework.dto.tm.TherapyProgramTM;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class TherapyProgramPageController {

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXComboBox<String> cmbTherapists;

    @FXML
    private TableColumn<TherapyProgramTM, Integer> colCost;

    @FXML
    private TableColumn<TherapyProgramTM, String> colDescription;

    @FXML
    private TableColumn<TherapyProgramTM, String> colDuration;

    @FXML
    private TableColumn<TherapyProgramTM, String> colId;

    @FXML
    private TableColumn<TherapyProgramTM, String> colName;

    @FXML
    private TableColumn<TherapyProgramTM, String> colTherapist;

    @FXML
    private AnchorPane subAnchor;

    @FXML
    private TableView<TherapyProgramTM> tblPrograms;

    @FXML
    private JFXTextField txtCost;

    @FXML
    private JFXTextArea txtDescription;

    @FXML
    private JFXTextField txtDuration;

    @FXML
    private JFXTextField txtProgramId;

    @FXML
    private JFXTextField txtProgramName;

    private final TherapyProgramBO THERAPYPROGRAMBO = (TherapyProgramBO) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_PROGRAM);


    public void initialize() {
        try {
            populateComboBoxes();
            refrashPage();
            loadTableData();
            visibleData();

            String nextProgramId = THERAPYPROGRAMBO.getNextTherapyProgramId();
            txtProgramId.setText(nextProgramId);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load Page: " + e.getMessage()).show();
        }
    }

    public void refrashPage() {

        String nextProgramId = null;
        try {
            nextProgramId = THERAPYPROGRAMBO.getNextTherapyProgramId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        txtProgramId.setText(nextProgramId);
        txtProgramName.setText("");
        txtDescription.setText("");
        txtCost.setText("");
        txtDuration.setText("");
        cmbTherapists.setValue(null);
    }

    public void loadTableData() throws Exception {
        ArrayList<TherapyProgramDTO> therapyProgramDTOS = THERAPYPROGRAMBO.getAllTherapyProgram();
        ObservableList<TherapyProgramTM> therapyProgramTMS = FXCollections.observableArrayList();

        for (TherapyProgramDTO therapyProgramDTO : therapyProgramDTOS) {
            TherapyProgramTM therapyProgramTM = new TherapyProgramTM(
                    therapyProgramDTO.getTherapyId(),
                    therapyProgramDTO.getProgramName(),
                    therapyProgramDTO.getDuration(),
                    therapyProgramDTO.getFee(),
                    therapyProgramDTO.getTherapist(),
                    therapyProgramDTO.getDescription()
            );
            therapyProgramTMS.add(therapyProgramTM);
        }
        tblPrograms.setItems(therapyProgramTMS);
    }

    public void visibleData() {
        colId.setCellValueFactory(new PropertyValueFactory<>("therapyId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("programName"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("fee"));
        colTherapist.setCellValueFactory(new PropertyValueFactory<>("therapist"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    public void populateComboBoxes() throws SQLException {
        TherapistDAO therapistDAO = new TherapistDAOImpl();

        ArrayList<String> therapistIds = therapistDAO.getTherapist();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(therapistIds);
        cmbTherapists.setItems(observableList);

    }

    @FXML
    void clearOnAction(ActionEvent event) {
        try {
            refrashPage();
            loadTableData();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void deleteOnAction(ActionEvent event) {
        TherapyProgramTM selectedTherapyProgram = tblPrograms.getSelectionModel().getSelectedItem();

        if (selectedTherapyProgram != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Deletion");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Are you sure you want to delete patient ID: " + selectedTherapyProgram.getTherapyId() + "?");

            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    boolean isDeleted = THERAPYPROGRAMBO.deleteTherapyProgram(selectedTherapyProgram.getTherapyId());

                    if (isDeleted) {
                        new Alert(Alert.AlertType.INFORMATION, "Therapy Program deleted successfully!").show();
                        loadTableData();
                        refrashPage();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Failed to delete the Therapy Program!").show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
                }
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a Therapy Program to delete.").show();
        }
    }

    @FXML
    void saveOnAction(ActionEvent event) {
        try{
            String programId = txtProgramId.getText();
            String programName = txtProgramName.getText();
            String duration = txtDuration.getText();
            BigDecimal fee = new BigDecimal(txtCost.getText());
            String description = txtDescription.getText();
            String therapistId = cmbTherapists.getValue();

            if (!programId.isEmpty() && !programName.isEmpty() && !duration.isEmpty() && fee != null  && !description.isEmpty() && !therapistId.isEmpty()) {
                TherapyProgramDTO therapyProgramDTO = new TherapyProgramDTO(
                        programId,
                        programName,
                        duration,
                        fee,
                        therapistId,
                        description

                );

                boolean isSaved = THERAPYPROGRAMBO.saveTherapyProgram(therapyProgramDTO);

                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Therapy_Program Saved Successfully!").show();
                    loadTableData();
                    refrashPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Save Therapy_Program!").show();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Please fill all the fields with valid data!").show();
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please enter valid numeric values for Age and Contact Number!").show();
        }
    }

    @FXML
    void searchOnAction(ActionEvent event) {
        String therapyProgramId = txtProgramId.getText();

        if (!therapyProgramId.isEmpty()) {
            try {
                TherapyProgramDTO therapyProgramDTO = THERAPYPROGRAMBO.searchTherapyProgram(therapyProgramId);

                if (therapyProgramDTO != null) {
                    txtProgramId.setText(therapyProgramDTO.getTherapyId());
                    txtProgramName.setText(therapyProgramDTO.getProgramName());
                    txtDuration.setText(therapyProgramDTO.getDuration());
                    txtCost.setText(therapyProgramDTO.getFee().toString());
                    txtDescription.setText(therapyProgramDTO.getDescription());
                    cmbTherapists.setValue(therapyProgramDTO.getTherapist());

                    ObservableList<TherapyProgramTM> therapyProgramTMS = FXCollections.observableArrayList();

                    TherapyProgramTM therapyProgramTM = new TherapyProgramTM(
                            therapyProgramDTO.getTherapyId(),
                            therapyProgramDTO.getProgramName(),
                            therapyProgramDTO.getDuration(),
                            therapyProgramDTO.getFee(),
                            therapyProgramDTO.getTherapist(),
                            therapyProgramDTO.getDescription()
                    );

                    therapyProgramTMS.add(therapyProgramTM);
                    tblPrograms.setItems(therapyProgramTMS);

                } else {
                    new Alert(Alert.AlertType.WARNING, "Therapy _ Program Not Found!").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "An error occurred while searching!").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please enter a Therapy_Programm ID to search!").show();
        }
    }

    @FXML
    void updateOnAction(ActionEvent event) {
        try{
            String programId = txtProgramId.getText();
            String programName = txtProgramName.getText();
            String duration = txtDuration.getText();
            BigDecimal fee = new BigDecimal(txtCost.getText());
            String description = txtDescription.getText();
            String therapistId = cmbTherapists.getValue();

            if (!programId.isEmpty() && !programName.isEmpty() && !duration.isEmpty() && fee != null  && !description.isEmpty() && !therapistId.isEmpty()) {
                TherapyProgramDTO therapyProgramDTO = new TherapyProgramDTO(
                        programId,
                        programName,
                        duration,
                        fee,
                        therapistId,
                        description
                );

                boolean isUpdated = THERAPYPROGRAMBO.updateTherapyProgram(therapyProgramDTO);

                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Therapy_Program Update Successfully!").show();
                    loadTableData();
                    refrashPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Update Therapy_Program!").show();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Please fill all the fields with valid data!").show();
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Please enter valid numeric values for Age and Contact Number!").show();
        }
    }

    public void tblTherapyProgramClickOnAction(MouseEvent mouseEvent) {
        TherapyProgramTM selectedTherapyProgram = tblPrograms.getSelectionModel().getSelectedItem();

        if (selectedTherapyProgram != null) {
            txtProgramId.setText(selectedTherapyProgram.getTherapyId());
            txtProgramName.setText(selectedTherapyProgram.getProgramName());
            txtDescription.setText(selectedTherapyProgram.getDescription());
            txtDuration.setText(selectedTherapyProgram.getDuration());
            cmbTherapists.setValue(selectedTherapyProgram.getTherapist());
            txtCost.setText(String.valueOf(selectedTherapyProgram.getFee()));
        }
    }
}
