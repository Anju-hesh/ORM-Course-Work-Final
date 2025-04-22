package lk.ijse.gdse72.ormfinalcoursework.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class PasswordRecoveryController {

    @FXML
    private JFXButton btnSendCode;

    @FXML
    private JFXButton btnSubmit;

    @FXML
    private AnchorPane fogetPasswordAnchorPane;

    @FXML
    private ImageView imgBack;

    @FXML
    private ImageView imgLogo;

    @FXML
    private ImageView imgSS;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblFogetPassword;

    @FXML
    private Label lblReturn;

    @FXML
    private Label lblSub;

    @FXML
    private Label lblUserName;

    @FXML
    private JFXTextField txtCode1;

    @FXML
    private JFXTextField txtCode2;

    @FXML
    private JFXTextField txtCode3;

    @FXML
    private JFXTextField txtCode4;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtUserName;

    public void initialize() {
        changeFocus();
    }

    public void changeFocus() {

        txtUserName.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                txtEmail.requestFocus();
            }
        });

        txtEmail.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                btnSendCode.fire();
            }
        });
    }

    @FXML
    void backOnAction(MouseEvent event) {
        try{
            fogetPasswordAnchorPane.getChildren().clear();
            fogetPasswordAnchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml")));
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,"Fail to load Page!" + e.getMessage()).show();
        }
    }

    @FXML
    void sendCodeOnAction(ActionEvent event) {

    }

    @FXML
    void submitButton(ActionEvent event) {

    }

}
