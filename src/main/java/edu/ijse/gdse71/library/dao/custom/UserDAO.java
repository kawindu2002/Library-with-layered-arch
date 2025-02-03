package edu.ijse.gdse71.library.dao.custom;

import edu.ijse.gdse71.library.dao.CrudDAO;
import edu.ijse.gdse71.library.dto.UserDTO;
import edu.ijse.gdse71.library.entity.User;
import edu.ijse.gdse71.library.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface UserDAO extends CrudDAO<User> {
    String getState(String userId) throws SQLException;

}

