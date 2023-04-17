package com.example.rezervacnisystem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmyDAO {
    public static List<Filmy> getFilmy() throws SQLException {
        Connection connection = DriverManager.getConnection
                ("jdbc:mysql://localhost/programovani", "root", "");
        List<Filmy> filmy = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM filmy");
        while(resultSet.next()){
            Filmy film = new Filmy();
            film.setId(resultSet.getInt("id"));
            film.setMesto(resultSet.getString("mesto"));
            film.setNazev(resultSet.getString("nazev"));
            filmy.add(film);

        }
        return filmy;
    }
}
