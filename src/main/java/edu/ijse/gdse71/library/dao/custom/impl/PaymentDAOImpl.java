package edu.ijse.gdse71.library.dao.custom.impl;

import edu.ijse.gdse71.library.dao.custom.PaymentDAO;
import edu.ijse.gdse71.library.dto.PaymentDTO;
import edu.ijse.gdse71.library.entity.Member;
import edu.ijse.gdse71.library.entity.Payment;
import edu.ijse.gdse71.library.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentDAOImpl implements PaymentDAO {

    public String getNextId()  throws SQLException {
        String query = "select Payment_Id from Payment order by Payment_Id desc limit 1";
        return CrudUtil.getNextId(query,"PY%03d","PY001");
    }

    @Override
    public boolean save(Payment entity) throws SQLException {
        return CrudUtil.execute(
                "insert into Payment values (?,?,?,?,?,?)",
                entity.getPaymentID(),
                entity.getMemberID(),
                entity.getPurpose(),
                entity.getPrice(),
                entity.getPaymentDate(),
                entity.getUserID()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return CrudUtil.execute("delete from Payment where Payment_Id=?", id);
    }

    @Override
    public boolean update(Payment entity) throws SQLException {
        return CrudUtil.execute(
                "update Reservation set Member_Id=?, Purpose=?, Price=?, Payment_date=?,User_Id=?  where Payment_Id=?",
                entity.getMemberID(),
                entity.getPurpose(),
                entity.getPrice(),
                entity.getPaymentDate(),
                entity.getUserID(),
                entity.getPaymentID()
        );
    }

    @Override
    public ArrayList<Payment> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Payment");

        ArrayList<Payment> allPayments = new ArrayList<>();

        while (rst.next()) {
            Payment entity = new Payment(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getDate(5),
                    rst.getString(6)

            );
            allPayments.add(entity);
        }
        return allPayments;
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
    public ArrayList<Payment> findById(String selectedId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Payment where Payment_Id=?", selectedId);

        ArrayList<Payment> payments = new ArrayList<>();

        while (rst.next()) {
            Payment payment = new Payment(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getDate(5),
                    rst.getString(6)
            );
            payments.add(payment);
        }
        return payments;
    }


}

