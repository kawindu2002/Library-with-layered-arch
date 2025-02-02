package edu.ijse.gdse71.library.dao;

import java.sql.SQLException;
import java.util.ArrayList;


public interface CrudDAO<T>  extends SuperDAO{

    boolean save(T dto) throws SQLException;
    boolean delete(String id) throws SQLException;
    boolean update(T dto) throws SQLException;
    ArrayList<T> getAll() throws SQLException;
    ArrayList<String> getAllIds() throws SQLException;
    T findById(String selectedId) throws SQLException;

}

