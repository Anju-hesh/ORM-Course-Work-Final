////package lk.ijse.gdse72.ormfinalcoursework.entity;
////
////import jakarta.persistence.*;
////import lombok.*;
////
////import java.math.BigDecimal;
////import java.util.ArrayList;
////import java.util.List;
////
////@Entity
////@Table(name = "therapy_programs")
////@Getter
////@Setter
////@NoArgsConstructor
////@AllArgsConstructor
////@Builder
////public class TherapyProgram {
////    @Id
////    @Column(name = "program_id", length = 10)
////    private String programId;
////
////    @Column(nullable = false, length = 100)
////    private String name;
////
////    @Column(nullable = false, length = 50)
////    private String duration;
////
////    @Column(nullable = false, precision = 10, scale = 2)
////    private BigDecimal fee;
////
////    @Column(length = 500)
////    private String description;
////
////    @OneToMany(mappedBy = "therapyProgram", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
////    private List<Registration> registrations = new ArrayList<>();
////
////    @ManyToMany(mappedBy = "assignedPrograms", fetch = FetchType.LAZY)
////    private List<Therapist> therapists = new ArrayList<>();
////}
//
//package lk.ijse.gdse72.ormfinalcoursework.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Table(name = "therapy_programs")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class TherapyProgram {
//
//    @Id
//    @Column(name = "program_id", length = 10)
//    private String programId;
//
//    @Column(nullable = false, length = 100)
//    private String name;
//
//    @Column(nullable = false, length = 50)
//    private String duration;
//
//    @Column(nullable = false, precision = 10, scale = 2)
//    private BigDecimal fee;
//
//    @Column(length = 500)
//    private String description;
//
//    @ManyToMany(mappedBy = "assignedPrograms", fetch = FetchType.LAZY)
//    private List<Therapist> therapists = new ArrayList<>();
//
//    @OneToMany(mappedBy = "therapyProgram", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<TherapySession> therapySessions = new ArrayList<>();
//
//    @OneToMany(mappedBy = "therapyProgram", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Payment> payments = new ArrayList<>();
//}


package lk.ijse.gdse72.ormfinalcoursework.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "therapy_programs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TherapyProgram {

    @Id
    @Column(name = "program_id", length = 10)
    private String programId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 50)
    private String duration;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal fee;

    @Column(nullable = false)
    private String therapist;

    @Column(length = 500)
    private String description;

    @OneToMany(mappedBy = "therapyProgram", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TherapySession> therapySessions = new ArrayList<>();

    @OneToMany(mappedBy = "therapyProgram", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Payment> payments = new ArrayList<>();

    public TherapyProgram(String therapyId, String programName, String duration, BigDecimal fee, String therapist, String description) {
        this.programId = therapyId;
        this.name = programName;
        this.duration = duration;
        this.fee = fee;
        this.therapist = therapist;
        this.description = description;
    }
}
