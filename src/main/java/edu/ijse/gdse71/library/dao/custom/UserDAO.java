package edu.ijse.gdse71.library.dao.custom;

import edu.ijse.gdse71.library.dao.CrudDAO;
import edu.ijse.gdse71.library.dto.UserDTO;
import edu.ijse.gdse71.library.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface UserDAO extends CrudDAO<UserDTO> {

    static String getNextUserId() throws SQLException {
        return null;
    }

    String getState(String userId) throws SQLException;

}

