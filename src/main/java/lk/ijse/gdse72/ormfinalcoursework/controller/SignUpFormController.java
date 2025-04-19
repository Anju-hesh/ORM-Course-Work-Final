package lk.ijse.gdse72.ormfinalcoursework.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.gdse72.ormfinalcoursework.bo.BOFactory;
import lk.ijse.gdse72.ormfinalcoursework.bo.custom.UserBO;
import lk.ijse.gdse72.ormfinalcoursework.dto.UserDTO;

import java.sql.SQLException;
import java.util.Objects;

public class SignUpFormController {

    @FXML
    private JFXButton btnSignUp;

    @FXML
    private JFXComboBox<String> cmbRole;

    @FXML
    private ImageView eyeConfirmPassword;

    @FXML
    private ImageView eyePassword;

    @FXML
    private ImageView imgback;

    @FXML
    private JFXPasswordField txtConfirmPassword;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXTextField txtUserId;

    @FXML
    private JFXTextField txtUserName;

    @FXML
    private TextField showConfirmPassword;

    @FXML
    private TextField showPassword;


    @FXML
    private AnchorPane mainAnchorRegistration;

    private final UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    @FXML
    public void initialize() {
        cmbRole.getItems().addAll("ADMIN", "RECEPTIONIST");

        String nextuserID = null;
        try {
            nextuserID = userBO.getNextuserId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        txtUserId.setText(nextuserID);

    }

    @FXML
    void SignUpOnAction(ActionEvent event) {
        try {
            String id = txtUserId.getText();
            String username = txtUserName.getText();
            String password = txtPassword.getText();
            String confirmPassword = txtConfirmPassword.getText();
            String role = cmbRole.getValue();

            if (id.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || role == null) {
                showAlert("Fill all fields!", Alert.AlertType.ERROR);
                return;
            }

            if (!password.equals(confirmPassword)) {
                showAlert("Passwords do not match!", Alert.AlertType.WARNING);
                return;
            }

            UserDTO userDTO = new UserDTO(id, username, password, role);
            boolean isSaved = userBO.saveUser(userDTO);

            if (isSaved) {
                showAlert("User registered successfully!", Alert.AlertType.INFORMATION);
                clearFields();

                mainAnchorRegistration.getChildren().clear();
                mainAnchorRegistration.getChildren().add(FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml")));

            } else {
                showAlert("User already exists or registration failed!", Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            showAlert("Something went wrong: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void clearFields() {
        txtUserId.clear();
        txtUserName.clear();
        txtPassword.clear();
        txtConfirmPassword.clear();
        cmbRole.setValue(null);
    }

    @FXML
    void eyeClickedOnAction(MouseEvent event) {}

    @FXML
    void eyeClickedOnActionConfirm(MouseEvent event) {}

    @FXML
    public void LoginOnAction(ActionEvent actionEvent) {
        try {
//            FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/view/LoginPage.fxml"));
//            Parent root = loader.load();
//            Scene scene = new javafx.scene.Scene(root);
//            Stage stage = (Stage) btnSignUp.getScene().getWindow();
//            stage.setScene(scene);
//            stage.setTitle("Login");
//            stage.centerOnScreen();

            mainAnchorRegistration.getChildren().clear();
            mainAnchorRegistration.getChildren().add(FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml")));

        } catch (Exception e) {
            showAlert("Failed to load login form: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }


    @FXML
    public void cmbOnAction(ActionEvent actionEvent) {
        String selectedRole = cmbRole.getValue();

        if (selectedRole != null) {
            showAlert("Role selected: " + selectedRole, Alert.AlertType.INFORMATION);
        }
    }

    private void showAlert(String msg, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Sign Up");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @FXML
    void mousePressedConfirmPassword(MouseEvent event) {
        showConfirmPassword.setText(txtConfirmPassword.getText());
        if(!txtPassword.getText().isEmpty()){
            showConfirmPassword.setVisible(true);
            eyeConfirmPassword.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/icons8-eye-50.png"))));
        }else{
            showConfirmPassword.setVisible(false);
        }
    }

    @FXML
    void mousePressedPassword(MouseEvent event) {
        showPassword.setText(txtPassword.getText());
        if(!txtPassword.getText().isEmpty()){
            showPassword.setVisible(true);
            eyePassword.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/icons8-eye-50.png"))));
        }else{
            showPassword.setVisible(false);
        }
    }

    @FXML
    void mouseReleasedConfirmPassword(MouseEvent event) {
        txtConfirmPassword.setText(showConfirmPassword.getText());
        showConfirmPassword.setVisible(false);
        eyeConfirmPassword.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/icons8-closed-eye-50.png"))));

    }

    @FXML
    void mouseReleasedPassword(MouseEvent event) {
        txtPassword.setText(showPassword.getText());
        showPassword.setVisible(false);
        eyePassword.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/icons8-closed-eye-50.png"))));

    }
}
