package com.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import java.util.ArrayList;
import java.sql.ResultSet;


public class db {
    
    public Connection conn;
    public Statement stmt;

    public db() throws Exception {
        this.conn = DriverManager.getConnection("jdbc:sqlite:chords.db");
        this.stmt = this.conn.createStatement();
        this.stmt.execute("CREATE TABLE IF NOT EXISTS chords (id INTEGER PRIMARY KEY AUTOINCREMENT, chord TEXT NOT NULL)");

    }
    public void insertChord(String chord) throws Exception {
        this.stmt.execute("INSERT INTO chords (chord) VALUES ('" + chord + "')");
        System.out.println("Inserted chord: " + chord);
    }

    public ArrayList<String> getChords() throws Exception {
        this.stmt.execute("SELECT * FROM chords");
        ResultSet rs = this.stmt.getResultSet();
        ArrayList<String> chords = new ArrayList<>();
        while (rs.next()) {
            String chord = rs.getString("chord");
            chords.add(chord);
            System.out.println("Retrieved chord: " + chord);
        }
        return chords;
    }   

    public void deleteCertainChord(String chord) throws Exception {
        this.stmt.execute("DELETE FROM chords WHERE chord = '" + chord + "'");
    }   

    public static void main(String[] args) throws Exception {
        db database = new db();
        database.insertChord("C-E-G");
        database.getChords();
        database.deleteCertainChord("C-E-G");
    }
}
