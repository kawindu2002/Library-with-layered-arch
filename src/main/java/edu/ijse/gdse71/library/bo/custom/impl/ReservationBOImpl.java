package edu.ijse.gdse71.library.bo.custom.impl;

import edu.ijse.gdse71.library.bo.custom.ReservationBO;
import edu.ijse.gdse71.library.dao.DAOFactory;
import edu.ijse.gdse71.library.dao.custom.BookDAO;
import edu.ijse.gdse71.library.dao.custom.ReservationDAO;
import edu.ijse.gdse71.library.db.DBConnection;
import edu.ijse.gdse71.library.dto.PublisherDTO;
import edu.ijse.gdse71.library.dto.ReservationDTO;
import edu.ijse.gdse71.library.entity.Publisher;
import edu.ijse.gdse71.library.entity.Reservation;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReservationBOImpl implements ReservationBO {

    ReservationDAO reservationDAO= (ReservationDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.RESERVATION);
    BookDAO bookDAO= (BookDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.BOOK);

    public String getNextId() throws SQLException {
        return reservationDAO.getNextId();

    }

    @Override
    public boolean save(ReservationDTO dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            boolean isReservationSaved = reservationDAO.save(new Reservation(
                    dto.getReservationID(),
                    dto.getMemberID(),
                    dto.getBookID(),
                    dto.getUserID(),
                    dto.getReservationDate()
            ));

            if (isReservationSaved) {
                boolean isBookStateSaved = bookDAO.setBookState("Reserved",dto.getBookID());
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
        return reservationDAO.delete(id);
    }

    @Override
    public boolean update(ReservationDTO dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            boolean isReservationUpdated = reservationDAO.update(new Reservation(
                    dto.getReservationID(),
                    dto.getMemberID(),
                    dto.getBookID(),
                    dto.getUserID(),
                    dto.getReservationDate()
            ));

            if (isReservationUpdated) {
                boolean isBookStateSaved = bookDAO.setBookState("Reserved",dto.getBookID());
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
    public ArrayList<ReservationDTO> getAll() throws SQLException {

        ArrayList<Reservation> reservations = reservationDAO.getAll();
        ArrayList<ReservationDTO> reservationDTOS = new ArrayList<>();
        for (Reservation reservation:reservations) {
            reservationDTOS.add(new ReservationDTO(
                    reservation.getReservationID(),
                    reservation.getMemberID(),
                    reservation.getBookID(),
                    reservation.getUserID(),
                    reservation.getReservationDate()

            ));
        }
        return reservationDTOS;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        return reservationDAO.getAllIds();

    }

    @Override
    public ReservationDTO findById(String selectedId) throws SQLException {
        ArrayList<Reservation> reservations = reservationDAO.findById(selectedId);

        if (!reservations.isEmpty()) {
            Reservation reservation = reservations.get(0);
            return new ReservationDTO(
                reservation.getReservationID(),
                reservation.getMemberID(),
                reservation.getBookID(),
                reservation.getUserID(),
                reservation.getReservationDate()
            );
        } else {
            return null;
        }
    }

}


