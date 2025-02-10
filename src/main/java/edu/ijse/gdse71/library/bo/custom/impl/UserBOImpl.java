package edu.ijse.gdse71.library.bo.custom.impl;

import edu.ijse.gdse71.library.bo.custom.UserBO;
import edu.ijse.gdse71.library.dao.DAOFactory;
import edu.ijse.gdse71.library.dao.custom.UserDAO;
import edu.ijse.gdse71.library.dto.ReturnDTO;
import edu.ijse.gdse71.library.dto.UserDTO;
import edu.ijse.gdse71.library.entity.Returns;
import edu.ijse.gdse71.library.entity.User;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserBOImpl implements UserBO {

    UserDAO userDAO= (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.USER);

    public String getNextId() throws SQLException {
        return userDAO.getNextId();

    }



    @Override
    public boolean save(UserDTO dto) throws SQLException {

        return userDAO.save(new User(
                dto.getUserID(),
                dto.getName(),
                dto.getPassword(),
                dto.getRole(),
                dto.getRegDate(),
                dto.getState()
        ));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return userDAO.delete(id);
    }

    @Override
    public boolean update(UserDTO dto) throws SQLException {
        return userDAO.update(new User(
                dto.getUserID(),
                dto.getName(),
                dto.getPassword(),
                dto.getRole(),
                dto.getRegDate(),
                dto.getState()
        ));
    }

    @Override
    public ArrayList<UserDTO> getAll() throws SQLException {

        ArrayList<User> users = userDAO.getAll();
        ArrayList<UserDTO> userDTOS = new ArrayList<>();
        for (User user:users) {
            userDTOS.add(new UserDTO(
                    user.getUserID(),
                    user.getName(),
                    user.getPassword(),
                    user.getRole(),
                    user.getRegDate(),
                    user.getState()

            ));
        }
        return userDTOS;
    }


    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        return userDAO.getAllIds();

    }


    @Override
    public UserDTO findById(String selectedId) throws SQLException {
        ArrayList<User> users = userDAO.findById(selectedId);

        if (!users.isEmpty()) {
            User user = users.get(0);
            return new UserDTO(
                user.getUserID(),
                user.getName(),
                user.getPassword(),
                user.getRole(),
                user.getRegDate(),
                user.getState()

            );
        } else {
            return null;
        }
    }


    @Override
    public String getState(String id) throws SQLException {
        return userDAO.getState(id);

    }
}

