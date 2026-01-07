package com.maria.Tema3;

import Entidades.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MenuUsuarioNormalController {

    @FXML
    private Button botonBibliotecaDigital;
    @FXML
    private Button botonPrestamos;
    @FXML
    private Button botonSalir;
    @FXML
    private Button botonUsuario;
    @FXML
    private Label labelBienvenida;

    private Usuario usuario;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        labelBienvenida.setText("Bienvenido, " + usuario.getNombre());
    }


    @FXML
    void abrirBiblioDigital(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MenuListaDeLibrosDisponible.fxml"));
            Parent root = loader.load();

            PaginaLibrosParaPrestamoController librosParaPrestamo = loader.getController();
            librosParaPrestamo.setUsuario(usuario);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void abrirMenuInfoUsuario(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/pantallaDatosUsuario.fxml"));
            Parent root = loader.load();

            DatosUsuarioController usuarioController = loader.getController();
            usuarioController.mostrarInformacion(usuario);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void abrirMenuPrestamosUsuario(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/paginaPrestamosUsuario.fxml"));
            Parent root = loader.load();

            PaginaPrestamosDelUsuarioController prestamosDelUsuarioController = loader.getController();
            prestamosDelUsuarioController.setUsuario(usuario);


            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void salirDelPrograma(ActionEvent event) {
        System.exit(0);
    }
}
