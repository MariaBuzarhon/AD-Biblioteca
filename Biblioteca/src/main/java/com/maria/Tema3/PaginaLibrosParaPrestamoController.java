package com.maria.Tema3;

import Entidades.Ejemplar;
import Entidades.Usuario;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class PaginaLibrosParaPrestamoController {

    @FXML private Button botonPedirPrestado;
    @FXML private Button botonVolver;
    @FXML private Label labelBienvenida;

    @FXML private TableView<Ejemplar> tablaLibrosDisponibles;
    @FXML private TableColumn<Ejemplar, String> colEstado;
    @FXML private TableColumn<Ejemplar, Integer> colIdLibro;
    @FXML private TableColumn<Ejemplar, String> colTitulo;
    @FXML private TableColumn<Ejemplar, String> colIsbn;
    @FXML private TableColumn<Ejemplar, String> colAutor;

    private Usuario usuario;
    private EjemplarDAO ejemplarDAO = new EjemplarDAO();
    private PrestamoDAO prestamoDAO = new PrestamoDAO();

    @FXML
    public void initialize() {
        colEstado.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getEstado()));
        colIdLibro.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getId()).asObject());
        colTitulo.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getIsbn().getTitulo()));
        colIsbn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getIsbn().getIsbn()));
        colAutor.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getIsbn().getAutor()));
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        labelBienvenida.setText("Libros disponibles para " + usuario.getNombre());
        cargarLibrosDisponibles();
    }

    private void cargarLibrosDisponibles() {
        List<Ejemplar> disponibles = ejemplarDAO.obtenerDisponibles();
        tablaLibrosDisponibles.setItems(FXCollections.observableArrayList(disponibles));
    }

    @FXML
    void pedirLibroPrestado(ActionEvent event) {
        if (usuario.getPenalizacionHasta() != null && usuario.getPenalizacionHasta().isAfter(LocalDate.now())) {
            mostrarAlerta("No permitido", "Tienes una penalización activa hasta " + usuario.getPenalizacionHasta());
            return;
        }

        int activos = prestamoDAO.contarPrestamosActivos(usuario);
        if (activos >= 3) {
            mostrarAlerta("Límite alcanzado", "Ya tienes 3 préstamos activos");
            return;
        }

        Ejemplar seleccionado = tablaLibrosDisponibles.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Aviso", "Selecciona un libro para pedir prestado");
            return;
        }
        prestamoDAO.crearPrestamo(usuario, seleccionado);
        cargarLibrosDisponibles();
        mostrarAlerta("Éxito", "Libro pedido prestado correctamente");
    }

    @FXML
    void volverAlMenu(ActionEvent event) throws IOException {
        Stage stage = (Stage) botonVolver.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
