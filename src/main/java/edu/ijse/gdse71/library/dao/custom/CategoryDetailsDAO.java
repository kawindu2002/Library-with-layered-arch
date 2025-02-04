package edu.ijse.gdse71.library.dao.custom;

import edu.ijse.gdse71.library.dao.SuperDAO;
import edu.ijse.gdse71.library.entity.CategoryDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CategoryDetailsDAO extends SuperDAO {

    boolean saveCategoryDetail(CategoryDetails categoryDetails) throws SQLException;

    boolean deleteCategoryDetail(String bookId) throws SQLException;

    ArrayList<CategoryDetails> getAllCategoryDetails() throws SQLException;

}

