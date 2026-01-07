package com.maria.Tema3;

import Entidades.Libro;
import Entidades.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class PaginaRegistrarLibroController {

    @FXML
    private Button botonCancelar;
    @FXML
    private Button botonRegistrar;
    @FXML
    private Button botonVolver;
    @FXML
    private Label labelBienvenida;
    @FXML
    private TextField tfAutor;
    @FXML
    private TextField tfISBN;
    @FXML
    private TextField tfNumeroEjemplares;
    @FXML
    private TextField tfTitulo;

    private Usuario usuario;

    private LibroDAO libroDAO = new LibroDAO();

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        }

    @FXML
    void cancelarRegistro(ActionEvent event) {
        tfISBN.clear();
        tfTitulo.clear();
        tfAutor.clear();
        tfNumeroEjemplares.clear();
    }

    @FXML
    void registrarLibro(ActionEvent event) {
        String isbn = tfISBN.getText().trim();
        String titulo = tfTitulo.getText().trim();
        String autor = tfAutor.getText().trim();

        if (isbn.isEmpty() || titulo.isEmpty() || autor.isEmpty()) {
            mostrarAlerta("Campos vacíos", "Debes completar todos los campos para registrar un libro.");
            return;
        }

        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAutor(autor);

        libroDAO.guardar(libro);

        mostrarAlerta("Éxito", "Libro registrado correctamente.");

        tfISBN.clear();
        tfTitulo.clear();
        tfAutor.clear();
        tfNumeroEjemplares.clear();
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
