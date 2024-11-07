module com.zenveus.the_culinary_academy {
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires static lombok;
    requires java.naming;
    requires com.jfoenix;
    requires spring.security.crypto;
    requires org.mapstruct;


    opens com.zenveus.the_culinary_academy to javafx.fxml;
    exports com.zenveus.the_culinary_academy;
    exports com.zenveus.the_culinary_academy.controllers;
    opens com.zenveus.the_culinary_academy.controllers to javafx.fxml;
    opens com.zenveus.the_culinary_academy.entity to org.hibernate.orm.core;
    opens com.zenveus.the_culinary_academy.tm to javafx.base;
    exports com.zenveus.the_culinary_academy.tm;
}