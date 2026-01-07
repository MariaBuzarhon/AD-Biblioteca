package com.maria.Tema3;

import Entidades.Prestamo;
import Entidades.Usuario;
import Entidades.Ejemplar;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class menuListaPrestamosController {

    @FXML
    private Button botonVolver;

    @FXML
    private TableView<Prestamo> tablaPrestamosUsuario;

    @FXML
    private TableColumn<Prestamo, Integer> colIdPrestamo;

    @FXML
    private TableColumn<Prestamo, Integer> colIdUsuario;

    @FXML
    private TableColumn<Prestamo, Integer> colIdEjemplar;

    @FXML
    private TableColumn<Prestamo, LocalDate> colFechaInicio;

    @FXML
    private TableColumn<Prestamo, LocalDate> colFechaDevolucion;

    @FXML
    private Label labelBienvenida;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    private PrestamoDAO prestamoDAO = new PrestamoDAO();
    private Usuario usuario;
    @FXML
    public void initialize() {
        colIdPrestamo.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getId()).asObject());
        colIdUsuario.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getUsuario().getId()).asObject());
        colIdEjemplar.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getEjemplar().getId()).asObject());
        colFechaInicio.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getFechaInicio()));
        colFechaDevolucion.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getFechaDevolucion()));

        cargarPrestamos();
    }

    private void cargarPrestamos() {
        List<Prestamo> prestamos = prestamoDAO.findAll();
        tablaPrestamosUsuario.setItems(FXCollections.observableArrayList(prestamos));
    }

    @FXML
    void volverAlMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menuAdminBiblio.fxml"));
        Parent root = loader.load();

        MenuPrincipalAdminController controller = loader.getController();
        controller.setUsuario(usuario);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Menú Administrador");
        stage.show();

        ((Stage) botonVolver.getScene().getWindow()).close();
    }
}
