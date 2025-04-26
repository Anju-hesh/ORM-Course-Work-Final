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
import lk.ijse.gdse72.ormfinalcoursework.bo.BOFactory;
import lk.ijse.gdse72.ormfinalcoursework.bo.custom.UserBO;
import lk.ijse.gdse72.ormfinalcoursework.servise.EnteredUserId;
import lk.ijse.gdse72.ormfinalcoursework.servise.SendEmail;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

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

    Random rand = new Random();
    private final SendEmail sendEmail = new SendEmail();
    private final UserBO USERBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);
    private int code = rand.nextInt(1000,9999);

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
    void sendCodeOnAction(ActionEvent event) throws SQLException {
        String[] userNameAndEmail = USERBO.getDetails(txtEmail.getText());
        System.out.println(userNameAndEmail[0] + " , " + userNameAndEmail[1]);

        if (userNameAndEmail[0].equals( txtUserName.getText() )&& userNameAndEmail[1].equals(txtEmail.getText())) {
            String userId = USERBO.getId(txtEmail.getText());
            EnteredUserId.setUserId(userId);
            String from = "anjanaheshan676@gmail.com";
            String to = txtEmail.getText();
            String subject = "Your password reset verification code";
            String body = "Your OTP " + code;
            sendEmail.sendEmailWithGmail(from, to, subject, body);
//            new Alert(Alert.AlertType.CONFIRMATION,"Enter the code below").show();

            txtCode1.setVisible(true);
            txtCode2.setVisible(true);
            txtCode3.setVisible(true);
            txtCode4.setVisible(true);
        }else {
            new Alert(Alert.AlertType.ERROR,"Invalid Email Address or User Name!").show();
        }
    }

    @FXML
    void submitButton(ActionEvent event) {
        String otpCcode = txtCode1.getText() + txtCode2.getText() + txtCode3.getText() + txtCode4.getText();
        if (code == Integer.parseInt(otpCcode)){
            try{
                fogetPasswordAnchorPane.getChildren().clear();
                fogetPasswordAnchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/resetPasswordPage.fxml")));
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR,"Fail to load Page!" + e.getMessage()).show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR,"Invalid OTP Code!").show();
        }
    }
//    public void changeFocusTextForget() {
//        JFXTextField[] textFields = { txtUserName, txtEmail };
//
//        for (int i = 0; i < textFields.length; i++) {
//            int currentIndex = i;
//            textFields[i].setOnKeyPressed(event -> {
//                if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.DOWN) {
//                    int nextIndex = (currentIndex + 1) % textFields.length;
//                    textFields[nextIndex].requestFocus();
//                } else if (event.getCode() == KeyCode.UP) {
//
//                    int previousIndex = (currentIndex - 1 + textFields.length) % textFields.length;
//                    textFields[previousIndex].requestFocus();
//
//                }
//            });
//        }
//    }

}
