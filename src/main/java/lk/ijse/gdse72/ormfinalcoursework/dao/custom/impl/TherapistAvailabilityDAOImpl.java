package lk.ijse.gdse72.ormfinalcoursework.dao.custom.impl;

import lk.ijse.gdse72.ormfinalcoursework.config.FactoryConfiguration;
import lk.ijse.gdse72.ormfinalcoursework.dao.custom.TherapistAvailabilityDAO;
import lk.ijse.gdse72.ormfinalcoursework.dto.AvailabilityChartChart;
import lk.ijse.gdse72.ormfinalcoursework.dto.TherapistAvailabilityDTO;
import lk.ijse.gdse72.ormfinalcoursework.entity.Therapist;
import lk.ijse.gdse72.ormfinalcoursework.entity.TherapistAvailability;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
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




    @Override
    public boolean isTherapistAvailable(String therapistName, LocalDate date, LocalTime startTime, LocalTime endTime) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();

        try {
            // First check if the therapist has any availability on that date
            String availabilityHql = "FROM TherapistAvailability " +
                    "WHERE therapistName = :name " +
                    "AND availableDate = :date " +
                    "AND startTime <= :requestedStart " +
                    "AND endTime >= :requestedEnd";

            Query<TherapistAvailability> availabilityQuery = session.createQuery(availabilityHql, TherapistAvailability.class);
            availabilityQuery.setParameter("name", therapistName);
            availabilityQuery.setParameter("date", date);
            availabilityQuery.setParameter("requestedStart", startTime);
            availabilityQuery.setParameter("requestedEnd", endTime);

            List<TherapistAvailability> availabilities = availabilityQuery.getResultList();

            // If no availabilities include the requested time slot, the therapist is not available
            if (availabilities.isEmpty()) {
                return false;
            }

            // Now check if the therapist has any appointments during this time
            // This part assumes you have a method to check for existing appointments
            // You would need to implement this based on your appointment entity
            // For example:
            // boolean hasConflictingAppointments = appointmentDAO.hasConflictingAppointments(therapistName, date, startTime, endTime);
            // return !hasConflictingAppointments;

            // For now, we'll just check availability
            return true;
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
    public int countAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            String hql = "SELECT COUNT(*) FROM TherapistAvailability";
            Query<Long> query = session.createQuery(hql, Long.class);
            Long count = query.uniqueResult();
            return count != null ? count.intValue() : 0;
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
    public List<AvailabilityChartChart> getAllAvailabilitySummary() throws Exception {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Transaction transaction = session.beginTransaction();

            String hql = "SELECT TherapistAvailabilityDTO(" +
                    "t.therapist.name, COUNT(t)) " +
                    "FROM TherapistAvailability t GROUP BY t.therapist.name";

            List<AvailabilityChartChart> list = session.createQuery(hql, AvailabilityChartChart.class).list();

            transaction.commit();
            return list.stream()
                    .map(dto -> new AvailabilityChartChart(dto.getTherapistName(), dto.getAvailableSlotCount()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error fetching therapist availability summary", e);
        }
    }
}

