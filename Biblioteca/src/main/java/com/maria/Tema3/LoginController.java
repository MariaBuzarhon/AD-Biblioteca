package com.maria.Tema3;

import Entidades.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private Button botonIniciarSesion;
    @FXML
    private CheckBox checkboxMostrarContraseña;
    @FXML
    private PasswordField contraseñaUsuario;
    @FXML
    private TextField contraseñaVisible;
    @FXML
    private TextField nombreUsuario;
    @FXML
    private Label mensajeErrorLogin;

    @FXML
    public void initialize() {
        botonIniciarSesion.disableProperty().bind(
                nombreUsuario.textProperty().isEmpty()
                        .or(contraseñaUsuario.textProperty().isEmpty())
        );
        contraseñaVisible.textProperty().bindBidirectional(
                contraseñaUsuario.textProperty()
        );
        checkboxMostrarContraseña.selectedProperty().addListener(
                (obs, oldVal, newVal)
                -> mostrarContrasena(newVal)
        );
    }
    private void mostrarContrasena(boolean mostrar) {
        contraseñaVisible.setVisible(mostrar);
        contraseñaVisible.setManaged(mostrar);

        contraseñaUsuario.setVisible(!mostrar);
        contraseñaUsuario.setManaged(!mostrar);
    }


    @FXML
    void iniciarSesion(ActionEvent event) {
        String email = nombreUsuario.getText();
        String contrasenia = contraseñaUsuario.getText();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usu = usuarioDAO.login(email, contrasenia);

        if (usu == null) {
            mensajeErrorLogin.setText("Usuario o contraseña incorrectos");
            System.out.println("Usuario o contraseña incorrectos");
        } else {
            System.out.println("Bienvenido " + usu.getNombre());
            System.out.println("Tipo de usuario: " + usu.getTipo());

            try {
                if (usu.getTipo().equalsIgnoreCase("administrador")) {
                    FXMLLoader loader =
                            new FXMLLoader(getClass().getResource("/fxml/menuAdminBiblio.fxml"));
                    Parent root = loader.load();
                    MenuPrincipalAdminController adminController = loader.getController();
                    adminController.setUsuario(usu);
                    Stage stage = new Stage();
                    stage.setTitle("Menú Administrador");
                    stage.setScene(new Scene(root));
                    stage.show();
                } else {
                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource("/fxml/menuBiblio.fxml")
                    );
                    Parent root = loader.load();
                    MenuUsuarioNormalController menuController =
                            loader.getController();
                    menuController.setUsuario(usu);
                    Stage stage = new Stage();
                    stage.setTitle("Menú Biblioteca");
                    stage.setScene(new Scene(root));
                    stage.show();
                }
                ((Stage) botonIniciarSesion.getScene().getWindow()).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
