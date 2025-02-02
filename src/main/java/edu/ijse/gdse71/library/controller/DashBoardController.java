package edu.ijse.gdse71.library.controller;

import edu.ijse.gdse71.library.util.CommonUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.ResourceBundle;

public class DashBoardController implements Initializable {

    @FXML
    private Button authorManageBtn;

    @FXML
    private Button bookManageBtn;

    @FXML
    private Button bookshelfManageBtn;

    @FXML
    private Button categoryManageBtn;

    @FXML
    private Label timeLbl;

    @FXML
    private Label timeShowLbl;

    @FXML
    private AnchorPane dashBoardAnchPane;

    @FXML
    private AnchorPane dashBoardBodyAnchPane;

    @FXML
    private AnchorPane dashBoardDateCountAnchPane;

    @FXML
    private AnchorPane dashBoardHeaderAnchPane;

    @FXML
    private AnchorPane dashBoardNavigationAnchPane;

    @FXML
    private Label dateLbl;

    @FXML
    private Label dateShowLbl;

    @FXML
    private Button fineManageBtn;

    @FXML
    private Button generateReportsBtn;

    @FXML
    private Label headerLbl;

    @FXML
    private Button loanManageBtn;

    @FXML
    private Button memberManageBtn;

    @FXML
    private Button paymentManageBtn;

    @FXML
    private Button publisherManageBtn;

    @FXML
    private Button reservationManageBtn;

    @FXML
    private Button returnManageBtn;

    @FXML
    private Button signOutBtn;

    @FXML
    private Label userIdLbl;

    @FXML
    private ImageView userImg;

    @FXML
    private Button userManageBtn;


    //------------------------------------------------------------------------------------------------------------------


    @FXML
    void authorManageBtnActionClicked(ActionEvent event) {
        CommonUtil.navigateTo(dashBoardBodyAnchPane,"/view/AuthorBodyView.fxml");

    }

    @FXML
    void bookManageBtnActionClicked(ActionEvent event) {
        CommonUtil.navigateTo(dashBoardBodyAnchPane,"/view/BookBodyView.fxml");
        new Alert(Alert.AlertType.INFORMATION, "Please add authors and categories before add other data...").show();

    }

    @FXML
    void bookshelfManageBtnActionClicked(ActionEvent event) {
        CommonUtil.navigateTo(dashBoardBodyAnchPane,"/view/BookshelfBodyView.fxml");

    }

    @FXML
    void categoryManageBtnActionClicked(ActionEvent event) {
        CommonUtil.navigateTo(dashBoardBodyAnchPane,"/view/CategoryBodyView.fxml");

    }

    @FXML
    void fineManageBtnActionClicked(ActionEvent event) {
        CommonUtil.navigateTo(dashBoardBodyAnchPane,"/view/FineBodyView.fxml");

    }

    @FXML
    void generateReportsBtnActionClicked(ActionEvent event) {
        CommonUtil.navigateTo(dashBoardBodyAnchPane,"/view/GenerateReportsView.fxml");

    }

    @FXML
    void loanManageBtnActionClicked(ActionEvent event) {
        CommonUtil.navigateTo(dashBoardBodyAnchPane,"/view/LoanBodyView.fxml");

    }

    @FXML
    void memberManageBtnActionClicked(ActionEvent event) {
        CommonUtil.navigateTo(dashBoardBodyAnchPane,"/view/MemberBodyView.fxml");
    }

    @FXML
    void paymentManageBtnActionClicked(ActionEvent event) {
        CommonUtil.navigateTo(dashBoardBodyAnchPane,"/view/PaymentBodyView.fxml");

    }

    @FXML
    void publisherManageBtnActionClicked(ActionEvent event) {
        CommonUtil.navigateTo(dashBoardBodyAnchPane,"/view/PublisherBodyView.fxml");

    }

    @FXML
    void reservationManageBtnActionClicked(ActionEvent event) {
        CommonUtil.navigateTo(dashBoardBodyAnchPane,"/view/ReservationBodyView.fxml");

    }

    @FXML
    void returnManageBtnActionClicked(ActionEvent event) {
        CommonUtil.navigateTo(dashBoardBodyAnchPane,"/view/ReturnBodyView.fxml");

    }

    @FXML
    void userManageBtnActionClicked(ActionEvent event) {
        CommonUtil.navigateTo(dashBoardBodyAnchPane,"/view/UserBodyView.fxml");

    }

    @FXML
    void signOutBtnActionClicked(ActionEvent event) {
        CommonUtil.navigateTo(dashBoardAnchPane,"/view/RoleChooseView.fxml");
    }

    //------------------------------------------------------------------------------------------------------------------

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateShowLbl.setText(" "+CommonUtil.date);
        CommonUtil.setTime(timeShowLbl);
        CommonUtil.navigateTo(dashBoardBodyAnchPane,"/view/MemberBodyView.fxml");
        if (CommonUtil.role.equals("Employee")) {
            userIdLbl.setText(CommonUtil.username);
            userImg.setImage(new Image(getClass().getResourceAsStream("/icon/user1Icon.png")));
            userManageBtn.setDisable(true);
            bookManageBtn.setDisable(true);
            generateReportsBtn.setDisable(true);
        }else if (CommonUtil.role.equals("Admin")){
            userIdLbl.setText(CommonUtil.username);
            userImg.setImage(new Image(getClass().getResourceAsStream("/icon/adminIcon.png")));

        }
    }

}

