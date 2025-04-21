package lk.ijse.gdse72.ormfinalcoursework.dao.custom;

import lk.ijse.gdse72.ormfinalcoursework.dao.CrudDAO;
import lk.ijse.gdse72.ormfinalcoursework.dao.SuperDAO;
import lk.ijse.gdse72.ormfinalcoursework.entity.Therapist;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TherapistDAO extends CrudDAO<Therapist ,String> {

    String getNextID();
    ArrayList<String> getTherapist() throws SQLException;
}
