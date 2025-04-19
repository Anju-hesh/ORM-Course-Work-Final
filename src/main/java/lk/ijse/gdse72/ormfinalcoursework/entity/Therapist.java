//package lk.ijse.gdse72.ormfinalcoursework.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Table(name = "therapists")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class Therapist {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private String id;
//
//    @Column(nullable = false, length = 100)
//    private String name;
//
//    @Column(length = 15)
//    private String contactNumber;
//
//    @Column(length = 100)
//    private String email;
//
//    @Column(length = 200)
//    private String specialization;
//
//    @OneToMany(mappedBy = "therapist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<TherapySession> therapySessions = new ArrayList<>();
//
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "therapist_program",
//            joinColumns = @JoinColumn(name = "therapist_id"),
//            inverseJoinColumns = @JoinColumn(name = "program_id")
//    )
//    private List<TherapyProgram> assignedPrograms = new ArrayList<>();
//}

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

    @Column(nullable = false, length = 15)
    private String contactNumber;

    @Column(length = 100)
    private String email;

    @ManyToMany
    @JoinTable(
            name = "therapist_programs",
            joinColumns = @JoinColumn(name = "therapist_id"),
            inverseJoinColumns = @JoinColumn(name = "program_id")
    )
    private List<TherapyProgram> assignedPrograms = new ArrayList<>();

    @OneToMany(mappedBy = "therapist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TherapySession> therapySessions = new ArrayList<>();
}