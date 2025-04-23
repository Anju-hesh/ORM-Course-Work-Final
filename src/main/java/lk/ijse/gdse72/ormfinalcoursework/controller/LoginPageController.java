//package lk.ijse.gdse72.ormfinalcoursework.controller;
//
//import com.jfoenix.controls.JFXButton;
//import com.jfoenix.controls.JFXPasswordField;
//import com.jfoenix.controls.JFXTextField;
//import javafx.application.Platform;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.input.KeyCode;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.Pane;
//import javafx.stage.Stage;
//import lk.ijse.gdse72.ormfinalcoursework.bo.BOFactory;
//import lk.ijse.gdse72.ormfinalcoursework.bo.custom.UserBO;
//import lk.ijse.gdse72.ormfinalcoursework.dto.UserDTO;
//import lk.ijse.gdse72.ormfinalcoursework.servise.PasswordUtil;
//
//import java.io.IOException;
//import java.util.Objects;
//
//public class LoginPageController {
//
//    @FXML
//    private ImageView backgroundImg;
//
//    @FXML
//    private JFXButton btnLogin;
//
//    @FXML
//    private CheckBox chkRemember;
//
//    @FXML
//    private Pane detailPane;
//
//    @FXML
//    private Hyperlink fogotPassword;
//
//    @FXML
//    private ImageView imgEye;
//
//    @FXML
//    private Hyperlink signUpForFree;
//
//    @FXML
//    private JFXPasswordField txtPassword;
//
//    @FXML
//    private JFXTextField txtUserName;
//
//    @FXML
//    private TextField showPassword;
//
//
//    private final UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);
//
//    public void initialize() {
//        changeFocus();
//    }
//
//    public void changeFocus() {
//
//        txtUserName.setOnKeyPressed(event -> {
//            if(event.getCode() == KeyCode.ENTER) {
//                txtPassword.requestFocus();
//            }
//        });
//
//        txtPassword.setOnKeyPressed(event -> {
//            if(event.getCode() == KeyCode.ENTER) {
//                btnLogin.fire();
//            }
//        });
//    }
//
//    @FXML
//    void fogotPasswordOnAction(ActionEvent event) {
//        navigateToPage("/view/PasswordRecovery.fxml", "ForgetPassword");
//    }
//
//    @FXML
//    void imgEyeMouseClick(MouseEvent event) {}
//
//    @FXML
//    void keepMeLoggingOnAction(ActionEvent event) {}
//
//    @FXML
//    void loginOnAction(ActionEvent event) {
//        String username = txtUserName.getText();
//        String rawPassword = txtPassword.getText();
//
//        if (username.isEmpty() || rawPassword.isEmpty()) {
//            showErrorMessage("Username and Password cannot be empty!");
//            return;
//        }
//
//        try {
//
//            boolean isAuthenticated = userBO.authenticateUser(username, rawPassword);
//            if (isAuthenticated) {
//                UserDTO user = userBO.getUserByUsername(username);
//                navigateToDashboard(user.getRole());
//            } else {
//                showErrorMessage("Invalid username or password!");
//            }
//        } catch (Exception e) {
//            showErrorMessage("An error occurred during login: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    private void navigateToDashboard(String role) {
//        try {
//            String fxmlPath;
//            String title;
//
//            if ("ADMIN".equalsIgnoreCase(role)) {
//                fxmlPath = "/view/OwnerDashboard.fxml";
//                title = "Owner Dashboard";
//            } else if ("RECEPTIONIST".equalsIgnoreCase(role)) {
//                fxmlPath = "/view/ReceptionDashboard.fxml";
//                title = "Receptionist Dashboard";
//            } else {
//                showErrorMessage("Unauthorized role!");
//                return;
//            }
//
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
//            Parent root = loader.load();
//
//            Stage currentStage = (Stage) btnLogin.getScene().getWindow();
//
//            Scene scene = new Scene(root);
//            currentStage.setScene(scene);
//            currentStage.setTitle(title);
//            currentStage.centerOnScreen();
//            currentStage.show();
//
//        } catch (IOException e) {
//            showErrorMessage("Failed to load dashboard: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    @FXML
//    void signUpForFreeOnAction(ActionEvent event) {
//        navigateToPage("/view/SignUpForm.fxml", "SignUpForm");
//    }
//
//    private void navigateToPage(String fxmlPath, String title) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
//            Parent root = loader.load();
//
//            Stage currentStage = (Stage) btnLogin.getScene().getWindow();
//
//            Scene scene = new Scene(root);
//            currentStage.setScene(scene);
//            currentStage.setTitle(title);
//            currentStage.centerOnScreen();
//            currentStage.show();
//        } catch (IOException e) {
//            showErrorMessage("Failed to load page: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    private void showErrorMessage(String message) {
//        Platform.runLater(() -> {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setHeaderText(null);
//            alert.setContentText(message);
//            alert.showAndWait();
//        });
//    }
//
//    public void mousePressed(MouseEvent mouseEvent) {
//        showPassword.setText(txtPassword.getText());
//        if(!txtPassword.getText().isEmpty()){
//            showPassword.setVisible(true);
//            imgEye.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/icons8-eye-50.png"))));
//        }else{
//            showPassword.setVisible(false);
//        }
//    }
//
//    public void mouseReleased(MouseEvent mouseEvent) {
//        txtPassword.setText(showPassword.getText());
//        showPassword.setVisible(false);
//        imgEye.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/icons8-closed-eye-50.png"))));
//
//    }
//}
package lk.ijse.gdse72.ormfinalcoursework.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lk.ijse.gdse72.ormfinalcoursework.bo.BOFactory;
import lk.ijse.gdse72.ormfinalcoursework.bo.custom.UserBO;
import lk.ijse.gdse72.ormfinalcoursework.dto.UserDTO;

import java.io.IOException;
import java.util.Objects;

public class LoginPageController {

    @FXML
    private ImageView backgroundImg;

    @FXML
    private JFXButton btnLogin;

    @FXML
    private CheckBox chkRemember;

    @FXML
    private Pane detailPane;

    @FXML
    private Hyperlink fogotPassword;

    @FXML
    private ImageView imgEye;

    @FXML
    private Hyperlink signUpForFree;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXTextField txtUserName;

    @FXML
    private TextField showPassword;

    private final UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);

    private boolean showValidation = false;

    public void initialize() {
        changeFocus();
        addFieldListeners();
        setInitialStyles();
    }

    private void setInitialStyles() {
        txtUserName.setStyle("-fx-text-fill: white;");
        txtPassword.setStyle("-fx-text-fill: white;");
    }

    private void changeFocus() {
        txtUserName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtPassword.requestFocus();
            }
        });

        txtPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnLogin.fire();
            }
        });
    }

    private void addFieldListeners() {
        txtUserName.textProperty().addListener((observable, oldVal, newVal) -> {
            if (showValidation) {
                validateField(txtUserName, newVal.matches("^[a-zA-Z0-9._-]{3,}$"));
            }
        });

        txtPassword.textProperty().addListener((observable, oldVal, newVal) -> {
            if (showValidation) {
                validateField(txtPassword, newVal.length() >= 4);
            }
        });
    }

    private void validateField(TextField field, boolean isValid) {
        field.setStyle("-fx-text-fill: white; -fx-border-color: " + (isValid ? "green" : "red") + ";");
    }

    @FXML
    void loginOnAction(ActionEvent event) {
        String username = txtUserName.getText();
        String password = txtPassword.getText();

        showValidation = true;

        boolean validUsername = username.matches("^[a-zA-Z0-9._-]{3,}$");
        boolean validPassword = password.length() >= 4;

        validateField(txtUserName, validUsername);
        validateField(txtPassword, validPassword);

        if (!validUsername || !validPassword) {
            showErrorMessage("Please correct the highlighted fields.");
            return;
        }

        try {
            boolean isAuthenticated = userBO.authenticateUser(username, password);
            if (isAuthenticated) {
                UserDTO user = userBO.getUserByUsername(username);
                navigateToDashboard(user.getRole());
            } else {
                showErrorMessage("Invalid username or password!");
                validateField(txtUserName, false);
                validateField(txtPassword, false);
            }
        } catch (Exception e) {
            showErrorMessage("Login failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void fogotPasswordOnAction(ActionEvent event) {
        navigateToPage("/view/PasswordRecovery.fxml", "ForgetPassword");
    }

    @FXML
    void signUpForFreeOnAction(ActionEvent event) {
        navigateToPage("/view/SignUpForm.fxml", "SignUpForm");
    }

    private void navigateToDashboard(String role) {
        try {
            String fxmlPath;
            String title;

            if ("ADMIN".equalsIgnoreCase(role)) {
                fxmlPath = "/view/OwnerDashboard.fxml";
                title = "Owner Dashboard";
            } else if ("RECEPTIONIST".equalsIgnoreCase(role)) {
                fxmlPath = "/view/ReceptionDashboard.fxml";
                title = "Receptionist Dashboard";
            } else {
                showErrorMessage("Unauthorized role!");
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage currentStage = (Stage) btnLogin.getScene().getWindow();

            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle(title);
            currentStage.centerOnScreen();
            currentStage.show();

        } catch (IOException e) {
            showErrorMessage("Failed to load dashboard: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void navigateToPage(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage currentStage = (Stage) btnLogin.getScene().getWindow();

            Scene scene = new Scene(root);
            currentStage.setScene(scene);
            currentStage.setTitle(title);
            currentStage.centerOnScreen();
            currentStage.show();
        } catch (IOException e) {
            showErrorMessage("Failed to load page: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showErrorMessage(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    public void mousePressed(MouseEvent mouseEvent) {
        showPassword.setText(txtPassword.getText());
        if (!txtPassword.getText().isEmpty()) {
            showPassword.setVisible(true);
            imgEye.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/icons8-eye-50.png"))));
        } else {
            showPassword.setVisible(false);
        }
    }

    public void mouseReleased(MouseEvent mouseEvent) {
        txtPassword.setText(showPassword.getText());
        showPassword.setVisible(false);
        imgEye.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/icons8-closed-eye-50.png"))));
    }

    public void imgEyeMouseClick(MouseEvent mouseEvent) {

    }

    public void keepMeLoggingOnAction(ActionEvent actionEvent) {

    }
}
