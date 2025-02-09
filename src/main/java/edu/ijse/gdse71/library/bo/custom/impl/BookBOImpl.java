package edu.ijse.gdse71.library.bo.custom.impl;

import edu.ijse.gdse71.library.bo.custom.BookBO;
import edu.ijse.gdse71.library.dao.DAOFactory;
import edu.ijse.gdse71.library.dao.custom.AuthorDetailsDAO;
import edu.ijse.gdse71.library.dao.custom.BookDAO;
import edu.ijse.gdse71.library.dao.custom.CategoryDetailsDAO;
import edu.ijse.gdse71.library.db.DBConnection;
import edu.ijse.gdse71.library.dto.BookDTO;
import edu.ijse.gdse71.library.entity.AuthorDetails;
import edu.ijse.gdse71.library.entity.Book;
import edu.ijse.gdse71.library.entity.CategoryDetails;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookBOImpl implements BookBO {

    BookDAO bookDAO= (BookDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.BOOK);
    AuthorDetailsDAO authorDetailsDAO= (AuthorDetailsDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.AUTHOR_DETAILS);
    CategoryDetailsDAO categoryDetailsDAO= (CategoryDetailsDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.CATEGORY_DETAILS);

    public String getNextId() throws SQLException {
        return bookDAO.getNextId();

    }

    @Override
    public boolean save(BookDTO dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            // @autoCommit: Disables auto-commit to manually control the transaction
            connection.setAutoCommit(false);

            // @isBookSaved: Saves the book details into the book table

            boolean isBookSaved =  bookDAO.save(new Book(
                    dto.getBookID(),
                    dto.getTitle(),
                    dto.getIsbn(),
                    dto.getRegDate(),
                    dto.getPublisherID(),
                    dto.getPrice(),
                    dto.getState(),
                    dto.getBookshelfID()
            ));


            // If the book is saved successfully
            if (isBookSaved) {
               // @isAuthorSaved: Saves the author details into the Author table
                boolean isAuthorDetailsListSaved = authorDetailsDAO.saveAuthorDetail(new AuthorDetails()); // Save Author details
                if (isAuthorDetailsListSaved) {
                    // @isCategoryDetailListSaved: Saves the list of category details
                    boolean isCategoryDetailListSaved = categoryDetailsDAO.saveCategoryDetail(new CategoryDetails());
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
            boolean isAuthorBookDeleted = authorDetailsDAO.deleteAuthorDetail(id);
            if (!isAuthorBookDeleted) {
                connection.rollback();  //(rollback if Author_Book deletion fails)
                return false;
            }

            // Then, delete the Category_Book associations for the given Book_Id
            boolean isCategoryBookDeleted = categoryDetailsDAO.deleteCategoryDetail(id);
            if (!isCategoryBookDeleted) {
                connection.rollback();  //(rollback if Category_Book deletion fails)
                return false;
            }

            // After successful deletions of Author_Book and Category_Book, delete the Book record
            boolean isBookDeleted = bookDAO.delete(id);
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

        return bookDAO.update(new Book(
                dto.getBookID(),
                dto.getTitle(),
                dto.getIsbn(),
                dto.getRegDate(),
                dto.getPublisherID(),
                dto.getPrice(),
                dto.getState(),
                dto.getBookshelfID()

        ));
    }

    @Override
    public ArrayList<BookDTO> getAll() throws SQLException {

        ArrayList<Book> books = bookDAO.getAll();
        ArrayList<BookDTO> bookDTO = new ArrayList<>();
        for (Book book:books) {
            bookDTO.add(new BookDTO(
                    book.getBookID(),
                    book.getTitle(),
                    book.getIsbn(),
                    book.getRegDate(),
                    book.getPublisherID(),
                    book.getPrice(),
                    book.getState(),
                    book.getBookshelfID()
            ));
        }
        return bookDTO;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        return bookDAO.getAllIds();
    }

    @Override
    public BookDTO findById(String selectedId) throws SQLException {
        ArrayList<Book> books = bookDAO.findById(selectedId);

        if (!books.isEmpty()) {
            Book book = books.get(0);
            return new BookDTO(
                book.getBookID(),
                book.getTitle(),
                book.getIsbn(),
                book.getRegDate(),
                book.getPublisherID(),
                book.getPrice(),
                book.getState(),
                book.getBookshelfID()
            );
        } else {
            return null;
        }
    }


    @Override
    public String getState(String id) throws SQLException {
        return bookDAO.getState(id);
    }

    @Override
    public ArrayList<String> getAllBookIdsByState(String state) throws SQLException {
        return bookDAO.getAllBookIdsByState(state);
    }

    @Override
    public boolean setBookState(String state, String bookId) throws SQLException {
        return bookDAO.setBookState(state,bookId);
    }

}


