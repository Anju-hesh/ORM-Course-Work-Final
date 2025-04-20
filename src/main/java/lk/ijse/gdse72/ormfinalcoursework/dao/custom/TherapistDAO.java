package lk.ijse.gdse72.ormfinalcoursework.dao.custom;

import lk.ijse.gdse72.ormfinalcoursework.dao.CrudDAO;
import lk.ijse.gdse72.ormfinalcoursework.dao.SuperDAO;
import lk.ijse.gdse72.ormfinalcoursework.entity.Therapist;

public interface TherapistDAO extends CrudDAO<Therapist ,String> {

    String getNextID();
}
