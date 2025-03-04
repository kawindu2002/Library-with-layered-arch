package edu.ijse.gdse71.library.controller;

import edu.ijse.gdse71.library.bo.BOFactory;
import edu.ijse.gdse71.library.bo.custom.PaymentBO;
import edu.ijse.gdse71.library.bo.custom.impl.PaymentBOImpl;
import edu.ijse.gdse71.library.db.DBConnection;
import edu.ijse.gdse71.library.dto.PaymentDTO;
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

public class PaymentReportController implements Initializable {

    @FXML
    private Button generateReportBtn;

    @FXML
    private Button generateTodayReportBtn;

    @FXML
    private Label paymentDateLbl;

    @FXML
    private Label paymentDateShowLbl;

    @FXML
    private ComboBox<String> paymentIdCombo;

    @FXML
    private Label paymentIdLbl;

    @FXML
    private AnchorPane paymentReportAnchPane;

    @FXML
    private AnchorPane paymentReportHeaderAnchPane;

    @FXML
    private Label paymentReportLbl;

    PaymentBOImpl paymentBO = (PaymentBOImpl) BOFactory.getInstance().getBO(BOFactory.BOTypes.PAYMENT);

    @FXML
    void generateReportBtnActionClicked(ActionEvent event) {

        boolean isNullPayment = paymentIdCombo.getSelectionModel().isEmpty();
        if (isNullPayment) {
            new Alert(Alert.AlertType.ERROR, "Please select the payment Id..!").show();
        }else{
            String selectedPaymentId = paymentIdCombo.getSelectionModel().getSelectedItem();

            try {
                JasperReport jasperReport = JasperCompileManager.compileReport(
                        getClass()
                                .getResourceAsStream("/report/PaymentReport.jrxml"
                                ));

                Connection connection = DBConnection.getInstance().getConnection();

                Map<String, Object> parameters = new HashMap<>();

                parameters.put("P_Date", LocalDate.now().toString());
                parameters.put("P_Payment_Id",selectedPaymentId);

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
                            .getResourceAsStream("/report/PaymentTodayReport.jrxml"
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
    void paymentIdComboActionClicked(ActionEvent event) throws SQLException {
        String selectedPaymentId = paymentIdCombo.getSelectionModel().getSelectedItem();
        PaymentDTO paymentDTO = paymentBO.findById(selectedPaymentId);
        if (paymentDTO != null) {

            paymentDateShowLbl.setText(String.valueOf(paymentDTO.getPaymentDate()));

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

    private void loadPaymentId() throws SQLException {
        ArrayList<String> paymentIds = paymentBO.getAllIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(paymentIds);
        paymentIdCombo.setItems(observableList);
    }

    private void refreshPage() throws SQLException {
        loadPaymentId();
        paymentIdCombo.getSelectionModel().clearSelection();
        paymentDateShowLbl.setText("");

    }


}
