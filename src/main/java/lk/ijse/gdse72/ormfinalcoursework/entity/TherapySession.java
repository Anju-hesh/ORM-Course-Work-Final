package lk.ijse.gdse72.ormfinalcoursework.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "therapy_sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TherapySession {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "therapySession_id" , length = 10)
    private String sessionId;

    @Column
    private String patientId;

    @Column
    private String patientName;

    @Column
    private String therapistId;

    @Column
    private String program;

    @Column
    private LocalDate sessionDate;

    @Column
    private LocalTime time;

    @Column
    private String duration;

    @Column
    private String status;


    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "program_id")
    private TherapyProgram therapyProgram;

    @ManyToOne
    @JoinColumn(name = "therapist_id")
    private Therapist therapist;

    public TherapySession(String sessionId, String patientId, String patientName, String therapistId, String program, LocalDate sessionDate, LocalTime time, String duration, String status) {
        this.sessionId = sessionId;
        this.patientId = patientId;
        this.patientName = patientName;
        this.therapistId = therapistId;
        this.program = program;
        this.sessionDate = sessionDate;
        this.time = time;
        this.duration = duration;
        this.status = status;
    }
}