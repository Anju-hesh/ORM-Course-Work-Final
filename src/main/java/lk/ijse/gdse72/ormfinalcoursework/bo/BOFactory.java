package lk.ijse.gdse72.ormfinalcoursework.bo;

import lk.ijse.gdse72.ormfinalcoursework.bo.custom.impl.PatientBOImpl;
import lk.ijse.gdse72.ormfinalcoursework.bo.custom.impl.PaymentBOImpl;
import lk.ijse.gdse72.ormfinalcoursework.bo.custom.impl.TherapistBOImpl;
import lk.ijse.gdse72.ormfinalcoursework.bo.custom.impl.TherapyProgramBOImpl;
import lk.ijse.gdse72.ormfinalcoursework.bo.custom.impl.TherapySessionBOImpl;
import lk.ijse.gdse72.ormfinalcoursework.bo.custom.impl.UserBOImpl;

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
