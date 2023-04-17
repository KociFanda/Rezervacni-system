package com.example.rezervacnisystem;

import com.example.rezervacnisystem.utills.CreateWindow;
import com.example.rezervacnisystem.utills.Save;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.List;

public class ProgramAdminController {
    public Label username;
    public TextField newCity;
    public TextField newMovie;
    public TextField newID;
    public TableView existingMovies;
    public TableColumn existingID;
    public TableColumn existingMovie;
    public TableColumn existingCity;
    public TextField removeID;
    Object object;
    List<Filmy> filmyList;


    public void initialize() throws IOException, SQLException {
        username.setText("Uzivatel:" + "ADMIN");

        //sql part

        //tvorba tabulek
        Connection connection = DriverManager.getConnection
                ("jdbc:mysql://localhost/programovani", "root", "");
        Statement stmt = connection.createStatement();
        String sql = "CREATE DATABASE IF NOT EXISTS PROGRAMOVANI";
        stmt.executeUpdate(sql);
        String sql1 = "CREATE TABLE IF NOT EXISTS FILMY " +
                "(id INTEGER not NULL, " +
                " mesto VARCHAR(255), " +
                " nazev VARCHAR(255), " +
                " PRIMARY KEY ( id ))";
        stmt.executeUpdate(sql1);
        String sql2 = "CREATE TABLE IF NOT EXISTS REZERVACE " +
                "(jmeno VARCHAR(255), " +
                " mesto VARCHAR(255), " +
                " nazev VARCHAR(255))";
        stmt.executeUpdate(sql2);

        //výpis dostupných filmů

        existingID.setCellValueFactory(new PropertyValueFactory<>("id"));
        existingCity.setCellValueFactory(new PropertyValueFactory<>("mesto"));
        existingMovie.setCellValueFactory(new PropertyValueFactory<>("nazev"));
        filmyList = FilmyDAO.getFilmy();
        for (Filmy filmy : filmyList) {
            existingMovies.getItems().addAll(filmy);
        }

    }

    public void back(ActionEvent event) throws IOException {
        Save.save("");
        CreateWindow.Create(event,"Login.fxml",
                850, 610,"Přihlášení" , true );
    }

    public void addMovie(ActionEvent event) throws SQLException {
        Connection connection = DriverManager.getConnection
                ("jdbc:mysql://localhost/programovani", "root", "");
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO filmy (id, mesto, nazev) VALUES (? , ? , ?);");
        preparedStatement.setInt(1, Integer.parseInt(newID.getText()));
        preparedStatement.setString(2, newCity.getText());
        preparedStatement.setString(3, newMovie.getText());
        preparedStatement.execute();

        filmyList = FilmyDAO.getFilmy();
        for (Filmy filmy : filmyList) {
            if (filmy.getId() == Integer.parseInt(newID.getText())) {
                existingMovies.getItems().addAll(filmy); //Doplní do tableview jen položku co admin ted přidal
            }

        }

    }

    public void remove(ActionEvent event) throws SQLException {
        Connection connection = DriverManager.getConnection
                ("jdbc:mysql://localhost/programovani", "root", "");
       int toBeRemovedID =  Integer.valueOf(removeID.getText());
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `filmy` WHERE id=?");
        preparedStatement.setInt(1, toBeRemovedID);
        preparedStatement.execute();

        existingMovies.getItems().clear(); //Smaže stávající položky z tabulky
        filmyList = FilmyDAO.getFilmy();
        for (Filmy filmy : filmyList) {
            existingMovies.getItems().addAll(filmy);//Vloží do tabulky všechny položky z databáze(nyní bez položky s id=toBeRemovedID)
        }

    }
}
