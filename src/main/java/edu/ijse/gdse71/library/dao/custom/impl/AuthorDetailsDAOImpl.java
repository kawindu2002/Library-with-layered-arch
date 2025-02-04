package edu.ijse.gdse71.library.dao.custom.impl;

import edu.ijse.gdse71.library.dao.custom.AuthorDetailsDAO;
import edu.ijse.gdse71.library.dto.AuthorDetailsDTO;
import edu.ijse.gdse71.library.entity.AuthorDetails;
import edu.ijse.gdse71.library.entity.Bookshelf;
import edu.ijse.gdse71.library.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AuthorDetailsDAOImpl implements AuthorDetailsDAO {


    @Override
    public boolean saveAuthorDetail(AuthorDetails entity) throws SQLException {
        // Executes an insert query to save the author detail into the database
        return CrudUtil.execute(
                "insert into Author_Book values (?,?)",
                entity.getBookID(),
                entity.getAuthorID()
        );
    }


    @Override
    public boolean deleteAuthorDetail(String bookId) throws SQLException {
        //Executes a delete query to delete the author detail in the database
        return CrudUtil.execute("delete from Author_Book where Book_Id=?", bookId);
    }


//--------------------------------------------------------------------------------
    @Override
    public ArrayList<AuthorDetails> getAllAuthorDetails() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Author_Book");

        ArrayList<AuthorDetails> allAuthorDetails = new ArrayList<>();

        while (rst.next()) {
            AuthorDetails entity = new AuthorDetails(
                    rst.getString(1),
                    rst.getString(2)
            );
            allAuthorDetails.add(entity);
        }
        return allAuthorDetails;
    }


}

