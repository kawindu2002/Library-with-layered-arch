package edu.ijse.gdse71.library.bo.impl;

import edu.ijse.gdse71.library.bo.custom.UserBO;
import edu.ijse.gdse71.library.dao.custom.UserDAO;
import edu.ijse.gdse71.library.dto.UserDTO;
import edu.ijse.gdse71.library.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserBOImpl implements UserBO {

    static String getNextUserId() throws SQLException {
        String query = "select User_Id from User order by User_Id desc limit 1";
        return CrudUtil.getNextId(query, "US%03d", "US001");

    }

    @Override
    public boolean save(UserDTO dto) throws SQLException {
        return CrudUtil.execute(
                "insert into User values (?,?,?,?,?,?)",
                dto.getUserID(),
                dto.getName(),
                dto.getPassword(),
                dto.getRole(),
                dto.getRegDate(),
                dto.getState()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return CrudUtil.execute("delete from User where User_Id=?", id);
    }

    @Override
    public boolean update(UserDTO dto) throws SQLException {
        return CrudUtil.execute(
                "update User set Name=?, Password=?, Role=?, Reg_date=?,State=? where User_Id=?",
                dto.getName(),
                dto.getPassword(),
                dto.getRole(),
                dto.getRegDate(),
                dto.getState(),
                dto.getUserID()
        );
    }

    @Override
    public ArrayList<UserDTO> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from User");

        ArrayList<UserDTO> userDTOS = new ArrayList<>();

        while (rst.next()) {
            UserDTO userDTO = new UserDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDate(5),
                    rst.getString(6)
            );
            userDTOS.add(userDTO);
        }
        return userDTOS;
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
    public UserDTO findById(String selectedId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from User where User_Id=?", selectedId);

        if (rst.next()) {
            return new UserDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDate(5),
                    rst.getString(6)
            );
        }
        return null;
    }

    @Override
    public String getState(String id) throws SQLException {
        String query = "select State from User where User_Id = ?";
        return CrudUtil.getState(query,id);    }
}