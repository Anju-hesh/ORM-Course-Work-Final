package lk.ijse.gdse72.ormfinalcoursework.dao.custom;

import lk.ijse.gdse72.ormfinalcoursework.dao.CrudDAO;
import lk.ijse.gdse72.ormfinalcoursework.dto.AvailabilityChartChart;
import lk.ijse.gdse72.ormfinalcoursework.dto.TherapistAvailabilityDTO;
import lk.ijse.gdse72.ormfinalcoursework.entity.TherapistAvailability;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface TherapistAvailabilityDAO extends CrudDAO<TherapistAvailability , String> {
    List<TherapistAvailabilityDTO> getAvailabilityForTherapistOnDate(String therapistName , LocalDate date) ;
    List<TherapistAvailability> searchTherapist( String therapistName ) throws Exception;
    boolean isTherapistAvailable(String therapistName, LocalDate date, LocalTime startTime, LocalTime endTime) throws Exception;

    List<AvailabilityChartChart> getAllAvailabilitySummary() throws Exception;
}
