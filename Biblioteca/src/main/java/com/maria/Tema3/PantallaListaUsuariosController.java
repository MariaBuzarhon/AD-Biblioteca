package com.maria.Tema3;

import Entidades.Usuario;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.stage.Stage;

import java.util.List;

public class PantallaListaUsuariosController {

    @FXML
    private Button botonVolver;
    @FXML
    private TableColumn<Usuario, Integer> colIdUsuario;
    @FXML
    private TableColumn<Usuario, String> colIdNombre;
    @FXML
    private TableColumn<Usuario, String> colIdEmail;
    @FXML
    private Label labelBienvenida;
    private Usuario usuario;
    @FXML
    private TableView<Usuario> tablaListaUsuarios;

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        cargarUsuarios();
    }

    private void cargarUsuarios() {
        List<Usuario> usuarios = usuarioDAO.findAll();
        tablaListaUsuarios.setItems(
                FXCollections.observableArrayList(usuarios)
        );
    }
    @FXML
    public void initialize() {

        colIdUsuario.setCellValueFactory(cell ->
                new SimpleIntegerProperty(cell.getValue().getId()).asObject()
        );

        colIdNombre.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getNombre())
        );

        colIdEmail.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getEmail())
        );

        cargarUsuarios();
    }

    @FXML
    void volverAlMenu(ActionEvent event) {
        Stage stage = (Stage) botonVolver.getScene().getWindow();
        stage.close();
    }
}
