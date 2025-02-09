package edu.ijse.gdse71.library.dao.custom.impl;

import edu.ijse.gdse71.library.dao.custom.CategoryDAO;
import edu.ijse.gdse71.library.dto.CategoryDTO;
import edu.ijse.gdse71.library.entity.Bookshelf;
import edu.ijse.gdse71.library.entity.Category;
import edu.ijse.gdse71.library.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDAOImpl implements CategoryDAO {

    public String getNextId()  throws SQLException {
        String query = "select Category_Id from Category order by Category_Id desc limit 1";
        return CrudUtil.getNextId(query,"CT%03d","CT001");
    }

    @Override
    public boolean save(Category entity) throws SQLException {
        return CrudUtil.execute(
                "insert into Category values (?,?,?)",
                entity.getCategoryID(),
                entity.getDescription(),
                entity.getRegDate()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return CrudUtil.execute("delete from Category where Category_Id=?", id);
    }

    @Override
    public boolean update(Category entity) throws SQLException {
        return CrudUtil.execute(
                "update Category set Description=?,Reg_date=? where Category_Id=?",
                entity.getDescription(),
                entity.getRegDate(),
                entity.getCategoryID()
        );
    }

    @Override
    public ArrayList<Category> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Category");

        ArrayList<Category> allCategory = new ArrayList<>();

        while (rst.next()) {
            Category entity = new Category(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDate(3)
            );
            allCategory.add(entity);
        }
        return allCategory;
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
    public ArrayList<Category> findById(String selectedId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Category where Category_Id=?", selectedId);

        ArrayList<Category> categories = new ArrayList<>();

        while (rst.next()) {
            Category category = new Category(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDate(3)
            );
            categories.add(category);
        }

        return categories;
    }

}

