package com.example.grupal.controllers;

import com.example.grupal.models.Alumno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/alumnos")
public class AlumnoController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/{id}")
    @ResponseBody
    public Alumno getAlumno(@PathVariable int id) {
        String sql = "SELECT * FROM alumno WHERE id_alumno = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) ->
                new Alumno(rs.getInt("id_alumno"), rs.getString("nombre"), rs.getString("apellidos"),
                        rs.getString("contraseña"), rs.getString("dni"), rs.getString("fechaN"),
                        rs.getString("correo"), rs.getInt("telefono"), rs.getString("empresa"),
                        rs.getString("profesor"), rs.getString("observaciones")));
    }

    @GetMapping("")
    @ResponseBody
    public List<Alumno> getAlumnos() {
        String sql = "SELECT * FROM alumno";
        List<Alumno> alumnos = new ArrayList<>();
        jdbcTemplate.query(sql, (rs, rowNum) -> alumnos.add(new Alumno(
                rs.getInt("id_alumno"), rs.getString("nombre"), rs.getString("apellidos"),
                rs.getString("contraseña"), rs.getString("dni"), rs.getString("fechaN"),
                rs.getString("correo"), rs.getInt("telefono"), rs.getString("empresa"),
                rs.getString("profesor"), rs.getString("observaciones"))));
        return alumnos;
    }

    @GetMapping("/profesor/{profesor}")
    @ResponseBody
    public List<Alumno> getAlumnosPorProfesor(@PathVariable String profesor) {
        String sql = "SELECT * FROM alumno WHERE profesor = ?";
        List<Alumno> alumnos = new ArrayList<>();
        jdbcTemplate.query(sql, new Object[]{profesor}, (rs, rowNum) -> alumnos.add(new Alumno(
                rs.getInt("id_alumno"), rs.getString("nombre"), rs.getString("apellidos"),
                rs.getString("contraseña"), rs.getString("dni"), rs.getString("fechaN"),
                rs.getString("correo"), rs.getInt("telefono"), rs.getString("empresa"),
                rs.getString("profesor"), rs.getString("observaciones"))));
        return alumnos;
    }

    @GetMapping("/{id}/dual")
    @ResponseBody
    public int getHorasDual(@PathVariable int id) {
        String sql = "SELECT SUM(totalHoras) AS horasDUAL FROM tarea WHERE tipo = 'Dual' AND id_alumno = ?";
        Integer horasDual = jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
        return horasDual != null ? horasDual : 0;
    }
    
    @GetMapping("/{id}/fct")
    @ResponseBody
    public int getHorasFCT(@PathVariable int id) {
        String sql = "SELECT SUM(totalHoras) AS horasFCT FROM tarea WHERE tipo = 'FCT' AND id_alumno = ?";
        Integer horasFCT = jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
        return horasFCT;
                }
    @PostMapping("")
@ResponseBody
public Alumno createAlumno(@RequestBody Alumno alumno) {
    String sql = "INSERT INTO alumno (nombre, apellidos, contraseña, dni, fechaN, correo, telefono, empresa, profesor, observaciones) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    jdbcTemplate.update(sql, alumno.getNombre(), alumno.getApellidos(), alumno.getContraseña(), alumno.getDni(),
            alumno.getFechaN(), alumno.getCorreo(), alumno.getTelefono(), alumno.getEmpresa(), alumno.getProfesor(),
            alumno.getObservaciones());
    return alumno;
}
    @DeleteMapping("/{id}")
@ResponseBody
public String deleteAlumno(@PathVariable int id) {
    String sql = "DELETE FROM alumno WHERE id_alumno = ?";
    jdbcTemplate.update(sql, id);
    return "Alumno eliminado correctamente";
}
    
    
}