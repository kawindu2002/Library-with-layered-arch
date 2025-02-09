package edu.ijse.gdse71.library.dao.custom;

import edu.ijse.gdse71.library.dao.CrudDAO;
import edu.ijse.gdse71.library.entity.User;

import java.sql.SQLException;

public interface UserDAO extends CrudDAO<User> {
    String getState(String userId) throws SQLException;

}

