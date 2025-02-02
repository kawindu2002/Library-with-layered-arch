package edu.ijse.gdse71.library.controller;

import edu.ijse.gdse71.library.dto.*;
import edu.ijse.gdse71.library.dto.tm.FineTM;
import edu.ijse.gdse71.library.model.*;
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

public class FineBodyController implements Initializable {

    @FXML
    private Label dateShowLbl;

    @FXML
    private AnchorPane fineBodyAnchPane;

    @FXML
    private TableView<FineTM> fineBodyTbl;

    @FXML
    private TableColumn<FineTM, Date> fineDateCol;

    @FXML
    private Label fineDateLbl;

    @FXML
    private AnchorPane fineHeaderAnchPane;

    @FXML
    private TableColumn<FineTM, String> fineIdCol;

    @FXML
    private Label fineIdLbl;

    @FXML
    private Label fineIdShowLbl;

    @FXML
    private Label fineManageLbl;

    @FXML
    private Button fineResetBtn;

    @FXML
    private Button fineSaveBtn;

    @FXML
    private Button fineUpdateBtn;

    @FXML
    private Button fineDeleteBtn;


    @FXML
    private TableColumn<FineTM, String> loanIdCol;

    @FXML
    private ComboBox<String> loanIdCombo;

    @FXML
    private Label loanIdLbl;

    @FXML
    private TableColumn<FineTM, String> memberIdCol;

    @FXML
    private ComboBox<String> memberIdCombo;

    @FXML
    private Label memberIdLbl;

    @FXML
    private TableColumn<FineTM, Double> priceCol;

    @FXML
    private Label priceLbl;

    @FXML
    private TextField priceTxt;

    @FXML
    private TextField reasonTxt;

    @FXML
    private TableColumn<FineTM, String> reasonCol;

    @FXML
    private Label reasonLbl;

    @FXML
    private TableColumn<FineTM, String> userIdCol;

    @FXML
    private Label loanDateLbl;

    @FXML
    private Label loanDateShowLbl;

    @FXML
    private Label memberNameLbl;

    @FXML
    private Label memberNameShowLbl;


    //------------------------------------------------------------------------------------------------------------------


    @FXML
    void fineDeleteBtnActionClicked(ActionEvent event) throws SQLException {
        String fineId = fineIdShowLbl.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = fineModel.deleteFine(fineId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Fine deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete fine...!").show();
            }
        }
    }


    @FXML
    void fineResetBtnActionClicked(ActionEvent event) throws SQLException {
        refreshPage();

    }

    @FXML
    void fineSaveBtnActionClicked(ActionEvent event) throws SQLException{
        FineDTO fineDTO = verifySaveUpdate();
        if (fineDTO != null) {
            boolean isSaved = fineModel.saveFine(fineDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Fine saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save fine...!").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Fail to save fine...").show();
        }
    }


    @FXML
    void fineUpdateBtnActionClicked(ActionEvent event) throws SQLException {
        FineDTO fineDTO = verifySaveUpdate();
        if (fineDTO != null) {
            boolean isUpdated = fineModel.updateFine(fineDTO);
            if (isUpdated) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Fine updated...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update fine...!").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Fail to update fine...").show();
        }
    }

    public void onClickFineTable(MouseEvent mouseEvent) throws SQLException{
        setDefaultBorder();
        FineTM fineTM = fineBodyTbl.getSelectionModel().getSelectedItem();
        if (fineTM != null) {

            fineIdShowLbl.setText(fineTM.getFineID());
            priceTxt.setText(String.valueOf(fineTM.getPrice()));
            dateShowLbl.setText(fineTM.getFineDate().toString());
            memberIdCombo.getSelectionModel().select(fineTM.getMemberID());
            loanIdCombo.getSelectionModel().select(fineTM.getLoanID());
            reasonTxt.setText(fineTM.getReason());

            fineSaveBtn.setDisable(true);

            fineUpdateBtn.setDisable(false);
            fineDeleteBtn.setDisable(false);
            CommonUtil.userPermissionManage(fineUpdateBtn,fineDeleteBtn);

        }
    }

    public void memberIdComboActionClicked(ActionEvent actionEvent) throws SQLException {
        String selectedMemberId = memberIdCombo.getSelectionModel().getSelectedItem();
        MemberDTO memberDTO = memberModel.findById(selectedMemberId);

        // If member found (memberTO not null)
        if (memberDTO != null) {

            // FIll category related labels
            memberNameShowLbl.setText(memberDTO.getName());

        }
    }

    public void loanIdComboActionClicked(ActionEvent actionEvent) throws SQLException {
        String selectedLoanId = loanIdCombo.getSelectionModel().getSelectedItem();
        LoanDTO loanDTO =loanModel.findById(selectedLoanId);

        // If loan found
        if (loanDTO != null) {

            // FIll loan related labels
            loanDateShowLbl.setText(String.valueOf(loanDTO.getLoanDate()));

        }
    }


    //------------------------------------------------------------------------------------------------------------------


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CommonUtil.userPermissionManage(fineUpdateBtn,fineDeleteBtn);

        setCellValues();

        try {
            refreshPage();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to refresh page").show();
        }

    }


    private void refreshPage() throws SQLException {
        loadNextFineId();
        loadTableData();
        loadMemberId();
        loadLoanId();
        setDefaultBorder();
        fineSaveBtn.setDisable(false);

        fineUpdateBtn.setDisable(true);
        fineDeleteBtn.setDisable(true);

        dateShowLbl.setText(CommonUtil.date);
        loanIdCombo.getSelectionModel().clearSelection();
        memberIdCombo.getSelectionModel().clearSelection();
        priceTxt.setText("");
        reasonTxt.setText("");
        loanDateShowLbl.setText("");
        memberNameShowLbl.setText("");

    }


    private void loadLoanId() throws SQLException {
        ArrayList<String> loanIds = loanModel.getAllLoanIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(loanIds);
        loanIdCombo.setItems(observableList);
    }

    private void loadMemberId() throws SQLException {
        ArrayList<String> memberIds = memberModel.getAllMemberIds();

        ObservableList<String> observableList = FXCollections.observableArrayList();

        for (String memberId : memberIds) {

            String state = memberModel.getMemberState(memberId);
            if (state.equals("Active")) {
                observableList.add(memberId);
            }
        }
        memberIdCombo.setItems(observableList);
    }


    private void setCellValues() {
        fineIdCol.setCellValueFactory(new PropertyValueFactory<>("fineID"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        memberIdCol.setCellValueFactory(new PropertyValueFactory<>("memberID"));
        loanIdCol.setCellValueFactory(new PropertyValueFactory<>("loanID"));
        fineDateCol.setCellValueFactory(new PropertyValueFactory<>("fineDate"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        reasonCol.setCellValueFactory(new PropertyValueFactory<>("reason"));
    }

    private void loadTableData() throws SQLException {
        ArrayList<FineDTO> fineDTOS = fineModel.getAllFines();

        ObservableList<FineTM> fineTMS = FXCollections.observableArrayList();

        for (FineDTO fineDTO : fineDTOS) {
            FineTM fineTM = new FineTM(
                    fineDTO.getFineID(),
                    fineDTO.getUserID(),
                    fineDTO.getMemberID(),
                    fineDTO.getLoanID(),
                    fineDTO.getPrice(),
                    fineDTO.getFineDate(),
                    fineDTO.getReason()
            );
            fineTMS.add(fineTM);
        }

        fineBodyTbl.setItems(fineTMS);
    }


    public void loadNextFineId() throws SQLException {
        String nextFineId = fineModel.getNextFineId();
        fineIdShowLbl.setText(nextFineId);
    }


    public FineDTO verifySaveUpdate() throws SQLException {

        String fineId = fineIdShowLbl.getText();
        String userId = CommonUtil.username;
        String memberId = memberIdCombo.getSelectionModel().getSelectedItem();
        String loanId = loanIdCombo.getSelectionModel().getSelectedItem();
        String reason = reasonTxt.getText();

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


        Date fineDate = Date.valueOf(dateShowLbl.getText());

        //set default border

        setDefaultBorder();

        //create boolean conditions

        boolean isNullMemberId = memberIdCombo.getSelectionModel().getSelectedItem() == null ||
                memberIdCombo.getSelectionModel().getSelectedItem().equals("");
        boolean isNullLoanId = loanIdCombo.getSelectionModel().getSelectedItem() == null ||
                loanIdCombo.getSelectionModel().getSelectedItem().equals("");

        boolean isValidPrice = priceTxt.getText().matches(pricePattern) && !priceTxt.getText().equals("");

        boolean isValidReason = !reasonTxt.getText().equals("");

        //check null booleans

        if (isNullMemberId) {
            new Alert(Alert.AlertType.ERROR, "please select the memberId").show();
        }
        if (isNullLoanId) {
            new Alert(Alert.AlertType.ERROR, "please select the loanId").show();
        }

        //check valid booleans

        if (!isValidPrice) {
            priceTxt.setStyle(priceTxt.getStyle() +  CommonUtil.redColorBorder);
            System.out.println("Invalid price!");
        }

        if (!isValidReason) {
            reasonTxt.setStyle(reasonTxt.getStyle() +  CommonUtil.redColorBorder);
            System.out.println("Invalid Reason!");
        }



        //validate and return object DTO

        if (!isNullMemberId && !isNullLoanId && isValidReason && isValidPrice) {
            FineDTO fineDTO = new FineDTO(
                    fineId,
                    userId,
                    memberId,
                    loanId,
                    price,
                    fineDate,
                    reason
            );

            return fineDTO;

        }

        return null;
    }

    public void setDefaultBorder() throws SQLException {
        priceTxt.setStyle(priceTxt.getStyle() + CommonUtil.defaultColorBorder);
        reasonTxt.setStyle(reasonTxt.getStyle() + CommonUtil.defaultColorBorder);
    }

}
