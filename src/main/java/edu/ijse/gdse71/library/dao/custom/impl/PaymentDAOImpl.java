package edu.ijse.gdse71.library.dao.custom.impl;

import edu.ijse.gdse71.library.dao.custom.PaymentDAO;
import edu.ijse.gdse71.library.dto.PaymentDTO;
import edu.ijse.gdse71.library.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentDAOImpl implements PaymentDAO {

    static String getNextPaymentId() throws SQLException {
        String query = "select Payment_Id from Payment order by Payment_Id desc limit 1";
        return CrudUtil.getNextId(query,"PY%03d","PY001");
    }

    @Override
    public boolean save(PaymentDTO dto) throws SQLException {
        return CrudUtil.execute(
                "insert into Payment values (?,?,?,?,?,?)",
                dto.getPaymentID(),
                dto.getMemberID(),
                dto.getPurpose(),
                dto.getPrice(),
                dto.getPaymentDate(),
                dto.getUserID()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return CrudUtil.execute("delete from Payment where Payment_Id=?", id);
    }

    @Override
    public boolean update(PaymentDTO dto) throws SQLException {
        return CrudUtil.execute(
                "update Reservation set Member_Id=?, Purpose=?, Price=?, Payment_date=?,User_Id=?  where Payment_Id=?",
                dto.getMemberID(),
                dto.getPurpose(),
                dto.getPrice(),
                dto.getPaymentDate(),
                dto.getUserID(),
                dto.getPaymentID()
        );
    }

    @Override
    public ArrayList<PaymentDTO> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Payment");

        ArrayList<PaymentDTO> paymentDTOS = new ArrayList<>();

        while (rst.next()) {
            PaymentDTO paymentDTO = new PaymentDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getDate(5),
                    rst.getString(6)

            );
            paymentDTOS.add(paymentDTO);
        }
        return paymentDTOS;
    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Payment_Id from Payment");

        ArrayList<String> paymentIds = new ArrayList<>();

        while (rst.next()) {
            paymentIds.add(rst.getString(1));
        }

        return paymentIds;
    }

    @Override
    public PaymentDTO findById(String selectedId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Payment where Payment_Id=?", selectedId);

        if (rst.next()) {
            return new PaymentDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getDate(5),
                    rst.getString(6)
            );
        }
        return null;
    }

}

