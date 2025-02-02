package edu.ijse.gdse71.library.bo.custom;

import edu.ijse.gdse71.library.dto.AuthorDTO;
import edu.ijse.gdse71.library.dto.AuthorDetailsDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AuthorDetailsBO {

    boolean saveAuthorDetailsList(ArrayList<AuthorDetailsDTO> authorDetailsDTOS) throws SQLException;

    boolean saveAuthorDetail(AuthorDetailsDTO authorDetailsDTO) throws SQLException;

    boolean deleteAuthorDetailsList(String bookId) throws SQLException;

    boolean deleteAuthorDetail(String bookId) throws SQLException;

    ArrayList<AuthorDetailsDTO> getAllAuthorDetails() throws SQLException;

}

