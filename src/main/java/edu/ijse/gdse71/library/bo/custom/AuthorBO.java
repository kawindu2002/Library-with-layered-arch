package edu.ijse.gdse71.library.bo.custom;

import edu.ijse.gdse71.library.dto.AuthorDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AuthorBO {
    boolean save(AuthorDTO dto) throws SQLException;
    boolean delete(String id) throws SQLException;
    boolean update(AuthorDTO dto) throws SQLException;
    ArrayList<AuthorDTO> getAll() throws SQLException;
    ArrayList<String> getAllIds() throws SQLException;
    AuthorDTO findById(String selectedId) throws SQLException;
}

