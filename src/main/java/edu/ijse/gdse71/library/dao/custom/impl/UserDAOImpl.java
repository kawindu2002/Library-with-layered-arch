package edu.ijse.gdse71.library.dao.custom.impl;

import edu.ijse.gdse71.library.dao.custom.UserDAO;
import edu.ijse.gdse71.library.dto.UserDTO;
import edu.ijse.gdse71.library.entity.Returns;
import edu.ijse.gdse71.library.entity.User;
import edu.ijse.gdse71.library.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {

    public String getNextId()  throws SQLException {
        String query = "select User_Id from User order by User_Id desc limit 1";
        return CrudUtil.getNextId(query, "US%03d", "US001");

    }

    @Override
    public boolean save(User entity) throws SQLException {
        return CrudUtil.execute(
                "insert into User values (?,?,?,?,?,?)",
                entity.getUserID(),
                entity.getName(),
                entity.getPassword(),
                entity.getRole(),
                entity.getRegDate(),
                entity.getState()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return CrudUtil.execute("delete from User where User_Id=?", id);
    }

    @Override
    public boolean update(User entity) throws SQLException {
        return CrudUtil.execute(
                "update User set Name=?, Password=?, Role=?, Reg_date=?,State=? where User_Id=?",
                entity.getName(),
                entity.getPassword(),
                entity.getRole(),
                entity.getRegDate(),
                entity.getState(),
                entity.getUserID()
        );
    }

    @Override
    public ArrayList<User> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from User");

        ArrayList<User> allUsers = new ArrayList<>();

        while (rst.next()) {
            User entity = new User(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDate(5),
                    rst.getString(6)
            );
            allUsers.add(entity);
        }
        return allUsers;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select User_Id from User");

        ArrayList<String> userIds = new ArrayList<>();

        while (rst.next()) {
            userIds.add(rst.getString(1));
        }

        return userIds;
    }


    @Override
    public ArrayList<User> findById(String selectedId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from User where User_Id=?", selectedId);

        ArrayList<User> users = new ArrayList<>();

        while (rst.next()) {
            User user = new User(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDate(5),
                    rst.getString(6)
            );
            users.add(user);
        }
        return users;
    }


    @Override
    public String getState(String id) throws SQLException {
        String query = "select State from User where User_Id = ?";
        return CrudUtil.getState(query,id);    }
}