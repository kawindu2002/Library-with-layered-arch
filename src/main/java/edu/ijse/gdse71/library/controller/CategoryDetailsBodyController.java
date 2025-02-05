package edu.ijse.gdse71.library.controller;

import edu.ijse.gdse71.library.bo.custom.CategoryBO;
import edu.ijse.gdse71.library.bo.custom.impl.CategoryBOImpl;
import edu.ijse.gdse71.library.dto.CategoryDTO;
import edu.ijse.gdse71.library.dto.CategoryDetailsDTO;
import edu.ijse.gdse71.library.dto.tm.CategoryCartTM;
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

public class CategoryDetailsBodyController implements Initializable {

    @FXML
    private TableColumn<CategoryCartTM, Button> actionCol;

    @FXML
    private Button addToListBtn;

    @FXML
    private Label bookIdLbl;

    @FXML
    private Label bookIdShowLbl;

    @FXML
    private AnchorPane categoryDetailsBodyAnchPane;

    @FXML
    private Button backBtn;

    @FXML
    private TableColumn<CategoryCartTM, String> categoryDetailsCartCategoryIdCol;

    @FXML
    private TableView<CategoryCartTM> categoryDetailsCartTbl;

    @FXML
    private AnchorPane categoryDetailsHeaderAnchPane;

    @FXML
    private Label categoryDetailsManageLbl;

    @FXML
    private ComboBox<String> categoryIdCombo;

    @FXML
    private Label categoryIdLbl;

    @FXML
    private Label descriptionLbl;

    @FXML
    private Label descriptionShowLbl;

    @FXML
    private Button insertCategoriesBtn;

    @FXML
    private Button resetBtn;

    @FXML
    String selectedCategoryId;


    // Observable list to manage cart items in TableView
    private final ObservableList<CategoryCartTM> categoryCartTMS = FXCollections.observableArrayList();

    CategoryBO categoryBO = new CategoryBOImpl();

    //------------------------------------------------------------------------------------------------------------------


    @FXML
    void addToListBtnActionClicked(ActionEvent event) throws SQLException {
        if (categoryDetailsCartTbl.getItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please add categories to table..!").show();
            return;
        }

        String bookId = CommonUtil.selectedBookId;
        String categoryId = selectedCategoryId;

        // List to hold category details
        ArrayList<CategoryDetailsDTO> categoryDetailsDTOS = new ArrayList<>();

        for (CategoryCartTM categoryCartTM : categoryCartTMS) {
            CategoryDetailsDTO categoryDetailsDTO = new CategoryDetailsDTO(
                    bookId,
                    categoryCartTM.getCategoryID()
            );

            categoryDetailsDTOS.add(categoryDetailsDTO);
        }

        if(categoryDetailsDTOS != null){
            CommonUtil.categoryDetailsDTOS = categoryDetailsDTOS;
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, "Category list added successfully..!").show();
            CommonUtil.navigateTo(categoryDetailsBodyAnchPane,"/view/BookBodyView.fxml");

        }else{
            new Alert(Alert.AlertType.ERROR, "No data found..!").show();
        }
    }


    @FXML
    void insertCategoriesBtnActionClicked(ActionEvent event) throws SQLException {

        if (selectedCategoryId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select category..!").show();
            return;
        }


        // Loop through each item in cart's observable list.
        for (CategoryCartTM categoryCartTM : categoryCartTMS) {
            // Check if the item is already in the cart
            if (categoryCartTM.getCategoryID().equals(selectedCategoryId)) {
                new Alert(Alert.AlertType.ERROR, "Category Id already exists..!").show();
                categoryDetailsCartTbl.refresh();
                return;
            }
        }

        Button removeBtn = new Button("Remove");

        // If the item does not already exist in the cart, create a new CartTM object to represent it.
        CategoryCartTM newCategoryCartTM = new CategoryCartTM(
                selectedCategoryId,
                removeBtn
        );

        // Set an action for the "Remove" button, which removes the item from the cart when clicked.
        removeBtn.setOnAction(actionEvent -> {

            // Remove the item from the cart's observable list (cartTMS).
            categoryCartTMS.remove(newCategoryCartTM);
            categoryIdCombo.getSelectionModel().clearSelection();
            descriptionShowLbl.setText("");


            // Refresh the table to reflect the removal of the item.
            categoryDetailsCartTbl.refresh();
        });


        // Add the newly created CartTM object to the cart's observable list.
        categoryCartTMS.add(newCategoryCartTM);
        categoryIdCombo.getSelectionModel().clearSelection();
        descriptionShowLbl.setText("");
        addToListBtn.setDisable(false);


    }

    @FXML
    void resetBtnActionClicked(ActionEvent event) throws SQLException {
        refreshPage();
    }

    public void categoryIdComboOnAction(ActionEvent actionEvent) throws SQLException {
        selectedCategoryId = categoryIdCombo.getSelectionModel().getSelectedItem();
        CategoryDTO categoryDTO = categoryBO.findById(selectedCategoryId);

        // If category found (categoryDTO not null)
        if (categoryDTO != null) {
            // FIll category related labels
            descriptionShowLbl.setText(categoryDTO.getDescription());

        }
    }


    public void backBtnActionClicked(ActionEvent actionEvent) {
        CommonUtil.navigateTo(categoryDetailsBodyAnchPane,"/view/BookBodyView.fxml");
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
        loadCategoryId();

        addToListBtn.setDisable(true);
        insertCategoriesBtn.setDisable(false);

        categoryIdCombo.getSelectionModel().clearSelection();
        descriptionShowLbl.setText("");
        categoryCartTMS.clear();

        categoryDetailsCartTbl.refresh();
    }


    private void loadCategoryId() throws SQLException {
        ArrayList<String> categoryIds = categoryBO.getAllIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(categoryIds);
        categoryIdCombo.setItems(observableList);
    }


    private void setCellValues() {
        categoryDetailsCartCategoryIdCol.setCellValueFactory(new PropertyValueFactory<>("categoryID"));
        actionCol.setCellValueFactory(new PropertyValueFactory<>("removeBtn"));
        categoryDetailsCartTbl.setItems(categoryCartTMS);

    }

}



