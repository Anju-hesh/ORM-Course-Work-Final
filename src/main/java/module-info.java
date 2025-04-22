module lk.ijse.gdse72.ormfinalcoursework {

    requires javafx.fxml;
    requires static lombok;
    requires com.jfoenix;
    requires javafx.controls;
//    requires jakarta.persistence;
//    requires org.hibernate.orm.core;
//    requires jakarta.validation;
    requires java.prefs;

    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;
    requires jbcrypt;
    requires java.management;

    opens lk.ijse.gdse72.ormfinalcoursework.config to jakarta.persistence;
    opens lk.ijse.gdse72.ormfinalcoursework.entity to org.hibernate.orm.core;
    opens lk.ijse.gdse72.ormfinalcoursework.dto.tm to javafx.base;

//    opens lk.ijse.gdse72.ormfinalcoursework.javafx.naming to org.hibernate.orm.core;


    opens lk.ijse.gdse72.ormfinalcoursework.controller to javafx.fxml;
    opens lk.ijse.gdse72.ormfinalcoursework to javafx.fxml;
    exports lk.ijse.gdse72.ormfinalcoursework;
}