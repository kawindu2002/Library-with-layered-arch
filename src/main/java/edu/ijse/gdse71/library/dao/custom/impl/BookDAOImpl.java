package edu.ijse.gdse71.library.dao.custom.impl;

import edu.ijse.gdse71.library.dao.custom.*;
import edu.ijse.gdse71.library.dto.BookWithDetailsDTO;
import edu.ijse.gdse71.library.entity.Book;
import edu.ijse.gdse71.library.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class BookDAOImpl implements BookDAO, QueryDAO {

    public String getNextId() throws SQLException {
        String query = "select Book_Id from Book order by Book_Id desc limit 1";
        return CrudUtil.getNextId(query,"BK%03d","BK001");
    }

    @Override
    public boolean save(Book entity) throws SQLException {

        return  CrudUtil.execute(
                "insert into Book values (?,?,?,?,?,?,?,?)",
                entity.getBookID(),
                entity.getTitle(),
                entity.getIsbn(),
                entity.getRegDate(),
                entity.getPublisherID(),
                entity.getPrice(),
                entity.getState(),
                entity.getBookshelfID()
        );
   }

    @Override
    public boolean delete(String id) throws SQLException {
        return CrudUtil.execute("delete from Book where Book_Id = ?", id);

    }

    @Override
    public boolean update(Book entity) throws SQLException {
        return CrudUtil.execute(
            "update Book set Title =?, ISBN=?, Reg_date=?, Publisher_Id=?, Price=?, State=?, Bookshelf_Id=? where Book_Id=?",
                entity.getTitle(),
                entity.getIsbn(),
                entity.getRegDate(),
                entity.getPublisherID(),
                entity.getPrice(),
                entity.getState(),
                entity.getBookshelfID(),
                entity.getBookID()

        );
    }

    @Override
    public ArrayList<Book> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Book");

        ArrayList<Book> allBooks = new ArrayList<>();

        while (rst.next()) {
            Book entity = new Book(
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
            allBooks.add(entity);
        }
        return allBooks;
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
    public Book findById(String selectedId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Book where Book_Id=?", selectedId);

        if (rst.next()) {
            return new BookWithDetailsDTO(
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
