package com.maria.Tema3;

import Entidades.Prestamo;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class PaginaPrestamosDelUsuarioController {

    @FXML
    private Button botonDevolver;
    @FXML
    private Button botonVolver;
    @FXML
    private Label labelBienvenida;
    @FXML
    private TableView<Prestamo> tablaPrestamosUsuario;
    @FXML
    private TableColumn<Prestamo, Integer> colIdPrestamo;
    @FXML
    private TableColumn<Prestamo, Integer> colIdEjemplar;
    @FXML
    private TableColumn<Prestamo, String> colNombreEjemplar;
    @FXML
    private TableColumn<Prestamo, LocalDate> colFechaInicio;
    @FXML
    private TableColumn<Prestamo, LocalDate> colFechaDevolucion;

    private Usuario usuario;

    private PrestamoDAO prestamoDAO = new PrestamoDAO();

    @FXML
    public void initialize() {

        colIdPrestamo.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );
        colIdEjemplar.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(
                        cellData.getValue().getEjemplar() != null
                                ? cellData.getValue().getEjemplar().getId()
                                : 0
                ).asObject()
        );
        colNombreEjemplar.setCellValueFactory(cellData -> {
            if (cellData.getValue().getEjemplar() != null &&
                    cellData.getValue().getEjemplar().getIsbn() != null) {
                return new SimpleStringProperty(
                        cellData.getValue().getEjemplar().getIsbn().getTitulo()
                );
            } else {
                return new SimpleStringProperty("Sin título");
            }
        });
        colFechaInicio.setCellValueFactory(
                new PropertyValueFactory<>("fechaInicio")
        );
        colFechaDevolucion.setCellValueFactory(
                new PropertyValueFactory<>("fechaDevolucion")
        );
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        labelBienvenida.setText("Préstamos de " + usuario.getNombre());
        cargarPrestamos();
    }

    private void cargarPrestamos() {
        List<Prestamo> prestamos = prestamoDAO.obtenerPrestamosPorUsuario(usuario);
        if (prestamos.isEmpty()) {
            mostrarAlerta("Aviso", "No tienes préstamos actualmente.");
        }
        tablaPrestamosUsuario.setItems(FXCollections.observableArrayList(prestamos));
    }

    @FXML
    void devolverLibro(ActionEvent event) {
        Prestamo seleccionado = tablaPrestamosUsuario.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta("Aviso", "Selecciona un préstamo para devolver");
            return;
        }

        prestamoDAO.devolverPrestamo(seleccionado);

        cargarPrestamos();

        mostrarAlerta("Éxito", "Préstamo devuelto correctamente");
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
