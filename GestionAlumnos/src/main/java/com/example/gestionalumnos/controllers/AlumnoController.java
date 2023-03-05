package com.example.gestionalumnos.controllers;

import com.example.gestionalumnos.App;
import com.example.gestionalumnos.db.AlumnoDB;
import com.example.gestionalumnos.db.TareasDB;
import com.example.gestionalumnos.models.Empresa;
import com.example.gestionalumnos.models.Profesor;
import com.example.gestionalumnos.models.Tarea;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URL;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
public class AlumnoController implements Initializable {

    private Tarea tareaSelected;

    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();
    @FXML
    private void back() throws IOException {
        App.setRoot("login-view");
    }
    @FXML
    private Button btnLogin;
    @FXML
    private TextField fechaTarea;
    @FXML
    private TextField totalTarea;
    @FXML
    private TextField observacionesTarea;
    @FXML
    private TextField tipoTarea;
    @FXML
    private TextField actividadTarea;
    @FXML
    private Button btnAddTarea;
    @FXML
    private Button btnUpdateTarea;
    @FXML
    private Button btnDeleteTarea;
    @FXML
    private Tab listaTareas;
    @FXML
    private TableView<Tarea> tablaTareas;
    @FXML
    private TableColumn<Tarea, String> tareaFecha;
    @FXML
    private TableColumn<Tarea, String> tareaTipo;
    @FXML
    private TableColumn<Tarea, Double> tareaTotal;
    @FXML
    private TableColumn<Tarea, String> tareaActividad;
    @FXML
    private TableColumn<Tarea, String> tareaObservaciones;
    @FXML
    private Tab listaEmpresa;
    @FXML
    private TableView<Empresa> tablaEmpresa;
    @FXML
    private TableColumn<Empresa, String> empresaNombre;
    @FXML
    private TableColumn<Empresa, Integer> empresaTel;
    @FXML
    private TableColumn<Empresa, String> empresaCorreo;
    @FXML
    private TableColumn<Empresa, String> empresaResponsable;
    @FXML
    private TableColumn<Empresa, String> empresaObservaciones;
    @FXML
    private Tab listaProfesor;
    @FXML
    private TableView<Profesor> tablaProfesor;
    @FXML
    private TableColumn<Profesor, String> profesorNombre;
    @FXML
    private TableColumn<Profesor, String> profesorApellido;
    @FXML
    private TableColumn<Profesor, String> profesorCorreo;
    @FXML
    private TextField txtDual;
    @FXML
    private TextField txtDualRes;
    @FXML
    private TextField txtFCT;
    @FXML
    private TextField txtFCTRest;

    @FXML
    void selectTarea(MouseEvent event) {
        if (tablaTareas.getSelectionModel().getSelectedItem() != null) {
            tareaSelected = tablaTareas.getSelectionModel().getSelectedItem();
            fechaTarea.setText(tareaSelected.getFecha());
            tipoTarea.setText(tareaSelected.getTipo());
            totalTarea.setText(String.valueOf((Double) tareaSelected.getTotalHoras()));
            actividadTarea.setText(tareaSelected.getActividad());
            observacionesTarea.setText(tareaSelected.getObservaciones());
            updateTablesOnly();
        } else {
            update();
        }
    }

    @FXML
    void addTarea(ActionEvent event) {
        Tarea tarea = new Tarea(0, fechaTarea.getText(), tipoTarea.getText(), Double.parseDouble(totalTarea.getText()),
                actividadTarea.getText(), observacionesTarea.getText(), App.alumno.getId());

        TareasDB.add(tarea, client).join();
        update();
    }

    @FXML
    void deleteTarea(ActionEvent event) {
        if(tareaSelected != null){
            TareasDB.delete(tareaSelected.getId(), client).join();
            updateTablesOnly();
        }
    }

    @FXML
    void updateTarea(ActionEvent event) {
        if(tareaSelected != null){
            Tarea newTarea = new Tarea(tareaSelected.getId(), fechaTarea.getText(), tipoTarea.getText(), Double.parseDouble(totalTarea.getText()),
                    actividadTarea.getText(), observacionesTarea.getText(), App.alumno.getId());
            TareasDB.update(tareaSelected.getId(), newTarea, client).join();
            update();
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //cellValueFactories for table profesor
        profesorNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        profesorApellido.setCellValueFactory(new PropertyValueFactory("apellido"));
        profesorCorreo.setCellValueFactory(new PropertyValueFactory("correo"));

        //cellValueFactory for table Empresa
        empresaNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        empresaTel.setCellValueFactory(new PropertyValueFactory("tel"));
        empresaCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
        empresaResponsable.setCellValueFactory(new PropertyValueFactory("responsable"));
        empresaObservaciones.setCellValueFactory(new PropertyValueFactory("observaciones"));

        //CellValueFactory for table Actividades
        tareaFecha.setCellValueFactory(new PropertyValueFactory("fecha"));
        tareaTipo.setCellValueFactory(new PropertyValueFactory("tipo"));
        tareaTotal.setCellValueFactory(new PropertyValueFactory("totalHoras"));
        tareaObservaciones.setCellValueFactory(new PropertyValueFactory("observaciones"));
        tareaActividad.setCellValueFactory(new PropertyValueFactory("actividad"));
        update();

    }

    private void update() {
        updateTablesOnly();
        fechaTarea.setText("");
        fechaTarea.setPromptText("dd/mm/yyyy");
        tipoTarea.setText("");
        totalTarea.setText("");
        actividadTarea.setText("");
        observacionesTarea.setText("");
    }

    private void updateTablesOnly() {
        ObservableList<Profesor> profesor = FXCollections.observableArrayList();
        ObservableList<Empresa> empresas = FXCollections.observableArrayList();
        ObservableList<Tarea> actividades = FXCollections.observableArrayList();

        List<Tarea> tareaList = TareasDB.getAllTareasByAlumno(App.alumno, client)
                .thenApplyAsync(TareasDB::transformResponseToTareas)
                .join();
        actividades.addAll(tareaList);
        tablaTareas.getItems().clear();
        tablaTareas.setItems(actividades);


        List<Profesor> profesorList = AlumnoDB.getProfesor(App.alumno, client)
                .thenApplyAsync(AlumnoDB::transformResponseToProfesor).join();
        profesor.addAll(profesorList);
        tablaProfesor.getItems().clear();
        tablaProfesor.setItems(profesor);

        List<Empresa> empresaList = AlumnoDB.getEmpresa(App.alumno, client).
                thenApplyAsync(AlumnoDB::transformResponseToEmpresa).join();
        empresas.addAll(empresaList);
        tablaEmpresa.getItems().clear();
        tablaEmpresa.setItems(empresas);

        Map<String, Double> horas = AlumnoDB.getHoras(App.alumno, client)
                .thenApplyAsync(AlumnoDB::transformResponseToHoras).join();

        txtDual.setText(String.valueOf(horas.get("horasDual")));
        txtFCT.setText(String.valueOf(String.valueOf(horas.get("horasFCT"))));
//            txtDualRes.setText(String.valueOf(530-alumnoSQL.horasDual(App.actualAlumno.getId_alumno())));
//            txtFCTRest.setText(String.valueOf(530-alumnoSQL.horasFCT(App.actualAlumno.getId_alumno())));
    }
}
