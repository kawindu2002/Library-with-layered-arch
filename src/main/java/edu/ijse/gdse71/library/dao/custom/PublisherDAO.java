package edu.ijse.gdse71.library.dao.custom;

import edu.ijse.gdse71.library.dao.CrudDAO;
import edu.ijse.gdse71.library.dto.PublisherDTO;

import java.sql.SQLException;

public interface PublisherDAO extends CrudDAO<PublisherDTO> {

    static String getNextPublisherId() throws SQLException {
        return null;
    }

}

