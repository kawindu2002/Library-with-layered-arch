package edu.ijse.gdse71.library.controller;

import edu.ijse.gdse71.library.dto.*;
import edu.ijse.gdse71.library.dto.tm.ReturnTM;
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

public class ReturnBodyController implements Initializable {

    @FXML
    private TableColumn<ReturnTM, String> bookIdCol;

    @FXML
    private ComboBox<String> bookIdCombo;

    @FXML
    private Label bookIdLbl;


    @FXML
    private Label dateShowLbl;

    @FXML
    private TableColumn<ReturnTM, String> loanIdCol;

    @FXML
    private ComboBox<String> loanIdCombo;

    @FXML
    private Label loanIdLbl;

    @FXML
    private TableColumn<ReturnTM, String> memberIdCol;

    @FXML
    private ComboBox<String> memberIdCombo;

    @FXML
    private Label memberIdLbl;

    @FXML
    private Label reservationDateLbl;

    @FXML
    private AnchorPane returnBodyAnchPane;

    @FXML
    private TableView<ReturnTM> returnBodyTbl;

    @FXML
    private TableColumn<ReturnTM, Date> returnDateCol;

    @FXML
    private Button returnDeleteBtn;

    @FXML
    private AnchorPane returnHeaderAnchPane;

    @FXML
    private TableColumn<ReturnTM, String> returnIdCol;

    @FXML
    private Label returnIdLbl;

    @FXML
    private Label returnIdShowLbl;

    @FXML
    private Label returnManageLbl;

    @FXML
    private Button returnResetBtn;

    @FXML
    private Button returnSaveBtn;

    @FXML
    private Button returnUpdateBtn;


    @FXML
    private TableColumn<ReturnTM, String> userIdCol;

    @FXML
    private Label bookTitleLbl;

    @FXML
    private Label bookTitleShowLbl;

    @FXML
    private Label loanDateLbl;

    @FXML
    private Label loanDateShowLbl;

    @FXML
    private Label memberNameLbl;

    @FXML
    private Label memberNameShowLbl;


    final MemberModel memberModel = new MemberModel();
    final BookModel bookModel = new BookModel();
    final LoanModel loanModel = new LoanModel();
    final ReturnModel returnModel = new ReturnModel();


    //------------------------------------------------------------------------------------------------------------------


    @FXML
    void returnDeleteBtnActionClicked(ActionEvent event) throws SQLException {
        String returnId = returnIdShowLbl.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = returnModel.deleteReturn(returnId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Return deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete return...!").show();
            }
        }
    }


    @FXML
    void returnResetBtnActionClicked(ActionEvent event) throws SQLException {
        refreshPage();
    }


    @FXML
    void returnSaveBtnActionClicked(ActionEvent event) throws SQLException {
        ReturnDTO returnDTO = verifySaveUpdate();
        if (returnDTO != null) {
            boolean isSaved = returnModel.saveReturn(returnDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Return saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save return...!").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Fail to save return...").show();
        }

    }

    @FXML
    void returnUpdateBtnActionClicked(ActionEvent event) throws SQLException {
        ReturnDTO returnDTO = verifySaveUpdate();
        if (returnDTO != null) {
            boolean isUpdated = returnModel.updateReturn(returnDTO);
            if (isUpdated) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Return updated...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update return...!").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Fail to update return...").show();
        }
    }

    public void onClickReturnTable(MouseEvent mouseEvent) {
        ReturnTM returnTM = returnBodyTbl.getSelectionModel().getSelectedItem();
        if (returnTM != null) {

            returnIdShowLbl.setText(returnTM.getReturnID());
            memberIdCombo.getSelectionModel().select(returnTM.getMemberID());
            bookIdCombo.getSelectionModel().select(returnTM.getBookID());
            loanIdCombo.getSelectionModel().select(returnTM.getLoanID());
            dateShowLbl.setText(String.valueOf(returnTM.getReturnDate()));

            returnSaveBtn.setDisable(true);

            returnUpdateBtn.setDisable(false);
            returnDeleteBtn.setDisable(false);
            CommonUtil.userPermissionManage(returnUpdateBtn,returnDeleteBtn);

        }
    }

    public void memberIdComboActionClicked(ActionEvent actionEvent) throws SQLException {
        String selectedMemberId = memberIdCombo.getSelectionModel().getSelectedItem();
        MemberDTO memberDTO = memberModel.findById(selectedMemberId);

        // If bookshelf found
        if (memberDTO != null) {

            // FIll bookshelf related labels
            memberNameShowLbl.setText(memberDTO.getName());

        }
    }

    public void bookIdComboActionClicked(ActionEvent actionEvent) throws SQLException {
        String selectedBookId = bookIdCombo.getSelectionModel().getSelectedItem();
        BookWithDetailsDTO bookDTO = bookModel.findById(selectedBookId);

        // If book found
        if (bookDTO != null) {

            // FIll book related labels
            bookTitleShowLbl.setText(bookDTO.getTitle());

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

        CommonUtil.userPermissionManage(returnUpdateBtn,returnDeleteBtn);

        setCellValues();

        try {
            refreshPage();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to refresh page").show();
        }
    }


    private void refreshPage() throws SQLException {
        loadNextReturnId();
        loadTableData();
        loadBookId();
        loadMemberId();
        loadLoanId();

        returnSaveBtn.setDisable(false);

        returnUpdateBtn.setDisable(true);
        returnDeleteBtn.setDisable(true);

        memberIdCombo.getSelectionModel().clearSelection();
        bookIdCombo.getSelectionModel().clearSelection();
        loanIdCombo.getSelectionModel().clearSelection();
        dateShowLbl.setText(CommonUtil.date);
        memberNameShowLbl.setText("");
        bookTitleShowLbl.setText("");
        loanDateShowLbl.setText("");

    }

    private void loadBookId() throws SQLException {
        ArrayList<String> bookIds = bookModel.getAllBookIdsByState("Checked Out");
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(bookIds);
        bookIdCombo.setItems(observableList);
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


    private void loadLoanId() throws SQLException {
        ArrayList<String> loanIds = loanModel.getAllLoanIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(loanIds);
        loanIdCombo.setItems(observableList);
    }


    private void setCellValues() {
        returnIdCol.setCellValueFactory(new PropertyValueFactory<>("returnID"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        memberIdCol.setCellValueFactory(new PropertyValueFactory<>("memberID"));
        loanIdCol.setCellValueFactory(new PropertyValueFactory<>("loanID"));
        bookIdCol.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        returnDateCol.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

    }


    private void loadTableData() throws SQLException {
        ArrayList<ReturnDTO> returnDTOS = returnModel.getAllReturns();

        ObservableList<ReturnTM> returnTMS = FXCollections.observableArrayList();

        for (ReturnDTO returnDTO : returnDTOS) {
            ReturnTM returnTM = new ReturnTM(
                    returnDTO.getReturnID(),
                    returnDTO.getUserID(),
                    returnDTO.getMemberID(),
                    returnDTO.getLoanID(),
                    returnDTO.getBookID(),
                    returnDTO.getReturnDate()
            );
            returnTMS.add(returnTM);
        }

        returnBodyTbl.setItems(returnTMS);
    }


    public void loadNextReturnId() throws SQLException {
        String nextReturnId = returnModel.getNextReturnId();
        returnIdShowLbl.setText(nextReturnId);
    }


    public ReturnDTO verifySaveUpdate() throws SQLException {

        String returnId = returnIdShowLbl.getText();
        String memberId = memberIdCombo.getSelectionModel().getSelectedItem();
        String bookId = bookIdCombo.getSelectionModel().getSelectedItem();
        String loanId = loanIdCombo.getSelectionModel().getSelectedItem();
        String userId = CommonUtil.username;
        Date returnDate = Date.valueOf(dateShowLbl.getText());

        //create boolean conditions

        boolean isNullBookId = bookIdCombo.getSelectionModel().getSelectedItem() == null ||
                bookIdCombo.getSelectionModel().getSelectedItem().equals("");
        boolean isNullMemberId = memberIdCombo.getSelectionModel().getSelectedItem() == null ||
                memberIdCombo.getSelectionModel().getSelectedItem().equals("");
        boolean isNullLoanId = loanIdCombo.getSelectionModel().getSelectedItem() == null ||
                loanIdCombo.getSelectionModel().getSelectedItem().equals("");


        //check null booleans

        if (isNullBookId) {
            new Alert(Alert.AlertType.ERROR, "please select the bookId").show();
        }
        if (isNullMemberId) {
            new Alert(Alert.AlertType.ERROR, "please select the memberId").show();
        }
        if (isNullLoanId) {
            new Alert(Alert.AlertType.ERROR, "please select the loanId").show();
        }

        //validate and return object DTO

        if (!isNullBookId && !isNullMemberId && !isNullLoanId ) {
            ReturnDTO returnDTO = new ReturnDTO(
                    returnId,
                    userId,
                    memberId,
                    loanId,
                    bookId,
                    returnDate
            );

            return returnDTO;

        }
        return null;
    }

}
