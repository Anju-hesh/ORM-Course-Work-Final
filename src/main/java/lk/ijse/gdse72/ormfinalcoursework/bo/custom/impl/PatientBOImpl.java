package lk.ijse.gdse72.ormfinalcoursework.bo.custom.impl;

import lk.ijse.gdse72.ormfinalcoursework.bo.custom.PatientBO;
import lk.ijse.gdse72.ormfinalcoursework.dao.DAOFactory;
import lk.ijse.gdse72.ormfinalcoursework.dao.custom.PatientDAO;
import lk.ijse.gdse72.ormfinalcoursework.dto.PatientDTO;
import lk.ijse.gdse72.ormfinalcoursework.entity.Patient;

import java.util.ArrayList;

public class PatientBOImpl implements PatientBO {

    PatientDAO patientDAO = DAOFactory.getInstance().getDAO(DAOFactory.DaoType.PATIENT);

    @Override
    public ArrayList<PatientDTO> getAllPatients() throws Exception {
        ArrayList<PatientDTO> patientDTOS = new ArrayList<>();
        ArrayList<Patient> patients = (ArrayList<Patient>) patientDAO.getAll();

        for (Patient patient : patients) {
            patientDTOS.add(new PatientDTO(
                    patient.getId(),
                    patient.getFirstName(),
                    patient.getLastName(),
                    patient.getAge(),
                    patient.getGender(),
                    patient.getMedicalHistory(),
                    patient.getContactNumber(),
                    patient.getEmail(),
                    patient.getAddress(),
                    patient.getBloodGroup(),
                    patient.getAllergies()
            ));
        }
        return patientDTOS;
    }

    @Override
    public boolean savePatient(PatientDTO patientDTO) throws Exception {
        return patientDAO.save(new Patient(
                patientDTO.getPatientId(),
                patientDTO.getFirstName(),
                patientDTO.getLastName(),
                patientDTO.getAge(),
                patientDTO.getGender(),
                patientDTO.getMedicalHistory(),
                patientDTO.getContact(),
                patientDTO.getEMail(),
                patientDTO.getAddress(),
                patientDTO.getBloodGroup(),
                patientDTO.getAllergies()
        ));
    }

    @Override
    public boolean updatePatient(PatientDTO patientDTO) throws Exception {
        Patient patient = new Patient(
                patientDTO.getPatientId(),
                patientDTO.getFirstName(),
                patientDTO.getLastName(),
                patientDTO.getAge(),
                patientDTO.getGender(),
                patientDTO.getMedicalHistory(),
                patientDTO.getContact(),
                patientDTO.getEMail(),
                patientDTO.getAddress(),
                patientDTO.getBloodGroup(),
                patientDTO.getAllergies()
        );
        return patientDAO.update(patient);
    }

    @Override
    public PatientDTO searchPatient(String patientID) throws Exception {
        Patient patient = patientDAO.search(patientID);
        if (patient == null) {
            return null;
        }
        return new PatientDTO(
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getAge(),
                patient.getGender(),
                patient.getMedicalHistory(),
                patient.getContactNumber(),
                patient.getEmail(),
                patient.getAddress(),
                patient.getBloodGroup(),
                patient.getAllergies()
        );
    }

    @Override
    public boolean deletePatient(String patientID) throws Exception {
        return patientDAO.delete(patientID);
    }

    @Override
    public String getNextPatientId() throws Exception {
        return patientDAO.getNextId();
    }

    // You could add a method to filter patients by therapy type if needed
    /*
    @Override
    public ArrayList<PatientDTO> getPatientsByTherapyType(String therapyType) throws Exception {
        // This would require you to add a method in your DAO layer
        // and potentially adjust your entity relationship model
        ArrayList<PatientDTO> patientDTOS = new ArrayList<>();
        ArrayList<Patient> patients = (ArrayList<Patient>) patientDAO.getPatientsByTherapyType(therapyType);

        for (Patient patient : patients) {
            patientDTOS.add(new PatientDTO(
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getAge(),
                patient.getGender(),
                patient.getMedicalHistory(),
                patient.getContactNumber(),
                patient.getEmail(),
                patient.getAddress(),
                patient.getBloodGroup(),
                patient.getAllergies()
            ));
        }
        return patientDTOS;
    }
    */
}