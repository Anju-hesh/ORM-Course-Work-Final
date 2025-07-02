package lk.ijse.gdse72.ormfinalcoursework.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import lk.ijse.gdse72.ormfinalcoursework.bo.BOFactory;
import lk.ijse.gdse72.ormfinalcoursework.bo.custom.PatientBO;
import lk.ijse.gdse72.ormfinalcoursework.bo.custom.TherapistAvailabilityBO;
import lk.ijse.gdse72.ormfinalcoursework.dto.AvailabilityChartChart;
import lk.ijse.gdse72.ormfinalcoursework.dto.TherapistAvailabilityDTO;
import lk.ijse.gdse72.ormfinalcoursework.servise.EnteredUserId;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ReceptionDashboardController implements Initializable {

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
    private Label lblPassword;

    @FXML
    private PieChart pieChart;

    @FXML
    private BarChart<String, Number> barChart;

    private final PatientBO patientBO = (PatientBO) BOFactory.getInstance().getBO(BOFactory.BOType.PATIENT);
    private final TherapistAvailabilityBO therapistAvailabilityBO = (TherapistAvailabilityBO) BOFactory.getInstance().getBO(BOFactory.BOType.AVAILABILITY);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblPassword.setText(EnteredUserId.getLoggedInUserId());
        try {
            loadPieChart();
            loadAvailableTherapistBarChart();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAvailableTherapistBarChart() {
        try {
            List<AvailabilityChartChart> availabilityList = therapistAvailabilityBO.getAllAvailabilitySummary();

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Available Slots");

            for (AvailabilityChartChart dto : availabilityList) {
                series.getData().add(new XYChart.Data<>(dto.getTherapistName(), dto.getAvailableSlotCount()));
            }

            barChart.getData().clear();
            barChart.getData().add(series);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPieChart() throws Exception {

        int maleCount = patientBO.countByGender("Male");;
        int femaleCount = patientBO.countByGender("Female");

        ObservableList<PieChart.Data> data = FXCollections.observableArrayList(
                new PieChart.Data("Male", maleCount),
                new PieChart.Data("Female", femaleCount)
        );

        pieChart.setData(data);
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
        try {
            mainAnchorPane.getChildren().clear();
            mainAnchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("/view/ReceptionDashboard.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
