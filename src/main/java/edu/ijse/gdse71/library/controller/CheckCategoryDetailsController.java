package edu.ijse.gdse71.library.controller;

import edu.ijse.gdse71.library.dto.CategoryDetailsDTO;
import edu.ijse.gdse71.library.dto.tm.CategoryDetailsTM;
import edu.ijse.gdse71.library.model.CategoryDetailsModel;
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


public class CheckCategoryDetailsController implements Initializable {

    @FXML
    private Button backBtn;

    @FXML
    private TableColumn<CategoryDetailsTM, String> bookCol;

    @FXML
    private TableColumn<CategoryDetailsTM, String> categoryCol;


    @FXML
    private AnchorPane checkCategoryDetailsBodyAnchPane;

    @FXML
    private AnchorPane checkCategoryDetailsHeaderAnchPane;

    @FXML
    private TableView<CategoryDetailsTM> checkCategoryDetailsTbl;


    final CategoryDetailsModel categoryDetailsModel = new CategoryDetailsModel();


    //------------------------------------------------------------------------------------------------------------------

    @FXML
    void backBtnActionClicked(ActionEvent event) {
        CommonUtil.navigateTo(checkCategoryDetailsBodyAnchPane, "/view/BookBodyView.fxml");

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
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("categoryID"));

    }


    private void loadTableData() throws SQLException {
        ArrayList<CategoryDetailsDTO> categoryDetailsDTOS = categoryDetailsModel.getAllCategoryDetails();

        ObservableList<CategoryDetailsTM> categoryDetailsTMS = FXCollections.observableArrayList();

        for (CategoryDetailsDTO categoryDetailsDTO : categoryDetailsDTOS) {
            CategoryDetailsTM categoryDetailsTM = new CategoryDetailsTM(
                    categoryDetailsDTO.getBookID(),
                    categoryDetailsDTO.getCategoryID()
            );
            categoryDetailsTMS.add(categoryDetailsTM);
        }

        checkCategoryDetailsTbl.setItems(categoryDetailsTMS);
    }

}


