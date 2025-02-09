package edu.ijse.gdse71.library.dao.custom.impl;

import edu.ijse.gdse71.library.dao.custom.CategoryDetailsDAO;
import edu.ijse.gdse71.library.dto.CategoryDetailsDTO;
import edu.ijse.gdse71.library.entity.AuthorDetails;
import edu.ijse.gdse71.library.entity.CategoryDetails;
import edu.ijse.gdse71.library.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDetailsDAOImpl implements CategoryDetailsDAO {
    @Override
    public boolean saveCategoryDetail(CategoryDetails entity) throws SQLException {
        // Executes an insert query to save the category detail into the database
        return CrudUtil.execute(
                "insert into Category_Book values (?,?)",
                entity.getBookID(),
                entity.getCategoryID()
        );
    }


    @Override
    public boolean deleteCategoryDetail(String bookId) throws SQLException {
        //Executes a delete query to delete the category detail in the database
        return CrudUtil.execute("delete from Category_Book where Book_Id=?", bookId);
    }


    @Override
    public ArrayList<CategoryDetails> getAllCategoryDetails() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Category_Book");

        ArrayList<CategoryDetails> allCategoryDetails = new ArrayList<>();

        while (rst.next()) {
            CategoryDetails entity = new CategoryDetails(
                    rst.getString(1),
                    rst.getString(2)
            );
            allCategoryDetails.add(entity);
        }
        return allCategoryDetails;
    }
}

