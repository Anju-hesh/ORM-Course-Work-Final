package lk.ijse.gdse72.ormfinalcoursework.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class PatientPageController  {

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
    private JFXComboBox<?> cmbBloodGroup;

    @FXML
    private JFXComboBox<?> cmbFilterTherapy;

    @FXML
    private JFXComboBox<?> cmbGender;

    @FXML
    private TableColumn<?, ?> colAge;

    @FXML
    private TableColumn<?, ?> colBloodGroup;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colFullName;

    @FXML
    private TableColumn<?, ?> colGender;

    @FXML
    private TableColumn<?, ?> colLastSession;

    @FXML
    private TableColumn<?, ?> colPatientId;

    @FXML
    private TableColumn<?, ?> colTherapyCount;

    @FXML
    private DatePicker dpDob;

    @FXML
    private AnchorPane subAnchor;

    @FXML
    private TableView<?> tblPatients;

    @FXML
    private JFXTextArea txtAddress;

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

    @FXML
    void clearFilterOnAction(ActionEvent event) {

    }

    @FXML
    void clearOnAction(ActionEvent event) {

    }

    @FXML
    void deleteOnAction(ActionEvent event) {

    }

    @FXML
    void filterOnAction(ActionEvent event) {

    }

    @FXML
    void saveOnAction(ActionEvent event) {

    }

    @FXML
    void updateOnAction(ActionEvent event) {

    }

    @FXML
    void viewTherapiesOnAction(ActionEvent event) {

    }

}
