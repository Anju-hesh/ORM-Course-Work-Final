package lk.ijse.gdse72.ormfinalcoursework.bo;

import lk.ijse.gdse72.ormfinalcoursework.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {}

    public static BOFactory getInstance() {
        return boFactory == null ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOType {
        PATIENT,
        PAYMENT,
        THERAPIST,
        AVAILABILITY,
        THERAPY_PROGRAM,
        THERAPY_SESSION,
        USER
    }

    public SuperBO getBO(BOType type) {
        switch (type) {
            case PATIENT:
                return new PatientBOImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            case THERAPIST:
                return new TherapistBOImpl();
            case AVAILABILITY:
                return new TherapistAvailabilityBOImpl();
            case THERAPY_PROGRAM:
                return new TherapyProgramBOImpl();
            case THERAPY_SESSION:
                return new TherapySessionBOImpl();
            case USER:
                return new UserBOImpl();
            default:
                return null;
        }
    }
}
