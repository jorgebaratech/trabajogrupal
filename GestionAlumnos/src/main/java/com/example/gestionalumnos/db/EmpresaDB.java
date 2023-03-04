package com.example.gestionalumnos.db;

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
import java.util.concurrent.CompletableFuture;

import static com.example.gestionalumnos.db.ApiRestful.*;

public class EmpresaDB {
    private static HttpRequest request;

    public static CompletableFuture<HttpResponse<String>> getAllEmpresas(Profesor profesor, HttpClient client) {
        request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(BASE_URL + EMPRESAS_ENDPOINT + "?profesor=" + profesor.getId())) // http://localhost:3000/empresas?profesor=1
                .build();

        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        return client.sendAsync(request, bodyHandler);
    }

    public static List<Empresa> transformResponseToEmpresas(HttpResponse<String> response) {
        if(response.statusCode() < 200 || response.statusCode() >= 300){
            return null;
        }

        final Type typeWrapper = new TypeToken<List<Empresa>>(){}.getType();
        final Gson gson = new Gson();
        final List<Empresa> list = gson.fromJson(response.body(), typeWrapper);
        return list;
    }

    public static CompletableFuture<HttpResponse<String>> add(Empresa empresa, HttpClient client) {
        String json = new GsonBuilder().create().toJson(empresa);
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(json);

        request = HttpRequest.newBuilder()
                .POST(bodyPublisher)
                .uri(URI.create(BASE_URL + EMPRESAS_ENDPOINT))
                .header("Content-Type", "application/json; charset=utf-8")
                .build();

        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        return client.sendAsync(request, bodyHandler);
    }

    public static CompletableFuture<HttpResponse<String>> update(Integer id, Empresa empresa, HttpClient client) {
        String json = new GsonBuilder().create().toJson(empresa);
        HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .PUT(bodyPublisher)
                .uri(URI.create(BASE_URL + EMPRESAS_ENDPOINT + "/" + id))
                .header("Content-Type", "application/json; charset=utf-8")
                .build();
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        return client.sendAsync(httpRequest, bodyHandler);
    }

    public static CompletableFuture<HttpResponse<String>> delete(int id, HttpClient client) {
        request = HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create(BASE_URL + EMPRESAS_ENDPOINT + "/" + id))
                .build();

        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandlers.ofString();
        return client.sendAsync(request, bodyHandler);
    }
}
