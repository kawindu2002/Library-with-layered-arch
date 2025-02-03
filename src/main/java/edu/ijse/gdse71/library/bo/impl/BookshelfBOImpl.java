package edu.ijse.gdse71.library.bo.impl;

import edu.ijse.gdse71.library.bo.custom.BookshelfBO;
import edu.ijse.gdse71.library.dao.DAOFactory;
import edu.ijse.gdse71.library.dao.custom.BookDAO;
import edu.ijse.gdse71.library.dao.custom.BookshelfDAO;
import edu.ijse.gdse71.library.dto.BookshelfDTO;
import edu.ijse.gdse71.library.entity.Author;
import edu.ijse.gdse71.library.entity.Book;
import edu.ijse.gdse71.library.entity.Bookshelf;
import edu.ijse.gdse71.library.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookshelfBOImpl implements BookshelfBO {

    BookshelfDAO bookshelfDAO= (BookshelfDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BOOKSHELF);

    public String getNextId() throws SQLException {
        return bookshelfDAO.getNextId();

    }

    @Override
    public boolean save(BookshelfDTO dto) throws SQLException {
        return bookshelfDAO.save(new Bookshelf(
            dto.getBookshelfID(),
            dto.getCategoryID(),
            dto.getCapacity(),
            dto.getLocation()));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return bookshelfDAO.delete(id);
    }

    @Override
    public boolean update(BookshelfDTO dto) throws SQLException {
        return bookshelfDAO.update(new Bookshelf(
                dto.getBookshelfID(),
                dto.getCategoryID(),
                dto.getCapacity(),
                dto.getLocation()));
    }

    @Override
    public ArrayList<BookshelfDTO> getAll() throws SQLException {
        ArrayList<Bookshelf> bookshelfs = bookshelfDAO.getAll();
        ArrayList<BookshelfDTO> bookshelfDTOS=new ArrayList<>();
        for (Bookshelf bookshelf:bookshelfs) {
            bookshelfDTOS.add(new BookshelfDTO(
                    bookshelf.getBookshelfID(),
                    bookshelf.getCategoryID(),
                    bookshelf.getCapacity(),
                    bookshelf.getLocation()));
        }
        return bookshelfDTOS;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        return bookshelfDAO.getAllIds();

    }

    @Override
    public BookshelfDTO findById(String selectedId) throws SQLException {
//        ResultSet rst = CrudUtil.execute("select * from Bookshelf where Bookshelf_Id=?", selectedId);
//
//        if (rst.next()) {
//            return new BookshelfDTO(
//                    rst.getString(1),
//                    rst.getString(2),
//                    rst.getInt(3),
//                    rst.getString(4)
//            );
//        }
        return null;
    }

}

