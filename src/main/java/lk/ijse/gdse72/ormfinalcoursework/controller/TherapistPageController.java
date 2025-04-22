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

import java.util.ArrayList;
import java.util.Optional;

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
    private TableColumn<TherapistTM , String > colAvailability;

    @FXML
    private TableColumn<TherapistTM , Integer> colContact;

    @FXML
    private TableColumn<TherapistTM , String> colId;

    @FXML
    private TableColumn<TherapistTM , String> colName;

    @FXML
    private TableColumn<TherapistTM , String> colPrograms;

    @FXML
    private TableColumn<TherapistTM , String> colSpecialization;

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
            new Alert(Alert.AlertType.ERROR, "Id Genaration Failed: " + e.getMessage()).show();
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
        try{
            String nextTherapistId = therapistBO.getNextTherapistId();
            txtTherapistId.setText(nextTherapistId);
            txtTherapistName.setText("");
            txtContact.setText("");
            txtEmail.setText("");
            cmbAvailability.setValue(null);
            cmbSpecialization.setValue(null);
            cmbPrograms.setValue(null);
        }catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Id Genaration Failed:" + e.getMessage()).show();
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

    public void populateComboBoxes() {
        ObservableList<String> availability = FXCollections.observableArrayList(
                "Available",
                "Unavailable",
                "On Leave",
                "Busy"
        );

        cmbAvailability.setItems(availability);

        ObservableList<String> programes = FXCollections.observableArrayList(
                "Cognitive Behavioral Therapy", "Mindfulness-Based Stress Reduction" ,
                        "Dialectical Behavior Therapy" , "Group Therapy Sessions" , "Family Counseling"
        );

        cmbPrograms.setItems(programes);

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
//            cmbAvailability.setValue(null);
//            cmbPrograms.setValue(null);
//            cmbSpecialization.setValue(null);
            refrashPage();
            loadTableData();
            visibleData();

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
            confirmAlert.setContentText("Are you sure you want to delete Therapist : " + selectedTherapist.getTherapistId() + "?");

            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    boolean isDeleted = therapistBO.deleteTherapist(selectedTherapist.getTherapistId());

                    if (isDeleted) {
                        new Alert(Alert.AlertType.INFORMATION, "Therapist deleted successfully!").show();
                        loadTableData();
                        refrashPage();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Failed to delete the Therapist!").show();
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

    @FXML
    void saveOnAction(ActionEvent event) {
        try {
            String therapistId = txtTherapistId.getText();
            String therapistName = txtTherapistName.getText();
            int contact = Integer.parseInt(txtContact.getText());
            String email = txtEmail.getText();
            String specialization = cmbSpecialization.getValue();
            String program = cmbPrograms.getValue();
            String availability = cmbAvailability.getValue();

            if (!therapistId.isEmpty() && !therapistName.isEmpty() && !email.isEmpty() && specialization != null && program != null && availability != null && contact > 0) {

                TherapistDTO therapistDTO = new TherapistDTO(therapistId,therapistName,specialization,availability,contact,program,email);

                boolean isSaved = therapistBO.saveTherapist(therapistDTO);

                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Therapist Saved Successfully!").show();
                    loadTableData();
                    refrashPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Save Therapist!").show();
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
    void searchOnAction(ActionEvent event) {

        String therapistId = txtTherapistId.getText();

        if (!therapistId.isEmpty()) {
            try{
                TherapistDTO therapistDTO = therapistBO.searchTherapist(therapistId);

                if (therapistDTO !=null) {
                    txtTherapistName.setText(therapistDTO.getTherapistName());
                    txtContact.setText(String.valueOf(therapistDTO.getContact()));
                    txtEmail.setText(therapistDTO.getMail());
                    cmbSpecialization.setValue(therapistDTO.getSpecialization());
                    cmbPrograms.setValue(therapistDTO.getProgram());
                    cmbAvailability.setValue(therapistDTO.getAvailability());

                    ObservableList<TherapistTM> therapistTMS = FXCollections.observableArrayList();

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
                    tblTherapists.setItems(therapistTMS);
                } else {
                    new Alert(Alert.AlertType.WARNING, "Therapist Not Found!").show();
                }
            }catch (Exception e){
                new Alert(Alert.AlertType.ERROR, "An error occurred while searching!").show();            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please enter a Therapist ID to search!").show();
        }
    }

    @FXML
    void updateOnAction(ActionEvent event) {
        try {
            String therapistId = txtTherapistId.getText();
            String therapistName = txtTherapistName.getText();
            int contact = Integer.parseInt(txtContact.getText());
            String email = txtEmail.getText();
            String specialization = cmbSpecialization.getValue();
            String program = cmbPrograms.getValue();
            String availability = cmbAvailability.getValue();

            if (!therapistId.isEmpty() && !therapistName.isEmpty() && !email.isEmpty() && specialization != null && program != null && availability != null && contact > 0) {

                TherapistDTO therapistDTO = new TherapistDTO(therapistId,therapistName,specialization,availability,contact,program,email);

                boolean isUpdated = therapistBO.updateTherapist(therapistDTO);

                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Therapist Updated Successfully!").show();
                    loadTableData();
                    refrashPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Update Therapist!").show();
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
        }
    }

    public void enterOnAction(ActionEvent actionEvent) {
        searchOnAction(actionEvent);
    }
}
