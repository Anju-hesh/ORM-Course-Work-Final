package lk.ijse.gdse72.ormfinalcoursework.dao.custom.impl;

import lk.ijse.gdse72.ormfinalcoursework.config.FactoryConfiguration;
import lk.ijse.gdse72.ormfinalcoursework.dao.custom.TherapistAvailabilityDAO;
import lk.ijse.gdse72.ormfinalcoursework.dto.TherapistAvailabilityDTO;
import lk.ijse.gdse72.ormfinalcoursework.entity.Therapist;
import lk.ijse.gdse72.ormfinalcoursework.entity.TherapistAvailability;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TherapistAvailabilityDAOImpl implements TherapistAvailabilityDAO {

    @Override
    public boolean save(TherapistAvailability entity) throws Exception {
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
    public boolean update(TherapistAvailability entity) throws Exception {
        return false;
    }

    @Override
    public boolean delete(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query query = session.createQuery("DELETE FROM TherapistAvailability WHERE id = :therapistId");
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
    public TherapistAvailability search(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            TherapistAvailability availability = session.get(TherapistAvailability.class, id);
            return availability;
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
    public List<TherapistAvailability> getAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            List<TherapistAvailability> availabilities = session.createQuery("FROM TherapistAvailability ", TherapistAvailability.class).list();
            return availabilities;
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





    @Override
    public List<TherapistAvailabilityDTO> getAvailabilityForTherapistOnDate(
            String therapistName, LocalDate date) {

        try (Session session = FactoryConfiguration.getInstance().getSession()) {

            String hql = "FROM TherapistAvailability " +
                    "WHERE therapistName = :name " +
                    "AND availableDate = :date " +
                    "ORDER BY startTime";

            return session.createQuery(hql, TherapistAvailability.class)
                    .setParameter("name", therapistName)
                    .setParameter("date", date)
                    .getResultList()
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
    }




    @Override
    public List<TherapistAvailability> searchTherapist(String therapistName) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();

        try {
            String hql = "FROM TherapistAvailability WHERE therapistName = :name";
            Query<TherapistAvailability> query = session.createQuery(hql, TherapistAvailability.class);
            query.setParameter("name", therapistName);

            List<TherapistAvailability> availabilities = query.getResultList();
            return availabilities;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private TherapistAvailabilityDTO convertToDTO(TherapistAvailability entity) {
        return new TherapistAvailabilityDTO(
                entity.getId(),
                entity.getTherapistName(),
                entity.getAvailableDate(),
                entity.getStartTime(),
                entity.getEndTime()
        );
    }


}

