package edu.ijse.gdse71.library.controller;

import edu.ijse.gdse71.library.bo.custom.BookshelfBO;
import edu.ijse.gdse71.library.bo.custom.CategoryBO;
import edu.ijse.gdse71.library.bo.custom.impl.BookshelfBOImpl;
import edu.ijse.gdse71.library.bo.custom.impl.CategoryBOImpl;
import edu.ijse.gdse71.library.dto.BookshelfDTO;
import edu.ijse.gdse71.library.dto.CategoryDTO;
import edu.ijse.gdse71.library.dto.tm.BookshelfTM;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class BookshelfBodyController implements Initializable {

    @FXML
    private AnchorPane bookshelfBodyAnchPane;

    @FXML
    private TableView<BookshelfTM> bookshelfBodyTbl;

    @FXML
    private Button bookshelfDeleteBtn;

    @FXML
    private AnchorPane bookshelfHeaderAnchPane;

    @FXML
    private TableColumn<BookshelfTM, String> bookshelfIdCol;

    @FXML
    private Label bookshelfIdLbl;

    @FXML
    private Label bookshelfIdShowLbl;

    @FXML
    private Label bookshelfManageLbl;

    @FXML
    private Button bookshelfResetBtn;

    @FXML
    private Button bookshelfSaveBtn;

    @FXML
    private Button bookshelfUpdateBtn;

    @FXML
    private TableColumn<BookshelfTM, Integer> capacityCol;

    @FXML
    private Label capacityLbl;

    @FXML
    private TextField capacityTxt;

    @FXML
    private TableColumn<BookshelfTM, String> categoryIdCol;

    @FXML
    private ComboBox<String> categoryIdCombo;

    @FXML
    private Label categoryIdLbl;

    @FXML
    private TableColumn<BookshelfTM, String> locationCol;

    @FXML
    private Label locationLbl;

    @FXML
    private TextField locationTxt;

    @FXML
    private Label descriptionLbl;

    @FXML
    private Label descriptionShowLbl;

    BookshelfBO bookshelfBO = new BookshelfBOImpl();
    CategoryBO categoryBO = new CategoryBOImpl();


    //------------------------------------------------------------------------------------------------------------------

    @FXML
    void bookshelfDeleteBtnActionClicked(ActionEvent event) throws SQLException {
        String bookshelfId = bookshelfIdShowLbl.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = bookshelfBO.delete(bookshelfId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Bookshelf deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete bookshelf...!").show();
            }
        }
    }

    @FXML
    void bookshelfResetBtnActionClicked(ActionEvent event) throws SQLException {
        refreshPage();

    }

    @FXML
    void bookshelfSaveBtnActionClicked(ActionEvent event) throws SQLException {
        BookshelfDTO bookshelfDTO = verifySaveUpdate();
        if (bookshelfDTO != null) {
            boolean isSaved = bookshelfBO.save(bookshelfDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Bookshelf saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save bookshelf...!").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Fail to save bookshelf...").show();
        }
    }

    @FXML
    void bookshelfUpdateBtnActionClicked(ActionEvent event) throws SQLException {
        BookshelfDTO bookshelfDTO = verifySaveUpdate();
        if (bookshelfDTO != null) {
            boolean isUpdated = bookshelfBO.update(bookshelfDTO);
            if (isUpdated) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Bookshelf updated...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update bookshelf...!").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Fail to update bookshelf...").show();
        }
    }

    public void onClickBookshelfTable(MouseEvent mouseEvent) throws SQLException {
        setDefaultBorder();
        BookshelfTM bookshelfTM = bookshelfBodyTbl.getSelectionModel().getSelectedItem();
        if (bookshelfTM != null) {

            bookshelfIdShowLbl.setText(bookshelfTM.getBookshelfID());
            categoryIdCombo.getSelectionModel().select(bookshelfTM.getCategoryID());
            capacityTxt.setText(String.valueOf(bookshelfTM.getCapacity()));
            locationTxt.setText(bookshelfTM.getLocation());

            bookshelfSaveBtn.setDisable(true);

            bookshelfUpdateBtn.setDisable(false);
            bookshelfDeleteBtn.setDisable(false);

            CommonUtil.userPermissionManage(bookshelfUpdateBtn,bookshelfDeleteBtn);

        }
    }

    @FXML
    void categoryComboOnAction(ActionEvent event) throws SQLException {
        String selectedCategoryId = categoryIdCombo.getSelectionModel().getSelectedItem();
        CategoryDTO categoryDTO = categoryBO.findById(selectedCategoryId);

        // If category found (categoryDTO not null)
        if (categoryDTO != null) {

            // FIll category related labels
            descriptionShowLbl.setText(categoryDTO.getDescription());

        }
    }



    //------------------------------------------------------------------------------------------------------------------


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        CommonUtil.userPermissionManage(bookshelfUpdateBtn,bookshelfDeleteBtn);

        setCellValues();

        try {
            refreshPage();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to refresh page").show();
        }

    }

    private void refreshPage() throws SQLException {
        loadNextBookshelfId();
        loadTableData();
        loadCategoryId();
        setDefaultBorder();
        bookshelfSaveBtn.setDisable(false);

        bookshelfUpdateBtn.setDisable(true);
        bookshelfDeleteBtn.setDisable(true);

        categoryIdCombo.getSelectionModel().clearSelection();
        capacityTxt.setText("");
        locationTxt.setText("");
        descriptionShowLbl.setText("");

    }


    private void loadCategoryId() throws SQLException {
        ArrayList<String> categoryIds = categoryBO.getAllIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(categoryIds);
        categoryIdCombo.setItems(observableList);
    }


    private void setCellValues() {
        bookshelfIdCol.setCellValueFactory(new PropertyValueFactory<>("bookshelfID"));
        categoryIdCol.setCellValueFactory(new PropertyValueFactory<>("categoryID"));
        capacityCol.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));

    }


    private void loadTableData() throws SQLException {
        ArrayList<BookshelfDTO> bookshelfDTOS = bookshelfBO.getAll();

        ObservableList<BookshelfTM> bookshelfTMS = FXCollections.observableArrayList();

        for (BookshelfDTO bookshelfDTO : bookshelfDTOS) {
            BookshelfTM bookshelfTM = new BookshelfTM(
                    bookshelfDTO.getBookshelfID(),
                    bookshelfDTO.getCategoryID(),
                    bookshelfDTO.getCapacity(),
                    bookshelfDTO.getLocation()
            );
            bookshelfTMS.add(bookshelfTM);
        }

        bookshelfBodyTbl.setItems(bookshelfTMS);
    }


    public void loadNextBookshelfId() throws SQLException {
        String nextBookshelfId = bookshelfBO.getNextId();
        bookshelfIdShowLbl.setText(nextBookshelfId);
    }


    public BookshelfDTO verifySaveUpdate() throws SQLException {
        String bookshelfId = bookshelfIdShowLbl.getText();
        String categoryId = categoryIdCombo.getSelectionModel().getSelectedItem();
        int capacity = 0;
        try {
            capacity = Integer.parseInt(capacityTxt.getText());
        }catch (NumberFormatException e){
            new Alert(Alert.AlertType.ERROR, "please enter valid capacity").show();
        }
        String location = locationTxt.getText();

        //set default border

        setDefaultBorder();

        //assign patterns

        String numericPattern = CommonUtil.zeroPositivePattern;

        //create boolean conditions

        boolean isValidCapacity = capacityTxt.getText().matches(numericPattern) && !capacityTxt.getText().equals("");
        boolean isNullLocation = locationTxt.getText().equals("");
        boolean isNullCategoryId = categoryIdCombo.getSelectionModel().getSelectedItem() == null ||
                categoryIdCombo.getSelectionModel().getSelectedItem().equals("");

        //check valid booleans

        if (!isValidCapacity) {
            capacityTxt.setStyle(capacityTxt.getStyle() + CommonUtil.redColorBorder);
            System.out.println("Invalid capacity!");

        }

        //check null booleans

        if (isNullCategoryId) {
            new Alert(Alert.AlertType.ERROR, "please select the categoryId").show();
        }

        if (isNullLocation) {
            locationTxt.setStyle(locationTxt.getStyle() +  CommonUtil.redColorBorder);
            System.out.println("please enter location!");
        }

        //validate and return object DTO

        if (isValidCapacity && !isNullLocation && !isNullCategoryId) {
            BookshelfDTO bookshelfDTO = new BookshelfDTO(
                    bookshelfId,
                    categoryId,
                    capacity,
                    location
            );

            return bookshelfDTO;

        }

        return null;
    }

    public void setDefaultBorder() throws SQLException {
        capacityTxt.setStyle(capacityTxt.getStyle() + CommonUtil.defaultColorBorder);
        locationTxt.setStyle(locationTxt.getStyle() + CommonUtil.defaultColorBorder);
    }
}

