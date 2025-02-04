package edu.ijse.gdse71.library.controller;

import edu.ijse.gdse71.library.bo.custom.AuthorBO;
import edu.ijse.gdse71.library.bo.impl.AuthorBOImpl;
import edu.ijse.gdse71.library.dto.AuthorDTO;
import edu.ijse.gdse71.library.dto.tm.AuthorTM;
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

public class AuthorBodyController implements Initializable {

    @FXML
    private AnchorPane authorBodyAnchPane;

    @FXML
    private TableView<AuthorTM> authorBodyTbl;

    @FXML
    private Button authorDeleteBtn;

    @FXML
    private AnchorPane authorHeaderAnchPane;

    @FXML
    private TableColumn<AuthorTM, String> authorIdCol;

    @FXML
    private Label authorIdLbl;

    @FXML
    private Label authorIdShowLbl;

    @FXML
    private Label authorManageLbl;

    @FXML
    private Button authorResetBtn;

    @FXML
    private Button authorSaveBtn;

    @FXML
    private Button authorUpdateBtn;

    @FXML
    private TableColumn<AuthorTM, String> biographyCol;

    @FXML
    private Label biographyLbl;

    @FXML
    private TextField biographyTxt;

    @FXML
    private Label dateShowLbl;

    @FXML
    private TableColumn<AuthorTM, String> nameCol;

    @FXML
    private Label nameLbl;

    @FXML
    private TextField nameTxt;

    @FXML
    private TableColumn<AuthorTM, Date> regDateCol;

    @FXML
    private Label regDateLbl;

    //property injection (Dependency injection)
    AuthorBO authorBO = new AuthorBOImpl();

    //------------------------------------------------------------------------------------------------------------------

    @FXML
    void authorDeleteBtnActionClicked(ActionEvent event) throws SQLException {
        String authorId = authorIdShowLbl.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = authorBO.delete(authorId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Author deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete author...!").show();
            }
        }
    }

    @FXML
    void authorResetBtnActionClicked(ActionEvent event) throws SQLException {
        refreshPage();

    }

    @FXML
    void authorSaveBtnActionClicked(ActionEvent event) throws SQLException {
        AuthorDTO authorDTO = verifySaveUpdate();
        if (authorDTO != null) {
            boolean isSaved = authorBO.save(authorDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Author saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save author...!").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Fail to save author...").show();
        }
    }

    @FXML
    void authorUpdateBtnActionClicked(ActionEvent event) throws SQLException {
        AuthorDTO authorDTO = verifySaveUpdate();
        if (authorDTO != null) {
            boolean isUpdated = authorBO.update(authorDTO);
            if (isUpdated) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Author updated...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update author...!").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Fail to update author...").show();
        }
    }

    public void onClickAuthorTable(MouseEvent mouseEvent) throws SQLException{
        setDefaultBorder();
        AuthorTM authorTM = authorBodyTbl.getSelectionModel().getSelectedItem();
        if (authorTM != null) {

            authorIdShowLbl.setText(authorTM.getAuthorID());
            nameTxt.setText(authorTM.getName());
            biographyTxt.setText(authorTM.getBiography());
            dateShowLbl.setText(authorTM.getRegDate().toString());

            authorSaveBtn.setDisable(true);

            authorUpdateBtn.setDisable(false);
            authorDeleteBtn.setDisable(false);
            CommonUtil.userPermissionManage(authorUpdateBtn,authorDeleteBtn);

        }
    }

    //------------------------------------------------------------------------------------------------------------------

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CommonUtil.userPermissionManage(authorUpdateBtn,authorDeleteBtn);

        setCellValues();

        try {
            refreshPage();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to refresh page").show();
        }

    }

    private void refreshPage() throws SQLException {
        loadNextAuthorId();
        loadTableData();
        setDefaultBorder();
        authorSaveBtn.setDisable(false);

        authorUpdateBtn.setDisable(true);
        authorDeleteBtn.setDisable(true);

        nameTxt.setText("");
        biographyTxt.setText("");
        dateShowLbl.setText(CommonUtil.date);

    }


    private void setCellValues() {
        authorIdCol.setCellValueFactory(new PropertyValueFactory<>("authorID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        biographyCol.setCellValueFactory(new PropertyValueFactory<>("biography"));
        regDateCol.setCellValueFactory(new PropertyValueFactory<>("regDate"));

    }


    private void loadTableData() throws SQLException {
        ArrayList<AuthorDTO> authorDTOS = authorBO.getAll();

        ObservableList<AuthorTM> authorTMS = FXCollections.observableArrayList();

        for (AuthorDTO authorDTO : authorDTOS) {
            AuthorTM authorTM = new AuthorTM(
                    authorDTO.getAuthorID(),
                    authorDTO.getName(),
                    authorDTO.getBiography(),
                    authorDTO.getRegDate()

            );
            authorTMS.add(authorTM);
        }

        authorBodyTbl.setItems(authorTMS);
    }

    public void loadNextAuthorId() throws SQLException {
        String nextAuthorId = authorBO.getNextId();
        authorIdShowLbl.setText(nextAuthorId);
    }


    public AuthorDTO verifySaveUpdate() throws SQLException {
        String authorId = authorIdShowLbl.getText();
        String name = nameTxt.getText();
        String biography = biographyTxt.getText();
        Date regDate = Date.valueOf(dateShowLbl.getText());

        //set default border

        setDefaultBorder();

        //assign patterns

        String namePattern = CommonUtil.namePattern;

        //create boolean conditions

        boolean isValidName = name.matches(namePattern) && !nameTxt.getText().equals("");
        boolean isNullBiography = biographyTxt.getText().equals("");

        //check valid booleans

        if (!isValidName) {
            nameTxt.setStyle(nameTxt.getStyle() +  CommonUtil.redColorBorder);
            System.out.println("Invalid name!");
        }

        //check null booleans

        if (isNullBiography) {
            biographyTxt.setStyle(biographyTxt.getStyle() +  CommonUtil.redColorBorder);
            System.out.println("please enter biography!");
        }

        //validate and return object DTO

        if (isValidName && !isNullBiography) {
            AuthorDTO authorDTO = new AuthorDTO(
                    authorId,
                    name,
                    biography,
                    regDate
            );

            return authorDTO;

        }
        return null;
    }

    public void setDefaultBorder() throws SQLException {
        nameTxt.setStyle(nameTxt.getStyle() + CommonUtil.defaultColorBorder);
        biographyTxt.setStyle(biographyTxt.getStyle() + CommonUtil.defaultColorBorder);
    }

}
