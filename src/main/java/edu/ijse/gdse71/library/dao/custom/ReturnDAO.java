package edu.ijse.gdse71.library.dao.custom;

import edu.ijse.gdse71.library.dao.CrudDAO;
import edu.ijse.gdse71.library.dto.ReturnDTO;

import java.sql.SQLException;

public interface ReturnDAO extends CrudDAO<ReturnDTO> {

    static String getNextReturnId() throws SQLException {
        return null;
    }

}

