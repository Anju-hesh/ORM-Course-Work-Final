package lk.ijse.gdse72.ormfinalcoursework.dao;

import lk.ijse.gdse72.ormfinalcoursework.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {}

    public static DAOFactory getInstance() {
        if (daoFactory == null) {
            daoFactory = new DAOFactory();
        }
        return daoFactory;
    }

    public enum DaoType {
        PATIENT,
        PAYMENT,
        THERAPIST,
        AVAILABILITY,
        THERAPY_PROGRAM,
        THERAPY_SESSION,
        USER
    }

    @SuppressWarnings("unchecked")
    public <T extends SuperDAO> T getDAO(DaoType type) {
        return switch (type) {
            case PATIENT -> (T) new PatientDAOImpl();
            case PAYMENT -> (T) new PaymentDAOImpl();
            case THERAPIST -> (T) new TherapistDAOImpl();
            case AVAILABILITY -> (T) new TherapistAvailabilityDAOImpl();
            case THERAPY_PROGRAM -> (T) new TherapyProgramDAOImpl();
            case THERAPY_SESSION -> (T) new TherapySessionDAOImpl();
            case USER -> (T) new UserDAOImpl();
        };
    }
}
