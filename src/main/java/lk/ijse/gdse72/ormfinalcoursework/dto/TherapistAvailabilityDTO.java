package lk.ijse.gdse72.ormfinalcoursework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class TherapistAvailabilityDTO {

    private Long id;
    private String therapistName;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

}
