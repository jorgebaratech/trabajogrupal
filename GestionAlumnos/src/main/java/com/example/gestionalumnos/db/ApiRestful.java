package com.example.gestionalumnos.db;

import com.example.gestionalumnos.models.Alumno;
import com.example.gestionalumnos.models.Profesor;
import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class ApiRestful {
    private ApiRestful(){}
    static String HOST = "http://localhost";
    static Integer PORT = 3000;
    static String ALUMNOS_ENDPOINT = "/alumnos";
    static String EMPRESAS_ENDPOINT = "/empresas";
    static String TAREAS_ENDPOINT = "/actividades";
    static String LOGIN_ALUMNO_ENDPOINT = "/login/alumno";
    static String LOGIN_PROFESOR_ENDPOINT = "/login/profesor";
    static String BASE_URL = String.format("%s:%d", HOST, PORT);
}
