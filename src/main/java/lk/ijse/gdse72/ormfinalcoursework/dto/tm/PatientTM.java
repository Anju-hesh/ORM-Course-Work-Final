package lk.ijse.gdse72.ormfinalcoursework.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class PatientTM {

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

//    public String getFullName(String firstName, String lastName) {
//        String fullName = firstName + " " + lastName;
//        return fullName;
//    }
}
