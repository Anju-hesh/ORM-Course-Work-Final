package lk.ijse.gdse72.ormfinalcoursework.dao.custom.impl;

import lk.ijse.gdse72.ormfinalcoursework.config.FactoryConfiguration;
import lk.ijse.gdse72.ormfinalcoursework.dao.custom.PaymentDAO;
import lk.ijse.gdse72.ormfinalcoursework.entity.Patient;
import lk.ijse.gdse72.ormfinalcoursework.entity.Payment;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {

    @Override
    public String getNextPaymentId() throws Exception {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {

            Integer maxNum = (Integer) session.createQuery(
                    "SELECT MAX(CAST(SUBSTRING(p.id, 5) AS int)) " +
                            "FROM Payment p " +
                            "WHERE p.id LIKE 'PAY%' " +
                            "AND LENGTH(p.id) = 6"
            ).uniqueResult();

            return maxNum != null ?
                    String.format("PAY%03d", maxNum + 1) :
                    "PAY001";

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate next ID", e);
        }
    }

    @Override
    public boolean save(Payment entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public boolean update(Payment entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {

            Payment existingPayment = session.get(Payment.class, entity.getId());
            if (existingPayment == null) {
                return false;
            }

            session.merge(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public boolean delete(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query query = session.createQuery("DELETE FROM Payment WHERE id = :paymentId");
            query.setParameter("paymentId", id);
            int result = query.executeUpdate();

            transaction.commit();
            return result > 0;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Payment search(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            Payment payment = session.get(Payment.class, id);
            return payment;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Payment> getAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            List<Payment> payment = session.createQuery("FROM Payment ", Payment.class).list();
            return payment;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void setSession(Session session) throws Exception {

    }
}
