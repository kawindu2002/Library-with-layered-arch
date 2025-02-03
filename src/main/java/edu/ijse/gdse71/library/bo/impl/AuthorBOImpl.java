package edu.ijse.gdse71.library.bo.impl;

import edu.ijse.gdse71.library.bo.custom.AuthorBO;
import edu.ijse.gdse71.library.dao.DAOFactory;
import edu.ijse.gdse71.library.dao.custom.AuthorDAO;
import edu.ijse.gdse71.library.dto.AuthorDTO;
import edu.ijse.gdse71.library.entity.Author;

import java.sql.SQLException;
import java.util.ArrayList;

public class AuthorBOImpl implements AuthorBO {

    AuthorDAO authorDAO= (AuthorDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.AUTHOR);

    public String getNextId() throws SQLException {
        return AuthorDAO.getNextId();

    }


    @Override
    public boolean save(AuthorDTO dto) throws SQLException {
        return authorDAO.save(new Author(
                dto.getAuthorID(),
                dto.getName(),
                dto.getBiography(),
                dto.getRegDate()));
    }

    @Override
    public boolean delete(String id) throws SQLException {
       return authorDAO.delete(id);

    }

    @Override
    public boolean update(AuthorDTO dto) throws SQLException {
        return authorDAO.update(new Author(dto.getAuthorID(),
                dto.getName(),
                dto.getBiography(),
                dto.getRegDate()));

    }


    @Override
    public ArrayList<AuthorDTO> getAll() throws SQLException {
        ArrayList<Author> customers=authorDAO.getAll();
        ArrayList<AuthorDTO> authorDTOS=new ArrayList<>();
        for (Author author:authors) {
            authorDTOS.add(new AuthorDTO(
                    author.getAuthorID(),
                    author.getName(),
                    author.getBiography(),
                    author.getRegDate()));
        }
        return authorDTOS;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {

    }

    @Override
    public AuthorDTO findById(String selectedId) throws SQLException {

    }

}

//-------------------------------------------------------------------------------------------------------------------

