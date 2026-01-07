package com.maria.Tema3;

import Entidades.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class DatosUsuarioController {

    @FXML
    private Button botonVolver;
    @FXML
    private Label labelBienvenida;
    @FXML
    private Label labelInfo;

    private Usuario usuario;

    public void mostrarInformacion(Usuario usu) {
        this.usuario = usu;

        labelBienvenida.setText("Datos de " + usu.getNombre());

        labelInfo.setText(
                "ID: " + usu.getId() +
                        "\nNombre: " + usu.getNombre() +
                        "\nDNI: " + usu.getDni() +
                        "\nEmail: " + usu.getEmail() +
                        "\nTipo de usuario: " + usu.getTipo()
        );
    }

    @FXML
    void volverAlMenu(ActionEvent event) {
        Stage stage = (Stage) botonVolver.getScene().getWindow();
        stage.close();
    }
}
