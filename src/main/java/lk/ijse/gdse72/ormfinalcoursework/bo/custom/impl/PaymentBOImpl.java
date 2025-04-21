package lk.ijse.gdse72.ormfinalcoursework.bo.custom.impl;

import lk.ijse.gdse72.ormfinalcoursework.bo.custom.PaymentBO;
import lk.ijse.gdse72.ormfinalcoursework.dao.DAOFactory;
import lk.ijse.gdse72.ormfinalcoursework.dao.custom.PaymentDAO;
import lk.ijse.gdse72.ormfinalcoursework.dao.custom.TherapyProgramDAO;
import lk.ijse.gdse72.ormfinalcoursework.dto.PaymentDTO;
import lk.ijse.gdse72.ormfinalcoursework.dto.TherapistDTO;
import lk.ijse.gdse72.ormfinalcoursework.dto.TherapyProgramDTO;
import lk.ijse.gdse72.ormfinalcoursework.entity.Payment;
import lk.ijse.gdse72.ormfinalcoursework.entity.Therapist;
import lk.ijse.gdse72.ormfinalcoursework.entity.TherapyProgram;

import java.util.ArrayList;

public class PaymentBOImpl implements PaymentBO {

    PaymentDAO PAYMENTDAO = DAOFactory.getInstance().getDAO(DAOFactory.DaoType.PAYMENT);


    @Override
    public String getNextPaymentId() throws Exception {
        return PAYMENTDAO.getNextPaymentId();
    }

    @Override
    public ArrayList<PaymentDTO> getAllPayments() throws Exception {
        ArrayList<PaymentDTO> paymentDTOS = new ArrayList<>();
        ArrayList<Payment> payments = (ArrayList<Payment>) PAYMENTDAO.getAll();

        for (Payment payment : payments) {
            paymentDTOS.add(new PaymentDTO(
                    payment.getId(),
                    payment.getSessionId(),
                    payment.getPatientName(),
                    payment.getAmount(),
                    payment.getPaymentMethod(),
                    payment.getPaymentDate(),
                    payment.getStatus(),
                    payment.getPaidAmount(),
                    payment.getBalance()
            ));
        }
        return paymentDTOS;
    }

    @Override
    public boolean savePayment(PaymentDTO paymentDTO) throws Exception {
        return PAYMENTDAO.save(new Payment(
                paymentDTO.getId(),
                paymentDTO.getSessionId(),
                paymentDTO.getPatientName(),
                paymentDTO.getAmount(),
                paymentDTO.getPaymentMethod(),
                paymentDTO.getPaymentDate(),
                paymentDTO.getStatus(),
                paymentDTO.getPaidAmount(),
                paymentDTO.getBalance()
        ));
    }

    @Override
    public boolean updatePayment(PaymentDTO paymentDTO) throws Exception {
        return PAYMENTDAO.update(new Payment(
                paymentDTO.getId(),
                paymentDTO.getSessionId(),
                paymentDTO.getPatientName(),
                paymentDTO.getAmount(),
                paymentDTO.getPaymentMethod(),
                paymentDTO.getPaymentDate(),
                paymentDTO.getStatus(),
                paymentDTO.getPaidAmount(),
                paymentDTO.getBalance()
        ));
    }

    @Override
    public boolean deletePayment(String paymentId) throws Exception {
        return PAYMENTDAO.delete(paymentId);
    }

    @Override
    public PaymentDTO searchPayment(String paymentId) throws Exception {
        Payment payment = PAYMENTDAO.search(paymentId);

        if (payment == null) {
            return null;
        }
        return new PaymentDTO(
                payment.getId(),
                payment.getSessionId(),
                payment.getPatientName(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getPaymentDate(),
                payment.getStatus(),
                payment.getPaidAmount(),
                payment.getBalance()
        );
    }
}
