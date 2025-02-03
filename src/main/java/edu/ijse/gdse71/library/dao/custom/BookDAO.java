package edu.ijse.gdse71.library.dao.custom;

import edu.ijse.gdse71.library.dao.CrudDAO;
import edu.ijse.gdse71.library.dto.BookDTO;
import edu.ijse.gdse71.library.entity.Book;

import java.sql.SQLException;
import java.util.ArrayList;

public interface BookDAO extends CrudDAO<Book> {

    ArrayList<String> getAllBookIdsByState(String state) throws SQLException;
    String getState(String userId) throws SQLException;
    boolean setBookState(String state,String bookId) throws SQLException;

}

