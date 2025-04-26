package lk.ijse.gdse72.ormfinalcoursework.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse72.ormfinalcoursework.bo.BOFactory;
import lk.ijse.gdse72.ormfinalcoursework.bo.custom.UserBO;
import lk.ijse.gdse72.ormfinalcoursework.servise.EnteredUserId;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class ResetPasswordController {

    @FXML
    private JFXButton btnConfirm;

    @FXML
    private JFXButton btnResentMail;

    @FXML
    private ImageView imgBack;

    @FXML
    private ImageView imgEyeReEnterResetPassword;

    @FXML
    private ImageView imgEyeResetPassword;

    @FXML
    private ImageView imgLeftSideResetPassword;

    @FXML
    private ImageView imgresetPassword;

    @FXML
    private Label lblEnterNewPassword;

    @FXML
    private Label lblMessage;

    @FXML
    private Label lblReEnterPassword;

    @FXML
    private AnchorPane resetPasswordAnchorPane;

    @FXML
    private JFXTextField txtNewPassword;

    @FXML
    private JFXPasswordField txtPasswordFieldNew;

    @FXML
    private JFXPasswordField txtPasswordFieldReEnter;

    @FXML
    private JFXTextField txtReEnterPassword;

    private final UserBO USERBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    @FXML
    void ResendMailOnActionImg(MouseEvent event) {
        try{
            resetPasswordAnchorPane.getChildren().clear();
            resetPasswordAnchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/PasswordRecovery.fxml")));
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,"Fail to load Page!").show();
        }
    }

    @FXML
    void confirmOnAction(ActionEvent event) throws SQLException {
        String newPassword = txtPasswordFieldNew.getText();
        String password = txtPasswordFieldReEnter.getText();
        if (newPassword.equals(password)) {
            String id = EnteredUserId.getUserId();
            boolean isUpdate = USERBO.updateDetails(newPassword,id);
            if (isUpdate) {
                try {
                    resetPasswordAnchorPane.getChildren().clear();
                    resetPasswordAnchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml")));
                } catch (IOException e) {
                    new Alert(Alert.AlertType.ERROR, "Fail to load Page!").show();
                }
            }else {
                new Alert(Alert.AlertType.ERROR,"Fail to update details!").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR,"Passwords do not match!").show();
        }
    }

    @FXML
    void eyePressOnAction(MouseEvent event) {
        txtNewPassword.setText(txtPasswordFieldNew.getText());
        if(!txtPasswordFieldNew.getText().isEmpty()){
            txtNewPassword.setVisible(true);
            imgEyeResetPassword.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/icons8-eye-50.png"))));
        }else{
            txtNewPassword.setVisible(false);
            new Alert(Alert.AlertType.ERROR,"Please enter The Password!").show();
        }
    }

    @FXML
    void eyePressOnActionReEnter(MouseEvent event) {
        txtReEnterPassword.setText(txtPasswordFieldReEnter.getText());
        if(!txtPasswordFieldReEnter.getText().isEmpty()){
            txtReEnterPassword.setVisible(true);
            imgEyeReEnterResetPassword.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/icons8-eye-50.png"))));
        }else{
            txtReEnterPassword.setVisible(false);
            new Alert(Alert.AlertType.ERROR,"Please enter The Password!").show();
        }
    }

    @FXML
    void eyeReleaseOnAction(MouseEvent event) {
        txtPasswordFieldNew.setText(txtNewPassword.getText());
        txtNewPassword.setVisible(false);
        imgEyeResetPassword.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/icons8-closed-eye-50.png"))));
    }

    @FXML
    void eyeReleaseOnActionReEnter(MouseEvent event) {
        txtReEnterPassword.setText(txtPasswordFieldReEnter.getText());
        txtReEnterPassword.setVisible(false);
        imgEyeReEnterResetPassword.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/icons8-closed-eye-50.png"))));
    }

    @FXML
    void resenMailOnAction(ActionEvent event) {
        try{
            resetPasswordAnchorPane.getChildren().clear();
            resetPasswordAnchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/PasswordRecovery.fxml")));
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,"Fail to load Page!").show();
        }
    }
}
