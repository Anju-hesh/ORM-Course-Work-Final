package lk.ijse.gdse72.ormfinalcoursework.bo.custom;

import lk.ijse.gdse72.ormfinalcoursework.bo.SuperBO;
import lk.ijse.gdse72.ormfinalcoursework.dto.TherapistAvailabilityDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface TherapistAvailabilityBO extends SuperBO {
    String getAllTherapistIds() throws SQLException;
    boolean saveAvailability(TherapistAvailabilityDTO therapistAvailabilityDTO) throws Exception;
    boolean deleteAvailability(String id) throws Exception;
    ArrayList<TherapistAvailabilityDTO> getAllavailabilities() throws Exception;
    List<TherapistAvailabilityDTO> searchAvailability(String therapist) throws Exception;
}
