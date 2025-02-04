package edu.ijse.gdse71.library.controller;

import edu.ijse.gdse71.library.bo.custom.UserBO;
import edu.ijse.gdse71.library.bo.custom.impl.UserBOImpl;
import edu.ijse.gdse71.library.db.DBConnection;
import edu.ijse.gdse71.library.dto.UserDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class UserReportController implements Initializable {

    @FXML
    private Button generateReportBtn;

    @FXML
    private ComboBox<String> userIdCombo;

    @FXML
    private Label userIdLbl;

    @FXML
    private Label userNameLbl;

    @FXML
    private Label userNameShowLbl;

    @FXML
    private AnchorPane userReportAnchPane;

    @FXML
    private AnchorPane userReportHeaderAnchPane;

    @FXML
    private Label userReportLbl;

    UserBO userBO = new UserBOImpl();

    @FXML
    void generateReportBtnActionClicked(ActionEvent event) {

        boolean isNullUser = userIdCombo.getSelectionModel().isEmpty();
        if (isNullUser) {
            new Alert(Alert.AlertType.ERROR, "Please select the user Id..!").show();
        }else{
            String selectedUserId = userIdCombo.getSelectionModel().getSelectedItem();

            try {
                JasperReport jasperReport = JasperCompileManager.compileReport(
                        getClass()
                                .getResourceAsStream("/report/UserReport.jrxml"
                                ));

                Connection connection = DBConnection.getInstance().getConnection();

                Map<String, Object> parameters = new HashMap<>();

                parameters.put("P_Date", LocalDate.now().toString());
                parameters.put("P_User_Id",selectedUserId);

                JasperPrint jasperPrint = JasperFillManager.fillReport(
                        jasperReport,
                        parameters,
                        connection
                );

                JasperViewer.viewReport(jasperPrint, false);
                refreshPage();

            } catch (JRException e) {
                new Alert(Alert.AlertType.ERROR, "Fail to generate report...!").show();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Connection error...!").show();
            }
        }
    }


    public void generateTodayReportBtnActionClicked(ActionEvent actionEvent) {
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass()
                            .getResourceAsStream("/report/UserTodayReport.jrxml")
            );

            Connection connection = DBConnection.getInstance().getConnection();

            Map<String, Object> parameters = new HashMap<>();

            parameters.put("P_Date", LocalDate.now().toString());

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    connection
            );

            JasperViewer.viewReport(jasperPrint, false);
            refreshPage();

        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to generate report...!").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "DB Connection error...!").show();
        }

    }


    @FXML
    void userIdComboActionClicked(ActionEvent event) throws SQLException {
        String selectedUserId = userIdCombo.getSelectionModel().getSelectedItem();
        UserDTO userDTO = userBO.findById(selectedUserId);

        if (userDTO != null) {

            userNameShowLbl.setText(userDTO.getName());

        }
    }

    //------------------------------------------------------------------------------------------------------------------

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            refreshPage();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to refresh page").show();
        }
    }

    private void loadUserId() throws SQLException {
        ArrayList<String> userIds = userBO.getAllIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(userIds);
        userIdCombo.setItems(observableList);
    }

    private void refreshPage() throws SQLException {
        loadUserId();
        userIdCombo.getSelectionModel().clearSelection();
        userNameLbl.setText("");

    }
}

