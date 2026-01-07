package com.maria.Tema3;

import Entidades.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class PaginaResgistrarUsuarioController {

    @FXML private Button botonCancelar;
    @FXML private Button botonRegistrar;
    @FXML private Button botonVolver;

    @FXML private Label labelBienvenida;
    @FXML private TextField tfDNI;
    @FXML private TextField tfEmail;
    @FXML private TextField tfNombre;
    @FXML private TextField tfPassword;
    @FXML private TextField tfTipo;

    private Usuario usuario;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        labelBienvenida.setText("Usuario conectado: " + usuario.getNombre());
    }

    @FXML
    void cancelarRegistroUsuario(ActionEvent event) {
        limpiarCampos();
    }

    @FXML
    void registrarUsuario(ActionEvent event) {
        if (tfDNI.getText().isEmpty() || tfEmail.getText().isEmpty() ||
                tfNombre.getText().isEmpty() || tfPassword.getText().isEmpty() ||
                tfTipo.getText().isEmpty()) {
            mostrarAlerta("Campos vacíos", "Debes rellenar todos los campos");
            return;
        }

        String tipo = tfTipo.getText().toLowerCase();
        if (!tipo.equals("normal") && !tipo.equals("administrador")) {
            mostrarAlerta("Tipo incorrecto", "El tipo debe ser 'normal' o 'administrador'");
            return;
        }

        Usuario nuevo = new Usuario();
        nuevo.setDni(tfDNI.getText());
        nuevo.setEmail(tfEmail.getText());
        nuevo.setNombre(tfNombre.getText());
        nuevo.setPassword(tfPassword.getText());
        nuevo.setTipo(tipo);

        boolean exito = usuarioDAO.guardarUsuario(nuevo);

        if (exito) {
            mostrarAlerta("Éxito", "Usuario registrado correctamente");
            limpiarCampos();
        } else {
            mostrarAlerta("Error", "No se pudo registrar el usuario. ¿El email ya existe?");
        }
    }

    @FXML
    void volverAlMenu(ActionEvent event) {
        Stage stage = (Stage) botonVolver.getScene().getWindow();
        stage.close();
    }

    private void limpiarCampos() {
        tfDNI.clear();
        tfEmail.clear();
        tfNombre.clear();
        tfPassword.clear();
        tfTipo.clear();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
