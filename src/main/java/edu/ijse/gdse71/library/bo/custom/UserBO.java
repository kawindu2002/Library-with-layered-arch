package edu.ijse.gdse71.library.bo.custom;

import edu.ijse.gdse71.library.bo.SuperBO;
import edu.ijse.gdse71.library.dto.UserDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserBO extends SuperBO {
    static String getNextUserId() throws SQLException {
        return null;
    }

    boolean save(UserDTO dto) throws SQLException;
    boolean delete(String id) throws SQLException;
    boolean update(UserDTO dto) throws SQLException;
    ArrayList<UserDTO> getAll() throws SQLException;
    ArrayList<String> getAllIds() throws SQLException;
    UserDTO findById(String selectedId) throws SQLException;
    String getState(String userId) throws SQLException;

}

