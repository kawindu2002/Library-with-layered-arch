package edu.ijse.gdse71.library.dao.custom;

import edu.ijse.gdse71.library.dao.CrudDAO;
import edu.ijse.gdse71.library.dto.CategoryDTO;
import java.sql.SQLException;

public interface CategoryDAO extends CrudDAO<CategoryDTO> {

     static String getNextCategoryId() throws SQLException {
         return null;
    }

}
