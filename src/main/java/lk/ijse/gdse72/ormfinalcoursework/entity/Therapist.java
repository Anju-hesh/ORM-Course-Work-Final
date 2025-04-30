package lk.ijse.gdse72.ormfinalcoursework.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "therapists")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Therapist {

    @Id
    @Column(name = "therapist_id", length = 10)
    private String therapistId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 50)
    private String specialization;

    @Column(nullable = false, length = 50)
    private String availability;

    @Column(nullable = false, length = 15)
    private int contactNumber;

    @Column(length = 100)
    private String email;

    @Column(length = 100)
    private String assignedProgram ;

//    @ManyToMany
//    @JoinTable(
//            name = "therapist_programs",
//            joinColumns = @JoinColumn(name = "therapist_id"),
//            inverseJoinColumns = @JoinColumn(name = "program_id")
//    )
//    private List<TherapyProgram> assignedPrograms = new ArrayList<>();


    @OneToMany(mappedBy = "therapist", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TherapySession> therapySessions = new ArrayList<>();

    @OneToMany(mappedBy = "therapist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TherapistAvailability> availabilities = new ArrayList<>();

    public Therapist(String therapistId, String therapistName, String specialization, String availability, int contact, String program, String mail) {
        this.therapistId = therapistId;
        this.name = therapistName;
        this.specialization = specialization;
        this.availability = availability;
        this.contactNumber = contact;
        this.email = mail;
        this.assignedProgram = program;
    }
}