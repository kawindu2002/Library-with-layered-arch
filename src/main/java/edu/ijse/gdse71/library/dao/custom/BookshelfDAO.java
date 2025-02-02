package edu.ijse.gdse71.library.dao.custom;

import edu.ijse.gdse71.library.dao.CrudDAO;
import edu.ijse.gdse71.library.dto.BookshelfDTO;

import java.sql.SQLException;

public interface BookshelfDAO extends CrudDAO<BookshelfDTO> {

    static String getNextBookshelfId() throws SQLException {
        return null;
    }

}

