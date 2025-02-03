package edu.ijse.gdse71.library.bo.custom;

import edu.ijse.gdse71.library.bo.SuperBO;
import edu.ijse.gdse71.library.dto.BookDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface BookBO extends SuperBO {

    String getNextId() throws SQLException;
    boolean save(BookDTO dto) throws SQLException;
    boolean delete(String id) throws SQLException;
    boolean update(BookDTO dto) throws SQLException;
    ArrayList<BookDTO> getAll() throws SQLException;
    ArrayList<String> getAllIds() throws SQLException;
    BookDTO findById(String selectedId) throws SQLException;
    ArrayList<String> getAllBookIdsByState(String state) throws SQLException;
    String getState(String userId) throws SQLException;
    boolean setBookState(String state,String bookId) throws SQLException;

}
