package lk.ijse.gdse72.ormfinalcoursework.bo.custom;

import lk.ijse.gdse72.ormfinalcoursework.bo.SuperBO;
import lk.ijse.gdse72.ormfinalcoursework.dto.PatientDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PatientBO extends SuperBO {
    ArrayList<PatientDTO> getAllPatients() throws Exception;
    boolean savePatient(PatientDTO patientDTO) throws Exception;
    boolean updatePatient(PatientDTO patientDTO) throws Exception;
    PatientDTO searchPatient(String patientID) throws Exception;
    boolean deletePatient(String patientID) throws Exception;
    String getNextPatientId() throws Exception;
}
