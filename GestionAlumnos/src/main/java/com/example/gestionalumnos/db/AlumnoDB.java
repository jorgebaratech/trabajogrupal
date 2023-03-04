package com.example.gestionalumnos.db;

import com.example.gestionalumnos.models.Alumno;
import com.example.gestionalumnos.models.Empresa;
import com.example.gestionalumnos.models.Profesor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.example.gestionalumnos.db.ApiRestful.*;

public class AlumnoDB {
    private static HttpRequest request;
    public static CompletableFuture<HttpResponse<String>> getAllAlumnos(Profesor profesor, HttpClient client){
        request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(BASE_URL + ALUMNOS_ENDPOINT + "?profesor=" + profesor.getId())) // http://localhost:3000/alumnos?profesor=1
                .build();

        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        return client.sendAsync(request, bodyHandler);
    }

    public static List<Alumno> transformResponseToAlumnos(HttpResponse<String> httpResponse) {
        if(httpResponse.statusCode() < 200 || httpResponse.statusCode() >= 300){
            return null;
        }

        final Type typeWrapper = new TypeToken<List<Alumno>>(){}.getType();
        final Gson gson = new Gson();
        final List<Alumno> list = gson.fromJson(httpResponse.body(), typeWrapper);
        return list;
    }

    public static CompletableFuture<HttpResponse<String>> add(Alumno alumno, HttpClient client) {
        String json = new GsonBuilder().create().toJson(alumno);
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(json);

        request = HttpRequest.newBuilder()
                .POST(bodyPublisher)
                .uri(URI.create(BASE_URL + ALUMNOS_ENDPOINT))
                .header("Content-Type", "application/json; charset=utf-8")
                .build();

        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        return client.sendAsync(request, bodyHandler);
    }

    public static CompletableFuture<HttpResponse<String>> delete(int id, HttpClient client) {
        request = HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create(BASE_URL + ALUMNOS_ENDPOINT + "/" + id))
                .build();

        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        return client.sendAsync(request, bodyHandler);
    }

    public static CompletableFuture<HttpResponse<String>> update(int id, Alumno newAlumno, HttpClient client){
        String json = new GsonBuilder().create().toJson(newAlumno);
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .PUT(bodyPublisher)
                .uri(URI.create(BASE_URL + ALUMNOS_ENDPOINT + "/" + id))
                .header("Content-Type", "application/json; charset=utf-8")
                .build();
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        return client.sendAsync(httpRequest, bodyHandler);
    }

    public static CompletableFuture<HttpResponse<String>> getProfesor(Alumno alumno, HttpClient client) {
        request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(BASE_URL + ALUMNOS_ENDPOINT + "/profesor/" + alumno.getId()))
                .build();

        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        return client.sendAsync(request, bodyHandler);
    }

    public static List<Profesor> transformResponseToProfesor(HttpResponse<String> httpResponse) {
        if(httpResponse.statusCode() < 200 || httpResponse.statusCode() >= 300){
            return null;
        }

        final Gson gson = new Gson();
        final Profesor profesor = gson.fromJson(httpResponse.body(), Profesor.class);
        return List.of(profesor);
    }

    public static CompletableFuture<HttpResponse<String>> getEmpresa(Alumno alumno, HttpClient client) {
        request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(BASE_URL + ALUMNOS_ENDPOINT + "/empresa/" + alumno.getId()))
                .build();

        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        return client.sendAsync(request, bodyHandler);
    }

    public static List<Empresa> transformResponseToEmpresa(HttpResponse<String> httpResponse) {
        if(httpResponse.statusCode() < 200 || httpResponse.statusCode() >= 300){
            return null;
        }

        final Gson gson = new Gson();
        final Empresa empresa = gson.fromJson(httpResponse.body(), Empresa.class);
        return List.of(empresa);
    }

    public static CompletableFuture<HttpResponse<String>> getHoras(Alumno alumno, HttpClient client) {
        request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(BASE_URL + ALUMNOS_ENDPOINT + "/horas/" + alumno.getId()))
                .build();

        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        return client.sendAsync(request, bodyHandler);
    }

    public static Map<String, Double> transformResponseToHoras(HttpResponse<String> httpResponse) {
        if(httpResponse.statusCode() < 200 || httpResponse.statusCode() >= 300){
            return null;
        }

        final Type typeWrapper = new TypeToken<Map<String, Double>>(){}.getType();
        final Gson gson = new Gson();
        final Map<String, Double> map = gson.fromJson(httpResponse.body(), typeWrapper);
        return map;
    }
}
