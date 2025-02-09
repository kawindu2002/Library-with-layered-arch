package edu.ijse.gdse71.library.dao.custom.impl;

import edu.ijse.gdse71.library.dao.custom.ReturnDAO;
import edu.ijse.gdse71.library.entity.Reservation;
import edu.ijse.gdse71.library.entity.Returns;
import edu.ijse.gdse71.library.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReturnDAOImpl implements ReturnDAO {

    public String getNextId()  throws SQLException {
        String query = "select Returns_Id from Returns order by Returns_Id desc limit 1";
        return CrudUtil.getNextId(query,"RT%03d","RT001");
    }

    @Override
    public boolean save(Returns entity) throws SQLException {
        return CrudUtil.execute(
                "insert into Returns values (?,?,?,?,?,?)",
                entity.getReturnID(),
                entity.getUserID(),
                entity.getMemberID(),
                entity.getLoanID(),
                entity.getBookID(),
                entity.getReturnDate()

        );
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return CrudUtil.execute("delete from Returns where Returns_Id=?", id);
    }

    @Override
    public boolean update(Returns entity) throws SQLException {

        return CrudUtil.execute(
                "update Returns set User_Id=?, Member_Id=?, Loan_Id=?, Book_Id=?,Returns_Date=? where Returns_Id=?",
                entity.getUserID(),
                entity.getMemberID(),
                entity.getLoanID(),
                entity.getBookID(),
                entity.getReturnDate(),
                entity.getReturnID()
        );
    }

    @Override
    public ArrayList<Returns> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Returns");

        ArrayList<Returns> allReturns = new ArrayList<>();

        while (rst.next()) {
            Returns entity = new Returns(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getDate(6)
            );
            allReturns.add(entity);
        }
        return allReturns;
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
    public ArrayList<Returns> findById(String selectedId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Returns where Returns_Id=?", selectedId);

        ArrayList<Returns> returnss = new ArrayList<>();

        while (rst.next()) {
            Returns returns = new Returns(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getDate(6)
            );
            returnss.add(returns);
        }
        return returnss;
    }
}

