package edu.ijse.gdse71.library.dao.custom;

import edu.ijse.gdse71.library.dao.SuperDAO;
import edu.ijse.gdse71.library.dto.CategoryDetailsDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CategoryDetailsDAO extends SuperDAO {

    boolean saveCategoryDetailsList(ArrayList<CategoryDetailsDTO> categoryDetailsDTOS) throws SQLException;

    boolean saveCategoryDetail(CategoryDetailsDTO categoryDetailsDTO) throws SQLException;

    boolean deleteCategoryDetailsList(String bookId) throws SQLException;

    boolean deleteCategoryDetail(String bookId) throws SQLException;

    ArrayList<CategoryDetailsDTO> getAllCategoryDetails() throws SQLException;

}

