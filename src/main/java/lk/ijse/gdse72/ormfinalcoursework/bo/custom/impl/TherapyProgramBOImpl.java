package lk.ijse.gdse72.ormfinalcoursework.bo.custom.impl;

import lk.ijse.gdse72.ormfinalcoursework.bo.custom.TherapyProgramBO;
import lk.ijse.gdse72.ormfinalcoursework.dao.DAOFactory;
import lk.ijse.gdse72.ormfinalcoursework.dao.custom.TherapyProgramDAO;
import lk.ijse.gdse72.ormfinalcoursework.dto.PatientDTO;
import lk.ijse.gdse72.ormfinalcoursework.dto.TherapyProgramDTO;
import lk.ijse.gdse72.ormfinalcoursework.entity.Patient;
import lk.ijse.gdse72.ormfinalcoursework.entity.TherapyProgram;

import java.sql.SQLException;
import java.util.ArrayList;

public class TherapyProgramBOImpl implements TherapyProgramBO {

    TherapyProgramDAO THERAPYPROGRAMDAO = DAOFactory.getInstance().getDAO(DAOFactory.DaoType.THERAPY_PROGRAM);

    @Override
    public String getNextTherapyProgramId() throws SQLException {
        return THERAPYPROGRAMDAO.getNextID();
    }

    @Override
    public ArrayList<TherapyProgramDTO> getAllTherapyProgram() throws Exception {
        ArrayList<TherapyProgramDTO> therapyProgramDTOS = new ArrayList<>();
        ArrayList<TherapyProgram> therapyPrograms = (ArrayList<TherapyProgram>) THERAPYPROGRAMDAO.getAll();

        for (TherapyProgram therapyProgram : therapyPrograms) {
            therapyProgramDTOS.add(new TherapyProgramDTO(
                    therapyProgram.getProgramId(),
                    therapyProgram.getName(),
                    therapyProgram.getDuration(),
                    therapyProgram.getFee(),
                    therapyProgram.getTherapist(),
                    therapyProgram.getDescription()
            ));
        }
        return therapyProgramDTOS;
    }

    @Override
    public boolean saveTherapyProgram(TherapyProgramDTO therapyProgramDTO) throws Exception {
        return THERAPYPROGRAMDAO.save(new TherapyProgram(
                therapyProgramDTO.getTherapyId(),
                therapyProgramDTO.getProgramName(),
                therapyProgramDTO.getDuration(),
                therapyProgramDTO.getFee(),
                therapyProgramDTO.getTherapist(),
                therapyProgramDTO.getDescription()
        ));
    }

    @Override
    public boolean updateTherapyProgram(TherapyProgramDTO therapyProgramDTO) throws Exception {
        return THERAPYPROGRAMDAO.update(new TherapyProgram(
                therapyProgramDTO.getTherapyId(),
                therapyProgramDTO.getProgramName(),
                therapyProgramDTO.getDuration(),
                therapyProgramDTO.getFee(),
                therapyProgramDTO.getTherapist(),
                therapyProgramDTO.getDescription()
        ));
    }

    @Override
    public TherapyProgramDTO searchTherapyProgram(String id) throws Exception {
        TherapyProgram therapyProgram = THERAPYPROGRAMDAO.search(id);

        if (therapyProgram == null) {
            return null;
        }
        return new TherapyProgramDTO(
                therapyProgram.getProgramId(),
                therapyProgram.getName(),
                therapyProgram.getDuration(),
                therapyProgram.getFee(),
                therapyProgram.getTherapist(),
                therapyProgram.getDescription()
        );
    }

    @Override
    public boolean deleteTherapyProgram(String id) throws Exception {
        return THERAPYPROGRAMDAO.delete(id);
    }
}
