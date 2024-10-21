module com.zenveus.the_culinary_academy {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.zenveus.the_culinary_academy to javafx.fxml;
    exports com.zenveus.the_culinary_academy;
    exports com.zenveus.the_culinary_academy.Controllers;
    opens com.zenveus.the_culinary_academy.Controllers to javafx.fxml;
}