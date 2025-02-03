package edu.ijse.gdse71.library.bo.impl;

import edu.ijse.gdse71.library.bo.custom.ReturnBO;
import edu.ijse.gdse71.library.dao.DAOFactory;
import edu.ijse.gdse71.library.dao.custom.BookDAO;
import edu.ijse.gdse71.library.dao.custom.ReturnDAO;
import edu.ijse.gdse71.library.dto.ReturnDTO;
import edu.ijse.gdse71.library.entity.Returns;

import java.sql.SQLException;
import java.util.ArrayList;

public class ReturnBOImpl implements ReturnBO {

    ReturnDAO returnDAO= (ReturnDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RETURN);
    BookDAO bookDAO= (BookDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BOOK);

    public String getNextId() throws SQLException {
        return returnDAO.getNextId();
    }

    @Override
    public boolean save(ReturnDTO dto) throws SQLException {
//        Connection connection = DBConnection.getInstance().getConnection();
//        try {
//            connection.setAutoCommit(false);
//
//            boolean isReturnSaved = CrudUtil.execute(
//                    "insert into Returns values (?,?,?,?,?,?)",
//                    dto.getReturnID(),
//                    dto.getUserID(),
//                    dto.getMemberID(),
//                    dto.getLoanID(),
//                    dto.getBookID(),
//                    dto.getReturnDate()
//
//            );
//
//            if (isReturnSaved) {
//                boolean isBookStateSaved = bookModel.setBookState("Available",dto.getBookID());
//                if (isBookStateSaved) {
//                    connection.commit();
//                    return true;
//                }
//            }
//
//            connection.rollback();
//            return false;
//        } catch (Exception e) {
//            connection.rollback();
//            return false;
//        } finally {
//            connection.setAutoCommit(true);
//        }
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return returnDAO.delete(id);
    }

    @Override
    public boolean update(ReturnDTO dto) throws SQLException {
//        Connection connection = DBConnection.getInstance().getConnection();
//        try {
//            connection.setAutoCommit(false);
//
//            boolean isReturnUpdated = CrudUtil.execute(
//                    "update Returns set User_Id=?, Member_Id=?, Loan_Id=?, Book_Id=?,Returns_Date=? where Returns_Id=?",
//                    dto.getUserID(),
//                    dto.getMemberID(),
//                    dto.getLoanID(),
//                    dto.getBookID(),
//                    dto.getReturnDate(),
//                    dto.getReturnID()
//
//            );
//
//            if (isReturnUpdated) {
//                boolean isBookStateSaved = bookModel.setBookState("Available",dto.getBookID());
//                if (isBookStateSaved) {
//                    connection.commit();
//                    return true;
//                }
//            }
//
//            connection.rollback();
//            return false;
//        } catch (Exception e) {
//            connection.rollback();
//            return false;
//        } finally {
//            connection.setAutoCommit(true);
//        }
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
//        ResultSet rst = CrudUtil.execute("select * from Returns where Returns_Id=?", selectedId);
//
//        if (rst.next()) {
//            return new ReturnDTO(
//                    rst.getString(1),
//                    rst.getString(2),
//                    rst.getString(3),
//                    rst.getString(4),
//                    rst.getString(5),
//                    rst.getDate(6)
//            );
//        }
            return null;
    }

}

