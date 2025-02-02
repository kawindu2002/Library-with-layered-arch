package edu.ijse.gdse71.library.dao.custom;

import edu.ijse.gdse71.library.dao.CrudDAO;
import edu.ijse.gdse71.library.dto.LoanDTO;
import java.sql.SQLException;

public interface LoanDAO extends CrudDAO<LoanDTO> {

    static String getNextLoanId() throws SQLException {
        return null;
    }

}

