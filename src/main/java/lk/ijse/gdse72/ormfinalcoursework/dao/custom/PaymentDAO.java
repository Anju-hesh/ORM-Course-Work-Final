package lk.ijse.gdse72.ormfinalcoursework.dao.custom;

import lk.ijse.gdse72.ormfinalcoursework.dao.CrudDAO;
import lk.ijse.gdse72.ormfinalcoursework.entity.Payment;

public interface PaymentDAO extends CrudDAO<Payment , String> {
    String getNextPaymentId() throws Exception;
}
