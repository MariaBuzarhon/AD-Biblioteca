package com.maria.Tema3;

import Entidades.Ejemplar;
import Entidades.Libro;
import Entidades.Usuario;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class PaginaRegistrarEjemplaresController {

    @FXML
    private Button botonCancelarRegistroEjemplares;
    @FXML
    private Button botonRegistrarEjemplares;
    @FXML
    private Button botonVolver;
    @FXML
    private Label labelBienvenida;
    @FXML
    private TextField tfEstado;
    @FXML
    private TextField tfISBN;
    private Usuario usuario;

    private EjemplarDAO ejemplarDAO = new EjemplarDAO();
    private LibroDAO libroDAO = new LibroDAO();

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        labelBienvenida.setText("Registrar ejemplares - " + usuario.getNombre());
    }

    @FXML
    void cancelarRegistroEjemplares(ActionEvent event) {
        tfISBN.clear();
        tfEstado.clear();
    }

    @FXML
    void registrarEjemplares(ActionEvent event) {
        String estado = tfEstado.getText().trim().toLowerCase();
        String isbn = tfISBN.getText().trim();

        List<String> estadosValidos = List.of("disponible", "prestado", "dañado");
        if (!estadosValidos.contains(estado)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Estado inválido");
            alert.setContentText("Los estados válidos son: disponible, prestado, dañado");
            alert.showAndWait();
            return;
        }

        Libro libro = new LibroDAO().buscarPorISBN(isbn);
        if (libro == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Libro no encontrado");
            alert.setContentText("No existe ningún libro con ese ISBN");
            alert.showAndWait();
            return;
        }

        Ejemplar e = new Ejemplar();
        e.setEstado(estado);
        e.setIsbn(libro);

        new EjemplarDAO().guardar(e);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText("Ejemplar registrado");
        alert.setContentText("Ejemplar registrado correctamente");
        alert.showAndWait();

        tfEstado.clear();
        tfISBN.clear();
    }

    @FXML
    void volverAlMenu(ActionEvent event) {
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
