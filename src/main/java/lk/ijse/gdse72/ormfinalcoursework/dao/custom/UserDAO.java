package lk.ijse.gdse72.ormfinalcoursework.dao.custom;

import lk.ijse.gdse72.ormfinalcoursework.dao.CrudDAO;
import lk.ijse.gdse72.ormfinalcoursework.entity.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public interface UserDAO extends CrudDAO<User, String> {
    User findByUsername(String username) throws Exception;
//    User getLastUser() throws Exception;


//    String getNextId() throws HibernateException;
}