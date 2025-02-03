package edu.ijse.gdse71.library.bo.impl;

import edu.ijse.gdse71.library.bo.custom.PublisherBO;
import edu.ijse.gdse71.library.dao.DAOFactory;
import edu.ijse.gdse71.library.dao.custom.PublisherDAO;
import edu.ijse.gdse71.library.dto.PublisherDTO;
import edu.ijse.gdse71.library.entity.Publisher;

import java.sql.SQLException;
import java.util.ArrayList;

public class PublisherBOImpl implements PublisherBO {

    PublisherDAO publisherDAO= (PublisherDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PUBLISHER);

    public String getNextId() throws SQLException {
        return publisherDAO.getNextId();

    }

    @Override
    public boolean save(PublisherDTO dto) throws SQLException {
        return publisherDAO.save(new Publisher(
                dto.getPublisherID(),
                dto.getName(),
                dto.getAddress(),
                dto.getRegDate()
        ));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return publisherDAO.delete(id);
    }

    @Override
    public boolean update(PublisherDTO dto) throws SQLException {
        return publisherDAO.update(new Publisher(
                dto.getPublisherID(),
                dto.getName(),
                dto.getAddress(),
                dto.getRegDate()
        ));
    }

    @Override
    public ArrayList<PublisherDTO> getAll() throws SQLException {

        ArrayList<Publisher> publishers = publisherDAO.getAll();
        ArrayList<PublisherDTO> publisherDTOS = new ArrayList<>();
        for (Publisher publisher:publishers) {
            publisherDTOS.add(new PublisherDTO(
                    publisher.getPublisherID(),
                    publisher.getName(),
                    publisher.getAddress(),
                    publisher.getRegDate()

            ));
        }
        return publisherDTOS;


    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        return publisherDAO.getAllIds();

    }

    @Override
    public PublisherDTO findById(String selectedId) throws SQLException {
//        ResultSet rst = CrudUtil.execute("select * from Publisher where Publisher_Id=?", selectedId);
//
//        if (rst.next()) {
//            return new PublisherDTO(
//                    rst.getString(1),
//                    rst.getString(2),
//                    rst.getString(3),
//                    rst.getDate(4)
//            );
//        }
        return null;
    }

}

