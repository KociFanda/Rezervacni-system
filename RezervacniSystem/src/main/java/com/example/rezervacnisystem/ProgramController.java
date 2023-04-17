package com.example.rezervacnisystem;

import com.example.rezervacnisystem.utills.CreateWindow;
import com.example.rezervacnisystem.utills.Save;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.sql.*;
import java.util.List;



public class ProgramController {
    public Label username;
    public TextField userSelection;

    public TableView existingMovies;
    public TableColumn existingID;
    public TableColumn existingCity;
    public TableColumn existingMovie;



    public TableView userReservations;
    public TableColumn reservationCity;
    public TableColumn reservationMovie;

    String theNameInTheFile;
    List<Filmy> filmyList;
    List<Reservation> reservationList;


    public void initialize() throws IOException, SQLException {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("username.txt"));
             theNameInTheFile = br.readLine();
            username.setText("Uzivatel:" + theNameInTheFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        br.close();

        //sql part

        //Tvorba tabulek
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


        //Výpis dostupných filmů
        existingID.setCellValueFactory(new PropertyValueFactory<>("id"));
        existingCity.setCellValueFactory(new PropertyValueFactory<>("mesto"));
        existingMovie.setCellValueFactory(new PropertyValueFactory<>("nazev"));
        filmyList = FilmyDAO.getFilmy();
        for (Filmy filmy: filmyList) {
            existingMovies.getItems().addAll(filmy);
        }

        //Výpis rezervací do tabulky
        reservationCity.setCellValueFactory(new PropertyValueFactory<>("mesto"));
        reservationMovie.setCellValueFactory(new PropertyValueFactory<>("nazev"));
        reservationList  = ReservationDAO.getReservations();
        for (Reservation reservation: reservationList) {
            userReservations.getItems().addAll(reservation);
        }

    }

    public void back(ActionEvent event) throws IOException {
        Save.save("");
        CreateWindow.Create(event, "Login.fxml", 850, 610,"Přihlášení" , true );
    }

    public void makeReservation(ActionEvent event) throws SQLException, IOException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/programovani", "root", "");
        int selectionButInteger;
        String theSelectionOfTheUser =userSelection.getText();
        selectionButInteger =  Integer.valueOf(theSelectionOfTheUser);
        List<Filmy> filmyList;
        filmyList = FilmyDAO.getFilmy();//Stáhne z databáze položky
        for (Filmy filmy: filmyList) {      //projde objekty "filmy" v Listu "filmylist"
            //U každého objektu v listu si getterem řekne o "id" a zeptá se jestli je stejný jako výběr uživatele
            if(selectionButInteger == filmy.getId()){
                System.out.println("Našel jsem v Listu výběr uživatele");
                System.out.println("Mesto a nazev filmu u tohoto ID je:" + filmy.getMesto() +", " + filmy.getNazev());
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO rezervace (jmeno, mesto, nazev) VALUES (? , ? , ?);");
                preparedStatement.setString(1,theNameInTheFile );
                preparedStatement.setString(2, filmy.getMesto());
                preparedStatement.setString(3, filmy.getNazev());
                preparedStatement.execute();
            } else{}
        }
        //Po vytvoření rezervace jí hned zobrazí v tabulce
        reservationList = ReservationDAO.getReservations();
        userReservations.getItems().clear();
        for (Reservation reservation : reservationList) {
                userReservations.getItems().addAll(reservation); //Doplní do tableview jen to co si uživatel teď zakoupil
        }

    }
}
