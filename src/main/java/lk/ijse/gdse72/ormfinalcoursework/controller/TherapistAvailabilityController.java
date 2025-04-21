package lk.ijse.gdse72.ormfinalcoursework.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse72.ormfinalcoursework.bo.BOFactory;
import lk.ijse.gdse72.ormfinalcoursework.bo.custom.TherapistAvailabilityBO;
import lk.ijse.gdse72.ormfinalcoursework.dao.custom.TherapistDAO;
import lk.ijse.gdse72.ormfinalcoursework.dao.custom.impl.TherapistDAOImpl;
import lk.ijse.gdse72.ormfinalcoursework.dto.TherapistAvailabilityDTO;
import lk.ijse.gdse72.ormfinalcoursework.dto.TherapistDTO;
import lk.ijse.gdse72.ormfinalcoursework.dto.tm.TherapistAvailabilityTM;
import lk.ijse.gdse72.ormfinalcoursework.dto.tm.TherapistTM;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

public class TherapistAvailabilityController {

    @FXML
    private ComboBox<String> cmbTherapistId;

    @FXML
    private JFXComboBox<LocalTime> cmbEndTime;

    @FXML
    private JFXComboBox<LocalTime> cmbStartTime;

    @FXML
    private TableView<TherapistAvailabilityTM> tblAvailability;

    @FXML
    private TableColumn<TherapistAvailabilityTM , LocalDate> colDate;

    @FXML
    private TableColumn<TherapistAvailabilityTM , LocalTime> colEndTime;

    @FXML
    private TableColumn<TherapistAvailabilityTM , LocalTime> colStartTime;

    @FXML
    private TableColumn<TherapistAvailabilityTM , String> colTherapistId;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button btnSave;

    private final TherapistAvailabilityBO availabilityBO = (TherapistAvailabilityBO) BOFactory.getInstance().getBO(BOFactory.BOType.AVAILABILITY);

    @FXML
    public void initialize() {

        try {
            TherapistDAO therapistDAO = new TherapistDAOImpl();

            ArrayList<String> therapistIds = therapistDAO.getTherapist();
            ObservableList<String> observableList = FXCollections.observableArrayList();
            observableList.addAll(therapistIds);
            cmbTherapistId.setItems(observableList);

            loadTableData();
            visibleData();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        for (int h = 8; h <= 18; h++) {
            cmbStartTime.getItems().add(LocalTime.of(h, 0));
            cmbStartTime.getItems().add(LocalTime.of(h, 30));
            cmbEndTime.getItems().add(LocalTime.of(h, 0));
            cmbEndTime.getItems().add(LocalTime.of(h, 30));
        }
    }

    @FXML
    public void onSaveAvailability() throws SQLException {
        String therapistName = cmbTherapistId.getValue();
        LocalDate date = datePicker.getValue();
        LocalTime start = cmbStartTime.getValue();
        LocalTime end = cmbEndTime.getValue();

        if (therapistName == null || date == null || start == null || end == null) {
            new Alert(Alert.AlertType.ERROR, "Please fill all fields").show();
            return;
        }

        if (!start.isBefore(end)) {
            new Alert(Alert.AlertType.WARNING, "Start time must be before end time").show();
            return;
        }

        boolean isSaved = false;
        try {
            isSaved = availabilityBO.saveAvailability(new TherapistAvailabilityDTO(
                    null,
                    therapistName,
                    date,
                    start,
                    end
            ));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Saved successfully!").show();
            try {
                loadTableData();
                refrashPage();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Save failed!").show();
        }
    }

    public void canselOnAction(ActionEvent actionEvent) {
        TherapistAvailabilityTM selectedAvailability = tblAvailability.getSelectionModel().getSelectedItem();

        if (selectedAvailability != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Deletion");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Are you sure you want to delete Availability Date And Time : " + selectedAvailability.getId() + "?");

            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    boolean isDeleted = availabilityBO.deleteAvailability(String.valueOf(selectedAvailability.getId()));

                    if (isDeleted) {
                        new Alert(Alert.AlertType.INFORMATION, "Availability deleted successfully!").show();
                        loadTableData();
                        refrashPage();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Failed to delete the Availability!").show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
                }
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a Therapist to delete.").show();
        }
    }

    public void refrashPage() {
        cmbTherapistId.setValue(null);
        cmbStartTime.setValue(null);
        cmbEndTime.setValue(null);
        datePicker.setValue(null);
    }

    public void loadTableData() throws Exception {
        ArrayList<TherapistAvailabilityDTO> availabilityDTOS = availabilityBO.getAllavailabilities();
        ObservableList<TherapistAvailabilityTM> availabilityTMS = FXCollections.observableArrayList();

        for (TherapistAvailabilityDTO availabilityDTO : availabilityDTOS) {
            TherapistAvailabilityTM availabilityTM = new TherapistAvailabilityTM(
                    availabilityDTO.getId(),
                    availabilityDTO.getTherapistName(),
                    availabilityDTO.getDate(),
                    availabilityDTO.getStartTime(),
                    availabilityDTO.getEndTime()
            );
            availabilityTMS.add(availabilityTM);
        }
        tblAvailability.setItems(availabilityTMS);
    }

    public void visibleData() {
        colTherapistId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStartTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        colEndTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
    }

    @FXML
    void tblAvailabilityClickOnAction(MouseEvent event) {

        TherapistAvailabilityTM selectedAvailability = tblAvailability.getSelectionModel().getSelectedItem();

        if (selectedAvailability != null) {
            cmbTherapistId.setValue(selectedAvailability.getTherapistName());
            cmbStartTime.setValue(selectedAvailability.getStartTime());
            cmbEndTime.setValue(selectedAvailability.getEndTime());
            datePicker.setValue(selectedAvailability.getDate());
        }
    }
}
