package com.example.gestionalumnos.db;

import com.example.gestionalumnos.models.Alumno;
import com.example.gestionalumnos.models.Profesor;
import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

import static com.example.gestionalumnos.db.ApiRestful.*;

public class LoginDB {
    private static HttpRequest request;
    public static CompletableFuture<HttpResponse<String>> loginAlumno(String email, String password, HttpClient client){
        String json = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", email, password);
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(json);
        request = HttpRequest.newBuilder()
                .POST(bodyPublisher)
                .uri(URI.create(BASE_URL + LOGIN_ALUMNO_ENDPOINT))
                .header("Content-Type", "application/json; charset=utf-8")
                .build();

        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        return client.sendAsync(request, bodyHandler);
    }

    public static Alumno getAlumnoFromResponse(HttpResponse<String> httpResponse)  {
        if(httpResponse.statusCode() < 200 || httpResponse.statusCode() >= 300){
            return null;
        }

        return new Gson().fromJson(httpResponse.body(), Alumno.class);
    }

    public static CompletableFuture<HttpResponse<String>> loginProfesor(String email, String password, HttpClient client) {
        String json = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", email, password);
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(json);
        request = HttpRequest.newBuilder()
                .POST(bodyPublisher)
                .uri(URI.create(BASE_URL + LOGIN_PROFESOR_ENDPOINT))
                .header("Content-Type", "application/json; charset=utf-8")
                .build();

        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        return client.sendAsync(request, bodyHandler);
    }

    public static Profesor getProfesorFromResponse(HttpResponse<String> httpResponse) {
        if(httpResponse.statusCode() < 200 || httpResponse.statusCode() >= 300){
            return null;
        }

        return new Gson().fromJson(httpResponse.body(), Profesor.class);
    }
}
