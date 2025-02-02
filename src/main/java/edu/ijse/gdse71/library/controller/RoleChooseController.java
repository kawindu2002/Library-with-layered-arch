package edu.ijse.gdse71.library.controller;

import edu.ijse.gdse71.library.util.CommonUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class RoleChooseController {

    @FXML
    private AnchorPane adminChooseAnchPane;

    @FXML
    private ImageView adminIng;

    @FXML
    private AnchorPane empChooseAnchPane;

    @FXML
    private ImageView empImg;

    @FXML
    private Button loginAsAdminBtn;

    @FXML
    private Button loginAsEmpBtn;

    @FXML
    private AnchorPane roleChooseAnchPane;

    @FXML
    private ImageView roleChooseImg;

    @FXML
    void loginAsAdminBtnActionClicked(ActionEvent event) {
        CommonUtil.navigateTo(roleChooseAnchPane, "/view/LoginView.fxml");
        CommonUtil.role = "Admin";

    }

    @FXML
    void loginAsEmpBtnActionClicked(ActionEvent event) {
        CommonUtil.navigateTo(roleChooseAnchPane,"/view/LoginView.fxml");
        CommonUtil.role = "Employee";

    }

}
