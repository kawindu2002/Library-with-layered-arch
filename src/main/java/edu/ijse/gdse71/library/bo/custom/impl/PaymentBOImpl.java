package edu.ijse.gdse71.library.bo.custom.impl;

import edu.ijse.gdse71.library.bo.custom.PaymentBO;
import edu.ijse.gdse71.library.dao.DAOFactory;
import edu.ijse.gdse71.library.dao.custom.PaymentDAO;
import edu.ijse.gdse71.library.dto.MemberDTO;
import edu.ijse.gdse71.library.dto.PaymentDTO;
import edu.ijse.gdse71.library.entity.Member;
import edu.ijse.gdse71.library.entity.Payment;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentBOImpl implements PaymentBO {

    PaymentDAO paymentDAO= (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PAYMENT);

    public String getNextId() throws SQLException {
        return paymentDAO.getNextId();

    }

    @Override
    public boolean save(PaymentDTO dto) throws SQLException {

        return paymentDAO.save(new Payment(
                dto.getPaymentID(),
                dto.getMemberID(),
                dto.getPurpose(),
                dto.getPrice(),
                dto.getPaymentDate(),
                dto.getUserID()
        ));
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return paymentDAO.delete(id);
    }

    @Override
    public boolean update(PaymentDTO dto) throws SQLException {

        return paymentDAO.update(new Payment(
                dto.getPaymentID(),
                dto.getMemberID(),
                dto.getPurpose(),
                dto.getPrice(),
                dto.getPaymentDate(),
                dto.getUserID()
        ));
    }

    @Override
    public ArrayList<PaymentDTO> getAll() throws SQLException {

        ArrayList<Payment> payments = paymentDAO.getAll();
        ArrayList<PaymentDTO> paymentDTOS = new ArrayList<>();
        for (Payment payment:payments) {
            paymentDTOS.add(new PaymentDTO(
                    payment.getPaymentID(),
                    payment.getMemberID(),
                    payment.getPurpose(),
                    payment.getPrice(),
                    payment.getPaymentDate(),
                    payment.getUserID()

            ));
        }
        return paymentDTOS;

    }

    @Override
    public ArrayList<String> getAllIds() throws SQLException {
        return paymentDAO.getAllIds();

    }

    @Override
    public PaymentDTO findById(String selectedId) throws SQLException {
        ArrayList<Payment> payments = paymentDAO.findById(selectedId);

        if (!payments.isEmpty()) {
            Payment payment = payments.get(0);
            return new PaymentDTO(
                payment.getPaymentID(),
                payment.getMemberID(),
                payment.getPurpose(),
                payment.getPrice(),
                payment.getPaymentDate(),
                payment.getUserID()
            );
        } else {
            return null;
        }
    }
}

