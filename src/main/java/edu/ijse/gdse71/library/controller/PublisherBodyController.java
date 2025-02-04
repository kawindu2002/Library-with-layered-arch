package edu.ijse.gdse71.library.controller;

import edu.ijse.gdse71.library.bo.custom.PublisherBO;
import edu.ijse.gdse71.library.bo.impl.PublisherBOImpl;
import edu.ijse.gdse71.library.dto.PublisherDTO;
import edu.ijse.gdse71.library.dto.tm.PublisherTM;
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

public class PublisherBodyController implements Initializable {


    @FXML
    private TableColumn<PublisherTM, String> addressCol;

    @FXML
    private Label addressLbl;

    @FXML
    private TextField addressTxt;

    @FXML
    private Label dateShowLbl;

    @FXML
    private TableColumn<PublisherTM, String> nameCol;

    @FXML
    private Label nameLbl;

    @FXML
    private TextField nameTxt;

    @FXML
    private AnchorPane publisherBodyAnchPane;

    @FXML
    private TableView<PublisherTM> publisherBodyTbl;

    @FXML
    private Button publisherDeleteBtn;

    @FXML
    private AnchorPane publisherHeaderAnchPane;

    @FXML
    private TableColumn<PublisherTM, String> publisherIdCol;

    @FXML
    private Label publisherIdLbl;

    @FXML
    private Label publisherIdShowLbl;

    @FXML
    private Label publisherManageLbl;

    @FXML
    private Button publisherResetBtn;

    @FXML
    private Button publisherSaveBtn;

    @FXML
    private Button publisherUpdateBtn;

    @FXML
    private TableColumn<PublisherTM, Date> regDateCol;

    @FXML
    private Label regDateLbl;

    PublisherBO publisherBO = new PublisherBOImpl();

    //------------------------------------------------------------------------------------------------------------------


    @FXML
    void publisherDeleteBtnActionClicked(ActionEvent event) throws SQLException {
        String publisherId = publisherIdShowLbl.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = publisherBO.delete(publisherId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Publisher deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete publisher...!").show();
            }
        }
    }

    @FXML
    void publisherResetBtnActionClicked(ActionEvent event) throws SQLException {
        refreshPage();

    }

    @FXML
    void publisherSaveBtnActionClicked(ActionEvent event) throws SQLException {
        PublisherDTO publisherDTO = verifySaveUpdate();
        if (publisherDTO != null) {
            boolean isSaved = publisherBO.save(publisherDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Publisher saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save publisher...!").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Fail to save publisher...").show();
        }
    }


    @FXML
    void publisherUpdateBtnActionClicked(ActionEvent event) throws SQLException {
        PublisherDTO publisherDTO = verifySaveUpdate();
        if (publisherDTO != null) {
            boolean isUpdated = publisherBO.update(publisherDTO);
            if (isUpdated) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Publisher updated...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update publisher...!").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Fail to update publisher...").show();
        }
    }


    public void onClickPublisherTable(MouseEvent mouseEvent) throws SQLException {
        setDefaultBorder();
        PublisherTM publisherTM = publisherBodyTbl.getSelectionModel().getSelectedItem();
        if (publisherTM != null) {

            publisherIdShowLbl.setText(publisherTM.getPublisherID());
            nameTxt.setText(publisherTM.getName());
            addressTxt.setText(publisherTM.getAddress());
            dateShowLbl.setText(publisherTM.getRegDate().toString());

            publisherSaveBtn.setDisable(true);

            publisherUpdateBtn.setDisable(false);
            publisherDeleteBtn.setDisable(false);

            CommonUtil.userPermissionManage(publisherUpdateBtn,publisherDeleteBtn);

        }
    }


    //------------------------------------------------------------------------------------------------------------------


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        CommonUtil.userPermissionManage(publisherUpdateBtn,publisherDeleteBtn);

        setCellValues();

        try {
            refreshPage();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to refresh page").show();
        }

    }

    private void refreshPage() throws SQLException {
        loadNextPublisherId();
        loadTableData();
        setDefaultBorder();
        publisherSaveBtn.setDisable(false);

        publisherUpdateBtn.setDisable(true);
        publisherDeleteBtn.setDisable(true);

        nameTxt.setText("");
        addressTxt.setText("");
        dateShowLbl.setText(CommonUtil.date);

    }

    private void setCellValues() {
        publisherIdCol.setCellValueFactory(new PropertyValueFactory<>("publisherID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        regDateCol.setCellValueFactory(new PropertyValueFactory<>("regDate"));

    }


    private void loadTableData() throws SQLException {
        ArrayList<PublisherDTO> publisherDTOS = publisherBO.getAll();

        ObservableList<PublisherTM> publisherTMS = FXCollections.observableArrayList();

        for (PublisherDTO publisherDTO : publisherDTOS) {
            PublisherTM publisherTM = new PublisherTM(
                    publisherDTO.getPublisherID(),
                    publisherDTO.getName(),
                    publisherDTO.getAddress(),
                    publisherDTO.getRegDate()

            );
            publisherTMS.add(publisherTM);
        }

        publisherBodyTbl.setItems(publisherTMS);
    }


    public void loadNextPublisherId() throws SQLException {
        String nextAuthorId = publisherBO.getNextId();
        publisherIdShowLbl.setText(nextAuthorId);
    }


    public PublisherDTO verifySaveUpdate() throws SQLException {
        String publisherId = publisherIdShowLbl.getText();
        String name = nameTxt.getText();
        String address = addressTxt.getText();
        Date regDate = Date.valueOf(dateShowLbl.getText());

        //set default border

        setDefaultBorder();

        //assign patterns

        String namePattern = CommonUtil.namePattern;

        //create boolean conditions

        boolean isValidName = name.matches(namePattern) && !nameTxt.getText().equals("");
        boolean isNullAddress = addressTxt.getText().equals("");

        //check valid booleans

        if (!isValidName) {
            nameTxt.setStyle(nameTxt.getStyle() +  CommonUtil.redColorBorder);
            System.out.println("Invalid name!");
        }

        //check null booleans

        if (isNullAddress) {
            addressTxt.setStyle(addressTxt.getStyle() +  CommonUtil.redColorBorder);
            System.out.println("please enter address!");
        }

        //validate and return object DTO

        if (isValidName && !isNullAddress) {
            PublisherDTO publisherDTO = new PublisherDTO(
                    publisherId,
                    name,
                    address,
                    regDate
            );

            return publisherDTO;

        }
        return null;
    }

    public void setDefaultBorder() throws SQLException {
        nameTxt.setStyle(nameTxt.getStyle() + CommonUtil.defaultColorBorder);
        addressTxt.setStyle(addressTxt.getStyle() + CommonUtil.defaultColorBorder);
    }

}

