package com.example.rezervacnisystem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {
    public static List<Reservation> getReservations() throws SQLException, IOException {
        BufferedReader br;
            br = new BufferedReader(new FileReader("username.txt"));
            String theNameInTheFile = br.readLine(); //Zjisti aktualniho uzivatele

        Connection connection = DriverManager.getConnection
                ("jdbc:mysql://localhost/programovani", "root", "");

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM rezervace WHERE jmeno=?");
        preparedStatement.setString(1, theNameInTheFile);
        ResultSet resultSet = preparedStatement.executeQuery();//Pozada o rezervace se jmenem momentalniho uzivatele

        List<Reservation> reservations = new ArrayList<>();
        while(resultSet.next()){
            Reservation reservation = new Reservation();
            reservation.setMesto(resultSet.getString("mesto"));
            reservation.setNazev(resultSet.getString("nazev"));
            reservation.setJmeno(resultSet.getString("jmeno"));
            reservations.add(reservation);

        }
        return reservations;
    }
}
