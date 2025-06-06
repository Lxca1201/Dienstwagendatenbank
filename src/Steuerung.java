import OOP.Datenbank;
import OOP.Dienstwagen;
import OOP.Fahrer;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Steuerung {

    public static void main(String[] args) {

        //Datenbank als neues Objekt mit Path erstellen
        Datenbank db = new Datenbank("C:\\Users\\lucag\\Documents\\DHBW\\2. Semester\\Programmieren 2\\Dienstwagendatenbank\\Dienstwagendatenbank\\src\\dienstwagenprojekt2025.db.txt");
        db.dateiEinlesen();

        //Überprüfe, ob die Argumentlänge passt
        if(args.length != 1) {
            System.out.println("Bitte gib einen korrekten Befehl ein!");
            System.exit(0);
        }

        String befehl = args[0];

        //Argumente überprüfen
        if(befehl.startsWith("--fahrersuche")) {
            String parameter = befehl.substring("--fahrersuche=".length());
            ArrayList<Fahrer> gesuchteFahrer = db.sucheFahrer(parameter);
            db.printFahrer(gesuchteFahrer, parameter);
        } else if(befehl.startsWith("--fahrzeugsuche")) {
            String parameter = befehl.substring("--fahrzeugsuche=".length());
            ArrayList<Dienstwagen> gesuchteFahrzeuge = db.sucheFahrzeug(parameter);
            db.printDienstwagen(gesuchteFahrzeuge, parameter);
        } else if(befehl.startsWith("--geblitzt")) {
            String suchAnfrage = befehl.substring("--geblitzt=".length());
            String[] teile = suchAnfrage.split(";");
            if(teile.length == 2) {
                String kennzeichen = teile[0].trim();
                LocalDateTime zeitpunkt = null;
                try {
                    zeitpunkt = LocalDateTime.parse(teile[1].trim());
                } catch (DateTimeException e) {
                    System.out.println("Ungültiges Datumformat");
                }
                Fahrer fahrer = db.geblitzt(kennzeichen, zeitpunkt);
                db.printGeblitzt(fahrer);
                db.geblitzt(kennzeichen, zeitpunkt);
            } else {
                System.out.println("Ungültiges Eingabe Format!");
            }
        } else if(befehl.startsWith("--liegenlassen")) {
            String suchAnfrage = befehl.substring("--liegenlassen=".length());
            String[] teile = suchAnfrage.split(";");
            if(teile.length == 2) {
                String fahrerID = teile[0].trim();
                LocalDate zeitpunkt = null;
                try {
                    zeitpunkt = LocalDate.parse(teile[1].trim());
                } catch (DateTimeException e) {
                    System.out.println("Ungültiges Datumformat");
                }
                //Hier soll alles passieren!
                ArrayList<Dienstwagen> alleAutos = db.getGefahreneAutos(db.getFahrerMitID(fahrerID), zeitpunkt);
                HashMap<Fahrer, String> alleFahrer = db.getAlleFahrerVonAutos(fahrerID, alleAutos, zeitpunkt);
                db.printAlleFahrer(alleFahrer);
            } else {
                System.out.println("Ungültiges Eingabe Format!");
            }
        } else {
            System.out.println("Ungültiger Befehl!");
            System.exit(0);
        }

    }

}