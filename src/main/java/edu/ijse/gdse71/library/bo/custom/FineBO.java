package edu.ijse.gdse71.library.bo.custom;

import edu.ijse.gdse71.library.bo.SuperBO;
import edu.ijse.gdse71.library.dto.FineDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface FineBO extends SuperBO {

    static String getNextFineId() throws SQLException {
        return null;
    }

    boolean save(FineDTO dto) throws SQLException;
    boolean delete(String id) throws SQLException;
    boolean update(FineDTO dto) throws SQLException;
    ArrayList<FineDTO> getAll() throws SQLException;
    ArrayList<String> getAllIds() throws SQLException;
    FineDTO findById(String selectedId) throws SQLException;

}

