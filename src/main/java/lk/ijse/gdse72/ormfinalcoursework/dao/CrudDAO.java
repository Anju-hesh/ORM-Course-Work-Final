package lk.ijse.gdse72.ormfinalcoursework.dao;

import org.hibernate.Session;

import java.util.List;

public interface CrudDAO<T, ID> extends SuperDAO {
    boolean save(T entity) throws Exception;
    boolean update(T entity) throws Exception;
    boolean delete(ID id) throws Exception;
    T find(ID id) throws Exception;
    List<T> findAll() throws Exception;
    void setSession(Session session) throws Exception;
}