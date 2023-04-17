package com.example.rezervacnisystem.utills;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class Save {

    public static void save(String name){
        try {
            String kontrola;
            FileWriter myWriter = new FileWriter("username.txt");
            BufferedReader br = new BufferedReader((new FileReader("username.txt")));
            myWriter.write(name);
            myWriter.close();
            kontrola = br.readLine();
            System.out.println("Zapisuji: " + kontrola);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
