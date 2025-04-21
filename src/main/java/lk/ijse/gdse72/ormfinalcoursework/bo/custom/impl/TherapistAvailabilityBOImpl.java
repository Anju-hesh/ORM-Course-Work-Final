package lk.ijse.gdse72.ormfinalcoursework.bo.custom.impl;

import lk.ijse.gdse72.ormfinalcoursework.bo.custom.TherapistAvailabilityBO;
import lk.ijse.gdse72.ormfinalcoursework.dao.DAOFactory;
import lk.ijse.gdse72.ormfinalcoursework.dao.custom.PatientDAO;
import lk.ijse.gdse72.ormfinalcoursework.dao.custom.TherapistAvailabilityDAO;
import lk.ijse.gdse72.ormfinalcoursework.dto.PatientDTO;
import lk.ijse.gdse72.ormfinalcoursework.dto.TherapistAvailabilityDTO;
import lk.ijse.gdse72.ormfinalcoursework.entity.Patient;
import lk.ijse.gdse72.ormfinalcoursework.entity.TherapistAvailability;

import java.sql.SQLException;
import java.util.ArrayList;

public class TherapistAvailabilityBOImpl implements TherapistAvailabilityBO {


    TherapistAvailabilityDAO therapistAvailabilityDAO = DAOFactory.getInstance().getDAO(DAOFactory.DaoType.AVAILABILITY);

    @Override
    public String getAllTherapistIds() throws SQLException {
        return "";
    }

    @Override
    public boolean saveAvailability(TherapistAvailabilityDTO therapistAvailabilityDTO) throws Exception {
        return therapistAvailabilityDAO.save(new TherapistAvailability(
                therapistAvailabilityDTO.getId(),
                therapistAvailabilityDTO.getTherapistName(),
                therapistAvailabilityDTO.getDate(),
                therapistAvailabilityDTO.getStartTime(),
                therapistAvailabilityDTO.getEndTime()
        ));
    }

    @Override
    public boolean deleteAvailability(String id) throws Exception {
        return therapistAvailabilityDAO.delete(id);
    }

    @Override
    public ArrayList<TherapistAvailabilityDTO> getAllavailabilities() throws Exception {
        ArrayList<TherapistAvailabilityDTO> therapistAvailabilityDTOS = new ArrayList<>();
        ArrayList<TherapistAvailability> availabilitys = (ArrayList<TherapistAvailability>) therapistAvailabilityDAO.getAll();

        for (TherapistAvailability availability : availabilitys) {
            therapistAvailabilityDTOS.add(new TherapistAvailabilityDTO(
                    availability.getId(),
                    availability.getTherapistName(),
                    availability.getAvailableDate(),
                    availability.getStartTime(),
                    availability.getEndTime()
            ));
        }
        return therapistAvailabilityDTOS;
    }
}
