package lk.ijse.gdse72.ormfinalcoursework.dao.custom;

import lk.ijse.gdse72.ormfinalcoursework.dao.CrudDAO;
import lk.ijse.gdse72.ormfinalcoursework.entity.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.sql.SQLException;

public interface UserDAO extends CrudDAO<User, String> {
    User findByUsername(String username) throws Exception;
//    User getLastUser() throws Exception;

    boolean confirmation(String userId, String password) throws SQLException;
    String[] getDetails(String text) throws SQLException ;
    String getNextId() throws HibernateException;
    String getId(String text) throws SQLException ;
    boolean updateDetails(String password, String id) throws SQLException ;
}