package edu.ijse.gdse71.library.bo.custom;

import edu.ijse.gdse71.library.bo.SuperBO;
import edu.ijse.gdse71.library.dto.LoanDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LoanBO extends SuperBO {
    String getNextId() throws SQLException;
    boolean save(LoanDTO dto) throws SQLException;
    boolean delete(String id) throws SQLException;
    boolean update(LoanDTO dto) throws SQLException;
    ArrayList<LoanDTO> getAll() throws SQLException;
    ArrayList<String> getAllIds() throws SQLException;
    LoanDTO findById(String selectedId) throws SQLException;

}

