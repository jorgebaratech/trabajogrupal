package com.example.gestionalumnos.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Profesor implements Serializable {
    private int id;
    private String nombre;
    private String apellido;
    private String password;
    private String correo;
}
