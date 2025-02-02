package edu.ijse.gdse71.library.dao.custom;

import edu.ijse.gdse71.library.dao.CrudDAO;
import edu.ijse.gdse71.library.dto.AuthorDTO;
import java.sql.SQLException;

public interface AuthorDAO extends CrudDAO<AuthorDTO> {

    public static String getNextAuthorId() throws SQLException {
        return null;

    }
}

