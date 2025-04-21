package lk.ijse.gdse72.ormfinalcoursework.bo.custom.impl;

import lk.ijse.gdse72.ormfinalcoursework.bo.custom.TherapySessionBO;
import lk.ijse.gdse72.ormfinalcoursework.dao.DAOFactory;
import lk.ijse.gdse72.ormfinalcoursework.dao.custom.TherapistAvailabilityDAO;
import lk.ijse.gdse72.ormfinalcoursework.dao.custom.TherapyProgramDAO;
import lk.ijse.gdse72.ormfinalcoursework.dao.custom.TherapySessionDAO;
import lk.ijse.gdse72.ormfinalcoursework.dto.TherapistAvailabilityDTO;
import lk.ijse.gdse72.ormfinalcoursework.dto.TherapyProgramDTO;
import lk.ijse.gdse72.ormfinalcoursework.dto.TherapySessionDTO;
import lk.ijse.gdse72.ormfinalcoursework.entity.TherapyProgram;
import lk.ijse.gdse72.ormfinalcoursework.entity.TherapySession;
import org.hibernate.service.spi.ServiceException;

import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TherapySessionBOImpl implements TherapySessionBO {

    TherapySessionDAO THERAPYSESSIONDAO = DAOFactory.getInstance().getDAO(DAOFactory.DaoType.THERAPY_SESSION);

    @Override
    public String getNextTherapySessionId() throws Exception {
        return THERAPYSESSIONDAO.getNextID();
    }

//    public boolean isTherapistAvailable(String therapistName, LocalDate date, LocalTime time) throws SQLException {
//        return THERAPYSESSIONDAO.isTherapistAvailable(therapistName, date, time);
//    }

    private final TherapistAvailabilityDAO availabilityDAO = DAOFactory.getInstance().getDAO(DAOFactory.DaoType.AVAILABILITY);

    public boolean isTherapistAvailable(String therapistName, LocalDate date, LocalTime time)
            throws ServiceException {

        try {
            // 1. Get all availability slots for the therapist on date
            List<TherapistAvailabilityDTO> slots =
                    availabilityDAO.getAvailabilityForTherapistOnDate(therapistName, date);

            // 2. Check if time falls within any slot
            return slots.stream().anyMatch(slot ->
                    !time.isBefore(slot.getStartTime()) &&
                            !time.isAfter(slot.getEndTime()));

        } catch (Exception e) {
            throw new ServiceException("Database error checking availability", e);
        }
    }

    @Override
    public ArrayList<TherapySessionDTO> getAllTherapySession() throws Exception {
        ArrayList<TherapySessionDTO> therapySessionDTOS = new ArrayList<>();
        ArrayList<TherapySession> therapySessions = (ArrayList<TherapySession>) THERAPYSESSIONDAO.getAll();

        for (TherapySession therapySession : therapySessions) {
            therapySessionDTOS.add(new TherapySessionDTO(
                    therapySession.getSessionId(),
                    therapySession.getPatientId(),
                    therapySession.getPatientName(),
                    therapySession.getTherapistId(),
                    therapySession.getProgram(),
                    therapySession.getSessionDate(),
                    therapySession.getTime(),
                    therapySession.getDuration(),
                    therapySession.getStatus()
            ));
        }
        return therapySessionDTOS;
    }

    @Override
    public boolean saveTherapySession(TherapySessionDTO therapySessionDTO) throws Exception {
        return THERAPYSESSIONDAO.save(
                new TherapySession(
                        therapySessionDTO.getSessionId(),
                        therapySessionDTO.getPatientId(),
                        therapySessionDTO.getPatientName(),
                        therapySessionDTO.getTherapistId(),
                        therapySessionDTO.getProgram(),
                        therapySessionDTO.getSessionDate(),
                        therapySessionDTO.getTime(),
                        therapySessionDTO.getDuration(),
                        therapySessionDTO.getStatus()
                )
        );
    }

    @Override
    public boolean updateTherapySession(TherapySessionDTO therapySessionDTO) throws Exception {
        return THERAPYSESSIONDAO.update(
                new TherapySession(
                        therapySessionDTO.getSessionId(),
                        therapySessionDTO.getPatientId(),
                        therapySessionDTO.getPatientName(),
                        therapySessionDTO.getTherapistId(),
                        therapySessionDTO.getProgram(),
                        therapySessionDTO.getSessionDate(),
                        therapySessionDTO.getTime(),
                        therapySessionDTO.getDuration(),
                        therapySessionDTO.getStatus()
                )
        );
    }

    @Override
    public TherapySessionDTO searchTherapySession(String sessionId) throws Exception {
        TherapySession therapySession = THERAPYSESSIONDAO.search(sessionId);

        if (therapySession == null) {
            return null;
        }
        return new TherapySessionDTO(
                therapySession.getSessionId(),
                therapySession.getPatientId(),
                therapySession.getPatientName(),
                therapySession.getTherapistId(),
                therapySession.getProgram(),
                therapySession.getSessionDate(),
                therapySession.getTime(),
                therapySession.getDuration(),
                therapySession.getStatus()
        );
    }

    @Override
    public boolean deleteTherapySession(String sessionId) throws Exception {
        return THERAPYSESSIONDAO.delete(sessionId);
    }
}
