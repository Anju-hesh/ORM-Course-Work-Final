package lk.ijse.gdse72.ormfinalcoursework.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse72.ormfinalcoursework.bo.BOFactory;
import lk.ijse.gdse72.ormfinalcoursework.bo.custom.PatientBO;
import lk.ijse.gdse72.ormfinalcoursework.dto.PatientDTO;
import lk.ijse.gdse72.ormfinalcoursework.dto.tm.PatientTM;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class PatientPageController {

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnClearFilter;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnFilter;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnViewTherapies;

    @FXML
    private JFXComboBox<String> cmbBloodGroup;

    @FXML
    private JFXComboBox<String> cmbFilterTherapy;

    @FXML
    private JFXComboBox<String> cmbGender;

    @FXML
    private TableColumn<PatientTM, Integer> colAge;

    @FXML
    private TableColumn<PatientTM, String> colBloodGroup;

    @FXML
    private TableColumn<PatientTM, Integer> colContact;

    @FXML
    private TableColumn<PatientTM, String> colFullName;

    @FXML
    private TableColumn<PatientTM, String> colGender;

    @FXML
    private TableColumn<PatientTM, String> colMedicalHistory;

    @FXML
    private TableColumn<PatientTM, String> colPatientId;

    @FXML
    private AnchorPane subAnchor;

    @FXML
    private TableView<PatientTM> tblPatients;

    @FXML
    private JFXTextArea txtAddress;

    @FXML
    private JFXTextField txtAge;

    @FXML
    private JFXTextField txtAllergies;

    @FXML
    private JFXTextField txtContact;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtFirstName;

    @FXML
    private JFXTextField txtLastName;

    @FXML
    private JFXTextArea txtMedicalHistory;

    @FXML
    private JFXTextField txtPatientId;

    private final PatientBO PATIENTBO = (PatientBO) BOFactory.getInstance().getBO(BOFactory.BOType.PATIENT);

    public void initialize() {
        try {
            populateComboBoxes();
            refrashPage();
            loadTableData();
            visibleData();

            String nextPatientId = PATIENTBO.getNextPatientId();
            txtPatientId.setText(nextPatientId);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load Page: " + e.getMessage()).show();
        }
    }

    private void populateComboBoxes() {
        ObservableList<String> bloodGroups = FXCollections.observableArrayList(
                "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"
        );
        cmbBloodGroup.setItems(bloodGroups);

        ObservableList<String> genders = FXCollections.observableArrayList(
                "Male", "Female", "Other"
        );
        cmbGender.setItems(genders);

        ObservableList<String> therapyTypes = FXCollections.observableArrayList(
                "All", "Physiotherapy", "Psychotherapy", "Occupational Therapy", "Speech Therapy"
        );
        cmbFilterTherapy.setItems(therapyTypes);
        cmbFilterTherapy.setValue("All");
    }

    @FXML
    void clearFilterOnAction(ActionEvent event) {
        try {
            cmbFilterTherapy.setValue(null);
            refrashPage();
            loadTableData();
            visibleData();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
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

    public void refrashPage() throws Exception {

        String nextPatientId = PATIENTBO.getNextPatientId();
        txtPatientId.setText(nextPatientId);
        txtFirstName.setText("");
        txtLastName.setText("");
        txtAge.setText("");
        cmbGender.setValue(null);
        txtMedicalHistory.setText("");
        txtContact.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
        cmbBloodGroup.setValue(null);
        txtAllergies.setText("");
    }

    public void loadTableData() throws Exception {
        ArrayList<PatientDTO> patientDTOS = PATIENTBO.getAllPatients();
        ObservableList<PatientTM> patientTMS = FXCollections.observableArrayList();

        for (PatientDTO patientDTO : patientDTOS) {
            PatientTM patientTM = new PatientTM(
                    patientDTO.getPatientId(),
                    patientDTO.getFirstName(),
                    patientDTO.getLastName(),
                    patientDTO.getAge(),
                    patientDTO.getGender(),
                    patientDTO.getMedicalHistory(),
                    patientDTO.getContact(),
                    patientDTO.getEMail(),
                    patientDTO.getAddress(),
                    patientDTO.getBloodGroup(),
                    patientDTO.getAllergies()
            );
            patientTMS.add(patientTM);
        }
        tblPatients.setItems(patientTMS);
    }

    public void visibleData() throws Exception {
        colPatientId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colBloodGroup.setCellValueFactory(new PropertyValueFactory<>("BloodGroup"));
        colMedicalHistory.setCellValueFactory(new PropertyValueFactory<>("medicalHistory"));
    }

    @FXML
    void deleteOnAction(ActionEvent event) {
        PatientTM selectedPatient = tblPatients.getSelectionModel().getSelectedItem();

        if (selectedPatient != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Deletion");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Are you sure you want to delete patient ID: " + selectedPatient.getPatientId() + "?");

            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    boolean isDeleted = PATIENTBO.deletePatient(selectedPatient.getPatientId());

                    if (isDeleted) {
                        new Alert(Alert.AlertType.INFORMATION, "Patient deleted successfully!").show();
                        loadTableData();
                        refrashPage();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Failed to delete the patient!").show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
                }
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a patient to delete.").show();
        }
    }

    @FXML
    void filterOnAction(ActionEvent event) {
        String therapyType = cmbFilterTherapy.getValue();

        if (therapyType == null || therapyType.equals("All")) {
            try {
                loadTableData();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Error loading data: " + e.getMessage()).show();
            }
            return;
        }

        try {
            // This is a placeholder - you would need to implement a method in your BO layer
            // to filter patients by therapy type if that's what you need
            // ArrayList<PatientDTO> filteredPatients = PATIENTBO.getPatientsByTherapyType(therapyType);

            // For now, just reload all data and display a message
            loadTableData();
            new Alert(Alert.AlertType.INFORMATION,
                    "Filter functionality needs to be implemented in PatientBO to filter by therapy type: " + therapyType).show();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error filtering data: " + e.getMessage()).show();
        }
    }

    @FXML
    void saveOnAction(ActionEvent event) {
        try {
            String patientId = txtPatientId.getText();
            String firstName = txtFirstName.getText();
            String lastName = txtLastName.getText();
            int age = Integer.parseInt(txtAge.getText());
            String gender = cmbGender.getValue();
            String medicalHistory = txtMedicalHistory.getText();
            int contact = Integer.parseInt(txtContact.getText());
            String eMail = txtEmail.getText();
            String address = txtAddress.getText();
            String BloodGroup = cmbBloodGroup.getValue();
            String allergies = txtAllergies.getText();

            if (!patientId.isEmpty() && !firstName.isEmpty() && !lastName.isEmpty() && gender != null
                    && !medicalHistory.isEmpty() && !eMail.isEmpty() && !address.isEmpty()
                    && BloodGroup != null && !allergies.isEmpty() && contact > 0 && age > 0) {

                PatientDTO patientDTO = new PatientDTO(patientId, firstName, lastName, age, gender, medicalHistory,
                        contact, eMail, address, BloodGroup, allergies);

                boolean isSaved = PATIENTBO.savePatient(patientDTO);
                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Patient Saved Successfully!").show();
                    loadTableData();
                    refrashPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Save Patient!").show();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Please fill all the fields with valid data!").show();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Please enter valid numeric values for Age and Contact Number!").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void tblPatientClickOnAction(MouseEvent event) {
        PatientTM selectedPatient = tblPatients.getSelectionModel().getSelectedItem();

        if (selectedPatient != null) {
            txtPatientId.setText(selectedPatient.getPatientId());
            txtFirstName.setText(selectedPatient.getFirstName());
            txtLastName.setText(selectedPatient.getLastName());
            txtAge.setText(String.valueOf(selectedPatient.getAge()));
            cmbGender.setValue(selectedPatient.getGender());
            txtMedicalHistory.setText(selectedPatient.getMedicalHistory());
            txtContact.setText(String.valueOf(selectedPatient.getContact()));
            txtEmail.setText(selectedPatient.getEMail());
            txtAddress.setText(selectedPatient.getAddress());
            cmbBloodGroup.setValue(selectedPatient.getBloodGroup());
            txtAllergies.setText(selectedPatient.getAllergies());
        }
    }

    @FXML
    void updateOnAction(ActionEvent event) {
        try {
            String patientId = txtPatientId.getText();

            if (patientId.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please select a patient to update!").show();
                return;
            }

            String firstName = txtFirstName.getText();
            String lastName = txtLastName.getText();
            int age = Integer.parseInt(txtAge.getText());
            String gender = cmbGender.getValue();
            String medicalHistory = txtMedicalHistory.getText();
            int contact = Integer.parseInt(txtContact.getText());
            String eMail = txtEmail.getText();
            String address = txtAddress.getText();
            String BloodGroup = cmbBloodGroup.getValue();
            String allergies = txtAllergies.getText();

            if (!firstName.isEmpty() && !lastName.isEmpty() && gender != null
                    && !medicalHistory.isEmpty() && !eMail.isEmpty() && !address.isEmpty()
                    && BloodGroup != null && !allergies.isEmpty() && contact > 0 && age > 0) {

                PatientDTO patientDTO = new PatientDTO(patientId, firstName, lastName, age, gender, medicalHistory,
                        contact, eMail, address, BloodGroup, allergies);

                boolean isUpdated = PATIENTBO.updatePatient(patientDTO);
                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Patient Updated Successfully!").show();
                    loadTableData();
                    refrashPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Update Patient!").show();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Please fill all the fields with valid data!").show();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Please enter valid numeric values for Age and Contact Number!").show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void viewTherapiesOnAction(ActionEvent event) {
        try {
            subAnchor.getChildren().clear();
            subAnchor.getChildren().add(FXMLLoader.load(getClass().getResource("/view/TherapistPage.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void searchOnAction(ActionEvent actionEvent) {
        String patientId = txtPatientId.getText();

        if (!patientId.isEmpty()) {
            try {
                PatientDTO patientDTO = PATIENTBO.searchPatient(patientId);

                if (patientDTO != null) {
                    txtFirstName.setText(patientDTO.getFirstName());
                    txtLastName.setText(patientDTO.getLastName());
                    txtAge.setText(String.valueOf(patientDTO.getAge()));
                    cmbGender.setValue(patientDTO.getGender());
                    txtMedicalHistory.setText(patientDTO.getMedicalHistory());
                    txtContact.setText(String.valueOf(patientDTO.getContact()));
                    txtEmail.setText(patientDTO.getEMail());
                    txtAddress.setText(patientDTO.getAddress());
                    cmbBloodGroup.setValue(patientDTO.getBloodGroup());
                    txtAllergies.setText(patientDTO.getAllergies());

                    ObservableList<PatientTM> patientTMS = FXCollections.observableArrayList();

                    PatientTM patientTM = new PatientTM(
                            patientDTO.getPatientId(),
                            patientDTO.getFirstName(),
                            patientDTO.getLastName(),
                            patientDTO.getAge(),
                            patientDTO.getGender(),
                            patientDTO.getMedicalHistory(),
                            patientDTO.getContact(),
                            patientDTO.getEMail(),
                            patientDTO.getAddress(),
                            patientDTO.getBloodGroup(),
                            patientDTO.getAllergies()
                    );
                    patientTMS.add(patientTM);
                    tblPatients.setItems(patientTMS);
                } else {
                    new Alert(Alert.AlertType.WARNING, "Patient Not Found!").show();
                }
            } catch (Exception e) {
//                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "An error occurred while searching!").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please enter a Patient ID to search!").show();
        }
    }
}