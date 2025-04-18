package lk.ijse.gdse72.ormfinalcoursework.dao.custom.impl;

import lk.ijse.gdse72.ormfinalcoursework.config.FactoryConfiguration;
import lk.ijse.gdse72.ormfinalcoursework.dao.custom.UserDAO;
import lk.ijse.gdse72.ormfinalcoursework.entity.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class UserDAOImpl implements UserDAO {

    private Session session;

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(User user) {
//        return (String) session.save(user);
        try {
            session.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(User user) {
        session.update(user);
        return true;
    }

    @Override
    public boolean delete(String id) {
        User user = session.get(User.class, id);
        session.delete(user);
        return true;
    }

    @Override
    public User find(String id) {
        return session.get(User.class, id);
    }

    @Override
    public List<User> findAll() {
        String hql = "FROM User";
        Query<User> query = session.createQuery(hql, User.class);
        return query.list();
    }

    @Override
    public User findByUsername(String username) {
        String hql = "FROM User WHERE userName = :username";
        Query<User> query = session.createQuery(hql, User.class);
        query.setParameter("username", username);
        return query.uniqueResult();
    }




//    public String getNextId() throws HibernateException {
//        String hql = "SELECT u.userId FROM User u ORDER BY u.userId DESC";
//
//        // Use the passed session instead of creating a new one
//        Query<String> query = session.createQuery(hql, String.class);
//        query.setMaxResults(1);
//        List<String> result = query.list();
//
//        if (result.isEmpty()) {
//            return "USER - 001"; // If no entries exist, return the first ID
//        }
//
//        String lastUserId = result.get(0); // Retrieve the last UserId, e.g., "USER - 001"
//        String subUserId = lastUserId.substring(7); // Extract the numerical part
//        int lastIdIndex = Integer.parseInt(subUserId); // Convert to int
//        int nextIndex = lastIdIndex + 1; // Increment the index
//        return String.format("USER - %03d", nextIndex); // Generate the new ID in the format USER - 002
//    }


}
