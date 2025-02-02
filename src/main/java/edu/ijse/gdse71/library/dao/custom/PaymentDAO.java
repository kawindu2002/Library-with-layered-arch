package edu.ijse.gdse71.library.dao.custom;

import edu.ijse.gdse71.library.dao.CrudDAO;
import edu.ijse.gdse71.library.dto.PaymentDTO;

import java.sql.SQLException;

public interface PaymentDAO extends CrudDAO<PaymentDTO> {

     static String getNextPaymentId() throws SQLException {
        return null;
    }

}

