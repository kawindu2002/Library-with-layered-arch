package edu.ijse.gdse71.library.dao.custom.impl;

import edu.ijse.gdse71.library.dao.custom.ReservationDAO;
import edu.ijse.gdse71.library.db.DBConnection;
import edu.ijse.gdse71.library.dto.ReservationDTO;
import edu.ijse.gdse71.library.entity.Reservation;
import edu.ijse.gdse71.library.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReservationDAOImpl implements ReservationDAO {

    public String getNextId() throws SQLException {
        String query = "select Reservation_Id from Reservation order by Reservation_Id desc limit 1";
        return CrudUtil.getNextId(query, "RE%03d", "RE001");
    }

    @Override
    public boolean save(Reservation entity) throws SQLException {

        return CrudUtil.execute(
                "insert into Reservation values (?,?,?,?,?)",
                entity.getReservationID(),
                entity.getMemberID(),
                entity.getBookID(),
                entity.getUserID(),
                entity.getReservationDate()

        );
  }

    @Override
    public boolean delete(String id) throws SQLException {
        return CrudUtil.execute("delete from Reservation where Reservation_Id=?", id);
    }

    @Override
    public boolean update(Reservation entity) throws SQLException {

        return CrudUtil.execute(
                "update Reservation set Member_Id=?, Book_Id=?, User_Id=?, Reservation_date=? where Reservation_Id=?",
                entity.getMemberID(),
                entity.getBookID(),
                entity.getUserID(),
                entity.getReservationDate(),
                entity.getReservationID()

        );
    }

    @Override
    public ArrayList<Reservation> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Reservation");

        ArrayList<Reservation> allReservations = new ArrayList<>();

        while (rst.next()) {
            Reservation entity = new Reservation(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDate(5)
            );
            allReservations.add(entity);
        }
        return allReservations;
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
    public Reservation findById(String selectedId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Reservation where Reservation_Id=?", selectedId);

        if (rst.next()) {
            return new Reservation(
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


