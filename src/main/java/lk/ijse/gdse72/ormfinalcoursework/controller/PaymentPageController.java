package lk.ijse.gdse72.ormfinalcoursework.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class PaymentPageController {

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnGenerate;

    @FXML
    private JFXButton btnLoadSession;

    @FXML
    private JFXButton btnPrint;

    @FXML
    private JFXButton btnProcess;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXComboBox<?> cmbPaymentMethod;

    @FXML
    private JFXComboBox<?> cmbStatus;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colBalance;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colInvoiceNo;

    @FXML
    private TableColumn<?, ?> colMethod;

    @FXML
    private TableColumn<?, ?> colPatientName;

    @FXML
    private TableColumn<?, ?> colPaymentId;

    @FXML
    private TableColumn<?, ?> colSessionId;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private DatePicker datePayment;

    @FXML
    private AnchorPane paymentAnchor;

    @FXML
    private TableView<?> tblPayments;

    @FXML
    private JFXTextField txtAmount;

    @FXML
    private JFXTextField txtBalance;

    @FXML
    private JFXTextField txtInvoiceNo;

    @FXML
    private JFXTextField txtNotes;

    @FXML
    private JFXTextField txtPatientName;

    @FXML
    private JFXTextField txtPaymentId;

    @FXML
    private JFXTextField txtSessionId;

    @FXML
    void clearOnAction(ActionEvent event) {

    }

    @FXML
    void generateInvoiceOnAction(ActionEvent event) {

    }

    @FXML
    void loadSessionOnAction(ActionEvent event) {

    }

    @FXML
    void printInvoiceOnAction(ActionEvent event) {

    }

    @FXML
    void processPaymentOnAction(ActionEvent event) {

    }

    @FXML
    void searchOnAction(ActionEvent event) {

    }

}
