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
import lk.ijse.gdse72.ormfinalcoursework.bo.custom.PaymentBO;
import lk.ijse.gdse72.ormfinalcoursework.bo.custom.TherapistBO;
import lk.ijse.gdse72.ormfinalcoursework.dao.custom.PatientDAO;
import lk.ijse.gdse72.ormfinalcoursework.dao.custom.TherapistDAO;
import lk.ijse.gdse72.ormfinalcoursework.dao.custom.TherapySessionDAO;
import lk.ijse.gdse72.ormfinalcoursework.dao.custom.impl.PatientDAOImpl;
import lk.ijse.gdse72.ormfinalcoursework.dao.custom.impl.TherapistDAOImpl;
import lk.ijse.gdse72.ormfinalcoursework.dao.custom.impl.TherapySessionDAOImpl;
import lk.ijse.gdse72.ormfinalcoursework.dto.*;
import lk.ijse.gdse72.ormfinalcoursework.dto.tm.PatientTM;
import lk.ijse.gdse72.ormfinalcoursework.dto.tm.PaymentTM;
import lk.ijse.gdse72.ormfinalcoursework.dto.tm.TherapistTM;
import lk.ijse.gdse72.ormfinalcoursework.entity.Payment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private JFXComboBox<String> cmbPaymentMethod;

    @FXML
    private JFXComboBox<String> cmbStatus;

    @FXML
    private TableColumn<PaymentTM , BigDecimal> colAmount;

    @FXML
    private TableColumn<PaymentTM , BigDecimal> colBalance;

    @FXML
    private TableColumn<PaymentTM , LocalDate> colDate;

    @FXML
    private TableColumn<PaymentTM , BigDecimal> colPayedAmount;

    @FXML
    private TableColumn<PaymentTM , String> colMethod;

    @FXML
    private TableColumn<PaymentTM , String> colPatientName;

    @FXML
    private TableColumn<PaymentTM , String> colPaymentId;

    @FXML
    private TableColumn<PaymentTM , String> colSessionId;

    @FXML
    private TableColumn<PaymentTM , String> colStatus;

    @FXML
    private DatePicker datePayment;

    @FXML
    private AnchorPane paymentAnchor;

    @FXML
    private TableView<PaymentTM> tblPayments;

    @FXML
    private JFXTextField txtAmount;

    @FXML
    private JFXTextField txtBalance;

    @FXML
    private JFXTextField txtPayedAmmount;

    @FXML
    private JFXTextField txtPatientName;

    @FXML
    private JFXTextField txtPaymentId;

//    @FXML
//    private JFXTextField txtSessionId;

    @FXML
    private JFXComboBox<String> cmbSessionId;

    private final PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBO(BOFactory.BOType.PAYMENT);


    public void initialize() {
        try {
            populateComboBoxes();
            refrashPage();
            loadTableData();
            visibleData();

            String nextTherapistId = paymentBO.getNextPaymentId();
            txtPaymentId.setText(nextTherapistId);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Id Genaration Failed: " + e.getMessage()).show();
        }
    }

    private void refrashPage() throws Exception {
        String nextTherapistId = paymentBO.getNextPaymentId();
        txtPaymentId.setText(nextTherapistId);
        cmbSessionId.setValue("");
        txtPatientName.setText("");
        txtAmount.setText("");
        cmbPaymentMethod.setValue(null);
        cmbStatus.setValue(null);
        cmbSessionId.setValue(null);
        txtBalance.setText("");
        txtPayedAmmount.setText("");
        datePayment.setValue(null);
    }

    private void loadTableData() throws Exception {
        ArrayList<PaymentDTO> paymentDTOS = paymentBO.getAllPayments();
        ObservableList<PaymentTM> paymentTMS = FXCollections.observableArrayList();

        for (PaymentDTO paymentDTO : paymentDTOS) {
            PaymentTM paymentTM = new PaymentTM(
                    paymentDTO.getId(),
                    paymentDTO.getSessionId(),
                    paymentDTO.getPatientName(),
                    paymentDTO.getAmount(),
                    paymentDTO.getPaymentMethod(),
                    paymentDTO.getPaymentDate(),
                    paymentDTO.getStatus(),
                    paymentDTO.getPaidAmount(),
                    paymentDTO.getBalance()
            );
            paymentTMS.add(paymentTM);
        }
        tblPayments.setItems(paymentTMS);
    }

    private void populateComboBoxes() throws SQLException {

        TherapySessionDAO sessionDAO = new TherapySessionDAOImpl();

        ArrayList<String> sessionId = sessionDAO.getSessionId();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(sessionId);
        cmbSessionId.setItems(observableList);

        ObservableList<String> methods = FXCollections.observableArrayList(
                "Cash",
                "Credit/Debit Card",
                "Bank Transfer",
                "Cheque"
        );

        cmbPaymentMethod.setItems(methods);

        ObservableList<String> status = FXCollections.observableArrayList(
                "Pending", "Completed", "Failed",
                "Refunded"
        );

        cmbStatus.setItems(status);
    }

    private void visibleData() {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colSessionId.setCellValueFactory(new PropertyValueFactory<>("sessionId"));
        colPatientName.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colPayedAmount.setCellValueFactory(new PropertyValueFactory<>("paidAmount"));
        colBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
    }


    @FXML
    void clearOnAction(ActionEvent event) {
        try {
            refrashPage();
            loadTableData();
            visibleData();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void generateInvoiceOnAction(ActionEvent event) {

    }

    @FXML
    void loadSessionOnAction(ActionEvent event) {
        try {
            TherapySessionDAO therapySessionDAO = new TherapySessionDAOImpl();
            TherapySessionDTO session = therapySessionDAO.getSession(cmbSessionId.getValue());
            if (session != null) {
                txtPatientName.setText(session.getPatientName());
            } else {
                new Alert(Alert.AlertType.WARNING, "No Session found!").show();
                refrashPage();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load patient: " + e.getMessage()).show();
        }
    }

    @FXML
    void processPaymentOnAction(ActionEvent event) {
        try{
            String paymentId = txtPaymentId.getText();
            String sessionId = cmbSessionId.getValue();
            String patientName = txtPatientName.getText();
            BigDecimal amount = new BigDecimal(txtAmount.getText());
            String paymentMethod = cmbPaymentMethod.getValue();
            LocalDate paymentDate = datePayment.getValue();
            String status = cmbStatus.getValue();
            BigDecimal paidAmount = new BigDecimal(txtPayedAmmount.getText());
            BigDecimal balance = new BigDecimal(txtBalance.getText());



            if (!paymentId.isEmpty() && !sessionId.isEmpty() && !patientName.isEmpty() && !paymentMethod.isEmpty() && !status.isEmpty() && paymentDate != null) {
                PaymentDTO paymentDTO = new PaymentDTO(
                        paymentId,
                        sessionId,
                        patientName,
                        amount,
                        paymentMethod,
                        paymentDate,
                        status,
                        paidAmount,
                        balance

                );

                boolean isSaved = paymentBO.savePayment(paymentDTO);

                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Payment Process Saved Successfully!").show();
                    loadTableData();
                    refrashPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Save Payment Process!").show();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Please fill all the fields with valid data!").show();
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void searchOnAction(ActionEvent event) {
        String paymentId = txtPaymentId.getText();

        if (!paymentId.isEmpty()) {
            try{
                PaymentDTO paymentDTO = paymentBO.searchPayment(paymentId);

                if (paymentDTO !=null) {
                    txtPaymentId.setText(paymentDTO.getId());
                    cmbSessionId.setValue(paymentDTO.getSessionId());
                    txtPatientName.setText(paymentDTO.getPatientName());
                    txtAmount.setText(String.valueOf(paymentDTO.getAmount()));
                    cmbStatus.setValue(paymentDTO.getStatus());
                    cmbPaymentMethod.setValue(paymentDTO.getPaymentMethod());
                    datePayment.setValue(paymentDTO.getPaymentDate());
                    txtBalance.setText(String.valueOf(paymentDTO.getBalance()));
                    txtPayedAmmount.setText(String.valueOf(paymentDTO.getPaidAmount()));

                    ObservableList<PaymentTM> paymentTMS = FXCollections.observableArrayList();

                    PaymentTM paymentTM = new PaymentTM(
                            paymentDTO.getId(),
                            paymentDTO.getSessionId(),
                            paymentDTO.getPatientName(),
                            paymentDTO.getAmount(),
                            paymentDTO.getPaymentMethod(),
                            paymentDTO.getPaymentDate(),
                            paymentDTO.getStatus(),
                            paymentDTO.getPaidAmount(),
                            paymentDTO.getBalance()
                    );
                    paymentTMS.add(paymentTM);
                    tblPayments.setItems(paymentTMS);
                } else {
                    new Alert(Alert.AlertType.WARNING, "Payment Not Found!").show();
                }
            }catch (Exception e){
                new Alert(Alert.AlertType.ERROR, "An error occurred while searching!").show();            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please enter a Payment ID to search!").show();
        }
    }

    @FXML
    void canselPaymentOnAction(ActionEvent event) {
        PaymentTM selectedPayment = tblPayments.getSelectionModel().getSelectedItem();

        if (selectedPayment != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Deletion");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Are you sure you want to Cansel Payment : " + selectedPayment.getId() + "?");

            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    boolean isDeleted = paymentBO.deletePayment(selectedPayment.getId());

                    if (isDeleted) {
                        new Alert(Alert.AlertType.INFORMATION, "Payment deleted successfully!").show();
                        loadTableData();
                        refrashPage();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Failed to delete the Payemnt!").show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
                }
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please select a Payment to delete.").show();
        }
    }


    public void tblPaymentClickOnAction(MouseEvent mouseEvent) {

        PaymentTM selectedPayment = tblPayments.getSelectionModel().getSelectedItem();

        if (selectedPayment != null) {
            txtPaymentId.setText(selectedPayment.getId());
            cmbSessionId.setValue(selectedPayment.getSessionId());
            txtPatientName.setText(selectedPayment.getPatientName());
            txtAmount.setText(selectedPayment.getAmount().toString());
            cmbPaymentMethod.setValue(selectedPayment.getPaymentMethod());
            cmbStatus.setValue(selectedPayment.getStatus());
            txtBalance.setText(selectedPayment.getBalance().toString());
            txtPayedAmmount.setText(selectedPayment.getPaidAmount().toString());
            datePayment.setValue(LocalDate.from(selectedPayment.getPaymentDate()));
        }
    }

//    @FXML
//    void calOnAction(ActionEvent event) {
//        try {
//
//            if (txtAmount.getText().isEmpty() || txtPayedAmmount.getText().isEmpty()) {
//                throw new IllegalArgumentException("Amount and paid amount cannot be empty.");
//            }
//
//            BigDecimal amount = new BigDecimal(txtAmount.getText().trim());
//            BigDecimal paidAmount = new BigDecimal(txtPayedAmmount.getText().trim());
//
//            if (amount.compareTo(BigDecimal.ZERO) < 0 || paidAmount.compareTo(BigDecimal.ZERO) < 0) {
//                throw new IllegalArgumentException("Values must be positive.");
//            }
//
//            BigDecimal balance = paidAmount.subtract(amount);
//
//            txtBalance.setText(balance.setScale(2, RoundingMode.HALF_UP).toString());
//
//        } catch (NumberFormatException e) {
//            new Alert(Alert.AlertType.ERROR, "Invalid number format. Enter numeric values only.").show();
//        } catch (IllegalArgumentException e) {
//            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
//        }
//    }

    @FXML
    void calOnAction(ActionEvent event) {
        try {
            if (txtAmount.getText().isEmpty() || txtPayedAmmount.getText().isEmpty()) {
                throw new IllegalArgumentException("Amount and paid amount cannot be empty.");
            }

            BigDecimal amount = new BigDecimal(txtAmount.getText().trim());
            BigDecimal paidAmount = new BigDecimal(txtPayedAmmount.getText().trim());

            if (amount.compareTo(BigDecimal.ZERO) < 0 || paidAmount.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Values must be positive.");
            }

            BigDecimal balance = paidAmount.subtract(amount); // balance = change to return

            if (balance.compareTo(BigDecimal.ZERO) < 0) {
                // Not enough payment
                txtBalance.setText("0.00");
                new Alert(Alert.AlertType.WARNING,
                        "Not enough payment. You still owe: Rs. " +
                                balance.abs().setScale(2, RoundingMode.HALF_UP)
                ).show();
            } else {
                // Paid enough or overpaid – show the balance (change)
                txtBalance.setText(balance.setScale(2, RoundingMode.HALF_UP).toString());
            }

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid number format. Enter numeric values only.").show();
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


}
