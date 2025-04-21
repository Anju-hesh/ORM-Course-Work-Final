package lk.ijse.gdse72.ormfinalcoursework.dao.custom.impl;

import lk.ijse.gdse72.ormfinalcoursework.config.FactoryConfiguration;
import lk.ijse.gdse72.ormfinalcoursework.dao.custom.TherapistDAO;
import lk.ijse.gdse72.ormfinalcoursework.entity.Patient;
import lk.ijse.gdse72.ormfinalcoursework.entity.Therapist;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TherapistDAOImpl implements TherapistDAO {

    @Override
    public String getNextID() {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {

            Integer maxNum = (Integer) session.createQuery(
                    "SELECT MAX(CAST(SUBSTRING(t.therapistId, 5) AS int)) " +
                            "FROM Therapist t " +
                            "WHERE t.therapistId LIKE 'THER%' " +
                            "AND LENGTH(t.therapistId) = 7"
            ).uniqueResult();

            return maxNum != null ?
                    String.format("THER%03d", maxNum + 1) :
                    "THER001";

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate next ID", e);
        }
    }

    @Override
    public ArrayList<String> getTherapist() throws SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;
        ArrayList<String> therapistIds = new ArrayList<>();

        try {
            transaction = session.beginTransaction();

            Query<String> query = session.createQuery("SELECT t.name FROM Therapist t", String.class);
            therapistIds = (ArrayList<String>) query.getResultList();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return therapistIds;
    }

    @Override
    public boolean save(Therapist entity) throws Exception {
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
    public boolean update(Therapist entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {

            Therapist existingTherapist = session.get(Therapist.class, entity.getTherapistId());
            if (existingTherapist == null) {
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
            Query query = session.createQuery("DELETE FROM Therapist WHERE therapistId = :therapistId");
            query.setParameter("therapistId", id);
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
    public Therapist search(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            Therapist therapist = session.get(Therapist.class, id);
            return therapist;
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
    public List<Therapist> getAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            List<Therapist> therapists = session.createQuery("FROM Therapist ", Therapist.class).list();
            return therapists;
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
