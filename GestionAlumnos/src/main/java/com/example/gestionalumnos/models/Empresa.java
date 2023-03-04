package com.example.gestionalumnos.models;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Empresa implements Serializable {
    private Integer id;
    private String nombre;
    private String tel;
    private String correo;
    private String responsable;
    private String observaciones;
}
