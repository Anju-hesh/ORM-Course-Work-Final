package lk.ijse.gdse72.ormfinalcoursework.dao.custom.impl;

import lk.ijse.gdse72.ormfinalcoursework.config.FactoryConfiguration;
import lk.ijse.gdse72.ormfinalcoursework.dao.custom.TherapyProgramDAO;
import lk.ijse.gdse72.ormfinalcoursework.entity.TherapyProgram;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class TherapyProgramDAOImpl implements TherapyProgramDAO {

    @Override
    public String getNextID() throws SQLException {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {

            Integer maxNum = (Integer) session.createQuery(
                    "SELECT MAX(CAST(SUBSTRING(tp.programId, 5) AS int)) " +
                            "FROM TherapyProgram tp " +
                            "WHERE tp.programId LIKE 'TP%' " +
                            "AND LENGTH(tp.programId) = 5"
            ).uniqueResult();

            return maxNum != null ?
                    String.format("TP%03d", maxNum + 1) :
                    "TP001";
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate next ID", e);
        }
    }

    @Override
    public boolean save(TherapyProgram entity) throws Exception {
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
    public boolean update(TherapyProgram entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {

            TherapyProgram existingTherapyProgram = session.get(TherapyProgram.class, entity.getProgramId());
            if (existingTherapyProgram == null) {
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
            Query query = session.createQuery("DELETE FROM TherapyProgram WHERE programId = :programId");
            query.setParameter("programId", id);
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
    public TherapyProgram search(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            TherapyProgram therapyProgram = session.get(TherapyProgram.class, id);
            return therapyProgram;
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
    public List<TherapyProgram> getAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            List<TherapyProgram> therapyPrograms = session.createQuery("FROM TherapyProgram ", TherapyProgram.class).list();
            return therapyPrograms;
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
