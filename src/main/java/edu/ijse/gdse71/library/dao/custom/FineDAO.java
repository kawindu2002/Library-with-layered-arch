package edu.ijse.gdse71.library.dao.custom;

import edu.ijse.gdse71.library.dao.CrudDAO;
import edu.ijse.gdse71.library.dto.FineDTO;

import java.sql.SQLException;

public interface FineDAO extends CrudDAO<FineDTO> {


    static String getNextFineId() throws SQLException {
        return null;
    }

}

