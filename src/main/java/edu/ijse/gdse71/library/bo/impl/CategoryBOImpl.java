package edu.ijse.gdse71.library.bo.impl;

import edu.ijse.gdse71.library.dao.custom.CategoryDAO;
import edu.ijse.gdse71.library.dto.CategoryDTO;
import edu.ijse.gdse71.library.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryBOImpl implements CategoryDAO {

    static String getNextCategoryId() throws SQLException {
        String query = "select Category_Id from Category order by Category_Id desc limit 1";
        return CrudUtil.getNextId(query,"CT%03d","CT001");
    }

    @Override
    public boolean save(CategoryDTO dto) throws SQLException {
        return CrudUtil.execute(
                "insert into Category values (?,?,?)",
                dto.getCategoryID(),
                dto.getDescription(),
                dto.getRegDate()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return CrudUtil.execute("delete from Category where Category_Id=?", id);
    }

    @Override
    public boolean update(CategoryDTO dto) throws SQLException {
        return CrudUtil.execute(
                "update Category set Description=?,Reg_date=? where Category_Id=?",
                dto.getDescription(),
                dto.getRegDate(),
                dto.getCategoryID()
        );
    }

    @Override
    public ArrayList<CategoryDTO> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Category");

        ArrayList<CategoryDTO> categoryDTOS = new ArrayList<>();

        while (rst.next()) {
            CategoryDTO categoryDTO = new CategoryDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDate(3)
            );
            categoryDTOS.add(categoryDTO);
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
