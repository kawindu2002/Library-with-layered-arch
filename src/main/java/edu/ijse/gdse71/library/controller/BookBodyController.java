package edu.ijse.gdse71.library.controller;

import edu.ijse.gdse71.library.bo.BOFactory;
import edu.ijse.gdse71.library.bo.custom.BookBO;
import edu.ijse.gdse71.library.bo.custom.BookshelfBO;
import edu.ijse.gdse71.library.bo.custom.PublisherBO;
import edu.ijse.gdse71.library.bo.custom.impl.AuthorBOImpl;
import edu.ijse.gdse71.library.bo.custom.impl.BookBOImpl;
import edu.ijse.gdse71.library.bo.custom.impl.BookshelfBOImpl;
import edu.ijse.gdse71.library.bo.custom.impl.PublisherBOImpl;
import edu.ijse.gdse71.library.dto.*;
import edu.ijse.gdse71.library.dto.tm.BookTM;
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

public class BookBodyController implements Initializable {

    @FXML
    private Button addAuthorBtn;

    @FXML
    private Button addCategoryBtn;

    @FXML
    private AnchorPane bookBodyAnchPane;

    @FXML
    private TableView<BookTM> bookBodyTbl;

    @FXML
    private Button bookDeleteBtn;

    @FXML
    private AnchorPane bookHeaderAnchPane;

    @FXML
    private TableColumn<BookTM, String> bookIdCol;

    @FXML
    private Button checkCategoryBtn;

    @FXML
    private Label bookIdLbl;

    @FXML
    private Label bookIdShowLbl;

    @FXML
    private Label bookManageLbl;

    @FXML
    private Button bookResetBtn;

    @FXML
    private Button bookSaveBtn;

    @FXML
    private Button bookUpdateBtn;

    @FXML
    private TableColumn<BookTM, String> bookshelfIdCol;

    @FXML
    private ComboBox<String> bookshelfIdCombo;

    @FXML
    private Label bookshelfIdLbl;

    @FXML
    private Label dateShowLbl;

    @FXML
    private TableColumn<BookTM, String> isbnCol;

    @FXML
    private Label isbnLbl;

    @FXML
    private TextField isbnTxt;

    @FXML
    private Label locationLbl;

    @FXML
    private Label locationShowLbl;

    @FXML
    private TableColumn<BookTM, Double> priceCol;

    @FXML
    private Label priceLbl;

    @FXML
    private TextField priceTxt;

    @FXML
    private TableColumn<BookTM, String> pubIdCol;

    @FXML
    private ComboBox<String> pubIdCombo;

    @FXML
    private Label pubIdLbl;

    @FXML
    private Label publisherNameLbl;

    @FXML
    private Label publisherNameShowLbl;

    @FXML
    private TableColumn<BookTM, Date> regDateCol;

    @FXML
    private Label regDateLbl;

    @FXML
    private TableColumn<BookTM, String> stateCol;

    @FXML
    private ComboBox<String> stateCombo;

    @FXML
    private Label stateLbl;

    @FXML
    private TableColumn<BookTM, String> titleCol;

    @FXML
    private Label titleLbl;

    @FXML
    private TextField titleTxt;

    BookBOImpl bookBO = (BookBOImpl) BOFactory.getInstance().getBO(BOFactory.BOTypes.BOOK);
    PublisherBOImpl publisherBO = (PublisherBOImpl) BOFactory.getInstance().getBO(BOFactory.BOTypes.PUBLISHER);
    BookshelfBOImpl bookshelfBO = (BookshelfBOImpl) BOFactory.getInstance().getBO(BOFactory.BOTypes.BOOKSHELF);


    //------------------------------------------------------------------------------------------------------------------

    @FXML
    public void checkCategoryBtnActionClicked(ActionEvent actionEvent) {
        CommonUtil.navigateTo(bookBodyAnchPane,"/view/CheckCategoryDetailsView.fxml");
    }

    public void checkAuthorBtnActionClicked(ActionEvent actionEvent) {
        CommonUtil.navigateTo(bookBodyAnchPane,"/view/CheckAuthorDetailsView.fxml");
    }

    @FXML
    void addAuthorBtnActionClicked(ActionEvent event) {

        CommonUtil.selectedBookId = bookIdShowLbl.getText();
        CommonUtil.navigateTo(bookBodyAnchPane,"/view/AuthorDetailsBodyView.fxml");
    }

    @FXML
    void addCategoryBtnActionClicked(ActionEvent event) {

        CommonUtil.selectedBookId = bookIdShowLbl.getText();
        CommonUtil.navigateTo(bookBodyAnchPane,"/view/CategoryDetailsBodyView.fxml");
    }

    @FXML
    void bookDeleteBtnActionClicked(ActionEvent event) throws SQLException {
        String bookId = bookIdShowLbl.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = bookBO.delete(bookId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Book deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete book...!").show();
            }
        }
    }

    @FXML
    void bookResetBtnActionClicked(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void bookSaveBtnActionClicked(ActionEvent event) throws SQLException {

        try{
            BookDTO bookDTO = verifySave();
//            boolean isNullAuthorDTOS= bookDTO.getAuthorDetailsDTOS() == null || bookDTO.getAuthorDetailsDTOS().isEmpty();
//            boolean isNullCategoryDTOS= bookDTO.getCategoryDetailsDTOS() == null || bookDTO.getCategoryDetailsDTOS().isEmpty();
            boolean isBookDTONull = bookDTO == null;


//            if (isNullAuthorDTOS){
//                new Alert(Alert.AlertType.ERROR, "Please insert authors...!").show();
//            }
//            if (isNullCategoryDTOS){
//                new Alert(Alert.AlertType.ERROR, "Please insert categories...!").show();
//            }

            //&& !isNullAuthorDTOS && !isNullCategoryDTOS
            if (!isBookDTONull ) {
                boolean isSaved = bookBO.save(bookDTO);
                if (isSaved) {
                    refreshPage();
                    new Alert(Alert.AlertType.INFORMATION, "Book saved...!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Fail to save book...!").show();
                }
            }else{
                new Alert(Alert.AlertType.ERROR, "Fail to save book...").show();
            }
        } catch (NullPointerException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to save book...").show();
        }
    }

    public void bookUpdateBtnActionClicked(ActionEvent actionEvent) throws SQLException {
        BookDTO bookDTO = verifyUpdate();
        if (bookDTO != null) {
            boolean isUpdated = bookBO.update(bookDTO);
            if (isUpdated) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Book updated...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update book...!").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Fail to update book...").show();
        }
    }

    public void onClickBookTable(MouseEvent mouseEvent) throws SQLException {
        setDefaultBorder();
        BookTM bookTM = bookBodyTbl.getSelectionModel().getSelectedItem();
        if (bookTM != null) {

            bookIdShowLbl.setText(bookTM.getBookID());
            bookshelfIdCombo.getSelectionModel().select(bookTM.getBookshelfID());
            pubIdCombo.getSelectionModel().select(bookTM.getPublisherID());
            stateCombo.getSelectionModel().select(bookTM.getState());
            priceTxt.setText(String.valueOf(bookTM.getPrice()));
            dateShowLbl.setText(String.valueOf(bookTM.getRegDate()));
            titleTxt.setText(bookTM.getTitle());
            isbnTxt.setText(bookTM.getIsbn());

            bookSaveBtn.setDisable(true);

            bookUpdateBtn.setDisable(false);
            bookDeleteBtn.setDisable(false);

            CommonUtil.userPermissionManage(bookUpdateBtn,bookDeleteBtn);


        }
    }

    public void pubIdComboActionClicked(ActionEvent actionEvent) throws SQLException {
        String selectedPublisherId = pubIdCombo.getSelectionModel().getSelectedItem();
        PublisherDTO publisherDTO = publisherBO.findById(selectedPublisherId);

        // If member found
        if (publisherDTO != null) {

            // FIll category related labels
            publisherNameShowLbl.setText(publisherDTO.getName());

        }
    }

    public void bookshelfIdComboActionClicked(ActionEvent actionEvent) throws SQLException {
        String selectedBookshelfId = bookshelfIdCombo.getSelectionModel().getSelectedItem();
        BookshelfDTO bookshelfDTO = bookshelfBO.findById(selectedBookshelfId);

        // If bookshelf found
        if (bookshelfDTO != null) {

            // FIll bookshelf related labels
            locationShowLbl.setText(bookshelfDTO.getLocation());

        }
    }


    //------------------------------------------------------------------------------------------------------------------


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CommonUtil.userPermissionManage(bookUpdateBtn,bookDeleteBtn);

        setCellValues();
        stateCombo.getItems().addAll("Available", "Checked Out","Reserved");

        try {
            refreshPage();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to refresh page").show();
        }

    }


    private void refreshPage() throws SQLException {
        loadNextBookId();
        loadTableData();
        loadPublisherId();
        loadBookshelfId();
        setDefaultBorder();
        bookSaveBtn.setDisable(false);

        bookUpdateBtn.setDisable(true);
        bookDeleteBtn.setDisable(true);

        dateShowLbl.setText(CommonUtil.date);
        bookshelfIdCombo.getSelectionModel().clearSelection();
        pubIdCombo.getSelectionModel().clearSelection();
        stateCombo.getSelectionModel().clearSelection();
        locationShowLbl.setText("");
        publisherNameShowLbl.setText("");
        titleTxt.setText("");
        isbnTxt.setText("");
        priceTxt.setText("");


    }


    private void loadPublisherId() throws SQLException {
        ArrayList<String> publisherIds = publisherBO.getAllIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(publisherIds);
        pubIdCombo.setItems(observableList);
    }

    private void loadBookshelfId() throws SQLException {
        ArrayList<String> bookshelfIds = bookshelfBO.getAllIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(bookshelfIds);
        bookshelfIdCombo.setItems(observableList);
    }


    private void setCellValues() {
        bookIdCol.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        regDateCol.setCellValueFactory(new PropertyValueFactory<>("regDate"));
        pubIdCol.setCellValueFactory(new PropertyValueFactory<>("publisherID"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        stateCol.setCellValueFactory(new PropertyValueFactory<>("state"));
        bookshelfIdCol.setCellValueFactory(new PropertyValueFactory<>("bookshelfID"));

    }


    private void loadTableData() throws SQLException {

        ArrayList<BookDTO> bookDTOS = bookBO.getAll();
        ObservableList<BookTM> bookTMS = FXCollections.observableArrayList();

        for (BookDTO bookDTO : bookDTOS) {
            BookTM bookTM = new BookTM(
                    bookDTO.getBookID(),
                    bookDTO.getTitle(),
                    bookDTO.getIsbn(),
                    bookDTO.getRegDate(),
                    bookDTO.getPublisherID(),
                    bookDTO.getPrice(),
                    bookDTO.getState(),
                    bookDTO.getBookshelfID()
            );
            bookTMS.add(bookTM);
//            bookDTO.getCategoryDetailsDTOS().clear();
//            bookDTO.getAuthorDetailsDTOS().clear();
        }

        bookBodyTbl.setItems(bookTMS);
    }


    public void loadNextBookId() throws SQLException {
        String nextBookId = bookBO.getNextId();
        bookIdShowLbl.setText(nextBookId);
    }


    public BookDTO verifySave() throws SQLException {

        String bookId = bookIdShowLbl.getText();
        String publisherId = pubIdCombo.getSelectionModel().getSelectedItem();
        String bookshelfId = bookshelfIdCombo.getSelectionModel().getSelectedItem();
        String state = stateCombo.getSelectionModel().getSelectedItem();

        String pricePattern = CommonUtil.pricePattern;
        double price = 0;

        try {
            price = Double.parseDouble(priceTxt.getText().trim());
            if (priceTxt.getText().matches(pricePattern)) {
                price = Double.valueOf(price);
            } else {
                priceTxt.setStyle(priceTxt.getStyle() + CommonUtil.redColorBorder);
            }
        } catch (IllegalArgumentException e) {
            priceTxt.setStyle(priceTxt.getStyle() + CommonUtil.redColorBorder);
        }


        Date regDate = Date.valueOf(dateShowLbl.getText());
        String title = titleTxt.getText();
        String isbn = isbnTxt.getText();
        ArrayList<CategoryDetailsDTO> categoryDetailsDTOS = CommonUtil.categoryDetailsDTOS;
        ArrayList<AuthorDetailsDTO> authorDetailsDTOS = CommonUtil.authorDetailsDTOS;


        //set default border

        setDefaultBorder();

        //create boolean conditions

        boolean isNullBookshelfId = bookshelfIdCombo.getSelectionModel().getSelectedItem() == null ||
                bookshelfIdCombo.getSelectionModel().getSelectedItem().equals("");
        boolean isNullPublisherId = pubIdCombo.getSelectionModel().getSelectedItem() == null ||
                pubIdCombo.getSelectionModel().getSelectedItem().equals("");
        boolean isNullState = stateCombo.getSelectionModel().getSelectedItem() == null ||
                stateCombo.getSelectionModel().getSelectedItem().equals("");

        boolean isValidPrice = priceTxt.getText().matches(pricePattern) && !priceTxt.getText().equals("");
        boolean isNullTitle = titleTxt.getText().equals("");
        boolean isNullIsbn = isbnTxt.getText().equals("");


        //check null booleans

        if (isNullBookshelfId) {
            new Alert(Alert.AlertType.ERROR, "please select the bookshelfId").show();
        }
        if (isNullPublisherId) {
            new Alert(Alert.AlertType.ERROR, "please select the publisherId").show();
        }
        if (isNullState) {
            new Alert(Alert.AlertType.ERROR, "please select the state").show();
        }

        if (isNullTitle) {
            titleTxt.setStyle(titleTxt.getStyle() +  CommonUtil.redColorBorder);
            System.out.println("please enter location!");
        }

        if (isNullIsbn) {
            isbnTxt.setStyle(isbnTxt.getStyle() +  CommonUtil.redColorBorder);
            System.out.println("please enter location!");
        }

        //check valid booleans

        if (!isValidPrice) {
            priceTxt.setStyle(priceTxt.getStyle() +  CommonUtil.redColorBorder);
            System.out.println("Invalid price!");
        }


        //validate and return object DTO

        if (!isNullBookshelfId && !isNullPublisherId && !isNullState && isValidPrice && !isNullTitle && !isNullIsbn ) {
            BookDTO bookDTO = new BookDTO(
                    bookId,
                    title,
                    isbn,
                    regDate,
                    publisherId,
                    price,
                    state,
                    bookshelfId

            );


            return bookDTO;
        }
        return null;
    }

    public void setDefaultBorder() throws SQLException {

        priceTxt.setStyle(priceTxt.getStyle() + CommonUtil.defaultColorBorder);
        titleTxt.setStyle(titleTxt.getStyle() + CommonUtil.defaultColorBorder);
        isbnTxt.setStyle(isbnTxt.getStyle() + CommonUtil.defaultColorBorder);
    }

    public BookDTO verifyUpdate() throws SQLException {
        String bookId = bookIdShowLbl.getText();
        String publisherId = pubIdCombo.getSelectionModel().getSelectedItem();
        String bookshelfId = bookshelfIdCombo.getSelectionModel().getSelectedItem();
        String state = stateCombo.getSelectionModel().getSelectedItem();

        String pricePattern = CommonUtil.pricePattern;
        double price = 0;

        try {
            price = Double.parseDouble(priceTxt.getText().trim());
            if (priceTxt.getText().matches(pricePattern)) {
                price = Double.valueOf(price);
            } else {
                priceTxt.setStyle(priceTxt.getStyle() + CommonUtil.redColorBorder);
            }
        } catch (IllegalArgumentException e) {
            priceTxt.setStyle(priceTxt.getStyle() + CommonUtil.redColorBorder);
        }

        Date regDate = Date.valueOf(dateShowLbl.getText());
        String title = titleTxt.getText();
        String isbn = isbnTxt.getText();


        //set default border

        setDefaultBorder();

        //create boolean conditions

        boolean isNullBookshelfId = bookshelfIdCombo.getSelectionModel().getSelectedItem() == null ||
                bookshelfIdCombo.getSelectionModel().getSelectedItem().equals("");
        boolean isNullPublisherId = pubIdCombo.getSelectionModel().getSelectedItem() == null ||
                pubIdCombo.getSelectionModel().getSelectedItem().equals("");
        boolean isNullState = stateCombo.getSelectionModel().getSelectedItem() == null ||
                stateCombo.getSelectionModel().getSelectedItem().equals("");

        boolean isValidPrice = priceTxt.getText().matches(pricePattern) && !priceTxt.getText().equals("");
        boolean isNullTitle = titleTxt.getText().equals("");
        boolean isNullIsbn = isbnTxt.getText().equals("");


        //check null booleans

        if (isNullBookshelfId) {
            new Alert(Alert.AlertType.ERROR, "please select the bookshelfId").show();
        }
        if (isNullPublisherId) {
            new Alert(Alert.AlertType.ERROR, "please select the publisherId").show();
        }
        if (isNullState) {
            new Alert(Alert.AlertType.ERROR, "please select the state").show();
        }

        if (isNullTitle) {
            titleTxt.setStyle(titleTxt.getStyle() +  CommonUtil.redColorBorder);
            System.out.println("please enter location!");
        }

        if (isNullIsbn) {
            isbnTxt.setStyle(isbnTxt.getStyle() +  CommonUtil.redColorBorder);
            System.out.println("please enter location!");
        }

        //check valid booleans

        if (!isValidPrice) {
            priceTxt.setStyle(priceTxt.getStyle() +  CommonUtil.redColorBorder);
            System.out.println("Invalid price!");
        }


        //validate and return object DTO

        if (!isNullBookshelfId && !isNullPublisherId && !isNullState && isValidPrice && !isNullTitle && !isNullIsbn ) {
            BookDTO bookWithoutDetailsDTO = new BookDTO(
                    bookId,
                    title,
                    isbn,
                    regDate,
                    publisherId,
                    price,
                    state,
                    bookshelfId

            );
            return bookWithoutDetailsDTO;

        }
        return null;
    }
}


