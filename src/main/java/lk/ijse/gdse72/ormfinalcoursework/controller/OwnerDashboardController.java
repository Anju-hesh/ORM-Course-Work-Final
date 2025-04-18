package lk.ijse.gdse72.ormfinalcoursework.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class OwnerDashboardController {

    @FXML
    private Button btnBackToLoginn;

    @FXML
    private Button btnPatient;

    @FXML
    private Button btnPayment;

    @FXML
    private Button btnReport;

    @FXML
    private Button btnTherapiSession;

    @FXML
    private Button btnTherapist;

    @FXML
    private Button btnTherapyProgram;

    @FXML
    private Button btnUser;

    @FXML
    private Pane rightPane;

    @FXML
    private AnchorPane mainAnchorPane;


    @FXML
    void backToLoginOnAction(ActionEvent event) {
        try {
            mainAnchorPane.getChildren().clear();
            mainAnchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void patentOnAction(ActionEvent event) {
        navigateToPage("/view/PatientManagementPage.fxml");
    }

    @FXML
    void paymentOnAction(ActionEvent event) {
        navigateToPage("/view/PaymentPage.fxml");
    }

    @FXML
    void reportOnAction(ActionEvent event) {

    }

    @FXML
    void therapiSessionOnAction(ActionEvent event) {
        navigateToPage("/view/TherapySessionPage.fxml");
    }

    @FXML
    void therapistOnAction(ActionEvent event) {
        navigateToPage("/view/TherapistPage.fxml");
    }

    @FXML
    void therapyprogramOnAction(ActionEvent event) {
        navigateToPage("/view/TherapyProgramPage.fxml");
    }

    @FXML
    void userOnAction(ActionEvent event) {
        navigateToPage("/view/UserManagePage.fxml");
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
//        navigateToPage("/view/OwnerDashboard.fxml");
        rightPane.getChildren().clear();
    }

}
