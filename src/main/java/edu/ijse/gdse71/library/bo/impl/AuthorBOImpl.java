package edu.ijse.gdse71.library.bo.impl;

import edu.ijse.gdse71.library.bo.custom.AuthorBO;
import edu.ijse.gdse71.library.dao.DAOFactory;
import edu.ijse.gdse71.library.dao.custom.AuthorDAO;
import edu.ijse.gdse71.library.dto.AuthorDTO;
import edu.ijse.gdse71.library.entity.Author;
import edu.ijse.gdse71.library.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AuthorBOImpl implements AuthorBO {

    AuthorDAO authorDAO= (AuthorDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.AUTHOR);

    public String getNextId() throws SQLException {
        return authorDAO.getNextId();
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
        ArrayList<Author> authors = authorDAO.getAll();
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
        return authorDAO.getAllIds();
    }


    @Override
    public AuthorDTO findById(String selectedId) throws SQLException {
//        ResultSet rst = CrudUtil.execute("select * from Author where Author_Id=?", selectedId);

//        if (rst.next()) {
//            return new Author(
//                    rst.getString(1),
//                    rst.getString(2),
//                    rst.getString(3),
//                    rst.getDate(4)
//            );
//        }

        return null;
    }

}


