package edu.ijse.gdse71.library.bo.custom;

import edu.ijse.gdse71.library.bo.SuperBO;
import edu.ijse.gdse71.library.dto.ReturnDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ReturnBO extends SuperBO {
    String getNextId() throws SQLException;
    boolean save(ReturnDTO dto) throws SQLException;
    boolean delete(String id) throws SQLException;
    boolean update(ReturnDTO dto) throws SQLException;
    ArrayList<ReturnDTO> getAll() throws SQLException;
    ArrayList<String> getAllIds() throws SQLException;
    ReturnDTO findById(String selectedId) throws SQLException;

}

