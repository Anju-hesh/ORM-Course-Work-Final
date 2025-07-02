package lk.ijse.gdse72.ormfinalcoursework.bo.custom.impl;

import lk.ijse.gdse72.ormfinalcoursework.bo.custom.OwnerDashboardBo;
import lk.ijse.gdse72.ormfinalcoursework.dao.DAOFactory;
import lk.ijse.gdse72.ormfinalcoursework.dao.custom.*;

import java.util.LinkedHashMap;
import java.util.Map;

public class OwnerDashboardBOImpl implements OwnerDashboardBo {

    private final PatientDAO patientDAO = (PatientDAO) DAOFactory.getInstance().getDAO(DAOFactory.DaoType.PATIENT);
    private final TherapistDAO therapistDAO = (TherapistDAO) DAOFactory.getInstance().getDAO(DAOFactory.DaoType.THERAPIST);
    private final TherapyProgramDAO therapyProgramDAO = (TherapyProgramDAO) DAOFactory.getInstance().getDAO(DAOFactory.DaoType.THERAPY_PROGRAM);
    private final TherapySessionDAO therapySessionDAO = (TherapySessionDAO) DAOFactory.getInstance().getDAO(DAOFactory.DaoType.THERAPY_SESSION);
    private final UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DaoType.USER);
    private final PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DaoType.PAYMENT);

    @Override
    public Map<String, Integer> getOverviewData() throws Exception {
        Map<String, Integer> data = new LinkedHashMap<>();
        data.put("Patients", patientDAO.countAll());
        data.put("Therapists", therapistDAO.countAll());
        data.put("Therapy Programs", therapyProgramDAO.countAll());
        data.put("Therapy Sessions", therapySessionDAO.countAll());
        data.put("Users", userDAO.countAll());
        data.put("Payments", paymentDAO.countAll());
        return data;
    }
}
