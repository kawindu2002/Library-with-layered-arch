package edu.ijse.gdse71.library.bo.custom;

import edu.ijse.gdse71.library.bo.SuperBO;
import edu.ijse.gdse71.library.dto.PublisherDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PublisherBO extends SuperBO {
    static String getNextPublisherId() throws SQLException {
        return null;
    }

    boolean save(PublisherDTO dto) throws SQLException;
    boolean delete(String id) throws SQLException;
    boolean update(PublisherDTO dto) throws SQLException;
    ArrayList<PublisherDTO> getAll() throws SQLException;
    ArrayList<String> getAllIds() throws SQLException;
    PublisherDTO findById(String selectedId) throws SQLException;

}
