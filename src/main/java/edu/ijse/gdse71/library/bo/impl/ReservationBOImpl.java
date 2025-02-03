package edu.ijse.gdse71.library.bo.impl;

import edu.ijse.gdse71.library.bo.custom.ReservationBO;
import edu.ijse.gdse71.library.dao.DAOFactory;
import edu.ijse.gdse71.library.dao.custom.BookDAO;
import edu.ijse.gdse71.library.dao.custom.ReservationDAO;
import edu.ijse.gdse71.library.db.DBConnection;
import edu.ijse.gdse71.library.dto.ReservationDTO;
import edu.ijse.gdse71.library.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReservationBOImpl implements ReservationBO {

    ReservationDAO reservationDAO= (ReservationDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RESERVATION);
    BookDAO bookDAO= (BookDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BOOK);

    static String getNextReservationId() throws SQLException {
        String query = "select Reservation_Id from Reservation order by Reservation_Id desc limit 1";
        return CrudUtil.getNextId(query, "RE%03d", "RE001");
    }

    @Override
    public boolean save(ReservationDTO dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            boolean isReservationSaved = CrudUtil.execute(
                    "insert into Reservation values (?,?,?,?,?)",
                    dto.getReservationID(),
                    dto.getMemberID(),
                    dto.getBookID(),
                    dto.getUserID(),
                    dto.getReservationDate()

            );

            if (isReservationSaved) {
                boolean isBookStateSaved = bookModel.setBookState("Reserved",dto.getBookID());
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
        return CrudUtil.execute("delete from Reservation where Reservation_Id=?", id);
    }

    @Override
    public boolean update(ReservationDTO dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            boolean isReservationUpdated = CrudUtil.execute(
                    "update Reservation set Member_Id=?, Book_Id=?, User_Id=?, Reservation_date=? where Reservation_Id=?",
                    dto.getMemberID(),
                    dto.getBookID(),
                    dto.getUserID(),
                    dto.getReservationDate(),
                    dto.getReservationID()

            );

            if (isReservationUpdated) {
                boolean isBookStateSaved = bookModel.setBookState("Reserved",dto.getBookID());
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
        ResultSet rst = CrudUtil.execute("select * from Reservation");

        ArrayList<ReservationDTO> reservationDTOS = new ArrayList<>();

        while (rst.next()) {
            ReservationDTO reservationDTO = new ReservationDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDate(5)
            );
            reservationDTOS.add(reservationDTO);
        }
        return reservationDTOS;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Reservation_Id from Reservation");

        ArrayList<String> reservationIds = new ArrayList<>();

        while (rst.next()) {
            reservationIds.add(rst.getString(1));
        }

        return reservationIds;
    }

    @Override
    public ReservationDTO findById(String selectedId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Reservation where Reservation_Id=?", selectedId);

        if (rst.next()) {
            return new ReservationDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDate(5)
            );
        }
        return null;
    }

}


