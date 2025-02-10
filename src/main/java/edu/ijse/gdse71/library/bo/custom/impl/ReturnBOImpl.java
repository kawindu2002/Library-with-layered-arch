package edu.ijse.gdse71.library.bo.custom.impl;

import edu.ijse.gdse71.library.bo.custom.ReturnBO;
import edu.ijse.gdse71.library.dao.DAOFactory;
import edu.ijse.gdse71.library.dao.custom.BookDAO;
import edu.ijse.gdse71.library.dao.custom.ReturnDAO;
import edu.ijse.gdse71.library.db.DBConnection;
import edu.ijse.gdse71.library.dto.ReservationDTO;
import edu.ijse.gdse71.library.dto.ReturnDTO;
import edu.ijse.gdse71.library.entity.Reservation;
import edu.ijse.gdse71.library.entity.Returns;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReturnBOImpl implements ReturnBO {

    ReturnDAO returnDAO= (ReturnDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.RETURN);
    BookDAO bookDAO= (BookDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.BOOK);

    public String getNextId() throws SQLException {
        return returnDAO.getNextId();
    }

    @Override
    public boolean save(ReturnDTO dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);


        boolean isReturnSaved =  returnDAO.save(new Returns(
                dto.getReturnID(),
                dto.getUserID(),
                dto.getMemberID(),
                dto.getLoanID(),
                dto.getBookID(),
                dto.getReturnDate()
        ));

            if (isReturnSaved) {
                boolean isBookStateSaved = bookDAO.setBookState("Available",dto.getBookID());
                if (isBookStateSaved) {
                    connection.commit();
                    return true;
                }
            }

            connection.rollback();
            return false;
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return returnDAO.delete(id);
    }

    @Override
    public boolean update(ReturnDTO dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

        boolean isReturnUpdated =  returnDAO.update(new Returns(
                dto.getReturnID(),
                dto.getUserID(),
                dto.getMemberID(),
                dto.getLoanID(),
                dto.getBookID(),
                dto.getReturnDate()
        ));

            if (isReturnUpdated) {
                boolean isBookStateSaved = bookDAO.setBookState("Available",dto.getBookID());
                if (isBookStateSaved) {
                    connection.commit();
                    return true;
                }
            }

            connection.rollback();
            return false;
        } catch (Exception e) {
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }


    @Override
    public ArrayList<ReturnDTO> getAll() throws SQLException {

        ArrayList<Returns> returnss = returnDAO.getAll();
        ArrayList<ReturnDTO> returnDTOS = new ArrayList<>();
            for (Returns returns:returnss) {
                returnDTOS.add(new ReturnDTO(
                        returns.getReturnID(),
                        returns.getUserID(),
                        returns.getMemberID(),
                        returns.getLoanID(),
                        returns.getBookID(),
                        returns.getReturnDate()

            ));
        }
        return returnDTOS;

    }


    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        return returnDAO.getAllIds();

    }


    @Override
    public ReturnDTO findById(String selectedId) throws SQLException {
        ArrayList<Returns> returnss = returnDAO.findById(selectedId);

        if (!returnss.isEmpty()) {
            Returns returns = returnss.get(0);
            return new ReturnDTO(
                returns.getReturnID(),
                returns.getUserID(),
                returns.getMemberID(),
                returns.getLoanID(),
                returns.getBookID(),
                returns.getReturnDate()

            );
        } else {
            return null;
        }
    }
}

