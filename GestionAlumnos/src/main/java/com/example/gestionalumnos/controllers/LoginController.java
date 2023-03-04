package com.example.gestionalumnos.controllers;

import com.example.gestionalumnos.App;
import com.example.gestionalumnos.db.LoginDB;
import com.example.gestionalumnos.models.Alumno;
import com.example.gestionalumnos.models.Profesor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.http.HttpClient;
import java.time.Duration;

import static com.example.gestionalumnos.db.LoginDB.*;

public class LoginController {
    @FXML
    private Button btnAccept;
    @FXML
    private Button btnCancel;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Label errorMessage;

    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();
    @FXML
    public void navigate(ActionEvent actionEvent) throws IOException {
        String usernameText = username.getText();
        String passwordText = password.getText();

        if(usernameText.isEmpty() || passwordText.isEmpty()){
            errorMessage.setText("No puedes dejar campos vacíos");
        } else {
            Alumno alumno = loginAlumno(usernameText, passwordText, client)
                    .thenApplyAsync(LoginDB::getAlumnoFromResponse)
                    .join();

            if(alumno != null) {
                System.out.println("Alumno -> " + alumno);
                App.alumno = alumno;
                App.setRoot("alumno-view");
                clean(actionEvent);
            } else{
                Profesor profesor = loginProfesor(usernameText, passwordText, client)
                        .thenApplyAsync(LoginDB::getProfesorFromResponse)
                        .join();

                if(profesor != null){
                    System.out.println("Profesor -> "+ profesor);
                    App.profesor = profesor;
                    App.setRoot("profesor-view");
                    errorMessage.setText("");
                    clean(actionEvent);
                } else {
                    errorMessage.setText("No existen ningún profesor/alumno con dichas credenciales");
                }
            }

        }
    }

    @FXML
    public void clean(ActionEvent actionEvent) {
        username.clear();
        password.clear();
    }
}