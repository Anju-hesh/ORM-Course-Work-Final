package lk.ijse.gdse72.ormfinalcoursework.dao.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.gdse72.ormfinalcoursework.config.FactoryConfiguration;
import lk.ijse.gdse72.ormfinalcoursework.dao.custom.UserDAO;
import lk.ijse.gdse72.ormfinalcoursework.entity.User;
import lk.ijse.gdse72.ormfinalcoursework.servise.PasswordUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    @Override
    public String[] getDetails(String email) {
        String[] details = {"", "0"};
        Transaction transaction = null;

        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            transaction = session.beginTransaction();

            // HQL Query
            Query<Object[]> query = session.createQuery(
                    "SELECT u.userName, u.email FROM User u WHERE u.email = :email",
                    Object[].class
            );
            query.setParameter("email", email);

            Object[] result = query.uniqueResult();

            if (result != null) {
                details[0] = (String) result[0]; // userName
                details[1] = (String) result[1]; // email
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return details;
    }

    @Override
    public String getId(String email) {
        String id = "";

        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            // HQL query to select only UserID
            Query<String> query = session.createQuery(
                    "SELECT u.userId FROM User u WHERE u.email = :email",
                    String.class
            );
            query.setParameter("email", email);

            String result = query.uniqueResult();

            if (result != null) {
                id = result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    @Override
    public boolean updateDetails(String password, String id) {
        boolean isUpdated = false;
        Transaction transaction = null;

        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            transaction = session.beginTransaction();

            // HQL update query
            Query<?> query = session.createQuery(
                    "UPDATE User u SET u.password = :password WHERE u.userId = :id"
            );
            query.setParameter("password", password);
            query.setParameter("id", id);

            int result = query.executeUpdate(); // returns number of affected rows

            transaction.commit();

            if (result > 0) {
                isUpdated = true;
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }

        return isUpdated;
    }
}