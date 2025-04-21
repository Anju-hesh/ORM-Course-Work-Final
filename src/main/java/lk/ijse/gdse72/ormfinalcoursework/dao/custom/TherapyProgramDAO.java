package lk.ijse.gdse72.ormfinalcoursework.dao.custom;

import lk.ijse.gdse72.ormfinalcoursework.dao.CrudDAO;
import lk.ijse.gdse72.ormfinalcoursework.entity.TherapyProgram;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TherapyProgramDAO extends CrudDAO<TherapyProgram ,String > {
    String getNextID() throws SQLException;
    ArrayList<String> getPrograms() throws SQLException;
}
