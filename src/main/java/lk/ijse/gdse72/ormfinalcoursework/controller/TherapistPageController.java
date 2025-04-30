package lk.ijse.gdse72.ormfinalcoursework.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse72.ormfinalcoursework.bo.BOFactory;
import lk.ijse.gdse72.ormfinalcoursework.bo.custom.TherapistBO;
import lk.ijse.gdse72.ormfinalcoursework.dto.TherapistDTO;
import lk.ijse.gdse72.ormfinalcoursework.dto.tm.TherapistTM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Pattern;

public class TherapistPageController {

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
    private JFXComboBox<String> cmbAvailability;

    @FXML
    private JFXComboBox<String> cmbPrograms;

    @FXML
    private JFXComboBox<String> cmbSpecialization;

    @FXML
    private TableColumn<TherapistTM, String> colAvailability;

    @FXML
    private TableColumn<TherapistTM, Integer> colContact;

    @FXML
    private TableColumn<TherapistTM, String> colId;

    @FXML
    private TableColumn<TherapistTM, String> colName;

    @FXML
    private TableColumn<TherapistTM, String> colPrograms;

    @FXML
    private TableColumn<TherapistTM, String> colSpecialization;

    @FXML
    private AnchorPane subAnchor;

    @FXML
    private TableView<TherapistTM> tblTherapists;

    @FXML
    private JFXTextField txtContact;

    @FXML
    private JFXTextField txtTherapistId;

    @FXML
    private JFXTextField txtTherapistName;

    @FXML
    private JFXTextField txtEmail;

    private final TherapistBO therapistBO = (TherapistBO) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPIST);

    // Regex patterns for validation
    private final Pattern ID_PATTERN = Pattern.compile("^THER\\d{3}$");
    private final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z ]{3,}$");
    private final Pattern CONTACT_PATTERN = Pattern.compile("^0[1-9][0-9]{8}$");
    private final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    // CSS styles
    private final String ERROR_STYLE = "-fx-border-color: red; -fx-border-width: 2px;";
    private final String DEFAULT_STYLE = "";

    public void initialize() {
        try {
            populateComboBoxes();
            refrashPage();
            loadTableData();
            visibleData();
            changeFocus();

            String nextTherapistId = therapistBO.getNextTherapistId();
            txtTherapistId.setText(nextTherapistId);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Id Generation Failed: " + e.getMessage()).show();
        }
    }

    public void changeFocus() {
        txtTherapistId.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                txtTherapistName.requestFocus();
            }
        });

        txtTherapistName.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                cmbSpecialization.requestFocus();
            }
        });

        cmbSpecialization.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                cmbAvailability.requestFocus();
            }
        });

        cmbAvailability.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                txtContact.requestFocus();
            }
        });

        txtContact.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                cmbPrograms.requestFocus();
            }
        });

        cmbPrograms.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                txtEmail.requestFocus();
            }
        });

        txtEmail.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                btnSave.fire();
            }
        });
    }

    public void refrashPage() {
        try {
            String nextTherapistId = therapistBO.getNextTherapistId();
            txtTherapistId.setText(nextTherapistId);
            txtTherapistName.setText("");
            txtContact.setText("");
            txtEmail.setText("");
            cmbAvailability.setValue(null);
            cmbSpecialization.setValue(null);
            cmbPrograms.setValue(null);
            resetFieldStyles();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Id Generation Failed:" + e.getMessage()).show();
        }
    }

    public void loadTableData() throws Exception {
        ArrayList<TherapistDTO> therapistDTOS = therapistBO.getAllTherapist();
        ObservableList<TherapistTM> therapistTMS = FXCollections.observableArrayList();

        for (TherapistDTO therapistDTO : therapistDTOS) {
            TherapistTM therapistTM = new TherapistTM(
                    therapistDTO.getTherapistId(),
                    therapistDTO.getTherapistName(),
                    therapistDTO.getSpecialization(),
                    therapistDTO.getAvailability(),
                    therapistDTO.getContact(),
                    therapistDTO.getProgram(),
                    therapistDTO.getMail()
            );
            therapistTMS.add(therapistTM);
        }
        tblTherapists.setItems(therapistTMS);
    }

    public void visibleData() {
        colId.setCellValueFactory(new PropertyValueFactory<>("therapistId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("therapistName"));
        colSpecialization.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));
        colPrograms.setCellValueFactory(new PropertyValueFactory<>("program"));
    }

    public void populateComboBoxes() throws SQLException {
        ObservableList<String> availability = FXCollections.observableArrayList(
                "Available",
                "Unavailable",
                "On Leave",
                "Busy"
        );
        cmbAvailability.setItems(availability);

        ObservableList<String> programs = FXCollections.observableArrayList(
                "Cognitive Behavioral Therapy",
                "Mindfulness-Based Stress Reduction",
                "Dialectical Behavior Therapy",
                "Group Therapy Sessions",
                "Family Counseling"
        );
        cmbPrograms.setItems(programs);

        ObservableList<String> specialization = FXCollections.observableArrayList(
                "Child Psychology",
                "Depression & Anxiety",
                "Addiction Recovery",
                "Trauma Therapy",
                "Relationship Counseling",
                "PTSD Specialist"
        );
        cmbSpecialization.setItems(specialization);
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
        TherapistTM selectedTherapist = tblTherapists.getSelectionModel().getSelectedItem();

        if (selectedTherapist != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Deletion");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Are you sure you want to delete Therapist: " + selectedTherapist.getTherapistId() + "?");

            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    boolean isDeleted = therapistBO.deleteTherapist(selectedTherapist.getTherapistId());
                    if (isDeleted) {
                        loadTableData();
                        refrashPage();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    void saveOnAction(ActionEvent event) {
        try {
            if (!validateFields()) {
                return;
            }

            String therapistId = txtTherapistId.getText();
            String therapistName = txtTherapistName.getText();
            int contact = Integer.parseInt(txtContact.getText());
            String email = txtEmail.getText();
            String specialization = cmbSpecialization.getValue();
            String program = cmbPrograms.getValue();
            String availability = cmbAvailability.getValue();

            TherapistDTO therapistDTO = new TherapistDTO(
                    therapistId, therapistName, specialization,
                    availability, contact, program, email
            );

            boolean isSaved = therapistBO.saveTherapist(therapistDTO);
            if (isSaved) {
                loadTableData();
                refrashPage();
            }
        } catch (NumberFormatException e) {
            txtContact.setStyle(ERROR_STYLE);
            txtContact.requestFocus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateFields() {
        boolean isValid = true;
        resetFieldStyles();

        // Validate Therapist ID
        if (!ID_PATTERN.matcher(txtTherapistId.getText()).matches()) {
            txtTherapistId.setStyle(ERROR_STYLE);
            if (isValid) txtTherapistId.requestFocus();
            isValid = false;
        }

        // Validate Therapist Name
        if (!NAME_PATTERN.matcher(txtTherapistName.getText()).matches()) {
            txtTherapistName.setStyle(ERROR_STYLE);
            if (isValid) txtTherapistName.requestFocus();
            isValid = false;
        }

        // Validate Specialization
        if (cmbSpecialization.getValue() == null) {
            cmbSpecialization.setStyle(ERROR_STYLE);
            if (isValid) cmbSpecialization.requestFocus();
            isValid = false;
        }

        // Validate Availability
        if (cmbAvailability.getValue() == null) {
            cmbAvailability.setStyle(ERROR_STYLE);
            if (isValid) cmbAvailability.requestFocus();
            isValid = false;
        }

        // Validate Contact
        if (!CONTACT_PATTERN.matcher(txtContact.getText()).matches()) {
            txtContact.setStyle(ERROR_STYLE);
            if (isValid) txtContact.requestFocus();
            isValid = false;
        }

        // Validate Program
        if (cmbPrograms.getValue() == null) {
            cmbPrograms.setStyle(ERROR_STYLE);
            if (isValid) cmbPrograms.requestFocus();
            isValid = false;
        }

        // Validate Email
        if (!EMAIL_PATTERN.matcher(txtEmail.getText()).matches()) {
            txtEmail.setStyle(ERROR_STYLE);
            if (isValid) txtEmail.requestFocus();
            isValid = false;
        }

        return isValid;
    }

    private void resetFieldStyles() {
        txtTherapistId.setStyle(DEFAULT_STYLE);
        txtTherapistName.setStyle(DEFAULT_STYLE);
        txtContact.setStyle(DEFAULT_STYLE);
        txtEmail.setStyle(DEFAULT_STYLE);
        cmbSpecialization.setStyle(DEFAULT_STYLE);
        cmbAvailability.setStyle(DEFAULT_STYLE);
        cmbPrograms.setStyle(DEFAULT_STYLE);
    }

    @FXML
    void searchOnAction(ActionEvent event) {
        String therapistId = txtTherapistId.getText();

        if (!therapistId.isEmpty()) {
            try {
                TherapistDTO therapistDTO = therapistBO.searchTherapist(therapistId);

                if (therapistDTO != null) {
                    txtTherapistName.setText(therapistDTO.getTherapistName());
                    txtContact.setText(String.valueOf(therapistDTO.getContact()));
                    txtEmail.setText(therapistDTO.getMail());
                    cmbSpecialization.setValue(therapistDTO.getSpecialization());
                    cmbPrograms.setValue(therapistDTO.getProgram());
                    cmbAvailability.setValue(therapistDTO.getAvailability());
                    resetFieldStyles();

                    ObservableList<TherapistTM> therapistTMS = FXCollections.observableArrayList();
                    therapistTMS.add(new TherapistTM(
                            therapistDTO.getTherapistId(),
                            therapistDTO.getTherapistName(),
                            therapistDTO.getSpecialization(),
                            therapistDTO.getAvailability(),
                            therapistDTO.getContact(),
                            therapistDTO.getProgram(),
                            therapistDTO.getMail()
                    ));
                    tblTherapists.setItems(therapistTMS);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void updateOnAction(ActionEvent event) {
        try {
            if (!validateFields()) {
                return;
            }

            String therapistId = txtTherapistId.getText();
            String therapistName = txtTherapistName.getText();
            int contact = Integer.parseInt(txtContact.getText());
            String email = txtEmail.getText();
            String specialization = cmbSpecialization.getValue();
            String program = cmbPrograms.getValue();
            String availability = cmbAvailability.getValue();

            TherapistDTO therapistDTO = new TherapistDTO(
                    therapistId, therapistName, specialization,
                    availability, contact, program, email
            );

            boolean isUpdated = therapistBO.updateTherapist(therapistDTO);
            if (isUpdated) {
                loadTableData();
                refrashPage();
            }
        } catch (NumberFormatException e) {
            txtContact.setStyle(ERROR_STYLE);
            txtContact.requestFocus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void tblTherapistClickOnAction(MouseEvent mouseEvent) {
        TherapistTM selectedTherapist = tblTherapists.getSelectionModel().getSelectedItem();

        if (selectedTherapist != null) {
            txtTherapistId.setText(selectedTherapist.getTherapistId());
            txtTherapistName.setText(selectedTherapist.getTherapistName());
            cmbSpecialization.setValue(selectedTherapist.getSpecialization());
            cmbPrograms.setValue(selectedTherapist.getProgram());
            cmbAvailability.setValue(selectedTherapist.getAvailability());
            txtContact.setText(String.valueOf(selectedTherapist.getContact()));
            txtEmail.setText(selectedTherapist.getMail());
            resetFieldStyles();
        }
    }

    @FXML
    public void enterOnAction(ActionEvent actionEvent) {
        searchOnAction(actionEvent);
    }
}