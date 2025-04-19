package lk.ijse.gdse72.ormfinalcoursework.dao.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.gdse72.ormfinalcoursework.config.FactoryConfiguration;
import lk.ijse.gdse72.ormfinalcoursework.dao.custom.UserDAO;
import lk.ijse.gdse72.ormfinalcoursework.entity.User;
import lk.ijse.gdse72.ormfinalcoursework.servise.PasswordUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public boolean save(User user) {
        Transaction transaction = null;
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(User user) {
        Transaction transaction = null;
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        Transaction transaction = null;
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User search(String id) {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            return session.get(User.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            String hql = "FROM User";
            Query<User> query = session.createQuery(hql, User.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setSession(Session session) throws Exception {

    }

    public boolean confirmation(String userId, String password) {
        Transaction transaction = null;

        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            transaction = session.beginTransaction();

            Query<User> query = session.createQuery("FROM User WHERE userId = :userId", User.class);
            query.setParameter("userId", userId);

            User user = query.uniqueResult();

            transaction.commit();

            return user != null && user.getPassword().equals(password);

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User findByUsername(String username) {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            String hql = "FROM User WHERE userName = :username";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("username", username);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getNextId() {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {

            Integer maxNum = (Integer) session.createQuery(
                    "SELECT MAX(CAST(SUBSTRING(u.userId, 5) AS int)) " +
                            "FROM User u " +
                            "WHERE u.userId LIKE 'USER%' " +
                            "AND LENGTH(u.userId) = 7"
            ).uniqueResult();

            // 2. Generate the next ID
            return maxNum != null ?
                    String.format("USER%03d", maxNum + 1) :
                    "USER001";

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate next ID", e);
        }
    }
}