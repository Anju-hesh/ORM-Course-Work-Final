package lk.ijse.gdse72.ormfinalcoursework.dao.custom;

import lk.ijse.gdse72.ormfinalcoursework.dao.CrudDAO;
import lk.ijse.gdse72.ormfinalcoursework.dto.PatientDTO;
import lk.ijse.gdse72.ormfinalcoursework.entity.Patient;

public interface PatientDAO extends CrudDAO<Patient , String> {
    String getNextId();

    PatientDTO getPatient() ;

}
