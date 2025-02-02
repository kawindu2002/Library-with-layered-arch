package edu.ijse.gdse71.library.controller;

import edu.ijse.gdse71.library.util.CommonUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class GenerateReportsController {

    @FXML
    private ImageView bookImg;

    @FXML
    private AnchorPane bookReportsAnchpane;

    @FXML
    private Button bookReportsBtn;

    @FXML
    private Label bookReportsLbl;

    @FXML
    private ImageView dollarImg;

    @FXML
    private ImageView fineImg;

    @FXML
    private AnchorPane fineReportsAnchpane;

    @FXML
    private Button fineReportsBtn;

    @FXML
    private Label fineReportsLbl;

    @FXML
    private AnchorPane generateReportsAnchPane;

    @FXML
    private AnchorPane generateReportsHeaderAnchPane;

    @FXML
    private Label generateReportsManageLbl;

    @FXML
    private ImageView loanImg;

    @FXML
    private AnchorPane loanReportsAnchpane;

    @FXML
    private Button loanReportsBtn;

    @FXML
    private Label loanReportsLbl;

    @FXML
    private ImageView memberImg;

    @FXML
    private AnchorPane memberReportsAnchpane;

    @FXML
    private Button memberReportsBtn;

    @FXML
    private Label memberReportsLbl;

    @FXML
    private AnchorPane paymentReportsAnchpane;

    @FXML
    private Button paymentReportsBtn;

    @FXML
    private Label paymentReportsLbl;

    @FXML
    private ImageView reservationImg;

    @FXML
    private AnchorPane reservationReportsAnchpane;

    @FXML
    private Button reservationReportsBtn;

    @FXML
    private Label reservationReportsLbl;

    @FXML
    private ImageView userImg;

    @FXML
    private AnchorPane userReportsAnchpane;

    @FXML
    private Button userReportsBtn;

    @FXML
    private Label userReportsLbl;


    @FXML
    void bookReportsBtnActionClicked(ActionEvent event) {
        CommonUtil.getPopup("/view/BookReportView.fxml",bookReportsBtn);

    }

    @FXML
    void fineReportsBtnActionClicked(ActionEvent event) {
        CommonUtil.getPopup("/view/FineReportView.fxml",fineReportsBtn);

    }


    @FXML
    void loanReportsBtnActionClicked(ActionEvent event) {
        CommonUtil.getPopup("/view/LoanReportView.fxml",loanReportsBtn);

    }

    @FXML
    void memberReportsBtnActionClicked(ActionEvent event) {
        CommonUtil.getPopup("/view/MemberReportView.fxml",memberReportsBtn);

    }

    @FXML
    void paymentReportsBtnActionClicked(ActionEvent event) {
        CommonUtil.getPopup("/view/PaymentReportView.fxml",paymentReportsBtn);

    }

    @FXML
    void reservationReportsBtnActionClicked(ActionEvent event) {
        CommonUtil.getPopup("/view/ReservationReportView.fxml",reservationReportsBtn);

    }

    @FXML
    void userReportsBtnActionClicked(ActionEvent event) {
        CommonUtil.getPopup("/view/UserReportView.fxml",userReportsBtn);
    }

}
