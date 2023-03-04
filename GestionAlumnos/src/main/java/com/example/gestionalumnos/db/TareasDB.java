package com.example.gestionalumnos.db;

import com.example.gestionalumnos.models.Alumno;
import com.example.gestionalumnos.models.Profesor;
import com.example.gestionalumnos.models.Tarea;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.example.gestionalumnos.db.ApiRestful.*;

public class TareasDB {

    private static HttpRequest request;

    public static CompletableFuture<HttpResponse<String>> getAllTareas(Profesor profesor, HttpClient client) {
        request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(BASE_URL + TAREAS_ENDPOINT + "?profesor=" + profesor.getId()))
                .build();

        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        return client.sendAsync(request, bodyHandler);
    }

    public static List<Tarea> transformResponseToTareas(HttpResponse<String> response) {
        if(response.statusCode() < 200 || response.statusCode() >= 300){
            return null;
        }

        final Type typeWrapper = new TypeToken<List<Tarea>>(){}.getType();
        final Gson gson = new Gson();
        final List<Tarea> list = gson.fromJson(response.body(), typeWrapper);
        return list;
    }

    public static CompletableFuture<HttpResponse<String>> getAllTareasByAlumno(Alumno alumno, HttpClient client) {
        request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(BASE_URL + ALUMNOS_ENDPOINT + TAREAS_ENDPOINT +"/" + alumno.getId()))
                .build();

        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        return client.sendAsync(request, bodyHandler);
    }

    public static CompletableFuture<HttpResponse<String>> add(Tarea tarea, HttpClient client) {
        String json = new GsonBuilder().create().toJson(tarea);
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(json);

        request = HttpRequest.newBuilder()
                .POST(bodyPublisher)
                .uri(URI.create(BASE_URL + TAREAS_ENDPOINT))
                .header("Content-Type", "application/json; charset=utf-8")
                .build();

        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        return client.sendAsync(request, bodyHandler);
    }

    public static CompletableFuture<HttpResponse<String>> delete(int id, HttpClient client) {
        request = HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create(BASE_URL + TAREAS_ENDPOINT + "/" + id))
                .build();

        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        return client.sendAsync(request, bodyHandler);
    }

    public static CompletableFuture<HttpResponse<String>> update(int id, Tarea tarea, HttpClient client){
        String json = new GsonBuilder().create().toJson(tarea);
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .PUT(bodyPublisher)
                .uri(URI.create(BASE_URL + TAREAS_ENDPOINT + "/" + id))
                .header("Content-Type", "application/json; charset=utf-8")
                .build();
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        return client.sendAsync(httpRequest, bodyHandler);
    }
}
