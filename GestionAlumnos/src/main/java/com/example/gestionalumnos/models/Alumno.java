package com.example.gestionalumnos.models;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Alumno implements Serializable {

    private int id;
    private String nombre;
    private String apellidos;
    private String password;
    private String dni;
    private String fechaN;
    private String correo;
    private String tel;
    private int empresa;
    private int tutor;
    private String observaciones;
    private Double horasDual;
    private Double horasFCT;
}
