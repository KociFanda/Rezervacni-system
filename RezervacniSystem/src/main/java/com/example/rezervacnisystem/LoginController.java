package com.example.rezervacnisystem;

import com.example.rezervacnisystem.utills.CreateWindow;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;


public class LoginController {
    public TextField username;
    public PasswordField password;
    public Label fail;
    Object object;
    public void initialize() throws IOException, SQLException {
        com.example.rezervacnisystem.utills.Save.save("NoName");
    }
    public void login(ActionEvent event) throws IOException {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("username.txt"));
            String checkIfNull = br.readLine();
            if (checkIfNull == null) {
                System.out.println("špatné údaje");
                fail.setVisible(true);}
            else {
                if (password.getText().equals("123")) {
                    System.out.println("Jste BFU");
                    com.example.rezervacnisystem.utills.Save.save(username.getText());
                    CreateWindow.Create(event, "Program.fxml",
                            850, 610,"Rezervace a program" , true);

                } else if (username.getText().equals("admin") && password.getText().equals("admin")) {
                    System.out.println("Jste admin");
                    com.example.rezervacnisystem.utills.Save.save(username.getText());
                    CreateWindow.Create(event, "ProgramAdmin.fxml",
                            850, 610,"Nastavení programu", true);

                } else {
                    System.out.println("špatné údaje");
                    fail.setVisible(true);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        br.close();
    }

    public void end(ActionEvent actionEvent) {
        System.out.println("Ending the application :]");
        Platform.exit();
    }

}

