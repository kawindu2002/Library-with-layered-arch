package edu.ijse.gdse71.library.bo.custom.impl;

import edu.ijse.gdse71.library.bo.custom.PublisherBO;
import edu.ijse.gdse71.library.dao.DAOFactory;
import edu.ijse.gdse71.library.dao.custom.PublisherDAO;
import edu.ijse.gdse71.library.dto.PaymentDTO;
import edu.ijse.gdse71.library.dto.PublisherDTO;
import edu.ijse.gdse71.library.entity.Payment;
import edu.ijse.gdse71.library.entity.Publisher;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class PublisherBOImpl implements PublisherBO {

    PublisherDAO publisherDAO= (PublisherDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PUBLISHER);

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
        ArrayList<Publisher> publishers = publisherDAO.findById(selectedId);

        if (!publishers.isEmpty()) {
            Publisher publisher = publishers.get(0);
            return new PublisherDTO(
                publisher.getPublisherID(),
                publisher.getName(),
                publisher.getAddress(),
                publisher.getRegDate()
            );
        } else {
            return null;
        }
    }

}

