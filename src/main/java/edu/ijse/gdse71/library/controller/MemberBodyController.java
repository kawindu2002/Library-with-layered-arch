package edu.ijse.gdse71.library.controller;

import edu.ijse.gdse71.library.bo.BOFactory;
import edu.ijse.gdse71.library.bo.custom.MemberBO;
import edu.ijse.gdse71.library.bo.custom.impl.LoanBOImpl;
import edu.ijse.gdse71.library.bo.custom.impl.MemberBOImpl;
import edu.ijse.gdse71.library.dto.MemberDTO;
import edu.ijse.gdse71.library.dto.tm.MemberTM;
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

public class MemberBodyController implements Initializable {

    @FXML
    private TableColumn<MemberTM, String> addressCol;

    @FXML
    private Label addressLbl;

    @FXML
    private TextField addressTxt;

    @FXML
    private TableColumn<MemberTM, String> contactCol;

    @FXML
    private Label contactLbl;

    @FXML
    private TextField contactTxt;

    @FXML
    private Label dateShowLbl;

    @FXML
    private TableColumn<MemberTM, String> emailCol;

    @FXML
    private Label emailLbl;

    @FXML
    private TextField emailTxt;

    @FXML
    private TableColumn<MemberTM, String> memIdCol;

    @FXML
    private Label memIdLbl;

    @FXML
    private Label memIdShowLbl;

    @FXML
    private Label memManageLbl;

    @FXML
    private AnchorPane memberBodyAnchPane;

    @FXML
    private TableView<MemberTM> memberBodyTbl;

    @FXML
    private Button memberDeleteBtn;

    @FXML
    private AnchorPane memberHeaderAnchPane;

    @FXML
    private Button memberResetBtn;

    @FXML
    private Button memberSaveBtn;

    @FXML
    private Button memberUpdateBtn;

    @FXML
    private TableColumn<MemberTM, String> nameCol;

    @FXML
    private Label nameLbl;

    @FXML
    private TextField nameTxt;

    @FXML
    private TableColumn<MemberTM, Date> regDateCol;

    @FXML
    private Label regDateLbl;

    @FXML
    private TableColumn<MemberTM, String> stateCol;

    @FXML
    private ComboBox<String> stateCombo;

    @FXML
    private Label stateLbl;

    MemberBOImpl memberBO = (MemberBOImpl) BOFactory.getInstance().getBO(BOFactory.BOTypes.MEMBER);

    //------------------------------------------------------------------------------------------------------------------


    @FXML
    void memberDeleteBtnActionClicked(ActionEvent event) throws SQLException {
        String memberId = memIdShowLbl.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = memberBO.delete(memberId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Member deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete member...!").show();
            }
        }
    }


    @FXML
    void memberResetBtnActionClicked(ActionEvent event) throws SQLException {
        refreshPage();
    }


    @FXML
    void memberSaveBtnActionClicked(ActionEvent event) throws SQLException {
        MemberDTO memberDTO = verifySaveUpdate();
        if (memberDTO != null) {
            boolean isSaved = memberBO.save(memberDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Member saved...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save member...!").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Fail to save member...").show();
        }

    }

    @FXML
    void memberUpdateBtnActionClicked(ActionEvent event) throws SQLException {
        MemberDTO memberDTO = verifySaveUpdate();
        if (memberDTO != null) {
            boolean isUpdated = memberBO.update(memberDTO);
            if (isUpdated) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "Member updated...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update member...!").show();
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Fail to update member...").show();
        }
    }

    public void onClickMemberTable(MouseEvent mouseEvent) throws SQLException {
        setDefaultBorder();
        MemberTM memberTM = memberBodyTbl.getSelectionModel().getSelectedItem();
        if (memberTM != null) {

            memIdShowLbl.setText(memberTM.getMemberID());
            nameTxt.setText(memberTM.getName());
            addressTxt.setText(memberTM.getAddress());
            emailTxt.setText(memberTM.getEmail());
            contactTxt.setText(memberTM.getContact());
            dateShowLbl.setText(memberTM.getRegDate().toString());
            stateCombo.getSelectionModel().select(memberTM.getState());

            memberSaveBtn.setDisable(true);

            memberUpdateBtn.setDisable(false);
            memberDeleteBtn.setDisable(false);

            CommonUtil.userPermissionManage(memberUpdateBtn,memberDeleteBtn);

        }
    }


    //------------------------------------------------------------------------------------------------------------------

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        CommonUtil.userPermissionManage(memberUpdateBtn,memberDeleteBtn);

        setCellValues();
        stateCombo.getItems().addAll("Inactive","Active");

        try {
            refreshPage();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to refresh page").show();
        }

    }


    private void refreshPage() throws SQLException {
        loadNextMemberId();
        loadTableData();
        setDefaultBorder();

        memberSaveBtn.setDisable(false);

        memberUpdateBtn.setDisable(true);
        memberDeleteBtn.setDisable(true);

        nameTxt.setText("");
        addressTxt.setText("");
        emailTxt.setText("");
        contactTxt.setText("");
        dateShowLbl.setText(CommonUtil.date);
        stateCombo.getSelectionModel().clearSelection();

    }


    private void setCellValues() {
        memIdCol.setCellValueFactory(new PropertyValueFactory<>("memberID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        regDateCol.setCellValueFactory(new PropertyValueFactory<>("regDate"));
        stateCol.setCellValueFactory(new PropertyValueFactory<>("state"));

    }


    private void loadTableData() throws SQLException {
        ArrayList<MemberDTO> memberDTOS = memberBO.getAll();

        ObservableList<MemberTM> memberTMS = FXCollections.observableArrayList();

        for (MemberDTO memberDTO : memberDTOS) {
            MemberTM memberTM = new MemberTM(
                    memberDTO.getMemberID(),
                    memberDTO.getName(),
                    memberDTO.getAddress(),
                    memberDTO.getEmail(),
                    memberDTO.getContact(),
                    memberDTO.getRegDate(),
                    memberDTO.getState()
            );
            memberTMS.add(memberTM);
        }

        memberBodyTbl.setItems(memberTMS);
    }


    public void loadNextMemberId() throws SQLException {
        String nextMemberId = memberBO.getNextId();
        memIdShowLbl.setText(nextMemberId);
    }


    public MemberDTO verifySaveUpdate() throws SQLException {
        String memberId = memIdShowLbl.getText();
        String name = nameTxt.getText();
        String address = addressTxt.getText();
        String email = emailTxt.getText();
        String contact = contactTxt.getText();
        Date regDate = Date.valueOf(dateShowLbl.getText());
        String state = stateCombo.getSelectionModel().getSelectedItem();

        //set default border

        setDefaultBorder();

        //assign patterns

        String namePattern = CommonUtil.namePattern;
        String emailPattern = CommonUtil.mailPattern;
        String contactPattern = CommonUtil.contactPattern;

        //create boolean conditions

        boolean isValidName = name.matches(namePattern) && !nameTxt.getText().equals("");;
        boolean isValidEmail = email.matches(emailPattern) && !emailTxt.getText().equals("");;
        boolean isValidContact = contact.matches(contactPattern) && !contactTxt.getText().equals("");
        boolean isNullState = stateCombo.getSelectionModel().getSelectedItem() == null ||
                stateCombo.getSelectionModel().getSelectedItem().equals("");
        boolean isNullAddress = addressTxt.getText().equals("");


        //check valid booleans

        if (!isValidName) {
            nameTxt.setStyle(nameTxt.getStyle() +  CommonUtil.redColorBorder);
            System.out.println("Invalid name!");
        }

        if (!isValidEmail) {
            emailTxt.setStyle(emailTxt.getStyle() + CommonUtil.redColorBorder);
            System.out.println("Invalid email!");

        }

        if (!isValidContact) {
            contactTxt.setStyle(contactTxt.getStyle() + CommonUtil.redColorBorder);
            System.out.println("Invalid contact!");

        }

        //check null booleans

        if (isNullAddress) {
            addressTxt.setStyle(addressTxt.getStyle() + CommonUtil.redColorBorder);
            System.out.println("please enter address!");
        }

        if (isNullState) {
            new Alert(Alert.AlertType.ERROR, "please select the state").show();
        }


        //validate and return object DTO

        if (isValidName && isValidEmail && isValidContact && !isNullState && !isNullAddress) {
            MemberDTO memberDTO = new MemberDTO(
                    memberId,
                    name,
                    address,
                    email,
                    contact,
                    regDate,
                    state
            );

            return memberDTO;
        }
        return null;
    }

    public void setDefaultBorder() throws SQLException {
        nameTxt.setStyle(nameTxt.getStyle() + CommonUtil.defaultColorBorder);
        addressTxt.setStyle(addressTxt.getStyle() + CommonUtil.defaultColorBorder);
        emailTxt.setStyle(emailTxt.getStyle() + CommonUtil.defaultColorBorder);
        contactTxt.setStyle(contactTxt.getStyle() + CommonUtil.defaultColorBorder);
        stateCombo.setStyle(stateCombo.getStyle() + CommonUtil.defaultColorBorder);
    }

}

