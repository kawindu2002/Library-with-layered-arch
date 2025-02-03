package edu.ijse.gdse71.library.dao.custom.impl;

import edu.ijse.gdse71.library.dao.custom.BookshelfDAO;
import edu.ijse.gdse71.library.dto.BookshelfDTO;
import edu.ijse.gdse71.library.entity.Bookshelf;
import edu.ijse.gdse71.library.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookshelfDAOImpl implements BookshelfDAO {

    public String getNextId()  throws SQLException {
        String query = "select Bookshelf_Id from Bookshelf order by Bookshelf_Id desc limit 1";
        return CrudUtil.getNextId(query,"BS%03d","BS001");
    }

    @Override
    public boolean save(Bookshelf entity) throws SQLException {
        return CrudUtil.execute(
                "insert into Bookshelf values (?,?,?,?)",
                entity.getBookshelfID(),
                entity.getCategoryID(),
                entity.getCapacity(),
                entity.getLocation()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return CrudUtil.execute("delete from Bookshelf where Bookshelf_Id=?", id);
    }

    @Override
    public boolean update(Bookshelf entity) throws SQLException {
        return CrudUtil.execute(
            "update Bookshelf set Category_Id =?, Capacity=?, Location=? where Bookshelf_Id=?",
                entity.getCategoryID(),
                entity.getCapacity(),
                entity.getLocation(),
                entity.getBookshelfID()
        );
    }

    @Override
    public ArrayList<Bookshelf> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Bookshelf");

        ArrayList<Bookshelf> allBookshelfs = new ArrayList<>();

        while (rst.next()) {
            Bookshelf entity = new Bookshelf(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getString(4)

            );
            allBookshelfs.add(entity);
        }
        return allBookshelfs;
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
    public Bookshelf findById(String selectedId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Bookshelf where Bookshelf_Id=?", selectedId);

        if (rst.next()) {
            return new Bookshelf(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getString(4)
            );
        }
        return null;
    }

}
