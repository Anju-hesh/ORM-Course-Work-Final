package lk.ijse.gdse72.ormfinalcoursework.bo.custom;

import lk.ijse.gdse72.ormfinalcoursework.bo.SuperBO;
import lk.ijse.gdse72.ormfinalcoursework.dto.PatientDTO;
import lk.ijse.gdse72.ormfinalcoursework.dto.TherapyProgramDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TherapyProgramBO extends SuperBO {

    String getNextTherapyProgramId() throws SQLException;
    ArrayList<TherapyProgramDTO> getAllTherapyProgram() throws Exception;
    boolean saveTherapyProgram(TherapyProgramDTO therapyProgram) throws Exception;
    boolean updateTherapyProgram(TherapyProgramDTO therapyProgram) throws Exception;
    TherapyProgramDTO searchTherapyProgram(String id) throws Exception;
    boolean deleteTherapyProgram(String id) throws Exception;
}
