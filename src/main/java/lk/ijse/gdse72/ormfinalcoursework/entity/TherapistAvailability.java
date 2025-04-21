package lk.ijse.gdse72.ormfinalcoursework.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "therapist_availabilities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TherapistAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String therapistName;

    private LocalDate availableDate;

    private LocalTime startTime;

    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "therapist_id")
    private Therapist therapist;

    public TherapistAvailability(Long ID , String name, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.id = ID;
        this.therapistName = name;
        this.availableDate = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
