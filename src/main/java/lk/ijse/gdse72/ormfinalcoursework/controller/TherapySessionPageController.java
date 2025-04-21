package lk.ijse.gdse72.ormfinalcoursework.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse72.ormfinalcoursework.bo.BOFactory;
import lk.ijse.gdse72.ormfinalcoursework.bo.custom.PatientBO;
import lk.ijse.gdse72.ormfinalcoursework.bo.custom.TherapySessionBO;
import lk.ijse.gdse72.ormfinalcoursework.dao.custom.PatientDAO;
import lk.ijse.gdse72.ormfinalcoursework.dao.custom.TherapistDAO;
import lk.ijse.gdse72.ormfinalcoursework.dao.custom.TherapyProgramDAO;
import lk.ijse.gdse72.ormfinalcoursework.dao.custom.impl.PatientDAOImpl;
import lk.ijse.gdse72.ormfinalcoursework.dao.custom.impl.TherapistDAOImpl;
import lk.ijse.gdse72.ormfinalcoursework.dao.custom.impl.TherapyProgramDAOImpl;
import lk.ijse.gdse72.ormfinalcoursework.dto.PatientDTO;
import lk.ijse.gdse72.ormfinalcoursework.dto.TherapySessionDTO;
import lk.ijse.gdse72.ormfinalcoursework.dto.tm.TherapyProgramTM;
import lk.ijse.gdse72.ormfinalcoursework.dto.tm.TherapySessionTM;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TherapySessionPageController {

    @FXML private JFXButton btnBook, btnCancel, btnCheckAvailable, btnClear, btnLoadPatient, btnReschedule, btnSearch;
    @FXML private JFXComboBox<String> cmbDuration, cmbProgram, cmbStatus, cmbTherapist;
    @FXML private TableColumn<TherapySessionTM, Date> colDate;
    @FXML private TableColumn<TherapySessionTM, String> colDuration, colPatientName, colProgram, colSessionId, colStatus, colTherapist;
    @FXML private TableColumn<TherapySessionTM, Time> colTime;
    @FXML private DatePicker datePicker;
    @FXML private AnchorPane subAnchor;
    @FXML private TableView<TherapySessionTM> tblSessions;
    @FXML private ComboBox<String> timeComboBox;
    @FXML private JFXTextField txtNotes, txtPatientId, txtPatientName, txtSessionId;

    private final TherapySessionBO THERAPYSESSIONBO = (TherapySessionBO) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_SESSION);

    public void initialize() {
        try {
            populateComboBoxes();
            refreshPage();
            loadTableData();
            visibleData();
            txtSessionId.setText(THERAPYSESSIONBO.getNextTherapySessionId());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load page: " + e.getMessage()).show();
        }
    }

    private void populateComboBoxes() throws Exception {
        // Therapist
        TherapistDAO therapistDAO = new TherapistDAOImpl();
        cmbTherapist.setItems(FXCollections.observableArrayList(therapistDAO.getTherapist()));

        // Program
        TherapyProgramDAO therapyProgramDAO = new TherapyProgramDAOImpl();
        cmbProgram.setItems(FXCollections.observableArrayList(therapyProgramDAO.getPrograms()));

        // Time slots
        List<String> timeSlots = new ArrayList<>();
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(17, 0);
        while (!start.isAfter(end)) {
            timeSlots.add(start.format(DateTimeFormatter.ofPattern("hh:mm a")));
            start = start.plusMinutes(30);
        }
        timeComboBox.setItems(FXCollections.observableArrayList(timeSlots));

        // Duration
        cmbDuration.setItems(FXCollections.observableArrayList(
                "15 mins", "30 mins", "45 mins", "1 hour", "1 hour 30 mins", "2 hours"
        ));

        // Status
        cmbStatus.setItems(FXCollections.observableArrayList(
                "Available", "Unavailable"
        ));
    }

    private void refreshPage() {
        try {
            txtSessionId.setText(THERAPYSESSIONBO.getNextTherapySessionId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        txtPatientId.clear();
        txtPatientName.clear();
        cmbTherapist.setValue(null);
        cmbStatus.setValue(null);
        cmbDuration.setValue(null);
        cmbProgram.setValue(null);
        datePicker.setValue(null);
        timeComboBox.setValue(null);
    }

    private void loadTableData() throws Exception {
        ObservableList<TherapySessionTM> sessionList = FXCollections.observableArrayList();
        for (TherapySessionDTO dto : THERAPYSESSIONBO.getAllTherapySession()) {
            sessionList.add(new TherapySessionTM(
                    dto.getSessionId(),
                    dto.getPatientId(),
                    dto.getPatientName(),
                    dto.getTherapistId(),
                    dto.getProgram(),
                    dto.getSessionDate(),
                    dto.getTime(),
                    dto.getDuration(),
                    dto.getStatus()
            ));
        }
        tblSessions.setItems(sessionList);
    }

    private void visibleData() {
        colSessionId.setCellValueFactory(new PropertyValueFactory<>("sessionId"));
        colPatientName.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        colTherapist.setCellValueFactory(new PropertyValueFactory<>("therapistId"));
        colProgram.setCellValueFactory(new PropertyValueFactory<>("program"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("sessionDate"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    @FXML
    void bookOnAction(ActionEvent event) {
        try {
            String sessionId = txtSessionId.getText();
            String patientId = txtPatientId.getText();
            String patientName = txtPatientName.getText();
            String therapistId = cmbTherapist.getValue();
            String program = cmbProgram.getValue();
            LocalDate date = datePicker.getValue();
            String selectedTime = timeComboBox.getValue();
            String duration = cmbDuration.getValue();
            String status = cmbStatus.getValue();

            if (sessionId.isEmpty() || patientId.isEmpty() || therapistId == null || program == null ||
                    patientName.isEmpty() || date == null || selectedTime == null || duration == null || status == null) {
                new Alert(Alert.AlertType.WARNING, "Please fill all the fields correctly!").show();
                return;
            }

            LocalTime time = LocalTime.parse(selectedTime, DateTimeFormatter.ofPattern("hh:mm a"));

            TherapySessionDTO dto = new TherapySessionDTO(
                    sessionId, patientId, patientName, therapistId, program, date, time, duration, status
            );

            if (THERAPYSESSIONBO.saveTherapySession(dto)) {
                new Alert(Alert.AlertType.INFORMATION, "Session booked successfully!").show();
                loadTableData();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to book session!").show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }
    }

    @FXML
    void checkAvailableOnAction(ActionEvent event) {
        try {
            String therapistId = cmbTherapist.getValue();
            LocalDate date = datePicker.getValue();
            String timeStr = timeComboBox.getValue();

            if (therapistId == null || date == null || timeStr == null) {
                cmbStatus.setValue("Fill all fields");
                cmbStatus.setStyle("-fx-text-fill: orange;");
                return;
            }

            LocalTime time = LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("hh:mm a"));
            boolean available = THERAPYSESSIONBO.isTherapistAvailable(therapistId, date, time);

            if (available) {
                cmbStatus.setValue("Available");
                cmbStatus.setStyle("-fx-text-fill: green;");
            } else {
                cmbStatus.setValue("Unavailable");
                cmbStatus.setStyle("-fx-text-fill: red;");
            }
        } catch (Exception e) {
            e.printStackTrace();
            cmbStatus.setValue("Error");
            cmbStatus.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    void clearOnAction(ActionEvent event) {
        refreshPage();
        try {
            loadTableData();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void loadPatientOnAction(ActionEvent event) {
        try {
            PatientDAO patientDAO = new PatientDAOImpl();
            PatientDTO patient = patientDAO.getPatient();
            if (patient != null) {
                txtPatientId.setText(patient.getPatientId());
                txtPatientName.setText(patient.getFirstName() + " " + patient.getLastName());
            } else {
                new Alert(Alert.AlertType.WARNING, "No patient found!").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load patient: " + e.getMessage()).show();
        }
    }

    @FXML
    void tblTherapySessionClickOnAction(MouseEvent event) {
        TherapySessionTM selected = tblSessions.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtSessionId.setText(selected.getSessionId());
            txtPatientId.setText(selected.getPatientId());
            txtPatientName.setText(selected.getPatientName());
            cmbTherapist.setValue(selected.getTherapistId());
            cmbProgram.setValue(selected.getProgram());
            cmbStatus.setValue(selected.getStatus());
            cmbDuration.setValue(selected.getDuration());
            datePicker.setValue(selected.getSessionDate());
            timeComboBox.setValue(selected.getTime().format(DateTimeFormatter.ofPattern("hh:mm a")));
        }
    }

    @FXML
    void cancelOnAction(ActionEvent event) {
        TherapySessionTM selectedTherapySession = tblSessions.getSelectionModel().getSelectedItem();

        if (selectedTherapySession != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Deletion");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Are you sure you want to delete Session ID: " + selectedTherapySession.getSessionId() + "?");

            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    boolean isDeleted = THERAPYSESSIONBO.deleteTherapySession(selectedTherapySession.getSessionId());

                    if (isDeleted) {
                        new Alert(Alert.AlertType.INFORMATION, "Therapy Program deleted successfully!").show();
                        loadTableData();
                        refreshPage();
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
        }    }

    @FXML
    void rescheduleOnAction(ActionEvent event) {
        try {
            String sessionId = txtSessionId.getText();
            String patientId = txtPatientId.getText();
            String patientName = txtPatientName.getText();
            String therapistId = cmbTherapist.getValue();
            String program = cmbProgram.getValue();
            LocalDate date = datePicker.getValue();
            String selectedTime = timeComboBox.getValue();
            String duration = cmbDuration.getValue();
            String status = cmbStatus.getValue();

            if (sessionId.isEmpty() || patientId.isEmpty() || therapistId == null || program == null ||
                    patientName.isEmpty() || date == null || selectedTime == null || duration == null || status == null) {
                new Alert(Alert.AlertType.WARNING, "Please fill all the fields correctly!").show();
                return;
            }

            LocalTime time = LocalTime.parse(selectedTime, DateTimeFormatter.ofPattern("hh:mm a"));

            TherapySessionDTO dto = new TherapySessionDTO(
                    sessionId, patientId, patientName, therapistId, program, date, time, duration, status
            );

            if (THERAPYSESSIONBO.updateTherapySession(dto)) {
                new Alert(Alert.AlertType.INFORMATION, "Session ReScheduled successfully!").show();
                loadTableData();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to ReScheduled session!").show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error: " + e.getMessage()).show();
        }    }

    @FXML
    void searchOnAction(ActionEvent event) {
        String sessionId = txtSessionId.getText();

        if (!sessionId.isEmpty()) {
            try{
                TherapySessionDTO therapySessionDTO = THERAPYSESSIONBO.searchTherapySession(sessionId);

                if (therapySessionDTO != null) {
                    txtSessionId.setText(therapySessionDTO.getSessionId());
                    txtPatientId.setText(therapySessionDTO.getPatientId());
                    txtPatientName.setText(therapySessionDTO.getPatientName());
                    cmbTherapist.setValue(therapySessionDTO.getTherapistId());
                    cmbProgram.setValue(therapySessionDTO.getProgram());
                    cmbStatus.setValue(therapySessionDTO.getStatus());
                    cmbDuration.setValue(therapySessionDTO.getDuration());
                    datePicker.setValue(therapySessionDTO.getSessionDate());
                    timeComboBox.setValue(therapySessionDTO.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));


                    ObservableList<TherapySessionTM> therapySessionTMS = FXCollections.observableArrayList();

                    TherapySessionTM therapySessionTM = new TherapySessionTM(
                            therapySessionDTO.getSessionId(),
                            therapySessionDTO.getPatientId(),
                            therapySessionDTO.getPatientName(),
                            therapySessionDTO.getTherapistId(),
                            therapySessionDTO.getProgram(),
                            therapySessionDTO.getSessionDate(),
                            therapySessionDTO.getTime(),
                            therapySessionDTO.getDuration(),
                            therapySessionDTO.getStatus()
                    );
                    therapySessionTMS.add(therapySessionTM);
                    tblSessions.setItems(therapySessionTMS);

                }else {
                    new Alert(Alert.AlertType.WARNING, "Therapy _ Session Not Found!").show();
                }
            }catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "An error occurred while searching!").show();
            }
        }else {
            new Alert(Alert.AlertType.WARNING, "Please enter a Therapy_Session ID to search!").show();
        }
    }
}
