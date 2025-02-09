package edu.ijse.gdse71.library.controller;

import edu.ijse.gdse71.library.bo.BOFactory;
import edu.ijse.gdse71.library.bo.custom.impl.BookBOImpl;
import edu.ijse.gdse71.library.bo.custom.impl.LoanBOImpl;
import edu.ijse.gdse71.library.bo.custom.impl.MemberBOImpl;
import edu.ijse.gdse71.library.dto.LoanDTO;
import edu.ijse.gdse71.library.dto.MemberDTO;
import edu.ijse.gdse71.library.dto.tm.LoanTM;
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

public class LoanBodyController implements Initializable {

    @FXML
    private TableColumn<LoanTM, String> bookIdCol;

    @FXML
    private ComboBox<String> bookIdCombo;

    @FXML
    private Label bookIdLbl;

    @FXML
    private Label dateShowLbl;

    @FXML
    private TableColumn<LoanTM, Date> dueDateCol;

    @FXML
    private Label dueDateLbl;

    @FXML
    private TextField dueDateTxt;

    @FXML
    private AnchorPane loanBodyAnchPane;

    @FXML
    private TableView<LoanTM> loanBodyTbl;

    @FXML
    private TableColumn<LoanTM, Date> loanDateCol;

    @FXML
    private Label loanDateLbl;

    @FXML
    private Button loanDeleteBtn;

    @FXML
    private AnchorPane loanHeaderAnchPane;

    @FXML
    private TableColumn<LoanTM, String> loanIdCol;

    @FXML
    private Label loanIdLbl;

    @FXML
    private Label loanIdShowLbl;

    @FXML
    private Label loanManageLbl;

    @FXML
    private Button loanResetBtn;

    @FXML
    private Button loanSaveBtn;

    @FXML
    private Button loanUpdateBtn;

    @FXML
    private TableColumn<LoanTM, String> memberIdCol;

    @FXML
    private ComboBox<String> memberIdCombo;

    @FXML
    private Label memberIdLbl;

    @FXML
    private TableColumn<LoanTM, String> userIdCol;

    @FXML
    private Label bookTitleLbl;

    @FXML
    private Label bookTitleShowLbl;

    @FXML
    private Label memberNameLbl;

    @FXML
    private Label memberNameShowLbl;

    MemberBOImpl memberBO = (MemberBOImpl) BOFactory.getInstance().getBO(BOFactory.BOTypes.MEMBER);
    BookBOImpl bookBO = (BookBOImpl) BOFactory.getInstance().getBO(BOFactory.BOTypes.BOOK);
    LoanBOImpl loanBO = (LoanBOImpl) BOFactory.getInstance().getBO(BOFactory.BOTypes.LOAN);


    //------------------------------------------------------------------------------------------------------------------


    @FXML
    void loanDeleteBtnActionClicked(ActionEvent event) throws SQLException {
        String loanId = loanIdShowLbl.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = loanBO.delete(loanId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Loan deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete loan...!").show();
            }
        }
    }

    @FXML
    void loanResetBtnActionClicked(ActionEvent event) throws SQLException {
        refreshPage();

    }

    @FXML
    void loanSaveBtnActionClicked(ActionEvent event) throws SQLException {
        LoanDTO loanDTO = verifySaveUpdate();
        if (loanDTO != null) {
            boolean isSaved = loanBO.save(loanDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Loan saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save loan...!").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Fail to save loan...").show();
        }
    }

    @FXML
    void loanUpdateBtnActionClicked(ActionEvent event) throws SQLException {
        LoanDTO loanDTO = verifySaveUpdate();
        if (loanDTO != null) {
            boolean isUpdated = loanBO.update(loanDTO);
            if (isUpdated) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Loan updated...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update loan...!").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Fail to update loan...").show();
        }
    }

    public void onClickLoanTable(MouseEvent mouseEvent) throws SQLException {
        setDefaultBorder();
        LoanTM loanTM = loanBodyTbl.getSelectionModel().getSelectedItem();
        if (loanTM != null) {

            loanIdShowLbl.setText(loanTM.getLoanID());
            memberIdCombo.getSelectionModel().select(loanTM.getMemberID());
            bookIdCombo.getSelectionModel().select(loanTM.getBookID());
            dateShowLbl.setText(String.valueOf(loanTM.getLoanDate()));
            dueDateTxt.setText(String.valueOf(loanTM.getDueDate()));

            loanSaveBtn.setDisable(true);

            loanUpdateBtn.setDisable(false);
            loanDeleteBtn.setDisable(false);
            CommonUtil.userPermissionManage(loanUpdateBtn,loanDeleteBtn);

        }
    }

    public void memberIdComboActionClicked(ActionEvent actionEvent) throws SQLException {
        String selectedMemberId = memberIdCombo.getSelectionModel().getSelectedItem();
        MemberDTO memberDTO = memberBO.findById(selectedMemberId);

        // If bookshelf found
        if (memberDTO != null) {

            // FIll bookshelf related labels
            memberNameShowLbl.setText(memberDTO.getName());

        }
    }

    public void bookIdComboActionClicked(ActionEvent actionEvent) throws SQLException {
        String selectedBookId = bookIdCombo.getSelectionModel().getSelectedItem();
        BookWithDetailsDTO bookDTO = bookBO.findById(selectedBookId);

        // If book found
        if (bookDTO != null) {

            // FIll book related labels
            bookTitleShowLbl.setText(bookDTO.getTitle());

        }
    }


    //------------------------------------------------------------------------------------------------------------------


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CommonUtil.userPermissionManage(loanUpdateBtn,loanDeleteBtn);

        setCellValues();

        try {
            refreshPage();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to refresh page").show();
        }

    }

    private void refreshPage() throws SQLException {
        loadNextLoanId();
        loadTableData();
        loadBookId();
        loadMemberId();
        setDefaultBorder();


        loanSaveBtn.setDisable(false);

        loanUpdateBtn.setDisable(true);
        loanDeleteBtn.setDisable(true);

        memberIdCombo.getSelectionModel().clearSelection();
        bookIdCombo.getSelectionModel().clearSelection();
        dateShowLbl.setText(CommonUtil.date);
        memberNameShowLbl.setText("");
        bookTitleShowLbl.setText("");
        dueDateTxt.setText("");

    }


    private void loadBookId() throws SQLException {
        ArrayList<String> bookIds = bookBO.getAllBookIdsByState("Available");
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(bookIds);
        bookIdCombo.setItems(observableList);
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
        loanIdCol.setCellValueFactory(new PropertyValueFactory<>("loanID"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        memberIdCol.setCellValueFactory(new PropertyValueFactory<>("memberID"));
        bookIdCol.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        loanDateCol.setCellValueFactory(new PropertyValueFactory<>("loanDate"));
        dueDateCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));

    }


    private void loadTableData() throws SQLException {
        ArrayList<LoanDTO> loanDTOS = loanBO.getAll();

        ObservableList<LoanTM> loanTMS = FXCollections.observableArrayList();

        for (LoanDTO loanDTO : loanDTOS) {
            LoanTM loanTM = new LoanTM(
                    loanDTO.getLoanID(),
                    loanDTO.getUserID(),
                    loanDTO.getMemberID(),
                    loanDTO.getBookID(),
                    loanDTO.getLoanDate(),
                    loanDTO.getDueDate()
            );
            loanTMS.add(loanTM);
        }

        loanBodyTbl.setItems(loanTMS);
    }


    public void loadNextLoanId() throws SQLException {
        String nextLoanId = loanBO.getNextId();
        loanIdShowLbl.setText(nextLoanId);
    }


    public LoanDTO verifySaveUpdate() throws SQLException {

        String loanId = loanIdShowLbl.getText();
        String userId = CommonUtil.username;
        String memberId = memberIdCombo.getSelectionModel().getSelectedItem();
        String bookId = bookIdCombo.getSelectionModel().getSelectedItem();
        Date loanDate = Date.valueOf(dateShowLbl.getText());

        //assign patterns

        String datePattern = CommonUtil.datePattern;
        Date dueDate = null;
        String duedate;

        try {
            duedate = dueDateTxt.getText().trim();
            if (duedate.matches(datePattern)) {
            dueDate = Date.valueOf(duedate);
            } else {
                dueDateTxt.setStyle(dueDateTxt.getStyle() + CommonUtil.redColorBorder);
            }
        } catch (IllegalArgumentException e) {
            dueDateTxt.setStyle(dueDateTxt.getStyle() + CommonUtil.redColorBorder);
        }


        //set default border

        setDefaultBorder();

        //create boolean conditions

        boolean isNullBookId = bookIdCombo.getSelectionModel().getSelectedItem() == null ||
                bookIdCombo.getSelectionModel().getSelectedItem().equals("");
        boolean isNullMemberId = memberIdCombo.getSelectionModel().getSelectedItem() == null ||
                memberIdCombo.getSelectionModel().getSelectedItem().equals("");
        boolean isValidDate = dueDateTxt.getText().matches(datePattern) && !dueDateTxt.getText().equals("");

        //check null booleans

        if (isNullBookId) {
            new Alert(Alert.AlertType.ERROR, "please select the bookId").show();
        }
        if (isNullMemberId) {
            new Alert(Alert.AlertType.ERROR, "please select the memberId").show();
        }



        //check valid booleans

        if (!isValidDate) {
            dueDateTxt.setStyle(dueDateTxt.getStyle() +  CommonUtil.redColorBorder);
            System.out.println("Invalid due date!");
        }

        //validate and return object DTO

        if (!isNullBookId && !isNullMemberId && isValidDate) {
            LoanDTO loanDTO = new LoanDTO(
                    loanId,
                    userId,
                    memberId,
                    bookId,
                    loanDate,
                    dueDate
            );

            return loanDTO;

        }
        return null;
    }

    public void setDefaultBorder() throws SQLException {
        dueDateTxt.setStyle(dueDateTxt.getStyle() + CommonUtil.defaultColorBorder);
    }

}

