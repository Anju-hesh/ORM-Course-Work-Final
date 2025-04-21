package lk.ijse.gdse72.ormfinalcoursework.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class TherapyProgramTM {

    private String therapyId;
    private String programName;
    private String duration;
    private BigDecimal fee;
    private String therapist;
    private String description;

}
