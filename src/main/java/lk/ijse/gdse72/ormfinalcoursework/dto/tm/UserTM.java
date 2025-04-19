package lk.ijse.gdse72.ormfinalcoursework.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class UserTM {

    private String userId;
    private String userName;
    private String password;
    private String role;
}
