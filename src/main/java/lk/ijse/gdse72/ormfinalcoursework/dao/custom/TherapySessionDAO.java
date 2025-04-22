package lk.ijse.gdse72.ormfinalcoursework.dao.custom;

import lk.ijse.gdse72.ormfinalcoursework.dao.CrudDAO;
import lk.ijse.gdse72.ormfinalcoursework.dto.TherapySessionDTO;
import lk.ijse.gdse72.ormfinalcoursework.entity.TherapySession;

import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public interface TherapySessionDAO extends CrudDAO<TherapySession , String> {
    String getNextID() throws SQLException;
    boolean isTherapistAvailable(String therapistId, LocalDate date, LocalTime time) throws SQLException;
    TherapySessionDTO getSession(String sessionId) throws SQLException;
    ArrayList<String> getSessionId() throws SQLException;
}
