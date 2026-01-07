package com.maria.Tema3;

import Entidades.Ejemplar;
import Entidades.Libro;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.util.*;
import java.util.stream.Collectors;

public class paginaListaLibrosAdminController {

    @FXML
    private Button botonVolver;
    @FXML
    private TableColumn<Libro, String> colAutor;
    @FXML
    private TableColumn<Libro, Integer> colEjemplaresDisponibles;
    @FXML
    private TableColumn<Libro, String> colEstado;
    @FXML
    private TableColumn<Libro, String> colIsbn;
    @FXML
    private Label labelBienvenida;
    @FXML
    private TableView<Libro> tablaLibros;

    private LibroDAO libroDAO = new LibroDAO();
    private EjemplarDAO ejemplarDAO = new EjemplarDAO();

    @FXML
    public void initialize() {
        colIsbn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getIsbn()));
        colAutor.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getAutor()));

        colEstado.setCellValueFactory(cell -> {
            List<Ejemplar> ejemplares = ejemplarDAO.obtenerPorLibro(cell.getValue());
            if (!ejemplares.isEmpty()) {
                String estados = ejemplares.stream()
                        .map(Ejemplar::getEstado)
                        .distinct()
                        .collect(Collectors.joining(", "));
                return new SimpleStringProperty(estados);
            }
            return new SimpleStringProperty("Sin ejemplares");
        });
        colEjemplaresDisponibles.setCellValueFactory(cell -> {
            List<Ejemplar> ejemplares = ejemplarDAO.obtenerPorLibro(cell.getValue());
            int disponibles = (int) ejemplares.stream()
                    .filter(e -> e.getEstado().equalsIgnoreCase("disponible"))
                    .count();
            return new SimpleIntegerProperty(disponibles).asObject();
        });
        cargarLibros();
    }

    private void cargarLibros() {
        List<Libro> lista = libroDAO.obtenerTodos();
        tablaLibros.setItems(FXCollections.observableArrayList(lista));
    }

    @FXML
    void volverAlMenu(ActionEvent event) {
        Stage stage = (Stage) botonVolver.getScene().getWindow();
        stage.close();
    }
}
