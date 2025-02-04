package edu.ijse.gdse71.library.controller;

import edu.ijse.gdse71.library.bo.custom.FineBO;
import edu.ijse.gdse71.library.bo.impl.FineBOImpl;
import edu.ijse.gdse71.library.db.DBConnection;
import edu.ijse.gdse71.library.dto.FineDTO;
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

public class FineReportController implements Initializable {

    @FXML
    private Button generateTodayReportBtn;

    @FXML
    private Label fineDateLbl;

    @FXML
    private Label fineDateShowLbl;

    @FXML
    private ComboBox<String> fineIdCombo;

    @FXML
    private Label fineIdLbl;

    @FXML
    private AnchorPane fineReportAnchPane;

    @FXML
    private AnchorPane fineReportHeaderAnchPane;

    @FXML
    private Label fineReportLbl;

    @FXML
    private Button generateReportBtn;

    FineBO fineBO = new FineBOImpl();


    @FXML
    void generateReportBtnActionClicked(ActionEvent event) {

        boolean isNullFine = fineIdCombo.getSelectionModel().isEmpty();
        if (isNullFine) {
            new Alert(Alert.AlertType.ERROR, "Please select the fine Id..!").show();
        }else{
            String selectedFineId = fineIdCombo.getSelectionModel().getSelectedItem();

            try {
                JasperReport jasperReport = JasperCompileManager.compileReport(
                        getClass()
                                .getResourceAsStream("/report/FineReport.jrxml"
                                ));

                Connection connection = DBConnection.getInstance().getConnection();

                Map<String, Object> parameters = new HashMap<>();

                parameters.put("P_Date", LocalDate.now().toString());
                parameters.put("P_Fine_Id",selectedFineId);

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
                            .getResourceAsStream("/report/FineTodayReport.jrxml"
                            ));

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
    void fineIdComboActionClicked(ActionEvent event) throws SQLException {
        String selectedFineId = fineIdCombo.getSelectionModel().getSelectedItem();
        FineDTO fineDTO = fineBO.findById(selectedFineId);

        if (fineDTO != null) {

            fineDateShowLbl.setText(String.valueOf(fineDTO.getFineDate()));

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

    private void loadFineId() throws SQLException {
        ArrayList<String> fineIds = fineBO.getAllIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(fineIds);
        fineIdCombo.setItems(observableList);
    }

    private void refreshPage() throws SQLException {
        loadFineId();
        fineIdCombo.getSelectionModel().clearSelection();
        fineDateShowLbl.setText("");

    }
}
