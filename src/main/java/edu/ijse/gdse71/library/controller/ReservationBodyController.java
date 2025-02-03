package edu.ijse.gdse71.library.controller;

import edu.ijse.gdse71.library.dto.*;
import edu.ijse.gdse71.library.dto.tm.ReservationTM;
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

public class ReservationBodyController implements Initializable {

    @FXML
    private TableColumn<ReservationTM, String> bookIdCol;

    @FXML
    private ComboBox<String> bookIdCombo;

    @FXML
    private Label bookIdLbl;

    @FXML
    private Label dateShowLbl;

    @FXML
    private TableColumn<ReservationTM, String> memberIdCol;

    @FXML
    private ComboBox<String> memberIdCombo;

    @FXML
    private Label memberIdLbl;

    @FXML
    private AnchorPane reservationBodyAnchPane;

    @FXML
    private TableView<ReservationTM> reservationBodyTbl;

    @FXML
    private TableColumn<ReservationTM, Date> reservationDateCol;

    @FXML
    private Label reservationDateLbl;

    @FXML
    private Button reservationDeleteBtn;

    @FXML
    private AnchorPane reservationHeaderAnchPane;

    @FXML
    private TableColumn<ReservationTM, String> reservationIdCol;

    @FXML
    private Label reservationIdLbl;

    @FXML
    private Label reservationIdShowLbl;

    @FXML
    private Label reservationManageLbl;

    @FXML
    private Button reservationResetBtn;

    @FXML
    private Button reservationSaveBtn;

    @FXML
    private Button reservationUpdateBtn;

    @FXML
    private TableColumn<ReservationTM, String> userIdCol;

    @FXML
    private Label bookTitleLbl;

    @FXML
    private Label bookTitleShowLbl;

    @FXML
    private Label memberNameLbl;

    @FXML
    private Label memberNameShowLbl;

    final MemberModel memberModel = new MemberModel();
    final BookModel bookModel = new BookModel();
    final ReservationModel reservationModel = new ReservationModel();


    //------------------------------------------------------------------------------------------------------------------


    @FXML
    void reservationDeleteBtnActionClicked(ActionEvent event) throws SQLException {
        String reservationId = reservationIdShowLbl.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = reservationModel.deleteReservation(reservationId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Reservation deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete reservation...!").show();
            }
        }
    }


    @FXML
    void reservationResetBtnActionClicked(ActionEvent event) throws SQLException {
        refreshPage();
    }


    @FXML
    void reservationSaveBtnActionClicked(ActionEvent event) throws SQLException {
        ReservationDTO reservationDTO = verifySaveUpdate();
        if (reservationDTO != null) {
            boolean isSaved = reservationModel.saveReservation(reservationDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Reservation saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save reservation...!").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Fail to save reservation...").show();
        }

    }


    @FXML
    void reservationUpdateBtnActionClicked(ActionEvent event) throws SQLException {
        ReservationDTO reservationDTO = verifySaveUpdate();
        if (reservationDTO != null) {
            boolean isUpdated = reservationModel.updateReservation(reservationDTO);
            if (isUpdated) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Reservation updated...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update reservation...!").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Fail to update reservation...").show();
        }

    }


    public void onClickReservationTable(MouseEvent mouseEvent) {
        ReservationTM reservationTM = reservationBodyTbl.getSelectionModel().getSelectedItem();
        if (reservationTM != null) {

            reservationIdShowLbl.setText(reservationTM.getReservationID());
            memberIdCombo.getSelectionModel().select(reservationTM.getMemberID());
            bookIdCombo.getSelectionModel().select(reservationTM.getBookID());
            dateShowLbl.setText(String.valueOf(reservationTM.getReservationDate()));

            reservationSaveBtn.setDisable(true);

            reservationUpdateBtn.setDisable(false);
            reservationDeleteBtn.setDisable(false);

            CommonUtil.userPermissionManage(reservationUpdateBtn,reservationDeleteBtn);

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


    public void memberIdComboActionClicked(ActionEvent actionEvent) throws SQLException {
        String selectedMemberId = memberIdCombo.getSelectionModel().getSelectedItem();
        MemberDTO memberDTO = memberModel.findById(selectedMemberId);

        // If bookshelf found
        if (memberDTO != null) {

            // FIll bookshelf related labels
            memberNameShowLbl.setText(memberDTO.getName());

        }
    }


    //------------------------------------------------------------------------------------------------------------------


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        CommonUtil.userPermissionManage(reservationUpdateBtn,reservationDeleteBtn);

        setCellValues();

        try {
            refreshPage();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to refresh page").show();
        }

    }

    private void refreshPage() throws SQLException {
        loadNextReservationId();
        loadTableData();
        loadBookId();
        loadMemberId();

        reservationSaveBtn.setDisable(false);

        reservationUpdateBtn.setDisable(true);
        reservationDeleteBtn.setDisable(true);

        memberIdCombo.getSelectionModel().clearSelection();
        bookIdCombo.getSelectionModel().clearSelection();
        dateShowLbl.setText(CommonUtil.date);
        memberNameShowLbl.setText("");
        bookTitleShowLbl.setText("");

    }


    private void loadBookId() throws SQLException {
        ArrayList<String> bookIds = bookModel.getAllBookIdsByState("Available");
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


    private void setCellValues() {
        reservationIdCol.setCellValueFactory(new PropertyValueFactory<>("reservationID"));
        memberIdCol.setCellValueFactory(new PropertyValueFactory<>("memberID"));
        bookIdCol.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        reservationDateCol.setCellValueFactory(new PropertyValueFactory<>("reservationDate"));

    }


    private void loadTableData() throws SQLException {
        ArrayList<ReservationDTO> reservationDTOS = reservationModel.getAllReservations();

        ObservableList<ReservationTM> reservationTMS = FXCollections.observableArrayList();

        for (ReservationDTO reservationDTO : reservationDTOS) {
            ReservationTM reservationTM = new ReservationTM(
                    reservationDTO.getReservationID(),
                    reservationDTO.getMemberID(),
                    reservationDTO.getBookID(),
                    reservationDTO.getUserID(),
                    reservationDTO.getReservationDate()
            );
            reservationTMS.add(reservationTM);
        }

        reservationBodyTbl.setItems(reservationTMS);
    }


    public void loadNextReservationId() throws SQLException {
        String nextReservationId = reservationModel.getNextReservationId();
        reservationIdShowLbl.setText(nextReservationId);
    }


    public ReservationDTO verifySaveUpdate() throws SQLException {

        String reservationId = reservationIdShowLbl.getText();
        String memberId = memberIdCombo.getSelectionModel().getSelectedItem();
        String bookId = bookIdCombo.getSelectionModel().getSelectedItem();
        String userId = CommonUtil.username;
        Date reservationDate = Date.valueOf(dateShowLbl.getText());

        //create boolean conditions

        boolean isNullBookId = bookIdCombo.getSelectionModel().getSelectedItem() == null ||
                bookIdCombo.getSelectionModel().getSelectedItem().equals("");
        boolean isNullMemberId = memberIdCombo.getSelectionModel().getSelectedItem() == null ||
                memberIdCombo.getSelectionModel().getSelectedItem().equals("");

        //check null booleans

        if (isNullBookId) {
            new Alert(Alert.AlertType.ERROR, "please select the bookId").show();
        }
        if (isNullMemberId) {
            new Alert(Alert.AlertType.ERROR, "please select the memberId").show();
        }


        //validate and return object DTO

        if (!isNullBookId && !isNullMemberId ) {
            ReservationDTO reservationDTO = new ReservationDTO(
                    reservationId,
                    memberId,
                    bookId,
                    userId,
                    reservationDate
            );

            return reservationDTO;

        }
        return null;
    }


}

