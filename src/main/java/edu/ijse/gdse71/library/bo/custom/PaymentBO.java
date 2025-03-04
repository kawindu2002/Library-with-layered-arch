package edu.ijse.gdse71.library.bo.custom;

import edu.ijse.gdse71.library.bo.SuperBO;
import edu.ijse.gdse71.library.dto.PaymentDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentBO extends SuperBO {
    String getNextId() throws SQLException;
    boolean save(PaymentDTO dto) throws SQLException;
    boolean delete(String id) throws SQLException;
    boolean update(PaymentDTO dto) throws SQLException;
    ArrayList<PaymentDTO> getAll() throws SQLException;
    ArrayList<String> getAllIds() throws SQLException;
    PaymentDTO findById(String selectedId) throws SQLException;
}

