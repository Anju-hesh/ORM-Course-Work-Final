package lk.ijse.gdse72.ormfinalcoursework.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class TherapySessionPageController {

    @FXML
    private JFXButton btnBook;

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXButton btnCheckAvailable;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnLoadPatient;

    @FXML
    private JFXButton btnReschedule;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXComboBox<?> cmbDuration;

    @FXML
    private JFXComboBox<?> cmbProgram;

    @FXML
    private JFXComboBox<?> cmbStatus;

    @FXML
    private JFXComboBox<?> cmbTherapist;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDuration;

    @FXML
    private TableColumn<?, ?> colPatientName;

    @FXML
    private TableColumn<?, ?> colProgram;

    @FXML
    private TableColumn<?, ?> colSessionId;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colTherapist;

    @FXML
    private TableColumn<?, ?> colTime;

    @FXML
    private DatePicker datePicker;

    @FXML
    private AnchorPane subAnchor;

    @FXML
    private TableView<?> tblSessions;

    @FXML
    private ComboBox<?> timeComboBox;

    @FXML
    private JFXTextField txtNotes;

    @FXML
    private JFXTextField txtPatientId;

    @FXML
    private JFXTextField txtPatientName;

    @FXML
    private JFXTextField txtSessionId;

    @FXML
    void bookOnAction(ActionEvent event) {

    }

    @FXML
    void cancelOnAction(ActionEvent event) {

    }

    @FXML
    void checkAvailableOnAction(ActionEvent event) {

    }

    @FXML
    void clearOnAction(ActionEvent event) {

    }

    @FXML
    void loadPatientOnAction(ActionEvent event) {

    }

    @FXML
    void rescheduleOnAction(ActionEvent event) {

    }

    @FXML
    void searchOnAction(ActionEvent event) {

    }

}
