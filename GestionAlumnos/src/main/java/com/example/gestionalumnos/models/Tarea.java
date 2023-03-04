package com.example.gestionalumnos.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Tarea {

    private int id;
    private String fecha;
    private String tipo;
    private double totalHoras;
    private String actividad;
    private String observaciones;
    private int id_alumno;
}
