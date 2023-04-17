module com.example.rezervacnisystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;



    opens com.example.rezervacnisystem to javafx.fxml;
    exports com.example.rezervacnisystem;
    exports com.example.rezervacnisystem.utills;
    opens com.example.rezervacnisystem.utills to javafx.fxml;
}