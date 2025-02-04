package edu.ijse.gdse71.library.bo.custom;

import edu.ijse.gdse71.library.bo.SuperBO;
import edu.ijse.gdse71.library.dto.AuthorDTO;
import edu.ijse.gdse71.library.dto.AuthorDetailsDTO;
import edu.ijse.gdse71.library.entity.AuthorDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AuthorDetailsBO extends SuperBO {
    boolean saveAuthorDetailsList(ArrayList<AuthorDetailsDTO> authorDetailsDTOS) throws SQLException;
    boolean deleteAuthorDetailsList(String bookId) throws SQLException;
    ArrayList<AuthorDetailsDTO> getAllAuthorDetails() throws SQLException;

}

