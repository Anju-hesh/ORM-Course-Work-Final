package lk.ijse.gdse72.ormfinalcoursework.bo.custom;

import lk.ijse.gdse72.ormfinalcoursework.bo.SuperBO;
import lk.ijse.gdse72.ormfinalcoursework.dto.TherapyProgramDTO;
import lk.ijse.gdse72.ormfinalcoursework.dto.TherapySessionDTO;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public interface TherapySessionBO extends SuperBO {

    String getNextTherapySessionId() throws Exception;
    boolean isTherapistAvailable(String therapistName, LocalDate date, LocalTime time) throws Exception;
    ArrayList<TherapySessionDTO>  getAllTherapySession() throws Exception;
    boolean saveTherapySession(TherapySessionDTO therapySessionDTO) throws Exception;
    boolean updateTherapySession(TherapySessionDTO therapySessionDTO) throws Exception;
    TherapySessionDTO searchTherapySession(String sessionId) throws Exception;
    boolean deleteTherapySession(String sessionId) throws Exception;
}
