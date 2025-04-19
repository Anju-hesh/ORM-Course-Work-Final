package lk.ijse.gdse72.ormfinalcoursework.dao;

import org.hibernate.Session;

import java.util.List;

public interface CrudDAO<T, ID> extends SuperDAO {
    boolean save(T entity) throws Exception;
    boolean update(T entity) throws Exception;
    boolean delete(ID id) throws Exception;
    T search(ID id) throws Exception;
    List<T> getAll() throws Exception;
    void setSession(Session session) throws Exception;
}