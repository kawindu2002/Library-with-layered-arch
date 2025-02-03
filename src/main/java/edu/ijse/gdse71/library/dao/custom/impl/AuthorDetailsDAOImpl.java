package edu.ijse.gdse71.library.dao.custom.impl;

import edu.ijse.gdse71.library.dao.custom.AuthorDetailsDAO;
import edu.ijse.gdse71.library.dto.AuthorDetailsDTO;
import edu.ijse.gdse71.library.entity.AuthorDetails;
import edu.ijse.gdse71.library.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AuthorDetailsDAOImpl implements AuthorDetailsDAO {

    @Override
    public boolean saveAuthorDetailsList(ArrayList<AuthorDetailsDTO> authorDetailsDTOS) throws SQLException {
        // Iterate through each author detail in the list
        for (AuthorDetailsDTO authorDetailsDTO : authorDetailsDTOS) {
            // @isAuthorDetailsSaved: Saves the individual author detail
            boolean isAuthorDetailsSaved = saveAuthorDetail(authorDetails);
            if (!isAuthorDetailsSaved) {
                // Return false if saving any author detail fails
                return false;
            }

        }
        // Return true if all author details are saved.
        return true;
    }

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
    public boolean deleteAuthorDetailsList(String bookId) throws SQLException {
         //@isAuthorDetailsDeleted: Delete the individual author detail
            boolean isAuthorDetailsDeleted = deleteAuthorDetail(bookId);
            if (!isAuthorDetailsDeleted) {
                // Return false if deleting any author detail fails
                return false;
            }


        // Return true if all author details are deleted.
        return true;

     }

    @Override
    public boolean deleteAuthorDetail(String bookId) throws SQLException {
        //Executes a delete query to delete the author detail in the database
        return CrudUtil.execute("delete from Author_Book where Book_Id=?", bookId);
    }

    @Override
    public ArrayList<AuthorDetailsDTO> getAllAuthorDetails() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Author_Book");

        ArrayList<AuthorDetailsDTO> authorDetailsDTOS = new ArrayList<>();

        while (rst.next()) {
            AuthorDetailsDTO authorDetailsDTO = new AuthorDetailsDTO(
                    rst.getString(1),
                    rst.getString(2)
            );
            authorDetailsDTOS.add(authorDetailsDTO);
        }
        return authorDetailsDTOS;
    }
}

