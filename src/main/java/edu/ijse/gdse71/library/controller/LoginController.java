package edu.ijse.gdse71.library.controller;

import edu.ijse.gdse71.library.bo.custom.UserBO;
import edu.ijse.gdse71.library.bo.impl.UserBOImpl;
import edu.ijse.gdse71.library.dto.UserDTO;
import edu.ijse.gdse71.library.util.CommonUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import java.sql.SQLException;

public class LoginController  {

    @FXML
    private Button backBtn;

    @FXML
    private AnchorPane loginAnchPane;

    @FXML
    private Button loginBtn;

    @FXML
    private AnchorPane loginContentAnchPane;

    @FXML
    private ImageView loginImg;

    @FXML
    private Label loginLbl;

    @FXML
    private Line loginLine;

    @FXML
    private Label passwordLbl;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private Label userIdLbl;

    @FXML
    private TextField userIdTxt;


    private String userId;
    private String password;
    private String role;

    UserBO userBO = new UserBOImpl();

    @FXML
    void backBtnActionClicked(ActionEvent event) {
        CommonUtil.navigateTo(loginAnchPane,"/view/RoleChooseView.fxml");
    }


    @FXML
    void loginBtnActionClicked(ActionEvent event) throws SQLException {

        validateUserDetails();

        boolean validUserId = userIdTxt.getText().equals(userId);
        boolean validPassword = passwordTxt.getText().equals(password);

        if (CommonUtil.role.equals(role)){
            if (!validUserId && !validPassword) {
                new Alert(Alert.AlertType.ERROR, "Username & password are invalid!").show();
            }else if (!validUserId){
                new Alert(Alert.AlertType.ERROR, "Username is invalid!").show();
            }else if (!validPassword) {
                new Alert(Alert.AlertType.ERROR, "Password is invalid!").show();
            }else{
                CommonUtil.username = userId;
                CommonUtil.password = password;
                CommonUtil.navigateTo(loginAnchPane, "/view/DashBoardView.fxml");
            }
        }else{
            new Alert(Alert.AlertType.ERROR, "Check the role!").show();

        }

    }

    void validateUserDetails() throws SQLException {
        String selectedUserId = userIdTxt.getText();
        UserDTO userDTO = userBO.findById(selectedUserId);

        if (userDTO != null) {
            userId = userDTO.getUserID();
            password = userDTO.getPassword();
            role = userDTO.getRole();
        }
    }
}

