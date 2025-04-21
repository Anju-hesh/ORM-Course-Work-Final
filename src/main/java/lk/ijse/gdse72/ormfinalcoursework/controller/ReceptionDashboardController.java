package lk.ijse.gdse72.ormfinalcoursework.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ReceptionDashboardController {

    @FXML
    private Button btnBackToLoginn;

    @FXML
    private Button btnPatient;

    @FXML
    private Button btnPayment;

    @FXML
    private Button btnReport;

    @FXML
    private Button btnUserManage;

    @FXML
    private Button btnTherapiSession;

    @FXML
    private Button btnTherapistAvailability;

    @FXML
    private Button btnTherapyProgram;

    @FXML
    private Pane rightPane;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    void patentOnAction(ActionEvent event) {
        navigateToPage("/view/PatientManagementPage.fxml");
    }

    @FXML
    void paymentOnAction(ActionEvent event) {
        navigateToPage("/view/PaymentPage.fxml");
    }

    @FXML
    void therapistAvailabilityOnAction(ActionEvent event) {
        navigateToPage("/view/TherapistAvailabilty.fxml");
    }
    @FXML
    void reportOnAction(ActionEvent event) {

    }

    @FXML
    void therapiSessionOnAction(ActionEvent event) {
        navigateToPage("/view/TherapySessionPage.fxml");
    }

    @FXML
    void therapyprogramOnAction(ActionEvent event) {
        navigateToPage("/view/TherapyProgramPage.fxml");
    }

    public void btnBackToLoginn(ActionEvent actionEvent) {
        try {
            mainAnchorPane.getChildren().clear();
            mainAnchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void navigateToPage(String fxml){
        try {
            rightPane.getChildren().clear();
            rightPane.getChildren().add(FXMLLoader.load(getClass().getResource(fxml)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void backOnAction(MouseEvent mouseEvent) {
        rightPane.getChildren().clear();
    }

}
