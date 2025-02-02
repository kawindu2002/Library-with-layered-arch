package edu.ijse.gdse71.library.bo.custom;

import edu.ijse.gdse71.library.bo.SuperBO;
import edu.ijse.gdse71.library.dto.ReservationDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ReservationBO extends SuperBO {
    static String getNextReservationId() throws SQLException {
        return null;
    }
    boolean save(ReservationDTO dto) throws SQLException;
    boolean delete(String id) throws SQLException;
    boolean update(ReservationDTO dto) throws SQLException;
    ArrayList<ReservationDTO> getAll() throws SQLException;
    ArrayList<String> getAllIds() throws SQLException;
    ReservationDTO findById(String selectedId) throws SQLException;



}
