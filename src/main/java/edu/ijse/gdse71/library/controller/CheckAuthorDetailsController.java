package edu.ijse.gdse71.library.controller;

import edu.ijse.gdse71.library.dto.AuthorDetailsDTO;
import edu.ijse.gdse71.library.dto.CategoryDetailsDTO;
import edu.ijse.gdse71.library.dto.tm.AuthorDetailsTM;
import edu.ijse.gdse71.library.dto.tm.CategoryDetailsTM;
import edu.ijse.gdse71.library.model.AuthorDetailsModel;
import edu.ijse.gdse71.library.model.CategoryDetailsModel;
import edu.ijse.gdse71.library.util.CommonUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class CheckAuthorDetailsController implements Initializable {

    @FXML
    private Button backBtn;

    @FXML
    private TableColumn<AuthorDetailsTM, String> bookCol;

    @FXML
    private TableColumn<AuthorDetailsTM, String> authorCol;

    @FXML
    private AnchorPane checkAuthorDetailsBodyAnchPane;

    @FXML
    private AnchorPane checkAuthorDetailsHeaderAnchPane;

    @FXML
    private TableView<AuthorDetailsTM> checkAuthorDetailsTbl;



    //------------------------------------------------------------------------------------------------------------------

    @FXML
    void backBtnActionClicked(ActionEvent event) {
        CommonUtil.navigateTo(checkAuthorDetailsBodyAnchPane, "/view/BookBodyView.fxml");

    }

    //------------------------------------------------------------------------------------------------------------------

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setCellValues();
        try {
            refreshPage();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to refresh page").show();
        }
    }

    private void refreshPage() throws SQLException {
        loadTableData();

    }

    private void setCellValues() {
        bookCol.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("authorID"));

    }


    private void loadTableData() throws SQLException {
        ArrayList<AuthorDetailsDTO> authorDetailsDTOS = authorDetailsModel.getAllAuthorDetails();

        ObservableList<AuthorDetailsTM> authorDetailsTMS = FXCollections.observableArrayList();

        for (AuthorDetailsDTO categoryDetailsDTO : authorDetailsDTOS) {
            AuthorDetailsTM categoryDetailsTM = new AuthorDetailsTM(
                    categoryDetailsDTO.getBookID(),
                    categoryDetailsDTO.getAuthorID()
            );
            authorDetailsTMS.add(categoryDetailsTM);
        }

        checkAuthorDetailsTbl.setItems(authorDetailsTMS);
    }

}


