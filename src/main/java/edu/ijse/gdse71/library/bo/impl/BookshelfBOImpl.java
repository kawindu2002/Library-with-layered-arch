package edu.ijse.gdse71.library.bo.impl;

import edu.ijse.gdse71.library.bo.custom.BookshelfBO;
import edu.ijse.gdse71.library.dao.DAOFactory;
import edu.ijse.gdse71.library.dao.custom.BookDAO;
import edu.ijse.gdse71.library.dao.custom.BookshelfDAO;
import edu.ijse.gdse71.library.dto.BookshelfDTO;
import edu.ijse.gdse71.library.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookshelfBOImpl implements BookshelfBO {

    BookshelfDAO bookshelfDAO= (BookshelfDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BOOKSHELF);

    static String getNextBookshelfId() throws SQLException {
        String query = "select Bookshelf_Id from Bookshelf order by Bookshelf_Id desc limit 1";
        return CrudUtil.getNextId(query,"BS%03d","BS001");
    }

    @Override
    public boolean save(BookshelfDTO dto) throws SQLException {
        return CrudUtil.execute(
                "insert into Bookshelf values (?,?,?,?)",
                dto.getBookshelfID(),
                dto.getCategoryID(),
                dto.getCapacity(),
                dto.getLocation()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return CrudUtil.execute("delete from Bookshelf where Bookshelf_Id=?", id);
    }

    @Override
    public boolean update(BookshelfDTO dto) throws SQLException {
        return CrudUtil.execute(
            "update Bookshelf set Category_Id =?, Capacity=?, Location=? where Bookshelf_Id=?",
            dto.getCategoryID(),
            dto.getCapacity(),
            dto.getLocation(),
            dto.getBookshelfID()
        );
    }

    @Override
    public ArrayList<BookshelfDTO> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Bookshelf");

        ArrayList<BookshelfDTO> bookshelfDTOS = new ArrayList<>();

        while (rst.next()) {
            BookshelfDTO bookshelfDTO = new BookshelfDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getString(4)

            );
            bookshelfDTOS.add(bookshelfDTO);
        }
        return bookshelfDTOS;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Bookshelf_Id from Bookshelf");

        ArrayList<String> bookshelfIds = new ArrayList<>();

        while (rst.next()) {
            bookshelfIds.add(rst.getString(1));
        }

        return bookshelfIds;
    }

    @Override
    public BookshelfDTO findById(String selectedId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Bookshelf where Bookshelf_Id=?", selectedId);

        if (rst.next()) {
            return new BookshelfDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getString(4)
            );
        }
        return null;
    }

}
