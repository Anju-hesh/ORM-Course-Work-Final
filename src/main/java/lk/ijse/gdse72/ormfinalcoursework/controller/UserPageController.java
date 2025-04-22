package lk.ijse.gdse72.ormfinalcoursework.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lk.ijse.gdse72.ormfinalcoursework.bo.BOFactory;
import lk.ijse.gdse72.ormfinalcoursework.bo.custom.UserBO;
import lk.ijse.gdse72.ormfinalcoursework.dto.UserDTO;
import lk.ijse.gdse72.ormfinalcoursework.dto.tm.UserTM;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserPageController {

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXComboBox<String> cmbRole;

    @FXML
    private TableColumn<UserTM, String> colRole;

    @FXML
    private TableColumn<UserTM, String> colUserId;

    @FXML
    private TableColumn<UserTM, String> colUsername;

    @FXML
    private AnchorPane subAnchor;

    @FXML
    private TableView<UserTM> tblUsers;

    @FXML
    private JFXTextField txtUserId;

    @FXML
    private JFXTextField txtUsername;

    private final UserBO USERBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOType.USER);


    public void initialize() {


        try {
            visibleData();
            refrashPage();
            loadTableData();
            changeFocus();

            String nextuserID = USERBO.getNextuserId();

            txtUserId.setText(nextuserID);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        cmbRole.getItems().addAll("ADMIN" , "RECEPTIONIST");

    }

    public void changeFocus() {

        txtUsername.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                cmbRole.requestFocus();
            }
        });

        cmbRole.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                btnUpdate.fire();
            }
        });
    }

    public void visibleData() {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
    }

    public void refrashPage()  {
//        txtUserId.setText("");
        String nextuserID = null;
        try {
            nextuserID = USERBO.getNextuserId();
        }catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        txtUserId.setText(nextuserID);
        txtUsername.setText("");
        cmbRole.setValue(null);
    }

    public void loadTableData() throws Exception {
        ArrayList<UserDTO> userDtos = USERBO.getAllUser();
        ObservableList<UserTM> userTMS = FXCollections.observableArrayList();

        for (UserDTO userDto : userDtos) {
            UserTM userTM = new UserTM(
                    userDto.getUserId(),
                    userDto.getUserName(),
                    userDto.getPassword(),
                    userDto.getRole()
            );
            userTMS.add(userTM);
        }
        tblUsers.setItems(userTMS);
    }

    @FXML
    void clearOnAction(ActionEvent event) {
        try {
            refrashPage();
            loadTableData();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void deleteOnAction(ActionEvent event) {
        UserTM selectedUser = tblUsers.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {

            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Deletion");
            confirmAlert.setHeaderText("Are you sure you want to delete this user?");
            confirmAlert.setContentText("Please enter your password to confirm.");

            PasswordField passwordField = new PasswordField();
            passwordField.setPromptText("Password");

            VBox dialogPaneContent = new VBox();
            dialogPaneContent.getChildren().addAll(new Label("Password:"), passwordField);
            confirmAlert.getDialogPane().setContent(dialogPaneContent);

            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    String password = passwordField.getText();
                    String userId = txtUserId.getText();
                    try {
                        boolean confirm = USERBO.confirmation(userId,password);
                        if (confirm) {
                            boolean isDeleted = USERBO.deleteUser(selectedUser.getUserId());
                            if (isDeleted) {
                                new Alert(Alert.AlertType.INFORMATION, "User Deleted Successfully!").show();
                                loadTableData();
                                refrashPage();
                            } else {
                                new Alert(Alert.AlertType.ERROR, "Failed to Delete User!").show();
                            }
                        }else{
                            new Alert(Alert.AlertType.WARNING, "Invalid password try again....!").show();
                        }
                    } catch (SQLException | ClassNotFoundException e) {
                        new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
                    } catch (Exception e) {
                        new Alert(Alert.AlertType.ERROR, "Something went wrong: " + e.getMessage()).show();
                    }
                }
            });
        }else {
            new Alert(Alert.AlertType.WARNING, "Please select a User to delete!").show();
        }
    }

    @FXML
    void searchOnAction(ActionEvent event) {
        String userId = txtUserId.getText().trim();

        if (userId.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please enter a User ID").show();
            return;
        }

        try {
            UserDTO userDto = USERBO.searchUser(userId);
            if (userDto != null) {

                txtUsername.setText(userDto.getUserName());
                cmbRole.setValue(userDto.getRole());

                ObservableList<UserTM> userTMS = FXCollections.observableArrayList();
                userTMS.add(new UserTM(
                        userDto.getUserId(),
                        userDto.getUserName(),
                        userDto.getPassword(),
                        userDto.getRole()
                ));
                tblUsers.setItems(userTMS);
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            e.printStackTrace();
        }
    }
    @FXML
    void updateOnAction(ActionEvent event) {
        String userId = txtUserId.getText();
        String userName = txtUsername.getText();
        String role = cmbRole.getValue();

        if (!userId.isEmpty() && !userName.isEmpty() && !role.isEmpty()) {

            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Updation");
            confirmAlert.setHeaderText("Are you sure you want to Update this user Details?");
            confirmAlert.setContentText("Please enter your password to confirm.");

            PasswordField passwordField = new PasswordField();
            passwordField.setPromptText("Password");

            VBox dialogPaneContent = new VBox();
            dialogPaneContent.getChildren().addAll(new Label("Password:"), passwordField);
            confirmAlert.getDialogPane().setContent(dialogPaneContent);

            confirmAlert.showAndWait().ifPresent(response -> {

                if (response == ButtonType.OK) {
                    String userpassword = passwordField.getText();
                    String userIdUp = txtUserId.getText();

                    try {
                        boolean confirm = USERBO.confirmation(userIdUp,userpassword);
                        if (confirm) {
                            UserDTO userDto = new UserDTO(userId, userName, userpassword ,role);
                            boolean isUpdated = USERBO.updateUser(userDto);
                            if (isUpdated) {
                                new Alert(Alert.AlertType.INFORMATION, "User Update Successfully!").show();
                                loadTableData();
                                refrashPage();
                            } else {
                                new Alert(Alert.AlertType.ERROR, "Failed to Update User!").show();
                            }
                        }else{
                            new Alert(Alert.AlertType.ERROR, "Invalid Password Please Try Again....!").show();
                        }
                    } catch (SQLException | ClassNotFoundException e) {
                        new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
                    } catch (Exception e) {
                        new Alert(Alert.AlertType.ERROR, "load Table Exception" + e.getMessage()).show();
                    }

                }
            });
        }else {
            new Alert(Alert.AlertType.WARNING, "Please fill out all fields!").show();
        }
    }

    public void cmbOnAction(ActionEvent actionEvent) {
        String selectedRole = cmbRole.getValue();

//        if (selectedRole != null) {
//            showAlert("Role selected: " + selectedRole, Alert.AlertType.INFORMATION);
//        }
    }

    public void selectTableOnAction(MouseEvent mouseEvent) {
        UserTM selectedUser = tblUsers.getSelectionModel().getSelectedItem();
//        btnClear.setDisable(false);

        if (selectedUser != null) {

            txtUserId.setText(selectedUser.getUserId());
            txtUsername.setText(selectedUser.getUserName());
            cmbRole.setValue(selectedUser.getRole());
        }
    }
}
