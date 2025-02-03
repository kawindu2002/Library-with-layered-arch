package edu.ijse.gdse71.library.dao.custom.impl;

import edu.ijse.gdse71.library.dao.custom.LoanDAO;
import edu.ijse.gdse71.library.entity.Loan;
import edu.ijse.gdse71.library.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoanDAOImpl implements LoanDAO {

    public String getNextId()  throws SQLException {
        String query = "select Loan_Id from Loan order by Loan_Id desc limit 1";
        return CrudUtil.getNextId(query,"LN%03d","LN001");
    }

    @Override
    public boolean save(Loan entity) throws SQLException {

            return CrudUtil.execute(
                    "insert into Loan values (?,?,?,?,?,?)",
                    entity.getLoanID(),
                    entity.getUserID(),
                    entity.getMemberID(),
                    entity.getBookID(),
                    entity.getLoanDate(),
                    entity.getDueDate()

            );

    }

    @Override
    public boolean delete(String id) throws SQLException {
        return CrudUtil.execute("delete from Loan where Loan_Id=?", id);
    }

    @Override
    public boolean update(Loan entity) throws SQLException {

        return CrudUtil.execute(

                "update Loan set User_Id=?, Member_Id=?, Book_Id=?, Loan_Date=?,Due_Date=? where Loan_Id=?",
                entity.getUserID(),
                entity.getMemberID(),
                entity.getBookID(),
                entity.getLoanDate(),
                entity.getDueDate(),
                entity.getLoanID()

        );
    }

    @Override
    public ArrayList<Loan> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Loan");

        ArrayList<Loan> allLoans = new ArrayList<>();

        while (rst.next()) {
            Loan entity = new Loan(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDate(5),
                    rst.getDate(6)
            );
            allLoans.add(entity);
        }
        return allLoans;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Loan_Id from Loan");

        ArrayList<String> loanIds = new ArrayList<>();

        while (rst.next()) {
            loanIds.add(rst.getString(1));
        }
        return loanIds;
    }

    @Override
    public Loan findById(String selectedId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Loan where Loan_Id=?", selectedId);

        if (rst.next()) {
            return new Loan(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDate(5),
                    rst.getDate(6)
            );
        }
        return null;
    }

}

