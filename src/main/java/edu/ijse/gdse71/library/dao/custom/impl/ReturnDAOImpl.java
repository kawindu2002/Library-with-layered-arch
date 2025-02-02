package edu.ijse.gdse71.library.dao.custom.impl;

import edu.ijse.gdse71.library.dao.custom.ReturnDAO;
import edu.ijse.gdse71.library.db.DBConnection;
import edu.ijse.gdse71.library.dto.ReturnDTO;
import edu.ijse.gdse71.library.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReturnDAOImpl implements ReturnDAO {

    static String getNextReturnId() throws SQLException {
        String query = "select Returns_Id from Returns order by Returns_Id desc limit 1";
        return CrudUtil.getNextId(query,"RT%03d","RT001");
    }

    @Override
    public boolean save(ReturnDTO dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            boolean isReturnSaved = CrudUtil.execute(
                    "insert into Returns values (?,?,?,?,?,?)",
                    dto.getReturnID(),
                    dto.getUserID(),
                    dto.getMemberID(),
                    dto.getLoanID(),
                    dto.getBookID(),
                    dto.getReturnDate()

            );

            if (isReturnSaved) {
                boolean isBookStateSaved = bookModel.setBookState("Available",dto.getBookID());
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
        return CrudUtil.execute("delete from Returns where Returns_Id=?", id);
    }

    @Override
    public boolean update(ReturnDTO dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            boolean isReturnUpdated = CrudUtil.execute(
                    "update Returns set User_Id=?, Member_Id=?, Loan_Id=?, Book_Id=?,Returns_Date=? where Returns_Id=?",
                    dto.getUserID(),
                    dto.getMemberID(),
                    dto.getLoanID(),
                    dto.getBookID(),
                    dto.getReturnDate(),
                    dto.getReturnID()

            );

            if (isReturnUpdated) {
                boolean isBookStateSaved = bookModel.setBookState("Available",dto.getBookID());
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
        ResultSet rst = CrudUtil.execute("select * from Returns");

        ArrayList<ReturnDTO> returnDTOS = new ArrayList<>();

        while (rst.next()) {
            ReturnDTO returnDTO = new ReturnDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getDate(6)
            );
            returnDTOS.add(returnDTO);
        }
        return returnDTOS;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Returns_Id from Returns");

        ArrayList<String> returnIds = new ArrayList<>();

        while (rst.next()) {
            returnIds.add(rst.getString(1));
        }

        return returnIds;
    }

    @Override
    public ReturnDTO findById(String selectedId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Returns where Returns_Id=?", selectedId);

        if (rst.next()) {
            return new ReturnDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getDate(6)
            );
        }
            return null;
    }

}

