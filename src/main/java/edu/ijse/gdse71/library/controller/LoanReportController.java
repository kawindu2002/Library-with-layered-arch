package edu.ijse.gdse71.library.controller;

import edu.ijse.gdse71.library.bo.BOFactory;
import edu.ijse.gdse71.library.bo.custom.LoanBO;
import edu.ijse.gdse71.library.bo.custom.impl.BookBOImpl;
import edu.ijse.gdse71.library.bo.custom.impl.LoanBOImpl;
import edu.ijse.gdse71.library.db.DBConnection;
import edu.ijse.gdse71.library.dto.LoanDTO;
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

public class LoanReportController implements Initializable {

    @FXML
    private Button generateReportBtn;

    @FXML
    private Button generateTodayReportBtn;

    @FXML
    private Label loanDateLbl;

    @FXML
    private Label loanDateShowLbl;

    @FXML
    private ComboBox<String> loanIdCombo;

    @FXML
    private Label loanIdLbl;

    @FXML
    private AnchorPane loanReportAnchPane;

    @FXML
    private AnchorPane loanReportHeaderAnchPane;

    @FXML
    private Label loanReportLbl;

    LoanBOImpl loanBO = (LoanBOImpl) BOFactory.getInstance().getBO(BOFactory.BOTypes.LOAN);

    @FXML
    void generateReportBtnActionClicked(ActionEvent event) {

        boolean isNullLoan = loanIdCombo.getSelectionModel().isEmpty();
        if (isNullLoan) {
            new Alert(Alert.AlertType.ERROR, "Please select the loan Id..!").show();
        }else{
            String selectedLoanId = loanIdCombo.getSelectionModel().getSelectedItem();

            try {
                JasperReport jasperReport = JasperCompileManager.compileReport(
                        getClass()
                                .getResourceAsStream("/report/LoanReport.jrxml"
                                ));

                Connection connection = DBConnection.getInstance().getConnection();

                Map<String, Object> parameters = new HashMap<>();

                parameters.put("P_Date", LocalDate.now().toString());
                parameters.put("P_Loan_Id",selectedLoanId);

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
                            .getResourceAsStream("/report/LoanTodayReport.jrxml"
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
    void loanIdComboActionClicked(ActionEvent event) throws SQLException {
        String selectedLoanId = loanIdCombo.getSelectionModel().getSelectedItem();
        LoanDTO loanDTO = loanBO.findById(selectedLoanId);
        if (loanDTO != null) {

            loanDateShowLbl.setText(String.valueOf(loanDTO.getLoanDate()));

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

    private void loadLoanId() throws SQLException {
        ArrayList<String> loanIds = loanBO.getAllIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(loanIds);
        loanIdCombo.setItems(observableList);
    }

    private void refreshPage() throws SQLException {
        loadLoanId();
        loanIdCombo.getSelectionModel().clearSelection();
        loanDateShowLbl.setText("");

    }


}
