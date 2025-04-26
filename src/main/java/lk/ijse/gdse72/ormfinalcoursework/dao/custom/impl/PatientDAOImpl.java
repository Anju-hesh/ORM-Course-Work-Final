//package lk.ijse.gdse72.ormfinalcoursework.dao.custom.impl;
//
//import lk.ijse.gdse72.ormfinalcoursework.config.FactoryConfiguration;
//import lk.ijse.gdse72.ormfinalcoursework.dao.custom.PatientDAO;
//import lk.ijse.gdse72.ormfinalcoursework.dto.PatientDTO;
//import lk.ijse.gdse72.ormfinalcoursework.entity.Patient;
//import org.hibernate.Session;
//import org.hibernate.Transaction;
//import org.hibernate.query.Query;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class PatientDAOImpl implements PatientDAO {
//
//    @Override
//    public boolean save(Patient entity) throws Exception {
//        Session session = FactoryConfiguration.getInstance().getSession();
//        Transaction transaction = session.beginTransaction();
//        try {
//            session.persist(entity);
//            transaction.commit();
//            return true;
//        } catch (Exception e) {
//            transaction.rollback();
//            e.printStackTrace();
//            throw e;
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//        }
//    }
//
//    @Override
//    public boolean update(Patient entity) throws Exception {
//        Session session = FactoryConfiguration.getInstance().getSession();
//        Transaction transaction = session.beginTransaction();
//        try {
//
//            Patient existingPatient = session.get(Patient.class, entity.getId());
//            if (existingPatient == null) {
//                return false;
//            }
//
//            session.merge(entity);
//            transaction.commit();
//            return true;
//        } catch (Exception e) {
//            transaction.rollback();
//            e.printStackTrace();
//            throw e;
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//        }
//    }
//
//    @Override
//    public boolean delete(String id) throws Exception {
//        Session session = FactoryConfiguration.getInstance().getSession();
//        Transaction transaction = session.beginTransaction();
//        try {
//            Query query = session.createQuery("DELETE FROM Patient WHERE id = :patientId");
//            query.setParameter("patientId", id);
//            int result = query.executeUpdate();
//
//            transaction.commit();
//            return result > 0;
//        } catch (Exception e) {
//            transaction.rollback();
//            e.printStackTrace();
//            throw e;
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//        }
//    }
//
//    @Override
//    public Patient search(String id) throws Exception {
//        Session session = FactoryConfiguration.getInstance().getSession();
//        try {
//            Patient patient = session.get(Patient.class, id);
//            return patient;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//        }
//    }
//
//    @Override
//    public List<Patient> getAll() throws Exception {
//        Session session = FactoryConfiguration.getInstance().getSession();
//        try {
//            List<Patient> patients = session.createQuery("FROM Patient", Patient.class).list();
//            return patients;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//        }
//    }
//
//    @Override
//    public void setSession(Session session) throws Exception {}
//
//    @Override
//    public String getNextId() {
//        try (Session session = FactoryConfiguration.getInstance().getSession()) {
//
//            Integer maxNum = (Integer) session.createQuery(
//                    "SELECT MAX(CAST(SUBSTRING(p.id, 5) AS int)) " +
//                            "FROM Patient p " +
//                            "WHERE p.id LIKE 'PAT%' " +
//                            "AND LENGTH(p.id) = 6"
//            ).uniqueResult();
//
//            return maxNum != null ?
//                    String.format("PAT%03d", maxNum + 1) :
//                    "PAT001";
//
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to generate next ID", e);
//        }
//    }
//
//    @Override
//    public PatientDTO getPatient(String patientId){
//        Session session = FactoryConfiguration.getInstance().getSession();
//        Transaction transaction = session.beginTransaction();
//
//        try {
//
//            Patient patient = session.createQuery(
//                            "FROM Patient WHERE id = :patientId", Patient.class)
//                    .setParameter("patientId", patientId)
//                    .uniqueResult();
//
//            transaction.commit();
//            session.close();
//
//            if (patient != null) {
//                return new PatientDTO(
//                        patient.getId(),
//                        patient.getFirstName(),
//                        patient.getLastName(),
//                        patient.getAge(),
//                        patient.getGender(),
//                        patient.getMedicalHistory(),
//                        patient.getContactNumber(),
//                        patient.getEmail(),
//                        patient.getAddress(),
//                        patient.getBloodGroup(),
//                        patient.getAllergies()
//                );
//            } else {
//                return null;
//            }
//
//        } catch (Exception e) {
//            transaction.rollback();
//            session.close();
//            throw e;
//        }
//    }
//
//    @Override
//    public ArrayList<String> getPatientid() {
//        Session session = FactoryConfiguration.getInstance().getSession();
//        Transaction transaction = null;
//        ArrayList<String> patientIds = new ArrayList<>();
//
//        try {
//            transaction = session.beginTransaction();
//
//            Query<String> query = session.createQuery("SELECT t.id FROM Patient t", String.class);
//            patientIds = (ArrayList<String>) query.getResultList();
//
//            transaction.commit();
//        } catch (Exception e) {
//            if (transaction != null) transaction.rollback();
//            e.printStackTrace();
//        } finally {
//            session.close();
//        }
//
//        return patientIds;
//    }
//
//    @Override
//    public ArrayList<Patient> getPatientsByTherapyType(String therapyType) throws Exception {
//        Session session = FactoryConfiguration.getInstance().getSession();
//        Transaction transaction = session.beginTransaction();
//
//        String hql = "SELECT DISTINCT p FROM TherapySession ts JOIN ts.patient p WHERE ts.therapyProgram.name = :therapyType";
//        List<Patient> patients = session.createQuery(hql, Patient.class)
//                .setParameter("therapyType", therapyType)
//                .list();
//
//        transaction.commit();
//        session.close();
//
//        return new ArrayList<>(patients);
//    }
//}


package lk.ijse.gdse72.ormfinalcoursework.dao.custom.impl;

import lk.ijse.gdse72.ormfinalcoursework.config.FactoryConfiguration;
import lk.ijse.gdse72.ormfinalcoursework.dao.custom.PatientDAO;
import lk.ijse.gdse72.ormfinalcoursework.dto.PatientDTO;
import lk.ijse.gdse72.ormfinalcoursework.entity.Patient;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PatientDAOImpl implements PatientDAO {

    @Override
    public boolean save(Patient entity) throws Exception {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save patient", e);
        }
    }

    @Override
    public boolean update(Patient entity) throws Exception {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Transaction transaction = session.beginTransaction();
            Patient existingPatient = session.get(Patient.class, entity.getId());
            if (existingPatient == null) return false;

            session.merge(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update patient", e);
        }
    }

    @Override
    public boolean delete(String id) throws Exception {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM Patient WHERE id = :patientId");
            query.setParameter("patientId", id);
            int result = query.executeUpdate();
            transaction.commit();
            return result > 0;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete patient", e);
        }
    }

    @Override
    public Patient search(String id) throws Exception {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            return session.get(Patient.class, id); // Uses second-level cache if enabled
        } catch (Exception e) {
            throw new RuntimeException("Failed to search patient", e);
        }
    }

    @Override
    public List<Patient> getAll() throws Exception {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Query<Patient> query = session.createQuery("FROM Patient", Patient.class);
            query.setCacheable(true); // Enables query cache
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all patients", e);
        }
    }

    @Override
    public void setSession(Session session) throws Exception {
        // Not needed for this implementation
    }

    @Override
    public String getNextId() {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Integer maxNum = (Integer) session.createQuery(
                            "SELECT MAX(CAST(SUBSTRING(p.id, 5) AS int)) FROM Patient p " +
                                    "WHERE p.id LIKE 'PAT%' AND LENGTH(p.id) = 6")
                    .setCacheable(true)
                    .uniqueResult();

            return (maxNum != null) ? String.format("PAT%03d", maxNum + 1) : "PAT001";
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate next ID", e);
        }
    }

    @Override
    public PatientDTO getPatient(String patientId) {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Transaction transaction = session.beginTransaction();

            Patient patient = session.createQuery("FROM Patient WHERE id = :patientId", Patient.class)
                    .setParameter("patientId", patientId)
                    .setCacheable(true)
                    .uniqueResult();

            transaction.commit();

            if (patient != null) {
                return new PatientDTO(
                        patient.getId(),
                        patient.getFirstName(),
                        patient.getLastName(),
                        patient.getAge(),
                        patient.getGender(),
                        patient.getMedicalHistory(),
                        patient.getContactNumber(),
                        patient.getEmail(),
                        patient.getAddress(),
                        patient.getBloodGroup(),
                        patient.getAllergies()
                );
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to get patient DTO", e);
        }
    }

    @Override
    public ArrayList<String> getPatientid() {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Transaction transaction = session.beginTransaction();
            Query<String> query = session.createQuery("SELECT t.id FROM Patient t", String.class);
            query.setCacheable(true);
            ArrayList<String> patientIds = new ArrayList<>(query.getResultList());
            transaction.commit();
            return patientIds;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get patient IDs", e);
        }
    }

    @Override
    public ArrayList<Patient> getPatientsByTherapyType(String therapyType) throws Exception {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Transaction transaction = session.beginTransaction();

            String hql = "SELECT DISTINCT p FROM TherapySession ts JOIN ts.patient p WHERE ts.therapyProgram.name = :therapyType";
            List<Patient> patients = session.createQuery(hql, Patient.class)
                    .setParameter("therapyType", therapyType)
                    .setCacheable(true)
                    .list();

            transaction.commit();
            return new ArrayList<>(patients);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get patients by therapy type", e);
        }
    }
}
