package edu.ijse.gdse71.library.dao.custom;

import edu.ijse.gdse71.library.dao.CrudDAO;
import edu.ijse.gdse71.library.dto.BookDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface BookDAO extends CrudDAO<BookDTO> {

    static String getNextBookId() throws SQLException {
        return null;
    }

    ArrayList<String> getAllBookIdsByState(String state) throws SQLException;
    String getState(String userId) throws SQLException;
    boolean setBookState(String state,String bookId) throws SQLException;

}

