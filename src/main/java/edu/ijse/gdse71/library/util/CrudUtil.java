package edu.ijse.gdse71.library.util;

import edu.ijse.gdse71.library.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudUtil {

    public static <T>T execute(String sql,Object... obj) throws SQLException {

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement(sql);

        for (int i=0;i<obj.length;i++){
            pst.setObject((i+1),obj[i]);
        }

        if (sql.startsWith("select") || sql.startsWith("SELECT")){
            ResultSet resultSet = pst.executeQuery();
            return (T) resultSet;
        }else {
            int i = pst.executeUpdate();
            boolean isSaved = i >0;

            return (T) ((Boolean) isSaved);
        }
    }


    public static String getNextId(String query,String formatPattern,String defaultPattern) throws SQLException {
        ResultSet rst = CrudUtil.execute(query);
        if (rst.next()) {
            String lastId = rst.getString(1); // Last ID
            String substring = lastId.substring(2); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format(formatPattern, newIdIndex); // Return the new ID in format
        }
        return defaultPattern; // Return the default  ID if no data is found
    }

//----------------------------------------------------------------------------------------------------------------------

    public static String getState(String query,String id) throws SQLException {
        ResultSet rst = CrudUtil.execute(query,id);
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }

//----------------------------------------------------------------------------------------------------------------------

}

