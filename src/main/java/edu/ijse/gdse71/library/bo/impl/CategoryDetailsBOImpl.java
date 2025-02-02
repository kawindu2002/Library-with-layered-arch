package edu.ijse.gdse71.library.bo.impl;

import edu.ijse.gdse71.library.bo.custom.CategoryDetailsBO;
import edu.ijse.gdse71.library.dao.custom.CategoryDetailsDAO;
import edu.ijse.gdse71.library.dto.CategoryDetailsDTO;
import edu.ijse.gdse71.library.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDetailsBOImpl implements CategoryDetailsBO {

    @Override
    public boolean saveCategoryDetailsList(ArrayList<CategoryDetailsDTO> categoryDetailsDTOS) throws SQLException {
         //Iterate through each category detail in the list
        for (CategoryDetailsDTO categoryDetailsDTO : categoryDetailsDTOS) {
            // @isCategoryDetailsSaved: Saves the individual category detail
            boolean isCategoryDetailsSaved = saveCategoryDetail(categoryDetailsDTO);
            if (!isCategoryDetailsSaved) {
                // Return false if saving any category detail fails
                return false;
            }

        }
        // Return true if all category details are saved.
        return true;
    }

    @Override
    public boolean saveCategoryDetail(CategoryDetailsDTO categoryDetailsDTO) throws SQLException {
        // Executes an insert query to save the category detail into the database
        return CrudUtil.execute(
                "insert into Category_Book values (?,?)",
                categoryDetailsDTO.getBookID(),
                categoryDetailsDTO.getCategoryID()
        );
    }

    @Override
    public boolean deleteCategoryDetailsList(String bookId) throws SQLException {
        boolean isCategoryDetailsDeleted = deleteCategoryDetail(bookId);
        if (!isCategoryDetailsDeleted) {
            return false;
        }

        return true;
    }

    @Override
    public boolean deleteCategoryDetail(String bookId) throws SQLException {
        // Executes a delete query to delete the category detail in the database
        return CrudUtil.execute("delete from Category_Book where Book_Id=?", bookId);
    }

    @Override
    public ArrayList<CategoryDetailsDTO> getAllCategoryDetails() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Category_Book");

        ArrayList<CategoryDetailsDTO> categoryDetailsDTOS = new ArrayList<>();

        while (rst.next()) {
            CategoryDetailsDTO categoryDetailsDTO = new CategoryDetailsDTO(
                    rst.getString(1),
                    rst.getString(2)
            );
            categoryDetailsDTOS.add(categoryDetailsDTO);
        }
        return categoryDetailsDTOS;
    }
}

