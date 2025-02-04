package edu.ijse.gdse71.library.bo.custom.impl;

import edu.ijse.gdse71.library.bo.custom.AuthorDetailsBO;
import edu.ijse.gdse71.library.dao.DAOFactory;
import edu.ijse.gdse71.library.dao.custom.AuthorDetailsDAO;
import edu.ijse.gdse71.library.dto.AuthorDetailsDTO;
import edu.ijse.gdse71.library.entity.AuthorDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public class AuthorDetailsBOImpl implements AuthorDetailsBO {

    AuthorDetailsDAO authorDetailsDAO = (AuthorDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.AUTHOR_DETAILS);

    public boolean saveAuthorDetailsList(ArrayList<AuthorDetailsDTO> authorDetailsDTOS) throws SQLException {
        for (AuthorDetailsDTO authorDetailsDTO : authorDetailsDTOS) {
            AuthorDetails authorDetails = new AuthorDetails();
            authorDetails.setBookID(authorDetailsDTO.getBookID());
            authorDetails.setAuthorID(authorDetailsDTO.getAuthorID());
            boolean isAuthorDetailsSaved = authorDetailsDAO.saveAuthorDetail(authorDetails);
            if (!isAuthorDetailsSaved) {
                return false;
            }
        }
        return true;
    }

    public boolean deleteAuthorDetailsList(String bookId) throws SQLException {
        boolean isAuthorDetailsDeleted = authorDetailsDAO.deleteAuthorDetail(bookId);
        if (!isAuthorDetailsDeleted) {
            return false;
        }
        return true;
    }

    //---------------------------------------------------------------------------------

    @Override
    public ArrayList<AuthorDetailsDTO> getAllAuthorDetails() throws SQLException {
        ArrayList<AuthorDetails> authorDetailss = authorDetailsDAO.getAllAuthorDetails();
        ArrayList<AuthorDetailsDTO> AuthorDetailsDTOS = new ArrayList<>();
        for (AuthorDetails authorDetails:authorDetailss) {
            AuthorDetailsDTOS.add(new AuthorDetailsDTO(

                    authorDetails.getBookID(),
                    authorDetails.getAuthorID()
            ));
        }
        return AuthorDetailsDTOS;
    }

}