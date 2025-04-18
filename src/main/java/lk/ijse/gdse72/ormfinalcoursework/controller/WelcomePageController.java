package lk.ijse.gdse72.ormfinalcoursework.controller;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.gdse72.ormfinalcoursework.servise.LoadingThread;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomePageController extends Application implements Initializable {

    @FXML
    private ImageView imgWelcomeBackground;

    @FXML
    private Label lblLoading;

    @FXML
    private Label lblPercentage;

    @FXML
    private Rectangle rectangleOn;

    @FXML
    private Rectangle rectangleUnder;

    public void initialize(URL url , ResourceBundle rsb)  {  // I want to run this when I open this
        LoadingThread task = new LoadingThread();
        task.progressProperty().addListener((observable, oldValue, newValue) -> {
            String percentage = String.format("%.0f", newValue.doubleValue() * 100); // If didn't formated the percentage label was ugly
            lblPercentage.setText(percentage + " % ");
            rectangleOn.setWidth(rectangleUnder.getWidth() * newValue.doubleValue());

            if(newValue.doubleValue() == 1.0) {
                Window window = rectangleUnder.getScene().getWindow();
                Stage stage = (Stage) window;
                stage.close();

                try {
                    start(new Stage());  // Aluthen stage ekak ganimt
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        new Thread(task).start();
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
            Scene scene = new Scene(root);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/icons/icon-TaskBar.png")));
            stage.setScene(scene);
            stage.setTitle("Apex Building Solution");
            //    stage.initStyle(StageStyle.DECORATED);
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
