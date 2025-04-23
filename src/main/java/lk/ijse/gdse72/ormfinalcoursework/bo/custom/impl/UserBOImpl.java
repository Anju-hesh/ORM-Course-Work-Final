package lk.ijse.gdse72.ormfinalcoursework.bo.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.gdse72.ormfinalcoursework.bo.custom.UserBO;
import lk.ijse.gdse72.ormfinalcoursework.dao.DAOFactory;
import lk.ijse.gdse72.ormfinalcoursework.dao.custom.UserDAO;
import lk.ijse.gdse72.ormfinalcoursework.dto.UserDTO;
import lk.ijse.gdse72.ormfinalcoursework.entity.User;
import lk.ijse.gdse72.ormfinalcoursework.servise.PasswordUtil;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserBOImpl implements UserBO {

    private final UserDAO userDAO = DAOFactory.getInstance().getDAO(DAOFactory.DaoType.USER);

    @Override
    public boolean authenticateUser(String username, String password) throws Exception {

            User user = userDAO.findByUsername(username);

            if (user != null) {
                boolean passwordMatches = PasswordUtil.checkPassword(password, user.getPassword());
//                System.out.println("User found: " + user.getUserName());
//                System.out.println("Stored hash: " + user.getPassword());
//                System.out.println("Plain password: " + password);
                return passwordMatches;
            }
            return false;

    }


    @Override
    public UserDTO getUserByUsername(String username) throws Exception {

        User user = userDAO.findByUsername(username);
        if (user != null) {
            return new UserDTO(
                user.getUserId(),
                user.getUserName(),
                user.getPassword(),
                user.getRole()
            );
        }
        return null;
    }

    @Override
    public String getUserIdByUsername(String username) throws Exception {

            User user = userDAO.findByUsername(username);
            return user != null ? user.getUserId() : null;

    }

    @Override
    public boolean saveUser(UserDTO dto) throws Exception {

            if (userDAO.findByUsername(dto.getUserName()) != null) {
                return false;
            }

            String hashedPassword = PasswordUtil.hashPassword(dto.getPassword());
            System.out.println("Hashed password during registration: " + hashedPassword);

            User user = new User(dto.getUserId(), dto.getUserName(), hashedPassword, dto.getRole());
            userDAO.save(user);
            return true;

    }

    @Override
    public UserDTO searchUser(String userId) throws Exception {
        try {
            User user = userDAO.search(userId);
            if (user == null) {
                new Alert(Alert.AlertType.ERROR,"User Not Found with ID: " + userId).show();
//                throw new Exception("User not found with ID: " + userId);
            }
            return new UserDTO(
                    user.getUserId(),
                    user.getUserName(),
                    user.getPassword(),
                    user.getRole()
            );
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"User Not Found").show();
            return null;
        }
    }

    @Override
    public boolean confirmation(String userId, String password) {

        User user = null;
        try {
            user = userDAO.search(userId);
            if (user != null) {
                return PasswordUtil.checkPassword(password, user.getPassword());
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public boolean deleteUser(String userId) throws Exception {
        return userDAO.delete(userId);
    }

    public boolean updateUser(UserDTO userDTO) throws Exception {

        String hashedPassword = PasswordUtil.hashPassword(userDTO.getPassword());

        User user = new User(
                userDTO.getUserId(),
                userDTO.getUserName(),
                hashedPassword,
                userDTO.getRole()
        );

        return userDAO.update(user);
    }

    public ArrayList<UserDTO> getAllUser() throws Exception {
//        return useraccountDAO.getAll();
        ArrayList<UserDTO> userAccountDtos = new ArrayList<>();
        ArrayList<User> users = (ArrayList<User>) userDAO.getAll();

        for (User user : users) {
            userAccountDtos.add(new UserDTO(
                    user.getUserId(),
                    user.getUserName(),
                    user.getPassword(),
                    user.getRole()
            ));
        }

        return userAccountDtos;
    }

    public String getNextuserId() throws SQLException, ClassNotFoundException {

        return userDAO.getNextId();
    }
}

