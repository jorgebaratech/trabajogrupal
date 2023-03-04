package com.example.gestionalumnos.controllers;

import com.example.gestionalumnos.App;
import com.example.gestionalumnos.db.AlumnoDB;
import com.example.gestionalumnos.db.EmpresaDB;
import com.example.gestionalumnos.db.TareasDB;
import com.example.gestionalumnos.models.Alumno;
import com.example.gestionalumnos.models.Empresa;
import com.example.gestionalumnos.models.Tarea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import static com.example.gestionalumnos.db.AlumnoDB.*;
import static com.example.gestionalumnos.db.EmpresaDB.*;
import static com.example.gestionalumnos.db.TareasDB.getAllTareas;

public class ProfesorController implements Initializable {

    private Alumno alumnoSelected;
    private Empresa empresaSelected;
    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    @FXML
    private TextField alumnoApellido;

    @FXML
    private Button btnBorrarAlumno;

    @FXML
    private Button alumno_borrar1;

    @FXML
    private TextField alumnoCorreo;

    @FXML
    private TextField alumnoDni;

    @FXML
    private TextField alumnoEmpresa;

    @FXML
    private TextField alumnoFecha;

    @FXML
    private Button btnAlumnoAdd;

    @FXML
    private Button alumno_introducir1;

    @FXML
    private Tab alumnoList;

    @FXML
    private Button alumnoUpdate;

    @FXML
    private Button alumno_modificar1;

    @FXML
    private TextField alumnoNombre;

    @FXML
    private TextField alumnoObservaciones;

    @FXML
    private TextField alumnoPassword;

    @FXML
    private TableColumn<Alumno, String> apellidosAlumno;

    @FXML
    private TableColumn<Alumno, String> telAlumno;

    @FXML
    private Button btnLogin;

    @FXML
    private TableColumn<Alumno, Double> dualAlumno;

    @FXML
    private TableColumn<Alumno, Double> fctAlumno;

    @FXML
    private TableColumn<Alumno, String> correoAlumno;

    @FXML
    private TableColumn<Empresa, String> correoEmpresa;

    @FXML
    private TableColumn<Alumno, String> dniAlumno;

    @FXML
    private TableColumn<Alumno, String> empresaAlumno;

    @FXML
    private Button btnBorrarEmpresa;

    @FXML
    private TextField empresaCorreo;

    @FXML
    private Button btnEmpresaAdd;

    @FXML
    private Tab empresaList;

    @FXML
    private Button btnEmpresaUpdate;

    @FXML
    private TextField empresaNombre;

    @FXML
    private TextField empresaObservaciones;

    @FXML
    private TextField empresaResponsable;

    @FXML
    private TextField empresaTel;

    @FXML
    private TableColumn<Tarea, String> fechaActividad;

    @FXML
    private TableColumn<Alumno, String> fechaNAlumno;

    @FXML
    private TableColumn<Alumno, String> nombreAlumno;

    @FXML
    private TableColumn<Empresa, String> nombreEmpresa;

    @FXML
    private TableColumn<Empresa, Integer> numeroEmpresa;

    @FXML
    private TableColumn<Tarea, String> observacionesActividad;

    @FXML
    private TableColumn<Alumno, String> observacionesAlumno;

    @FXML
    private TableColumn<Empresa, String> observacionesEmpresa;

    @FXML
    private TableColumn<Alumno, String> profesorAlumno;

    @FXML
    private TableColumn<Tarea, String> realizadaActividad;

    @FXML
    private TableColumn<Empresa, String> responsableEmpresa;

    @FXML
    private TableColumn<Tarea, String> tipoActividad;

    @FXML
    private TableColumn<Tarea, String> totalHorasActividad;

    @FXML
    private TableView<Alumno> tablaAlumno;

    @FXML
    private TableView<Empresa> tablaEmpresa;

    @FXML
    private TableView<Tarea> tablaActividades;

    @FXML
    private TextField alumnoTel;

    @FXML
    private Tab tabActividades;

    @FXML
    public void backToLogin(ActionEvent actionEvent) throws IOException {
        App.setRoot("login-view");
    }

    public void selectAlumno(MouseEvent mouseEvent) {
        alumnoSelected = tablaAlumno.getSelectionModel().getSelectedItem();
        if(alumnoSelected != null){
            alumnoNombre.setText(alumnoSelected.getNombre());
            alumnoApellido.setText(alumnoSelected.getApellidos());
            alumnoDni.setText(alumnoSelected.getDni());
            alumnoTel.setText(alumnoSelected.getTel());
            alumnoFecha.setText(alumnoSelected.getFechaN());
            alumnoEmpresa.setText(String.valueOf(alumnoSelected.getEmpresa()));
            alumnoCorreo.setText(alumnoSelected.getCorreo());
            alumnoPassword.setText(alumnoSelected.getPassword());
            alumnoObservaciones.setText(alumnoSelected.getObservaciones());
        }
        updateTablesOnly();
    }

    public void addAlumno(ActionEvent actionEvent) {
        Alumno alumno = new Alumno(0, alumnoNombre.getText(), alumnoApellido.getText(),
                alumnoPassword.getText(), alumnoDni.getText(), alumnoFecha.getText(), alumnoCorreo.getText(),
                alumnoTel.getText(),Integer.valueOf(alumnoEmpresa.getText()), App.profesor.getId(), alumnoObservaciones.getText(), 0.0,0.0);
        add(alumno, client).join();
        update();
    }

    public void borrarAlumno(ActionEvent actionEvent) {
        if(alumnoSelected != null){
            AlumnoDB.delete(alumnoSelected.getId(), client).join();
            update();
        }
    }

    public void modificarAlumno(ActionEvent actionEvent) {
        if(alumnoSelected != null){
            Alumno alumno = new Alumno(0, alumnoNombre.getText(), alumnoApellido.getText(),
                    alumnoPassword.getText(), alumnoDni.getText(), alumnoFecha.getText(), alumnoCorreo.getText(),
                    alumnoTel.getText(),Integer.valueOf(alumnoEmpresa.getText()), App.profesor.getId(), alumnoObservaciones.getText(), 0.0,0.0);
            AlumnoDB.update(alumnoSelected.getId(),alumno, client).join();
            update();
        }
    }

    public void getEmpresaTabla(MouseEvent mouseEvent) {
        empresaSelected = tablaEmpresa.getSelectionModel().getSelectedItem();
        if(empresaSelected != null){
            empresaNombre.setText(empresaSelected.getNombre());
            empresaTel.setText(String.valueOf(empresaSelected.getTel()));
            empresaCorreo.setText(empresaSelected.getCorreo());
            empresaResponsable.setText(empresaSelected.getResponsable());
            empresaObservaciones.setText(empresaSelected.getObservaciones());
            updateTablesOnly();
        }
    }

    public void addEmpresa(ActionEvent actionEvent) {
        Empresa empresa = new Empresa(0, empresaNombre.getText(), empresaTel.getText(),
                empresaCorreo.getText(), empresaResponsable.getText(), empresaObservaciones.getText());

        EmpresaDB.add(empresa, client).join();
        update();
    }

    public void delEmpresa(ActionEvent actionEvent) {
        if(empresaSelected != null){
            EmpresaDB.delete(empresaSelected.getId(), client).join();
            update();
        }
    }

    public void modEmpresa(ActionEvent actionEvent) {
        if(empresaSelected != null){
            Empresa empresa = new Empresa(0, empresaNombre.getText(), empresaTel.getText(),
                    empresaCorreo.getText(), empresaResponsable.getText(), empresaObservaciones.getText());

            EmpresaDB.update(empresaSelected.getId(), empresa, client).join();
            update();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nombreAlumno.setCellValueFactory(new PropertyValueFactory("nombre"));
        apellidosAlumno.setCellValueFactory(new PropertyValueFactory("apellidos"));
        dniAlumno.setCellValueFactory(new PropertyValueFactory("dni"));
        telAlumno.setCellValueFactory(new PropertyValueFactory("tel"));
        fechaNAlumno.setCellValueFactory(new PropertyValueFactory("fechaN"));
        correoAlumno.setCellValueFactory(new PropertyValueFactory("correo"));
        empresaAlumno.setCellValueFactory(new PropertyValueFactory("empresa"));
        profesorAlumno.setCellValueFactory(new PropertyValueFactory("tutor"));
        observacionesAlumno.setCellValueFactory(new PropertyValueFactory("observaciones"));
        dualAlumno.setCellValueFactory(new PropertyValueFactory("horasDual"));
        fctAlumno.setCellValueFactory(new PropertyValueFactory("horasFCT"));

        //cellValueFactory for table Empresa
        nombreEmpresa.setCellValueFactory(new PropertyValueFactory("nombre"));
        numeroEmpresa.setCellValueFactory(new PropertyValueFactory("tel"));
        correoEmpresa.setCellValueFactory(new PropertyValueFactory("correo"));
        responsableEmpresa.setCellValueFactory(new PropertyValueFactory("responsable"));
        observacionesEmpresa.setCellValueFactory(new PropertyValueFactory("observaciones"));

        //CellValueFactory for table Actividades
        fechaActividad.setCellValueFactory(new PropertyValueFactory("fecha"));
        tipoActividad.setCellValueFactory(new PropertyValueFactory("tipo"));
        totalHorasActividad.setCellValueFactory(new PropertyValueFactory("totalHoras"));
        observacionesActividad.setCellValueFactory(new PropertyValueFactory("observaciones"));
        realizadaActividad.setCellValueFactory(new PropertyValueFactory("actividad"));
        update();
    }

    private void update() {
        updateTablesOnly();
        alumnoNombre.setText("");
        alumnoApellido.setText("");
        alumnoDni.setText("");
        alumnoFecha.setText("");
        alumnoFecha.setPromptText("yyyy-mm-dd");
        alumnoEmpresa.setText("");
        alumnoCorreo.setText("");
        alumnoPassword.setText("");
        alumnoObservaciones.setText("");
        alumnoTel.setText("");

        empresaNombre.setText("");
        empresaTel.setText("");
        empresaCorreo.setText("");
        empresaResponsable.setText("");
        empresaObservaciones.setText("");
    }

    private void updateTablesOnly() {
        ObservableList<Alumno> alumnos = FXCollections.observableArrayList();
        ObservableList<Empresa> empresas = FXCollections.observableArrayList();
        ObservableList<Tarea> actividades = FXCollections.observableArrayList();

        List<Alumno> alumnoList = getAllAlumnos(App.profesor, client)
                .thenApplyAsync(AlumnoDB::transformResponseToAlumnos)
                .join();
        alumnos.addAll(alumnoList);
        tablaAlumno.getItems().clear();
        tablaAlumno.setItems(alumnos);

        List<Empresa> empresaList = getAllEmpresas(App.profesor, client)
                .thenApplyAsync(EmpresaDB::transformResponseToEmpresas)
                .join();
        empresas.addAll(empresaList);
        tablaEmpresa.getItems().clear();
        tablaEmpresa.setItems(empresas);

        List<Tarea> tareaList = getAllTareas(App.profesor, client)
                .thenApplyAsync(TareasDB::transformResponseToTareas)
                .join();
        actividades.addAll(tareaList);
        tablaActividades.getItems().clear();
        tablaActividades.setItems(actividades);


    }
}
