package lk.ijse.gdse72.ormfinalcoursework.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class PatientDTO {

    private String patientId;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String medicalHistory;
    private int contact;
    private String eMail;
    private String address;
    private String BloodGroup;
    private String allergies;

}
