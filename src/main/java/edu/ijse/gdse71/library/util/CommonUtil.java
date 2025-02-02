package edu.ijse.gdse71.library.util;

import edu.ijse.gdse71.library.dto.AuthorDetailsDTO;
import edu.ijse.gdse71.library.dto.CategoryDetailsDTO;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CommonUtil {

    // store regex patterns
    public static String namePattern = "^[A-Za-z\\. ]+$";
    public static String contactPattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";
    public static String mailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    public static String pricePattern =  "^\\d+(\\.\\d{2})?$";
    public static String datePattern = "^(?:(?:19|20)\\d\\d)-(?:0[1-9]|1[0-2])-(?:0[1-9]|[12][0-9]|3[01])$";
    public static String zeroPositivePattern = "^\\d+$";


    // store data
    public static String date =  LocalDate.now().toString();

    // store essentials for permission manage
    public static String username = "";
    public static String role = "";
    public static String password = "";

    // store border color changes
    public static String defaultColorBorder = ";-fx-border-color: #000000;";
    public static String redColorBorder = ";-fx-border-color: red;";

    // store essentials for book manage
    public static String selectedBookId = "";
    public static ArrayList<CategoryDetailsDTO> categoryDetailsDTOS = new ArrayList<>();
    public static ArrayList<AuthorDetailsDTO> authorDetailsDTOS = new ArrayList<>();


    // use to navigate between anchor panes
    public static void navigateTo(AnchorPane anch,String fxmlPath) {
        try {
            anch.getChildren().clear();
            AnchorPane load = FXMLLoader.load(CommonUtil.class.getResource(fxmlPath));
            anch.getChildren().add(load);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load panel!").show();
            e.printStackTrace();
        }
    }


    //Manage update & delete Btn user permissions

    public static void userPermissionManage(Button updateBtn ,Button deletebtn){
        if (CommonUtil.role.equals("Employee")) {
            updateBtn.setDisable(true);
            deletebtn.setDisable(true);
        }
    }

    // use to return categoryDetailsDTOS
    public static ArrayList<CategoryDetailsDTO> getCategoryDetailsDTO(){
        return categoryDetailsDTOS;
    }

    // use to return authorDetailsDTOS
    public static ArrayList<AuthorDetailsDTO> getAuthorDetailsDTO(){
        return authorDetailsDTOS;
    }


    // set time to dash board time label
    public static void setTime(Label timeShowLbl){
        Timeline time = new Timeline(
                new KeyFrame(Duration.ZERO,e -> {
                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    timeShowLbl.setText("  "+LocalDateTime.now().format(timeFormatter));

                }),new KeyFrame(Duration.seconds(1)));
        time.setCycleCount(Timeline.INDEFINITE);
        time.play();

    }


    //popup window
    public static void getPopup(String fxml,Button btn) {
        try {
            // Load the mail dialog from FXML file
            FXMLLoader loader = new FXMLLoader(CommonUtil.class.getResource(fxml));
            Parent load = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.getIcons().add(new Image(CommonUtil.class.getResourceAsStream("/icon/usercardIcon.png")));

            // Set window as modal
            stage.initModality(Modality.APPLICATION_MODAL);

            Window underWindow = btn.getScene().getWindow();
            stage.initOwner(underWindow);

            stage.showAndWait();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load window..!");
            e.printStackTrace();
        }
    }
}

