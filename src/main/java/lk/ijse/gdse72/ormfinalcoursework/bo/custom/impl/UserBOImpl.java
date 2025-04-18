package lk.ijse.gdse72.ormfinalcoursework.bo.custom.impl;

import lk.ijse.gdse72.ormfinalcoursework.bo.custom.UserBO;
import lk.ijse.gdse72.ormfinalcoursework.config.FactoryConfiguration;
import lk.ijse.gdse72.ormfinalcoursework.dao.DAOFactory;
import lk.ijse.gdse72.ormfinalcoursework.dao.custom.UserDAO;
import lk.ijse.gdse72.ormfinalcoursework.dto.UserDTO;
import lk.ijse.gdse72.ormfinalcoursework.entity.User;
import lk.ijse.gdse72.ormfinalcoursework.servise.PasswordUtil;
import org.hibernate.Session;

public class UserBOImpl implements UserBO {

    private final UserDAO userDAO = DAOFactory.getInstance().getDAO(DAOFactory.DaoType.USER);

    @Override
    public boolean authenticateUser(String username, String password) throws Exception {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            userDAO.setSession(session);
            User user = userDAO.findByUsername(username);

            if (user != null) {
                boolean passwordMatches = PasswordUtil.checkPassword(password, user.getPassword());
                System.out.println("User found: " + user.getUserName());
                System.out.println("Stored hash: " + user.getPassword());
                System.out.println("Plain password: " + password);
                return passwordMatches;
            }
            return false;
        }
    }


    @Override
    public UserDTO getUserByUsername(String username) throws Exception {

        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            userDAO.setSession(session);
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
    }

    @Override
    public String getUserIdByUsername(String username) throws Exception {

        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            userDAO.setSession(session);
            User user = userDAO.findByUsername(username);
            return user != null ? user.getUserId() : null;
        }
    }

    @Override
    public boolean saveUser(UserDTO dto) throws Exception {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            userDAO.setSession(session);
            session.beginTransaction();

            if (userDAO.findByUsername(dto.getUserName()) != null) {
                return false;
            }

            String hashedPassword = PasswordUtil.hashPassword(dto.getPassword());
            System.out.println("Hashed password during registration: " + hashedPassword);  // Debugging line

            User user = new User(dto.getUserId(), dto.getUserName(), hashedPassword, dto.getRole());
            userDAO.save(user);
            session.getTransaction().commit();
            return true;
        }
    }




//    public String getNextuserId() throws SQLException, ClassNotFoundException {
//
//        return userDAO.getNextId();
//    }

}

