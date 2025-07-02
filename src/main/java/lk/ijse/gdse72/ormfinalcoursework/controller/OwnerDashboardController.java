package lk.ijse.gdse72.ormfinalcoursework.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import lk.ijse.gdse72.ormfinalcoursework.bo.BOFactory;
import lk.ijse.gdse72.ormfinalcoursework.bo.custom.OwnerDashboardBo;
import lk.ijse.gdse72.ormfinalcoursework.bo.custom.impl.OwnerDashboardBOImpl;
import lk.ijse.gdse72.ormfinalcoursework.servise.EnteredUserId;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class OwnerDashboardController implements Initializable {

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
    private Label lblPassword;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private final OwnerDashboardBo ownerDashboardBO = new OwnerDashboardBOImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblPassword.setText(EnteredUserId.getLoggedInUserId());
        loadBarChartData();
    }

    private void loadBarChartData() {
        try {
            Map<String, Integer> overviewData = ownerDashboardBO.getOverviewData();

            barChart.getData().clear();
            xAxis.setCategories(FXCollections.observableArrayList(overviewData.keySet()));

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Overview");

            for (Map.Entry<String, Integer> entry : overviewData.entrySet()) {
                series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            }
            barChart.getData().add(series);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
        // Optionally implement report page navigation
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

    public void backOnAction(MouseEvent mouseEvent) throws IOException {
        try {
            mainAnchorPane.getChildren().clear();
            mainAnchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/OwnerDashboard.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void therapistAvailabilityOnAction(ActionEvent actionEvent) {
        navigateToPage("/view/TherapistAvailabilty.fxml");
    }
}
