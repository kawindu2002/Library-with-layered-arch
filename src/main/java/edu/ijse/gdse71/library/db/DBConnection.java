package edu.ijse.gdse71.library.db;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
public class DBConnection {
    private static DBConnection dbConnection;
    private Connection connection;
    private DBConnection() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/Library",
                "root",
                "Ijse@1234"
        );
    }

    public static DBConnection getInstance() throws SQLException {
        if (dbConnection==null){
            dbConnection=new DBConnection();
        }
        return dbConnection;
    }

}


