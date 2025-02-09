package edu.ijse.gdse71.library.controller;

import edu.ijse.gdse71.library.bo.BOFactory;
import edu.ijse.gdse71.library.bo.custom.MemberBO;
import edu.ijse.gdse71.library.bo.custom.PaymentBO;
import edu.ijse.gdse71.library.bo.custom.impl.MemberBOImpl;
import edu.ijse.gdse71.library.bo.custom.impl.PaymentBOImpl;
import edu.ijse.gdse71.library.dto.MemberDTO;
import edu.ijse.gdse71.library.dto.PaymentDTO;
import edu.ijse.gdse71.library.dto.tm.PaymentTM;
import edu.ijse.gdse71.library.util.CommonUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class PaymentBodyController implements Initializable {

    @FXML
    private Label dateShowLbl;

    @FXML
    private TableColumn<PaymentTM, String> memberIdCol;

    @FXML
    private ComboBox<String> memberIdCombo;

    @FXML
    private Label memberIdLbl;

    @FXML
    private Label memberNameLbl;

    @FXML
    private Label memberNameShowLbl;

    @FXML
    private AnchorPane paymentBodyAnchPane;

    @FXML
    private TableView<PaymentTM> paymentBodyTbl;

    @FXML
    private TableColumn<PaymentTM, Date> paymentDateCol;

    @FXML
    private Label paymentDateLbl;

    @FXML
    private AnchorPane paymentHeaderAnchPane;

    @FXML
    private TableColumn<PaymentTM, String> paymentIdCol;

    @FXML
    private Label paymentIdLbl;

    @FXML
    private Label paymentIdShowLbl;

    @FXML
    private Label paymentManageLbl;

    @FXML
    private Button paymentResetBtn;

    @FXML
    private Button paymentSaveBtn;

    @FXML
    private Button paymentUpdateBtn;

    @FXML
    private Button paymentDeleteBtn;

    @FXML
    private TableColumn<PaymentTM, Double> priceCol;

    @FXML
    private Label priceLbl;

    @FXML
    private TextField priceTxt;

    @FXML
    private TableColumn<PaymentTM, String> purposeCol;

    @FXML
    private ComboBox<String> purposeCombo;

    @FXML
    private Label purposeLbl;

    @FXML
    private TableColumn<PaymentTM, String> userIdCol;

    MemberBOImpl memberBO = (MemberBOImpl) BOFactory.getInstance().getBO(BOFactory.BOTypes.MEMBER);
    PaymentBOImpl paymentBO = (PaymentBOImpl) BOFactory.getInstance().getBO(BOFactory.BOTypes.PAYMENT);


    //------------------------------------------------------------------------------------------------------------------


    @FXML
    void paymentDeleteBtnActionClicked(ActionEvent event) throws SQLException {
        String paymentId = paymentIdShowLbl.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = paymentBO.delete(paymentId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Payment deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete payment...!").show();
            }
        }
    }

    @FXML
    void paymentResetBtnActionClicked(ActionEvent event) throws SQLException {
        refreshPage();
        CommonUtil.navigateTo(paymentBodyAnchPane,"/view/PaymentBodyView.fxml");

    }

    @FXML
    void paymentSaveBtnActionClicked(ActionEvent event) throws SQLException {
        PaymentDTO paymentDTO = verifySaveUpdate();
        if (paymentDTO != null) {
            boolean isSaved = paymentBO.save(paymentDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Payment saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save payment...!").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Fail to save payment...").show();
        }

    }


    @FXML
    void paymentUpdateBtnActionClicked(ActionEvent event) throws SQLException {
        PaymentDTO paymentDTO = verifySaveUpdate();
        if (paymentDTO != null) {
            boolean isUpdated = paymentBO.update(paymentDTO);
            if (isUpdated) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Payment updated...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update payment...!").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Fail to update payment...").show();
        }

    }


    public void onClickPaymentTable(MouseEvent mouseEvent) throws SQLException {
        setDefaultBorder();
        PaymentTM paymentTM = paymentBodyTbl.getSelectionModel().getSelectedItem();
        if (paymentTM != null) {

            paymentIdShowLbl.setText(paymentTM.getPaymentID());
            memberIdCombo.getSelectionModel().select(paymentTM.getMemberID());
            purposeCombo.getSelectionModel().select(paymentTM.getPurpose());
            priceTxt.setText(String.valueOf(paymentTM.getPrice()));
            dateShowLbl.setText(String.valueOf(paymentTM.getPaymentDate()));

            paymentSaveBtn.setDisable(true);

            paymentUpdateBtn.setDisable(false);
            paymentDeleteBtn.setDisable(false);

            CommonUtil.userPermissionManage(paymentUpdateBtn,paymentDeleteBtn);

        }
    }

    @FXML
    void memberIdComboOnAction(ActionEvent event) throws SQLException {
        String selectedMemberId = memberIdCombo.getSelectionModel().getSelectedItem();
        MemberDTO memberDTO = memberBO.findById(selectedMemberId);

        // If member found (memberTO not null)
        if (memberDTO != null) {

            // FIll category related labels
            memberNameShowLbl.setText(memberDTO.getName());

        }
    }


    //------------------------------------------------------------------------------------------------------------------


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CommonUtil.userPermissionManage(paymentUpdateBtn,paymentDeleteBtn);

        setCellValues();
        purposeCombo.getItems().addAll("Fine payment","Member fees","Donation","Others");
        purposeCombo.getSelectionModel().clearSelection();

        try {
            refreshPage();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to refresh page").show();
        }

    }


    private void refreshPage() throws SQLException {
        loadNextPaymentId();
        loadTableData();
        loadMemberId();
        setDefaultBorder();

        paymentSaveBtn.setDisable(false);
        paymentUpdateBtn.setDisable(true);
        paymentDeleteBtn.setDisable(true);


        priceTxt.setText("");
        dateShowLbl.setText(CommonUtil.date);
        memberNameShowLbl.setText("");
        purposeCombo.getSelectionModel().clearSelection();
        memberIdCombo.getSelectionModel().clearSelection();

    }

    private void loadMemberId() throws SQLException {
        ArrayList<String> memberIds = memberBO.getAllIds();

        ObservableList<String> observableList = FXCollections.observableArrayList();

        for (String memberId : memberIds) {

            String state = memberBO.getState(memberId);
            if (state.equals("Active")) {
                observableList.add(memberId);
            }
        }
        memberIdCombo.setItems(observableList);
    }

    private void setCellValues() {
        paymentIdCol.setCellValueFactory(new PropertyValueFactory<>("paymentID"));
        memberIdCol.setCellValueFactory(new PropertyValueFactory<>("memberID"));
        purposeCol.setCellValueFactory(new PropertyValueFactory<>("purpose"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        paymentDateCol.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userID"));

    }


    private void loadTableData() throws SQLException {
        ArrayList<PaymentDTO> paymentDTOS = paymentBO.getAll();

        ObservableList<PaymentTM> paymentTMS = FXCollections.observableArrayList();

        for (PaymentDTO paymentDTO : paymentDTOS) {
            PaymentTM paymentTM = new PaymentTM(
                    paymentDTO.getPaymentID(),
                    paymentDTO.getMemberID(),
                    paymentDTO.getPurpose(),
                    paymentDTO.getPrice(),
                    paymentDTO.getPaymentDate(),
                    paymentDTO.getUserID()
            );
            paymentTMS.add(paymentTM);
        }

        paymentBodyTbl.setItems(paymentTMS);
    }


    public void loadNextPaymentId() throws SQLException {
        String nextPaymentId = paymentBO.getNextId();
        paymentIdShowLbl.setText(nextPaymentId);
    }


    public PaymentDTO verifySaveUpdate() throws SQLException {
        String paymentId = paymentIdShowLbl.getText();
        String memberId = memberIdCombo.getSelectionModel().getSelectedItem();
        String purpose = purposeCombo.getSelectionModel().getSelectedItem();

        String pricePattern = CommonUtil.pricePattern;
        double price = 0;

        try {
            price = Double.parseDouble(priceTxt.getText().trim());
            if (priceTxt.getText().matches(pricePattern)) {
                price = Double.valueOf(price);
            } else {
                priceTxt.setStyle(priceTxt.getStyle() + CommonUtil.redColorBorder);
            }
        } catch (IllegalArgumentException e) {
            priceTxt.setStyle(priceTxt.getStyle() + CommonUtil.redColorBorder);
        }

        Date paymentDate = Date.valueOf(dateShowLbl.getText());
        String userId = CommonUtil.username;

        //set default border

        setDefaultBorder();

        //create boolean conditions

        boolean isNullMemberId = memberIdCombo.getSelectionModel().getSelectedItem() == null ||
                memberIdCombo.getSelectionModel().getSelectedItem().equals("");

       boolean isNullPurpose = purposeCombo.getSelectionModel().getSelectedItem() == null ||
               purposeCombo.getSelectionModel().getSelectedItem().equals("");

       boolean isValidPrice = priceTxt.getText().matches(pricePattern) && !priceTxt.getText().equals("");


        //check null booleans

        if (isNullPurpose) {
            new Alert(Alert.AlertType.ERROR, "please select the purpose").show();
        }

        if (isNullMemberId) {
            new Alert(Alert.AlertType.ERROR, "please select the member Id").show();
        }

        //check valid booleans

        if (!isValidPrice) {
            priceTxt.setStyle(priceTxt.getStyle() +  CommonUtil.redColorBorder);
            System.out.println("Invalid price!");
        }

//        //validate and return object DTO

        if (!isNullPurpose && !isNullMemberId && isValidPrice) {
            PaymentDTO paymentDTO = new PaymentDTO(
                    paymentId,
                    memberId,
                    purpose,
                    price,
                    paymentDate,
                    userId
            );

            return paymentDTO;
        }
        return null;
    }

    public void setDefaultBorder() throws SQLException {
        priceTxt.setStyle(priceTxt.getStyle() + CommonUtil.defaultColorBorder);
        memberIdCombo.setStyle(memberIdCombo.getStyle() + CommonUtil.defaultColorBorder);
        purposeCombo.setStyle(purposeCombo.getStyle() + CommonUtil.defaultColorBorder);
    }

}

