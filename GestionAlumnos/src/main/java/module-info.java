module com.example.gestionalumnos {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.google.gson;
    requires java.sql;
    requires lombok;
    requires java.net.http;

    opens com.example.gestionalumnos to javafx.fxml;
    exports com.example.gestionalumnos;
    exports com.example.gestionalumnos.controllers;
    exports com.example.gestionalumnos.models;

//    opens com.edencoding.models.openVision to gson;
//    opens com.exam.models.dogs to gson;
    opens com.example.gestionalumnos.models;
    opens com.example.gestionalumnos.controllers to javafx.fxml;
}