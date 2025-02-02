package edu.ijse.gdse71.library.controller;
import edu.ijse.gdse71.library.dto.AuthorDTO;
import edu.ijse.gdse71.library.dto.AuthorDetailsDTO;
import edu.ijse.gdse71.library.dto.tm.AuthorCartTM;
import edu.ijse.gdse71.library.model.AuthorDetailsModel;
import edu.ijse.gdse71.library.model.AuthorModel;
import edu.ijse.gdse71.library.util.CommonUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AuthorDetailsBodyController implements Initializable {

    @FXML
    private TableColumn<AuthorCartTM, Button> actionCol;

    @FXML
    private Button addToListBtn;

    @FXML
    private Button backBtn;

    @FXML
    private AnchorPane authorDetailsBodyAnchPane;

    @FXML
    private TableColumn<AuthorCartTM, String> authorDetailsCartAuthorIdCol;

    @FXML
    private TableView<AuthorCartTM> authorDetailsCartTbl;

    @FXML
    private AnchorPane authorDetailsHeaderAnchPane;

    @FXML
    private Label authorDetailsManageLbl;

    @FXML
    private ComboBox<String> authorIdCombo;

    @FXML
    private Label authorIdLbl;

    @FXML
    private Label authorNameLbl;

    @FXML
    private Label bookIdLbl;

    @FXML
    private Label bookIdShowLbl;

    @FXML
    private Button insertAuthorsBtn;

    @FXML
    private Label nameShowLbl;

    @FXML
    private Button resetBtn;

    @FXML
    String selectedAuthorId;



    // Observable list to manage cart items in TableView
    private final ObservableList<AuthorCartTM> authorCartTMS = FXCollections.observableArrayList();


    //------------------------------------------------------------------------------------------------------------------


    @FXML
    void addToListBtnActionClicked(ActionEvent event) throws SQLException {
        if (authorDetailsCartTbl.getItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please add authors to table..!").show();
            return;
        }

        String bookId = CommonUtil.selectedBookId;
        String authorId = selectedAuthorId;

        // List to hold author details
        ArrayList<AuthorDetailsDTO> authorDetailsDTOS = new ArrayList<>();

        for (AuthorCartTM authorCartTM : authorCartTMS) {
            AuthorDetailsDTO authorDetailsDTO = new AuthorDetailsDTO(
                    bookId,
                    authorCartTM.getAuthorID()
            );

            authorDetailsDTOS.add(authorDetailsDTO);
        }

        if(authorDetailsDTOS != null){
            CommonUtil.authorDetailsDTOS = authorDetailsDTOS;
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Author list added successfully..!").show();
            CommonUtil.navigateTo(authorDetailsBodyAnchPane,"/view/BookBodyView.fxml");

        }else{
            new Alert(Alert.AlertType.ERROR, "No data found..!").show();
        }
    }


    @FXML
    void insertAuthorsBtnActionClicked(ActionEvent event) throws SQLException {

        if (selectedAuthorId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select author..!").show();
            return;
        }


        // Loop through each item in cart's observable list.
        for (AuthorCartTM authorCartTM : authorCartTMS) {
            // Check if the item is already in the cart
            if (authorCartTM.getAuthorID().equals(selectedAuthorId)) {
                new Alert(Alert.AlertType.ERROR, "Author Id already exists..!").show();
                authorDetailsCartTbl.refresh();
                return;
            }
        }

        Button removeBtn = new Button("Remove");

        // If the item does not already exist in the cart, create a new CartTM object to represent it.
        AuthorCartTM newAuthorCartTM = new AuthorCartTM(
                selectedAuthorId,
                removeBtn
        );

        // Set an action for the "Remove" button, which removes the item from the cart when clicked.
        removeBtn.setOnAction(actionEvent -> {

            // Remove the item from the cart's observable list (cartTMS).
            authorCartTMS.remove(newAuthorCartTM);
            authorIdCombo.getSelectionModel().clearSelection();
            nameShowLbl.setText("");


            // Refresh the table to reflect the removal of the item.
            authorDetailsCartTbl.refresh();
        });


        // Add the newly created CartTM object to the cart's observable list.
        authorCartTMS.add(newAuthorCartTM);
        addToListBtn.setDisable(false);
        authorIdCombo.getSelectionModel().clearSelection();
        nameShowLbl.setText("");

    }

    @FXML
    void resetBtnActionClicked(ActionEvent event) throws SQLException {
        refreshPage();
    }

    public void authorIdComboOnAction(ActionEvent actionEvent) throws SQLException {
        selectedAuthorId = authorIdCombo.getSelectionModel().getSelectedItem();
        AuthorDTO authorDTO = authorModel.findById(selectedAuthorId);

        // If author found (authorDTO not null)
        if (authorDTO != null) {
            // FIll author related labels
            nameShowLbl.setText(authorDTO.getName());

        }
    }


    public void backBtnActionClicked(ActionEvent actionEvent) {
        CommonUtil.navigateTo(authorDetailsBodyAnchPane,"/view/BookBodyView.fxml");
    }


    //------------------------------------------------------------------------------------------------------------------


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();
        addToListBtn.setDisable(true);

        try {
            refreshPage();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to refresh page").show();
        }

    }

    private void refreshPage() throws SQLException {
        bookIdShowLbl.setText(CommonUtil.selectedBookId);
        loadAuthorId();

        addToListBtn.setDisable(true);
        insertAuthorsBtn.setDisable(false);

        authorIdCombo.getSelectionModel().clearSelection();
        nameShowLbl.setText("");
        authorCartTMS.clear();

        authorDetailsCartTbl.refresh();
    }


    private void loadAuthorId() throws SQLException {
        ArrayList<String> authorIds = authorModel.getAllAuthorIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(authorIds);
        authorIdCombo.setItems(observableList);
    }


    private void setCellValues() {
        authorDetailsCartAuthorIdCol.setCellValueFactory(new PropertyValueFactory<>("authorID"));
        actionCol.setCellValueFactory(new PropertyValueFactory<>("removeBtn"));
        authorDetailsCartTbl.setItems(authorCartTMS);

    }

}
