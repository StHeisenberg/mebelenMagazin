module com.example.mebelenmagazin {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires java.desktop;
    requires java.sql;


    opens com.example.mebelenmagazin to javafx.fxml;
    exports com.example.mebelenmagazin;
}