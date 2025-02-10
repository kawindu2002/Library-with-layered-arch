package edu.ijse.gdse71.library.bo.custom.impl;

import edu.ijse.gdse71.library.bo.custom.CategoryBO;
import edu.ijse.gdse71.library.dao.DAOFactory;
import edu.ijse.gdse71.library.dao.custom.CategoryDAO;
import edu.ijse.gdse71.library.dto.BookshelfDTO;
import edu.ijse.gdse71.library.dto.CategoryDTO;
import edu.ijse.gdse71.library.entity.Bookshelf;
import edu.ijse.gdse71.library.entity.Category;

import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryBOImpl implements CategoryBO {

    CategoryDAO categoryDAO= (CategoryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.CATEGORY);

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
        return categoryDAO.getAllIds();

    }

    @Override
    public CategoryDTO findById(String selectedId) throws SQLException {
        ArrayList<Category> categories = categoryDAO.findById(selectedId);

        if (!categories.isEmpty()) {
            Category category = categories.get(0);
            return new CategoryDTO(
                category.getCategoryID(),
                category.getDescription(),
                category.getRegDate()
            );
        } else {
            return null;
        }
    }

}

