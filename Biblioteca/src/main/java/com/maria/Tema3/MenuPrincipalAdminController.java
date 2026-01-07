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

public class MenuPrincipalAdminController {

    private Usuario usuario;

    @FXML
    private Button botonAdminPrestamos;
    @FXML
    private Button botonAdminSalir;
    @FXML
    private Button botonAdminUsuarios;
    @FXML
    private Button botonListaLibros;
    @FXML
    private Button botonRegistrarEjemplares;
    @FXML
    private Button botonRegistrarLibros;
    @FXML
    private Button botonRegistrarUsuarios;
    @FXML
    private Label labelBienvenidaAdmin;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        labelBienvenidaAdmin.setText(
                "Bienvenido administrador, " + usuario.getNombre()
        );
    }

    @FXML
    void listarLibros(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/listaLibrosAdmin.fxml"));
            Parent root = loader.load();

            paginaListaLibrosAdminController controller = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void listarPrestamos(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/paginaPrestamosAdmin.fxml"));
            Parent root = loader.load();

            menuListaPrestamosController controller = loader.getController();
            controller.setUsuario(usuario);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void listarUsuarios(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menuListaUsuarios.fxml"));
            Parent root = loader.load();

            PantallaListaUsuariosController controller = loader.getController();
            controller.setUsuario(usuario);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void registrarEjemplares(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menuRegistrarEjemplares.fxml"));
            Parent root = loader.load();

            PaginaRegistrarEjemplaresController controller = loader.getController();
            controller.setUsuario(usuario);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void registrarLibros(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/paginaRegistroLibros.fxml"));
            Parent root = loader.load();

            PaginaRegistrarLibroController controller = loader.getController();
            controller.setUsuario(usuario);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void registrarUsuario(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menuRegistrarUsuario.fxml"));
            Parent root = loader.load();

            PaginaResgistrarUsuarioController controller = loader.getController();
            controller.setUsuario(usuario);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void salir(ActionEvent event) {
        System.exit(0);
    }
}
