package edu.ijse.gdse71.library.bo.custom;

import edu.ijse.gdse71.library.bo.SuperBO;
import edu.ijse.gdse71.library.dto.AuthorDetailsDTO;
import edu.ijse.gdse71.library.dto.CategoryDetailsDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CategoryDetailsBO extends SuperBO {
    boolean saveCategoryDetailsList(ArrayList<CategoryDetailsDTO> categoryDetailsDTOS) throws SQLException;
    boolean saveCategoryDetail(CategoryDetailsDTO categoryDetailsDTO) throws SQLException;
    boolean deleteCategoryDetailsList(String bookId) throws SQLException;
    boolean deleteCategoryDetail(String bookId) throws SQLException;
    ArrayList<CategoryDetailsDTO> getAllCategoryDetails() throws SQLException;

}

