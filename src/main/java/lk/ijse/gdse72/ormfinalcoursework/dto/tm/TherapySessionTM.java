package lk.ijse.gdse72.ormfinalcoursework.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class TherapySessionTM {

    private String sessionId;
    private String patientId;
    private String patientName;
    private String therapistId;
    private String program;
    private LocalDate sessionDate;
    private LocalTime time;
    private String duration;
    private String status;

}
