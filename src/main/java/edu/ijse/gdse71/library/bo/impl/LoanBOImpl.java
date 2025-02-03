package edu.ijse.gdse71.library.bo.impl;

import edu.ijse.gdse71.library.bo.custom.LoanBO;
import edu.ijse.gdse71.library.dao.DAOFactory;
import edu.ijse.gdse71.library.dao.custom.BookDAO;
import edu.ijse.gdse71.library.dao.custom.FineDAO;
import edu.ijse.gdse71.library.dao.custom.LoanDAO;
import edu.ijse.gdse71.library.db.DBConnection;
import edu.ijse.gdse71.library.dto.LoanDTO;
import edu.ijse.gdse71.library.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoanBOImpl implements LoanBO {

    LoanDAO loanDAO= (LoanDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.LOAN);
    BookDAO bookDAO= (BookDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BOOK);


    static String getNextLoanId() throws SQLException {
        String query = "select Loan_Id from Loan order by Loan_Id desc limit 1";
        return CrudUtil.getNextId(query,"LN%03d","LN001");
    }

    @Override
    public boolean save(LoanDTO dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            boolean isLoanSaved = CrudUtil.execute(
                    "insert into Loan values (?,?,?,?,?,?)",
                    dto.getLoanID(),
                    dto.getUserID(),
                    dto.getMemberID(),
                    dto.getBookID(),
                    dto.getLoanDate(),
                    dto.getDueDate()

            );

            if (isLoanSaved) {
                boolean isBookStateSaved = bookModel.setBookState("Checked Out",dto.getBookID());
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
        return CrudUtil.execute("delete from Loan where Loan_Id=?", id);
    }

    @Override
    public boolean update(LoanDTO dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            boolean isLoanUpdated = CrudUtil.execute(

                    "update Loan set User_Id=?, Member_Id=?, Book_Id=?, Loan_Date=?,Due_Date=? where Loan_Id=?",
                    dto.getUserID(),
                    dto.getMemberID(),
                    dto.getBookID(),
                    dto.getLoanDate(),
                    dto.getDueDate(),
                    dto.getLoanID()

            );

            if (isLoanUpdated) {
                boolean isBookStateSaved = bookModel.setBookState("Checked Out",dto.getBookID());
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
    public ArrayList<LoanDTO> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Loan");

        ArrayList<LoanDTO> loanDTOS = new ArrayList<>();

        while (rst.next()) {
            LoanDTO loanDTO = new LoanDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDate(5),
                    rst.getDate(6)
            );
            loanDTOS.add(loanDTO);
        }
        return loanDTOS;
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
    public LoanDTO findById(String selectedId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Loan where Loan_Id=?", selectedId);

        if (rst.next()) {
            return new LoanDTO(
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

