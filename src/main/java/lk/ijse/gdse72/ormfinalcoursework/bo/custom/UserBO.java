package lk.ijse.gdse72.ormfinalcoursework.bo.custom;

import lk.ijse.gdse72.ormfinalcoursework.bo.SuperBO;
import lk.ijse.gdse72.ormfinalcoursework.dto.UserDTO;

import java.sql.SQLException;

public interface UserBO extends SuperBO {
    boolean authenticateUser(String username, String password) throws Exception;
    UserDTO getUserByUsername(String username) throws Exception;
    String getUserIdByUsername(String username) throws Exception;
    boolean saveUser(UserDTO dto) throws Exception;


//    String getNextuserId() throws SQLException, ClassNotFoundException;


}