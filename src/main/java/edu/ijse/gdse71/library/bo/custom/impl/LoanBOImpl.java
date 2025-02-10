package edu.ijse.gdse71.library.bo.custom.impl;

import edu.ijse.gdse71.library.bo.custom.LoanBO;
import edu.ijse.gdse71.library.dao.DAOFactory;
import edu.ijse.gdse71.library.dao.custom.BookDAO;
import edu.ijse.gdse71.library.dao.custom.LoanDAO;
import edu.ijse.gdse71.library.db.DBConnection;
import edu.ijse.gdse71.library.dto.FineDTO;
import edu.ijse.gdse71.library.dto.LoanDTO;
import edu.ijse.gdse71.library.entity.Fine;
import edu.ijse.gdse71.library.entity.Loan;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoanBOImpl implements LoanBO {

    LoanDAO loanDAO= (LoanDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.LOAN);
    BookDAO bookDAO= (BookDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.BOOK);

    public String getNextId() throws SQLException {
        return loanDAO.getNextId();

    }

    @Override
    public boolean save(LoanDTO dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            boolean isLoanSaved =  loanDAO.save(new Loan(
                    dto.getLoanID(),
                    dto.getUserID(),
                    dto.getMemberID(),
                    dto.getBookID(),
                    dto.getLoanDate(),
                    dto.getDueDate()
            ));

            if (isLoanSaved) {
                boolean isBookStateSaved = bookDAO.setBookState("Checked Out",dto.getBookID());
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
        return loanDAO.delete(id);
    }

    @Override
    public boolean update(LoanDTO dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);

            boolean isLoanUpdated =  loanDAO.update(new Loan(
                    dto.getLoanID(),
                    dto.getUserID(),
                    dto.getMemberID(),
                    dto.getBookID(),
                    dto.getLoanDate(),
                    dto.getDueDate()
            ));

            if (isLoanUpdated) {
                boolean isBookStateSaved = bookDAO.setBookState("Checked Out",dto.getBookID());
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

        ArrayList<Loan> loans = loanDAO.getAll();
        ArrayList<LoanDTO> loanDTOS = new ArrayList<>();
        for (Loan loan:loans) {
            loanDTOS.add(new LoanDTO(
                loan.getLoanID(),
                loan.getUserID(),
                loan.getMemberID(),
                loan.getBookID(),
                loan.getLoanDate(),
                loan.getDueDate()));
        }
        return loanDTOS;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        return loanDAO.getAllIds();

    }


    @Override
    public LoanDTO findById(String selectedId) throws SQLException {
        ArrayList<Loan> loans = loanDAO.findById(selectedId);

        if (!loans.isEmpty()) {
            Loan loan = loans.get(0);
            return new LoanDTO(
                loan.getLoanID(),
                loan.getUserID(),
                loan.getMemberID(),
                loan.getBookID(),
                loan.getLoanDate(),
                loan.getDueDate()
            );
        } else {
            return null;
        }
    }
}


