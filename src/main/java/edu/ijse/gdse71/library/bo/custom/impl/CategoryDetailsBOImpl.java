package edu.ijse.gdse71.library.bo.custom.impl;

import edu.ijse.gdse71.library.bo.custom.CategoryDetailsBO;
import edu.ijse.gdse71.library.dao.DAOFactory;
import edu.ijse.gdse71.library.dao.custom.CategoryDetailsDAO;
import edu.ijse.gdse71.library.dto.CategoryDetailsDTO;
import edu.ijse.gdse71.library.entity.CategoryDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDetailsBOImpl implements CategoryDetailsBO {


    CategoryDetailsDAO categoryDetailsDAO = (CategoryDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CATEGORY_DETAILS);

    public boolean saveCategoryDetailsList(ArrayList<CategoryDetailsDTO> categoryDetailsDTOS) throws SQLException {
        for (CategoryDetailsDTO categoryDetailsDTO : categoryDetailsDTOS) {
            CategoryDetails categoryDetails = new CategoryDetails();
            categoryDetails.setBookID(categoryDetailsDTO.getBookID());
            categoryDetails.setCategoryID(categoryDetailsDTO.getCategoryID());
            boolean isCategoryDetailsSaved = categoryDetailsDAO.saveCategoryDetail(categoryDetails);
            if (!isCategoryDetailsSaved) {
                return false;
            }
        }
        return true;
    }

    public boolean deleteCategoryDetailsList(String bookId) throws SQLException {
        boolean isCategoryDetailsDeleted = categoryDetailsDAO.deleteCategoryDetail(bookId);
        if (!isCategoryDetailsDeleted) {
            return false;
        }
        return true;
    }

    //---------------------------------------------------------------------------------

    @Override
    public ArrayList<CategoryDetailsDTO> getAllCategoryDetails() throws SQLException {
        ArrayList<CategoryDetails> categoryDetailss = categoryDetailsDAO.getAllCategoryDetails();
        ArrayList<CategoryDetailsDTO> CategoryDetailsDTOS = new ArrayList<>();
        for (CategoryDetails categoryDetails : categoryDetailss) {
            CategoryDetailsDTOS.add(new CategoryDetailsDTO(

                    categoryDetails.getBookID(),
                    categoryDetails.getCategoryID()
            ));
        }
        return CategoryDetailsDTOS;
    }
}