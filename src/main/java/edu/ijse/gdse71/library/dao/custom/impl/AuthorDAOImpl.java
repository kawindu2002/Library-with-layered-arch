package edu.ijse.gdse71.library.dao.custom.impl;

import edu.ijse.gdse71.library.dao.custom.AuthorDAO;
import edu.ijse.gdse71.library.entity.Author;
import edu.ijse.gdse71.library.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AuthorDAOImpl implements AuthorDAO {

    public String getNextId() throws SQLException {
        String query = "select Author_Id from Author order by Author_Id desc limit 1";
        return CrudUtil.getNextId(query,"AU%03d","AU001");

    }

    @Override
    public boolean save(Author entity) throws SQLException {
        return CrudUtil.execute(
                "insert into Author values (?,?,?,?)",
                entity.getAuthorID(),
                entity.getName(),
                entity.getBiography(),
                entity.getRegDate()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return CrudUtil.execute("delete from Author where Author_Id=?", id);

    }

    @Override
    public boolean update(Author entity) throws SQLException {
        return CrudUtil.execute(
                "update Author set Name=?, Biography=?,Reg_date=? where Author_Id=?",
                entity.getName(),
                entity.getBiography(),
                entity.getRegDate(),
                entity.getAuthorID()
        );
    }


    @Override
    public ArrayList<Author> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Author");

        ArrayList<Author> allAuthors = new ArrayList<>();

        while (rst.next()) {
            Author entity = new Author(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDate(4)
            );
            allAuthors.add(entity);
        }
        return allAuthors;
    }


    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Author_Id from Author");

        ArrayList<String> authorIds = new ArrayList<>();

        while (rst.next()) {
            authorIds.add(rst.getString(1));
        }

        return authorIds;
    }

    @Override
    public Author findById(String selectedId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Author where Author_Id=?", selectedId);

        if (rst.next()) {
            return new Author(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDate(4)
            );
        }
        return null;
    }

}

