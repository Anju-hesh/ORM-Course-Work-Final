//package lk.ijse.gdse72.ormfinalcoursework.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "therapy_sessions")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class TherapySession {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private String id;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "patient_id", nullable = false)
//    private Patient patient;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "therapist_id", nullable = false)
//    private Therapist therapist;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "program_id", nullable = false)
//    private TherapyProgram therapyProgram;
//
//    @Column(nullable = false)
//    private LocalDateTime scheduledTime;
//
//    @Column(length = 50)
//    private String status; // SCHEDULED, COMPLETED, CANCELLED
//
//    @Column(length = 500)
//    private String notes;
//}

package lk.ijse.gdse72.ormfinalcoursework.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "therapy_sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TherapySession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionId;

    private LocalDateTime sessionDate;
    private int durationMinutes;
    private String notes;
    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "program_id")
    private TherapyProgram therapyProgram;

    @ManyToOne
    @JoinColumn(name = "therapist_id")
    private Therapist therapist;
}