package edu.ijse.gdse71.library.controller;

import edu.ijse.gdse71.library.bo.custom.BookBO;
import edu.ijse.gdse71.library.bo.impl.BookBOImpl;
import edu.ijse.gdse71.library.db.DBConnection;
import edu.ijse.gdse71.library.dto.BookWithDetailsDTO;
import edu.ijse.gdse71.library.model.BookModel;
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

public class BookReportController implements Initializable {

    @FXML
    private ComboBox<String> bookIdCombo;

    @FXML
    private Label bookIdLbl;

    @FXML
    private AnchorPane bookReportAnchPane;

    @FXML
    private AnchorPane bookReportHeaderAnchPane;

    @FXML
    private Label bookReportLbl;

    @FXML
    private Button generateReportBtn;

    @FXML
    private Button generateTodayReportBtn;

    @FXML
    private Label titleLbl;

    @FXML
    private Label titleShowLbl;

    BookBO bookBO = new BookBOImpl();


    @FXML
    void generateReportBtnActionClicked(ActionEvent event) {

        boolean isNullBook = bookIdCombo.getSelectionModel().isEmpty();
        if (isNullBook) {
            new Alert(Alert.AlertType.ERROR, "Please select the book Id..!").show();
        }else{
            String selectedLoanId = bookIdCombo.getSelectionModel().getSelectedItem();

            try {
                JasperReport jasperReport = JasperCompileManager.compileReport(
                        getClass()
                                .getResourceAsStream("/report/BookReport.jrxml"
                                ));

                Connection connection = DBConnection.getInstance().getConnection();

                Map<String, Object> parameters = new HashMap<>();

                parameters.put("P_Date", LocalDate.now().toString());
                parameters.put("P_Book_Id",selectedLoanId);

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
                            .getResourceAsStream("/report/BookTodayReport.jrxml"
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
    void bookIdComboActionClicked(ActionEvent event) throws SQLException {
        String selectedBookId = bookIdCombo.getSelectionModel().getSelectedItem();
        BookWithDetailsDTO bookDTO = bookBO.findById(selectedBookId);
        if (bookDTO != null) {

            titleShowLbl.setText(bookDTO.getTitle());

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

    private void loadBookId() throws SQLException {
        ArrayList<String> bookIds = bookBO.getAllIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(bookIds);
        bookIdCombo.setItems(observableList);
    }


    private void refreshPage() throws SQLException {
        loadBookId();
        bookIdCombo.getSelectionModel().clearSelection();
        titleShowLbl.setText("");

    }

}
