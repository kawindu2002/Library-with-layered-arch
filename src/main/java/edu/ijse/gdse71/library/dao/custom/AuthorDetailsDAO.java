package edu.ijse.gdse71.library.dao.custom;

import edu.ijse.gdse71.library.dao.SuperDAO;
import edu.ijse.gdse71.library.entity.AuthorDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AuthorDetailsDAO extends SuperDAO {

     boolean saveAuthorDetail(AuthorDetails authorDetails) throws SQLException;

     boolean deleteAuthorDetail(String bookId) throws SQLException;

     ArrayList<AuthorDetails> getAllAuthorDetails() throws SQLException;

}

