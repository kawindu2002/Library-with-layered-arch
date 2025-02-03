package edu.ijse.gdse71.library.bo.impl;

import edu.ijse.gdse71.library.bo.custom.CategoryBO;
import edu.ijse.gdse71.library.dao.DAOFactory;
import edu.ijse.gdse71.library.dao.custom.BookshelfDAO;
import edu.ijse.gdse71.library.dao.custom.CategoryDAO;
import edu.ijse.gdse71.library.dto.BookshelfDTO;
import edu.ijse.gdse71.library.dto.CategoryDTO;
import edu.ijse.gdse71.library.entity.Bookshelf;
import edu.ijse.gdse71.library.entity.Category;
import edu.ijse.gdse71.library.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryBOImpl implements CategoryBO {

    CategoryDAO categoryDAO= (CategoryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CATEGORY);

    public String getNextId() throws SQLException {
        return categoryDAO.getNextId();

    }

    @Override
    public boolean save(CategoryDTO dto) throws SQLException {

        return categoryDAO.save(new Category(
                dto.getCategoryID(),
                dto.getDescription(),
                dto.getRegDate()));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return categoryDAO.delete(id);
    }

    @Override
    public boolean update(CategoryDTO dto) throws SQLException {
        return categoryDAO.update(new Category(
                dto.getCategoryID(),
                dto.getDescription(),
                dto.getRegDate()));
    }

    @Override
    public ArrayList<CategoryDTO> getAll() throws SQLException {
        ArrayList<Category> categories = categoryDAO.getAll();
        ArrayList<CategoryDTO> categoryDTOS=new ArrayList<>();
        for (Category category:categories) {
            categoryDTOS.add(new CategoryDTO(
                    category.getCategoryID(),
                    category.getDescription(),
                    category.getRegDate()));
        }
        return categoryDTOS;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Category_Id from Category");

        ArrayList<String> categoryIds = new ArrayList<>();

        while (rst.next()) {
            categoryIds.add(rst.getString(1));
        }

        return categoryIds;
    }

    @Override
    public CategoryDTO findById(String selectedId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Category where Category_Id=?", selectedId);

        if (rst.next()) {
            return new CategoryDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDate(3)
            );
        }
        return null;
    }

}
