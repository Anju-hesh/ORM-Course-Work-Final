package lk.ijse.gdse72.ormfinalcoursework.bo.custom;

import lk.ijse.gdse72.ormfinalcoursework.bo.SuperBO;
import lk.ijse.gdse72.ormfinalcoursework.dto.UserDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserBO extends SuperBO {
    boolean authenticateUser(String username, String password) throws Exception;
    UserDTO getUserByUsername(String username) throws Exception;
    String getUserIdByUsername(String username) throws Exception;
    boolean saveUser(UserDTO dto) throws Exception;

    UserDTO searchUser(String userId) throws Exception;
    boolean confirmation(String userId, String password) throws SQLException ;
    boolean deleteUser(String userId) throws Exception;
    boolean updateUser(UserDTO userDTO) throws Exception;
    ArrayList<UserDTO> getAllUser() throws Exception;
    String getNextuserId() throws SQLException, ClassNotFoundException;

}