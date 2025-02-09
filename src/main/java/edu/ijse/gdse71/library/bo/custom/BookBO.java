package edu.ijse.gdse71.library.bo.custom;

import edu.ijse.gdse71.library.bo.SuperBO;
import edu.ijse.gdse71.library.dto.BookDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface BookBO extends SuperBO {

    String getNextId() throws SQLException;
    boolean save(BookWithDetailsDTO dto) throws SQLException;
    boolean delete(String id) throws SQLException;
    boolean update(BookDTO dto) throws SQLException;
    ArrayList<BookWithDetailsDTO> getAll() throws SQLException;
    ArrayList<String> getAllIds() throws SQLException;
    BookWithDetailsDTO findById(String selectedId) throws SQLException;
    ArrayList<String> getAllBookIdsByState(String state) throws SQLException;
    String getState(String userId) throws SQLException;
    boolean setBookState(String state,String bookId) throws SQLException;

}
