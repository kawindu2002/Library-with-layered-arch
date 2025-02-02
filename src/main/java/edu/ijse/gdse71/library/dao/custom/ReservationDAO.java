package edu.ijse.gdse71.library.dao.custom;

import edu.ijse.gdse71.library.dao.CrudDAO;
import edu.ijse.gdse71.library.dto.ReservationDTO;

import java.sql.SQLException;

public interface ReservationDAO extends CrudDAO<ReservationDTO> {

    static String getNextReservationId() throws SQLException {
        return null;
    }

}

