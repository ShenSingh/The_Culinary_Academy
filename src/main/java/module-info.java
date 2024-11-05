module com.zenveus.the_culinary_academy {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires static lombok;
    requires java.naming;


    opens com.zenveus.the_culinary_academy to javafx.fxml;
    exports com.zenveus.the_culinary_academy;
    exports com.zenveus.the_culinary_academy.controllers;
    opens com.zenveus.the_culinary_academy.controllers to javafx.fxml;
    opens com.zenveus.the_culinary_academy.entity to org.hibernate.orm.core;
}