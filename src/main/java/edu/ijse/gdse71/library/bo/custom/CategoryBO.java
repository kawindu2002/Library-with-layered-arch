package edu.ijse.gdse71.library.bo.custom;

import edu.ijse.gdse71.library.bo.SuperBO;
import edu.ijse.gdse71.library.dto.CategoryDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CategoryBO extends SuperBO {

    static String getNextCategoryId() throws SQLException {
        return null;
    }

    boolean save(CategoryDTO dto) throws SQLException;
    boolean delete(String id) throws SQLException;
    boolean update(CategoryDTO dto) throws SQLException;
    ArrayList<CategoryDTO> getAll() throws SQLException;
    ArrayList<String> getAllIds() throws SQLException;
    CategoryDTO findById(String selectedId) throws SQLException;
}
