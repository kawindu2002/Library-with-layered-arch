package edu.ijse.gdse71.library.controller;

import edu.ijse.gdse71.library.bo.custom.CategoryBO;
import edu.ijse.gdse71.library.bo.impl.CategoryBOImpl;
import edu.ijse.gdse71.library.dto.CategoryDTO;
import edu.ijse.gdse71.library.dto.tm.CategoryTM;
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

public class CategoryBodyController implements Initializable {

    @FXML
    private AnchorPane categoryBodyAnchPane;

    @FXML
    private TableView<CategoryTM> categoryBodyTbl;

    @FXML
    private Button categoryDeleteBtn;

    @FXML
    private AnchorPane categoryHeaderAnchPane;

    @FXML
    private TableColumn<CategoryTM, String> categoryIdCol;

    @FXML
    private Label categoryIdLbl;

    @FXML
    private Label categoryIdShowLbl;

    @FXML
    private Label categoryManageLbl;

    @FXML
    private Button categoryResetBtn;

    @FXML
    private Button categorySaveBtn;

    @FXML
    private Button categoryUpdateBtn;

    @FXML
    private Label dateShowLbl;

    @FXML
    private TableColumn<CategoryTM, String> descriptionCol;

    @FXML
    private Label descriptionLbl;

    @FXML
    private TextField descriptionTxt;

    @FXML
    private TableColumn<CategoryTM, Date> regDateCol;

    @FXML
    private Label regDateLbl;

    CategoryBO categoryBO = new CategoryBOImpl();


    //------------------------------------------------------------------------------------------------------------------


    @FXML
    void categoryDeleteBtnActionClicked(ActionEvent event) throws SQLException {
        String categoryId = categoryIdShowLbl.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = categoryBO.delete(categoryId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Category deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete category...!").show();
            }
        }
    }


    @FXML
    void categoryResetBtnActionClicked(ActionEvent event) throws SQLException {
        refreshPage();

    }

    @FXML
    void categorySaveBtnActionClicked(ActionEvent event) throws SQLException {
        CategoryDTO categoryDTO = verifySaveUpdate();
        if (categoryDTO != null) {
            boolean isSaved = categoryBO.save(categoryDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Category saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save category...!").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to save category...").show();
        }
    }

    @FXML
    void categoryUpdateBtnActionClicked(ActionEvent event) throws SQLException {
        CategoryDTO categoryDTO = verifySaveUpdate();
        if (categoryDTO != null) {
            boolean isUpdated = categoryBO.update(categoryDTO);
            if (isUpdated) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Category updated...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update category...!").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to update category...").show();
        }
    }

    public void onClickCategoryTable(MouseEvent mouseEvent) throws SQLException {
        setDefaultBorder();
        CategoryTM categoryTM = categoryBodyTbl.getSelectionModel().getSelectedItem();
        if (categoryTM != null) {

            categoryIdShowLbl.setText(categoryTM.getCategoryID());
            descriptionTxt.setText(categoryTM.getDescription());
            dateShowLbl.setText(categoryTM.getRegDate().toString());

            categorySaveBtn.setDisable(true);

            categoryUpdateBtn.setDisable(false);
            categoryDeleteBtn.setDisable(false);

            CommonUtil.userPermissionManage(categoryUpdateBtn,categoryDeleteBtn);

        }
    }


    //------------------------------------------------------------------------------------------------------------------


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CommonUtil.userPermissionManage(categoryUpdateBtn,categoryDeleteBtn);

        setCellValues();

        try {
            refreshPage();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to refresh page").show();
        }
    }

    private void refreshPage() throws SQLException {
        loadNextCategoryId();
        loadTableData();
        setDefaultBorder();

        categorySaveBtn.setDisable(false);

        categoryUpdateBtn.setDisable(true);
        categoryDeleteBtn.setDisable(true);

        descriptionTxt.setText("");
    }


    private void setCellValues() {
        categoryIdCol.setCellValueFactory(new PropertyValueFactory<>("categoryID"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        regDateCol.setCellValueFactory(new PropertyValueFactory<>("regDate"));

    }

    private void loadTableData() throws SQLException {
        ArrayList<CategoryDTO> categoryDTOS = categoryBO.getAll();

        ObservableList<CategoryTM> categoryTMS = FXCollections.observableArrayList();

        for (CategoryDTO categoryDTO : categoryDTOS) {
            CategoryTM categoryTM = new CategoryTM(
                    categoryDTO.getCategoryID(),
                    categoryDTO.getDescription(),
                    categoryDTO.getRegDate()
            );
            categoryTMS.add(categoryTM);
        }

        categoryBodyTbl.setItems(categoryTMS);
    }


    public void loadNextCategoryId() throws SQLException {
        String nextCategoryId = categoryBO.getNextId();
        categoryIdShowLbl.setText(nextCategoryId);

    }


    public CategoryDTO verifySaveUpdate() throws SQLException {
        String categoryId = categoryIdShowLbl.getText();
        String description = descriptionTxt.getText();
        Date regDate = Date.valueOf(dateShowLbl.getText());

        //set default border

        setDefaultBorder();

        //create boolean conditions

        boolean isNullDescription = descriptionTxt.getText().equals("");

        //check null booleans

        if (isNullDescription) {
            descriptionTxt.setStyle(descriptionTxt.getStyle() + CommonUtil.redColorBorder);
            System.out.println("please enter description!");
        }

        //validate and return object DTO

        if (!isNullDescription) {
            CategoryDTO categoryDTO = new CategoryDTO(
                    categoryId,
                    description,
                    regDate
            );

            return categoryDTO;
        }

        return null;
    }

    public void setDefaultBorder() throws SQLException {
        descriptionTxt.setStyle(descriptionTxt.getStyle()+CommonUtil.defaultColorBorder);

    }


}


