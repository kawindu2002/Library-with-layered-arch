package edu.ijse.gdse71.library.controller;

import edu.ijse.gdse71.library.bo.BOFactory;
import edu.ijse.gdse71.library.bo.custom.MemberBO;
import edu.ijse.gdse71.library.bo.custom.impl.MemberBOImpl;
import edu.ijse.gdse71.library.db.DBConnection;
import edu.ijse.gdse71.library.dto.MemberDTO;
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

public class MemberReportController implements Initializable {

    @FXML
    private Button generateReportBtn;

    @FXML
    private ComboBox<String> memberIdCombo;

    @FXML
    private Label memberIdLbl;

    @FXML
    private Button generateTodayReportBtn;

    @FXML
    private Label memberNameLbl;

    @FXML
    private Label memberNameShowLbl;

    @FXML
    private AnchorPane memberReportAnchPane;

    @FXML
    private AnchorPane memberReportHeaderAnchPane;

    @FXML
    private Label memberReportLbl;

    MemberBOImpl memberBO = (MemberBOImpl) BOFactory.getInstance().getBO(BOFactory.BOTypes.MEMBER);


    @FXML
    void generateReportBtnActionClicked(ActionEvent event) {

        boolean isNullMember = memberIdCombo.getSelectionModel().isEmpty();
        if (isNullMember) {
            new Alert(Alert.AlertType.ERROR, "Please select the member Id..!").show();
        }else{
            String selectedMemberId = memberIdCombo.getSelectionModel().getSelectedItem();

            try {
                JasperReport jasperReport = JasperCompileManager.compileReport(
                        getClass()
                                .getResourceAsStream("/report/MemberReport.jrxml"
                                ));

                Connection connection = DBConnection.getInstance().getConnection();

                Map<String, Object> parameters = new HashMap<>();

                parameters.put("P_Date", LocalDate.now().toString());
                parameters.put("P_Member_Id",selectedMemberId);

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
                            .getResourceAsStream("/report/MemberTodayReport.jrxml"
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
    void memberIdComboActionClicked(ActionEvent event) throws SQLException {
        String selectedMemberId = memberIdCombo.getSelectionModel().getSelectedItem();
        MemberDTO memberDTO = memberBO.findById(selectedMemberId);
        if (memberDTO != null) {

            memberNameShowLbl.setText(memberDTO.getName());

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
        ArrayList<String> memberIds = memberBO.getAllIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(memberIds);
        memberIdCombo.setItems(observableList);
    }

    private void refreshPage() throws SQLException {
        loadUserId();
        memberIdCombo.getSelectionModel().clearSelection();
        memberNameShowLbl.setText("");

    }
}

