package lk.ijse.gdse72.ormfinalcoursework.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {

    @Id
    private String id;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Column(nullable = false, length = 15)
    private int age;

    @Column(nullable = false, length = 15)
    private String gender;

    @Column(length = 1000)
    private String medicalHistory;

    @Column(nullable = false, length = 15)
    private int contactNumber;

    @Column(length = 100)
    private String email;

    @Column(length = 200)
    private String address;

    @Column(nullable = false, length = 15)
    private String bloodGroup;

    @Column(length = 1000)
    private String allergies;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TherapySession> therapySessions = new ArrayList<>();

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Payment> payments = new ArrayList<>();

    public Patient(String patientId, String firstName, String lastName, int age, String gender,
                   String medicalHistory, int contact, String eMail, String address,
                   String bloodGroup, String allergies) {
        this.id = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.medicalHistory = medicalHistory;
        this.contactNumber = contact;
        this.email = eMail;
        this.address = address;
        this.bloodGroup = bloodGroup;
        this.allergies = allergies;
    }
}