package edu.ijse.gdse71.library.bo.impl;

import edu.ijse.gdse71.library.bo.custom.BookBO;
import edu.ijse.gdse71.library.dao.custom.BookDAO;
import edu.ijse.gdse71.library.db.DBConnection;
import edu.ijse.gdse71.library.dto.BookDTO;
import edu.ijse.gdse71.library.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookBOImpl implements BookBO {

    static String getNextBookId() throws SQLException {
        String query = "select Book_Id from Book order by Book_Id desc limit 1";
        return CrudUtil.getNextId(query,"BK%03d","BK001");
    }


    @Override
    public boolean save(BookDTO dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            // @autoCommit: Disables auto-commit to manually control the transaction
            connection.setAutoCommit(false);

            // @isBookSaved: Saves the book details into the book table
            boolean isBookSaved = CrudUtil.execute(
                    "insert into Book values (?,?,?,?,?,?,?,?)",
                    dto.getBookID(),
                    dto.getTitle(),
                    dto.getIsbn(),
                    dto.getRegDate(),
                    dto.getPublisherID(),
                    dto.getPrice(),
                    dto.getState(),
                    dto.getBookshelfID()
            );


            // If the book is saved successfully
            if (isBookSaved) {
               // @isAuthorSaved: Saves the author details into the Author table
                boolean isAuthorDetailsListSaved = authorDetailsModel.saveAuthorDetailsList(dto.getAuthorDetailsDTOS()); // Save Author details
                if (isAuthorDetailsListSaved) {
                    // @isCategoryDetailListSaved: Saves the list of category details
                    boolean isCategoryDetailListSaved = categoryDetailsModel.saveCategoryDetailsList(dto.getCategoryDetailsDTOS());
                    if (isCategoryDetailListSaved) {
                        // @commit: Commits the transaction if book, author, and category details are saved successfully
                        connection.commit();
                        return true;
                    }
                }
            }

            // @rollback: Rolls back the transaction if author details or category details saving fails
            connection.rollback();
            return false;
        } catch (Exception e) {
            // @catch: Rolls back the transaction in case of any exception
            connection.rollback();
            return false;
        } finally {
            // @finally: Resets auto-commit to true after the operation
            connection.setAutoCommit(true);
        }

   }

    @Override
    public boolean delete(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            // @autoCommit: Disables auto-commit to manually control the transaction
            connection.setAutoCommit(false);

            // First, delete the Author_Book associations for the given Book_Id
            boolean isAuthorBookDeleted = authorDetailsModel.deleteAuthorDetailsList(id);
            if (!isAuthorBookDeleted) {
                connection.rollback();  //(rollback if Author_Book deletion fails)
                return false;
            }

            // Then, delete the Category_Book associations for the given Book_Id
            boolean isCategoryBookDeleted = categoryDetailsModel.deleteCategoryDetailsList(id);
            if (!isCategoryBookDeleted) {
                connection.rollback();  //(rollback if Category_Book deletion fails)
                return false;
            }

            // After successful deletions of Author_Book and Category_Book, delete the Book record
            boolean isBookDeleted = CrudUtil.execute("delete from Book where Book_Id = ?", id);
            if (isBookDeleted) {
                // @commit: Commit the transaction if all deletions were successful
                connection.commit();
                return true;
            } else {
                connection.rollback(); //  (rollback if Book deletion fails)
                return false;
            }

        } catch (Exception e) {
            // @catch: Rolls back the transaction in case of any exception
            connection.rollback();
            return false;
        } finally {
            // @finally: Resets auto-commit to true after the operation
            connection.setAutoCommit(true); //
        }
    }

    @Override
    public boolean update(BookDTO dto) throws SQLException {
        return CrudUtil.execute(
            "update Book set Title =?, ISBN=?, Reg_date=?, Publisher_Id=?, Price=?, State=?, Bookshelf_Id=? where Book_Id=?",
                dto.getTitle(),
                dto.getIsbn(),
                dto.getRegDate(),
                dto.getPublisherID(),
                dto.getPrice(),
                dto.getState(),
                dto.getBookshelfID(),
                dto.getBookID()

        );
    }

    @Override
    public ArrayList<BookDTO> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Book");

        ArrayList<BookDTO> bookDTOS = new ArrayList<>();

        while (rst.next()) {
            BookDTO bookDTO = new BookDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDate(4),
                    rst.getString(5),
                    rst.getDouble(6),
                    rst.getString(7),
                    rst.getString(8),
                    categoryDetailsModel.getAllCategoryDetails(),
                    authorDetailsModel.getAllAuthorDetails()
            );
            bookDTOS.add(bookDTO);
        }
        return bookDTOS;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Book_Id from Book");

        ArrayList<String> bookIds = new ArrayList<>();

        while (rst.next()) {
            bookIds.add(rst.getString(1));
        }

        return bookIds;
    }

    @Override
    public BookDTO findById(String selectedId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Book where Book_Id=?", selectedId);

        if (rst.next()) {
            return new BookDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDate(4),
                    rst.getString(5),
                    rst.getDouble(6),
                    rst.getString(7),
                    rst.getString(8),
                    categoryDetailsModel.getAllCategoryDetails(),
                    authorDetailsModel.getAllAuthorDetails()
            );
        }
        return null;
    }

    @Override
    public String getState(String id) throws SQLException {
        String query = "select State from Book where Book_Id = ?";
        return CrudUtil.getState(query,id);
    }

    @Override
    public ArrayList<String> getAllBookIdsByState(String state) throws SQLException {
        ResultSet rst = CrudUtil.execute("select Book_Id from Book where State=?",state);

        ArrayList<String> bookIds = new ArrayList<>();

        while (rst.next()) {
            bookIds.add(rst.getString(1));
        }

        return bookIds;
    }

    @Override
    public boolean setBookState(String state, String bookId) throws SQLException {
            return CrudUtil.execute(
            "update Book set State=?  where Book_Id=?",
            state,
            bookId
        );
    }

}

