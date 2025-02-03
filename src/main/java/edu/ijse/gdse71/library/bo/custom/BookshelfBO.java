package edu.ijse.gdse71.library.bo.custom;

import edu.ijse.gdse71.library.bo.SuperBO;
import edu.ijse.gdse71.library.dto.BookshelfDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface BookshelfBO extends SuperBO {

    String getNextId() throws SQLException;
    boolean save(BookshelfDTO dto) throws SQLException;
    boolean delete(String id) throws SQLException;
    boolean update(BookshelfDTO dto) throws SQLException;
    ArrayList<BookshelfDTO> getAll() throws SQLException;
    ArrayList<String> getAllIds() throws SQLException;
    BookshelfDTO findById(String selectedId) throws SQLException;
}

