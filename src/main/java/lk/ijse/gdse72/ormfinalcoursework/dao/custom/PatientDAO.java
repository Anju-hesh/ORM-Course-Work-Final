package lk.ijse.gdse72.ormfinalcoursework.dao.custom;

import lk.ijse.gdse72.ormfinalcoursework.dao.CrudDAO;
import lk.ijse.gdse72.ormfinalcoursework.dto.PatientDTO;
import lk.ijse.gdse72.ormfinalcoursework.entity.Patient;

import java.util.ArrayList;

public interface PatientDAO extends CrudDAO<Patient , String> {
    String getNextId();

    PatientDTO getPatient(String patientId) ;
    ArrayList<String> getPatientid();
    ArrayList<Patient> getPatientsByTherapyType(String therapyType) throws Exception;

}
