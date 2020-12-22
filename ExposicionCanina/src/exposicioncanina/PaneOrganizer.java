/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exposicioncanina;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import modelo.Perro;

/**
 *
 * @author gecarri
 */
public class PaneOrganizer {

    private BorderPane root;
    private ListView lvPerros;
    private TreeMap<Perro, Integer> perrosParticipantes;
    private TextField txtNombre;
    private TextField txtRaza;
    private TextField txtPuntos;
    private ImageView imgView;
    String filename = "src/datos/perros.txt";
    ArrayList<Perro> perros;

    private Button btnRegistrar;

    public PaneOrganizer() {
        //cargar los datos de los perros
        cargarParticipantes();
        createContent();
    }

    public BorderPane getRoot() {
        return root;
    }

    private void createContent() {

        root = new BorderPane();
        crearContenidoTop();
        crearContenidoIzquierdo();
        crearContenidoCentral();

    }

    private void crearContenidoTop() {
        HBox cont = new HBox();
        Label titulo = new Label("Exposición canina");
        titulo.setId("titulo");
        cont.getChildren().add(titulo);
        cont.setAlignment(Pos.CENTER);
        root.setTop(cont);//agregar al borderpane en top

    }

    private void crearContenidoIzquierdo() {
        VBox cont = new VBox();
        Label titulo = new Label("Perros participantes");
        lvPerros = new ListView();
        for (Perro perro : perrosParticipantes.keySet()) {
            lvPerros.getItems().add(perro);

        }

        lvPerros.setOnMouseClicked(e -> mostrarInfoPerro());//cuando se da click en un perro de la lista se muestra su info
        cont.getChildren().addAll(titulo, lvPerros);
        cont.setAlignment(Pos.CENTER);
        root.setLeft(cont);//agregar al borderpane en left
        root.setMargin(cont, new Insets(10));

    }

    private void crearContenidoCentral() {
        VBox cont = new VBox(10);
        Label titulo = new Label("Información del perro");
        imgView = new ImageView();
        FileInputStream input = null;
        //cuando se carga la aplicacion la figura por defecto es en blanco
        try {
            input = new FileInputStream("img/blank-img.jpg");
            imgView = new ImageView(new Image(input));
        } catch (FileNotFoundException ex) {
            System.out.println("Archivo no encontrado " + ex.getMessage());
        } finally {
            try {
                input.close();
            } catch (IOException ex) {
                System.out.println("Error al cerrar:" + ex.getMessage());
            }
        }
        GridPane gridpane = new GridPane();
        gridpane.add(new Label("Nombre:"), 0, 0);
        txtNombre = new TextField();
        gridpane.add(txtNombre, 1, 0);
        gridpane.add(new Label("Raza:"), 2, 0);
        txtRaza = new TextField();
        gridpane.add(txtRaza, 3, 0);
        gridpane.add(new Label("Puntos:"), 0, 1);
        txtPuntos = new TextField();
        gridpane.add(txtPuntos, 1, 1);
        btnRegistrar = new Button("Registrar puntaje");
        btnRegistrar.setDisable(true);
        gridpane.add(btnRegistrar, 3, 2, 4, 1);
        cont.getChildren().addAll(titulo, imgView, gridpane);
        cont.setAlignment(Pos.CENTER);
        root.setCenter(cont);

    }

    private void cargarParticipantes() {
        //crear el objeto para la coleccion de perros participantes
        perrosParticipantes = new TreeMap<>();
        perros= new ArrayList<>();
        //cargar los datos de  src/datos/perros.txt al mapa
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename))));
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] data = linea.split(",");
                if (data.length > 1) {
                    Perro perro = new Perro(data[0], data[1], data[2]);
                    perros.add(perro);
                    if ((perrosParticipantes.get(perro)) == null) {
                        perro.setPoints(Integer.valueOf(data[3]));
                        perrosParticipantes.put(perro, Integer.valueOf(data[3]));
                    } else {
                        perrosParticipantes.put(perro, perro.getPoints());
                    }
                }
                br.close();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PaneOrganizer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PaneOrganizer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void mostrarInfoPerro() {
        //recuperar el perro seleccionado
        root.setBottom(new VBox());
        Perro perro = (Perro) lvPerros.getSelectionModel().getSelectedItem();
        VBox cont = new VBox(10);
        Label titulo = new Label("Información del perro");

        //actualizar imagen
        FileInputStream input = null;
        try {
            input = new FileInputStream(perro.getImagen());
            imgView = new ImageView(new Image(input));
        } catch (FileNotFoundException ex) {
            System.out.println("Archivo no encontrado " + ex.getMessage());
        } finally {
            try {
                input.close();
            } catch (IOException ex) {
                System.out.println("Error al cerrar:" + ex.getMessage());
            }
        }

        //recuperar atributos y ubicarlos en las cajas de texto
        GridPane gridpane = new GridPane();
        gridpane.add(new Label("Nombre:"), 0, 0);
        txtNombre.setText(perro.getNombre());
        gridpane.add(txtNombre, 1, 0);
        gridpane.add(new Label("Raza:"), 2, 0);
        txtRaza.setText(perro.getRaza());
        gridpane.add(txtRaza, 3, 0);
        gridpane.add(new Label("Puntos:"), 0, 1);
        txtPuntos.setText(String.valueOf(perro.getPoints()));
        gridpane.add(txtPuntos, 1, 1);
        btnRegistrar = new Button("Registrar puntaje");
        gridpane.add(btnRegistrar, 3, 2, 4, 1);

        cont.getChildren().addAll(titulo, imgView, gridpane);
        cont.setAlignment(Pos.CENTER);
        root.setCenter(cont);

        //programar accion de boton btnRegistrar
        btnRegistrar.setDisable(false);
        btnRegistrar.setOnAction(e -> {
            crearContenidoInferior(perro);
        });

    }

    private void crearContenidoInferior(Perro perro) {
        btnRegistrar.setDisable(true);
        VBox cont = new VBox(10);
        HBox cont1 = new HBox(10);
        Button btnAsignar = new Button("Asignar");
        Label lbl = new Label("Asignar puntos a perro");
        Label lbl2 = new Label("Puntos: ");
        TextField txtPoints = new TextField();
        cont1.getChildren().addAll(lbl2, txtPoints);
        cont.getChildren().addAll(lbl, cont1, btnAsignar);
        root.setBottom(cont);
        cont.setAlignment(Pos.CENTER);
        cont1.setAlignment(Pos.CENTER);
        root.setMargin(cont, new Insets(10));
        btnAsignar.setOnAction(e -> {
            try {
                Integer puntos = perrosParticipantes.get(perro);
                puntos += Integer.valueOf(txtPoints.getText());
                perro.setPoints(puntos);
                perrosParticipantes.put(perro, perro.getPoints());
                txtPuntos.setText(String.valueOf(perrosParticipantes.get(perro)));
            } catch (NumberFormatException ex) {
                System.out.println(ex.getMessage());
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Informacion");
                alert.setHeaderText("Mensaje de accion");
                alert.setContentText("Valor debe ser numero");

                alert.showAndWait();
            }
            actualizarArchivoPersonas();
        });

    }

    public void actualizarArchivoPersonas() {
        BufferedWriter bw;
        try {
            bw= new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename)));
            for (Perro p: perros) {
                String linea = p.getNombre() + "," + p.getRaza() + "," + p.getImagen() + "," + String.valueOf(perrosParticipantes.get(p))+"\n";
                bw.write(linea);
            }
            bw.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }
    
}
