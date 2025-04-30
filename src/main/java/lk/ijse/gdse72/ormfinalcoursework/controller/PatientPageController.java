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
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse72.ormfinalcoursework.bo.BOFactory;
import lk.ijse.gdse72.ormfinalcoursework.bo.custom.PatientBO;
import lk.ijse.gdse72.ormfinalcoursework.dto.PatientDTO;
import lk.ijse.gdse72.ormfinalcoursework.dto.tm.PatientTM;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Pattern;

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

    // Regex patterns for validation
    private final Pattern ID_PATTERN = Pattern.compile("^PAT\\d{3}$");
    private final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z]{3,}$");
    private final Pattern AGE_PATTERN = Pattern.compile("^(?:1[0-9]|[1-9]|[1-9][0-9]|100)$");
    private final Pattern CONTACT_PATTERN = Pattern.compile("^0[1-9][0-9]{8}$");
    private final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private final Pattern ADDRESS_PATTERN = Pattern.compile("^[A-Za-z0-9/,\\s]{5,}$");
    private final Pattern ALLERGIES_PATTERN = Pattern.compile("^[A-Za-z,\\s]{3,}$");

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

            String nextPatientId = PATIENTBO.getNextPatientId();
            txtPatientId.setText(nextPatientId);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load Page: " + e.getMessage()).show();
        }
    }

    public void changeFocus() {
        txtPatientId.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                txtFirstName.requestFocus();
            }
        });

        txtFirstName.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                txtLastName.requestFocus();
            }
        });

        txtLastName.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                txtAge.requestFocus();
            }
        });

        txtAge.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                cmbGender.requestFocus();
            }
        });

        cmbGender.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                txtMedicalHistory.requestFocus();
            }
        });

        txtMedicalHistory.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                txtContact.requestFocus();
            }
        });

        txtContact.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                txtEmail.requestFocus();
            }
        });

        txtEmail.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                txtAddress.requestFocus();
            }
        });

        txtAddress.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                cmbBloodGroup.requestFocus();
            }
        });

        cmbBloodGroup.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                txtAllergies.requestFocus();
            }
        });

        txtAllergies.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                btnSave.fire();
            }
        });
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
                "All", "Cognitive Behavioral Therapy", "Mindfulness-Based Stress Reduction",
                "Dialectical Behavior Therapy", "Group Therapy Sessions", "Family Counseling"
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
            resetFieldStyles();
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
        resetFieldStyles();
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
    void filterOnAction(ActionEvent event) {
        String therapyType = cmbFilterTherapy.getValue();

        try {
            if (therapyType == null || therapyType.equals("All")) {
                loadTableData();
            } else {
                ArrayList<PatientDTO> filteredPatients = PATIENTBO.getPatientsByTherapyType(therapyType);

                ObservableList<PatientTM> patientTMS = FXCollections.observableArrayList();
                for (PatientDTO patientDTO : filteredPatients) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void saveOnAction(ActionEvent event) {
        try {
            if (!validateFields()) {
                return;
            }

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

            PatientDTO patientDTO = new PatientDTO(patientId, firstName, lastName, age, gender, medicalHistory,
                    contact, eMail, address, BloodGroup, allergies);

            boolean isSaved = PATIENTBO.savePatient(patientDTO);
            if (isSaved) {
                loadTableData();
                refrashPage();
            }
        } catch (NumberFormatException e) {
            txtAge.setStyle(ERROR_STYLE);
            txtContact.setStyle(ERROR_STYLE);
            txtAge.requestFocus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateFields() {
        boolean isValid = true;
        resetFieldStyles();

        if (!ID_PATTERN.matcher(txtPatientId.getText()).matches()) {
            txtPatientId.setStyle(ERROR_STYLE);
            if (isValid) txtPatientId.requestFocus();
            isValid = false;
        }

        if (!NAME_PATTERN.matcher(txtFirstName.getText()).matches()) {
            txtFirstName.setStyle(ERROR_STYLE);
            if (isValid) txtFirstName.requestFocus();
            isValid = false;
        }

        if (!NAME_PATTERN.matcher(txtLastName.getText()).matches()) {
            txtLastName.setStyle(ERROR_STYLE);
            if (isValid) txtLastName.requestFocus();
            isValid = false;
        }

        if (!AGE_PATTERN.matcher(txtAge.getText()).matches()) {
            txtAge.setStyle(ERROR_STYLE);
            if (isValid) txtAge.requestFocus();
            isValid = false;
        }

        if (cmbGender.getValue() == null) {
            cmbGender.setStyle(ERROR_STYLE);
            if (isValid) cmbGender.requestFocus();
            isValid = false;
        }

        if (!CONTACT_PATTERN.matcher(txtContact.getText()).matches()) {
            txtContact.setStyle(ERROR_STYLE);
            if (isValid) txtContact.requestFocus();
            isValid = false;
        }

        if (!EMAIL_PATTERN.matcher(txtEmail.getText()).matches()) {
            txtEmail.setStyle(ERROR_STYLE);
            if (isValid) txtEmail.requestFocus();
            isValid = false;
        }

        if (!ADDRESS_PATTERN.matcher(txtAddress.getText()).matches()) {
            txtAddress.setStyle(ERROR_STYLE);
            if (isValid) txtAddress.requestFocus();
            isValid = false;
        }

        if (cmbBloodGroup.getValue() == null) {
            cmbBloodGroup.setStyle(ERROR_STYLE);
            if (isValid) cmbBloodGroup.requestFocus();
            isValid = false;
        }

        if (!ALLERGIES_PATTERN.matcher(txtAllergies.getText()).matches()) {
            txtAllergies.setStyle(ERROR_STYLE);
            if (isValid) txtAllergies.requestFocus();
            isValid = false;
        }

        return isValid;
    }

    private void resetFieldStyles() {
        txtPatientId.setStyle(DEFAULT_STYLE);
        txtFirstName.setStyle(DEFAULT_STYLE);
        txtLastName.setStyle(DEFAULT_STYLE);
        txtAge.setStyle(DEFAULT_STYLE);
        cmbGender.setStyle(DEFAULT_STYLE);
        txtContact.setStyle(DEFAULT_STYLE);
        txtEmail.setStyle(DEFAULT_STYLE);
        txtAddress.setStyle(DEFAULT_STYLE);
        cmbBloodGroup.setStyle(DEFAULT_STYLE);
        txtAllergies.setStyle(DEFAULT_STYLE);
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
            resetFieldStyles();
        }
    }

    @FXML
    void updateOnAction(ActionEvent event) {
        try {
            String patientId = txtPatientId.getText();

            if (patientId.isEmpty()) {
                txtPatientId.setStyle(ERROR_STYLE);
                txtPatientId.requestFocus();
                return;
            }

            if (!validateFields()) {
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

            PatientDTO patientDTO = new PatientDTO(patientId, firstName, lastName, age, gender, medicalHistory,
                    contact, eMail, address, BloodGroup, allergies);

            boolean isUpdated = PATIENTBO.updatePatient(patientDTO);
            if (isUpdated) {
                loadTableData();
                refrashPage();
            }
        } catch (NumberFormatException e) {
            txtAge.setStyle(ERROR_STYLE);
            txtContact.setStyle(ERROR_STYLE);
            txtAge.requestFocus();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void viewTherapiesOnAction(ActionEvent event) {
        try {
            subAnchor.getChildren().clear();
            subAnchor.getChildren().add(FXMLLoader.load(getClass().getResource("/view/TherapistPage.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
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
                    resetFieldStyles();

                    ObservableList<PatientTM> patientTMS = FXCollections.observableArrayList();
                    patientTMS.add(new PatientTM(
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
                    ));
                    tblPatients.setItems(patientTMS);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void enterOnAction(ActionEvent actionEvent) {
        searchOnAction(actionEvent);
    }
}