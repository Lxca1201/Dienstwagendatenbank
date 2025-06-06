package OOP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Datenbank {

    //Listen in denen die ausgelesenen Objekte gespeichert werden
    public ArrayList<Fahrer> fahrerListe = new ArrayList<>();
    public ArrayList<Dienstwagen> fahrzeugListe = new ArrayList<>();
    public ArrayList<Fahrt> fahrtenBuch = new ArrayList<>();

    private String path;

    public Datenbank(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public ArrayList<Fahrer> getFahrerListe() {
        return fahrerListe;
    }

    public ArrayList<Dienstwagen> getFahrzeugListe() {
        return fahrzeugListe;
    }

    public ArrayList<Fahrt> getFahrtenBuch() {
        return fahrtenBuch;
    }

    //Methode zum Einlesen der Datenbank (Datei)
    public void dateiEinlesen() {
        File file = new File(getPath());

        //Existiert die File?
        if(!file.isFile()) {
            System.out.println("Fehler beim Laden der Datei!");
            System.exit(0);
        }

        String zeile = null;
        String entity_type = "";
        try (BufferedReader br =
                     new BufferedReader(new FileReader(path))) {
            //Jede Zeile durchgehen
            while((zeile = br.readLine()) != null) {
                String[] teilString = zeile.split(",");
                //Entität Fahrer erkennen
                if(zeile.startsWith("New_Entity:fahrerId,vorname,nachname,fuehrerscheinklasse")) {
                    entity_type = "Fahrer";
                    continue;
                    //Entität Fahrzeug erkennen
                } else if(zeile.startsWith("New_Entity:fahrzeugId,hersteller,modell,kennzeichen")) {
                    entity_type = "Fahrzeug";
                    continue;
                    //Entität Fahrt erkennen
                } else if(zeile.startsWith("New_Entity:fahrerId,fahrzeugId,startKm,endKm,startzeit,endzeit")) {
                    entity_type = "Fahrt";
                    continue;
                }

                //Switch case um die verschiedenen Listen zu füllen und unnötige Zeichen zu entfernen
                switch (entity_type) {
                    case "Fahrer":
                        fahrerListe.add(new Fahrer(
                                teilString[0].trim(),
                                teilString[1].trim(),
                                teilString[2].trim(),
                                teilString[3].trim()
                        ));
                        break;
                    case "Fahrzeug":
                        fahrzeugListe.add(new Dienstwagen(
                                teilString[0].trim(),
                                teilString[1].trim(),
                                teilString[2].trim(),
                                teilString[3].trim()
                        ));
                        break;
                    case "Fahrt":
                        fahrtenBuch.add(new Fahrt(
                                teilString[0].trim(),
                                teilString[1].trim(),
                                Integer.parseInt(teilString[2].trim()),
                                Integer.parseInt(teilString[3].trim()),
                                LocalDateTime.parse(teilString[4].trim()),
                                LocalDateTime.parse(teilString[5].trim())
                        ));
                        break;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Hilfs-Methode
    public Fahrer getFahrerMitID(String fahrerID) {

        Fahrer fahrer = null;

        for (int i = 0; i < getFahrerListe().size(); i++) {

            if(getFahrerListe().get(i).getId().equals(fahrerID)) {
                fahrer = getFahrerListe().get(i);
            }

        }

        return fahrer;

    }

    //Hilfs-Methode
    public String getKennzeichenByID(String id) {

        String kennzeichen = "";

        for(int i = 0; i < getFahrzeugListe().size(); i++) {

            if(getFahrzeugListe().get(i).getId().equals(id)) {
                kennzeichen = getFahrzeugListe().get(i).getKennzeichen();
            }

        }

        return kennzeichen;

    }

    //Hilfs-Methode
    public Dienstwagen getFahrzeugMitKennzeichen(String kennzeichen) {

        Dienstwagen fahrzeug = null;

        for (int i = 0; i < getFahrzeugListe().size(); i++) {
            if(getFahrzeugListe().get(i).getKennzeichen().equalsIgnoreCase(kennzeichen)) {
                fahrzeug = getFahrzeugListe().get(i);
            }
        }

        return fahrzeug;

    }

    //Hilfs-Methode
    public Dienstwagen getFahrzeugMitID(String id) {
        Dienstwagen dienstwagen = null;

        for (int i = 0; i < getFahrzeugListe().size(); i++) {
            if(getFahrzeugListe().get(i).getId().equals(id)) {
                dienstwagen = getFahrzeugListe().get(i);
            }
        }
        return dienstwagen;
    }

    //Fahrer anhand des Nachnamens suchen
    public ArrayList<Fahrer> sucheFahrer(String nachname) {
        ArrayList<Fahrer> passendeFahrer = new ArrayList<>();

        //Liste der Fahrer durchgehen und Namen vergleichen
        for (int i = 0; i < fahrerListe.size(); i++) {
            if(fahrerListe.get(i).getNachname().contains(nachname)) {
                passendeFahrer.add(fahrerListe.get(i));
            }
        }

        return passendeFahrer;

    }

    //Methode zur Darstellung aller gefunden Fahrer (Nutzerfreundlich)
    public void printFahrer(ArrayList<Fahrer> fahrer, String suchbegriff) {

        System.out.println("----------| Fahrer mit der Info: " + suchbegriff + " |----------");

        for (int i = 0; i < fahrer.size(); i++) {
            System.out.println("FahrerID: " + fahrer.get(i).getId() + "\n" +
                    "Vorname: " + fahrer.get(i).getVorname() + "\n" +
                    "Nachname: " + fahrer.get(i).getNachname() + "\n" +
                    "Fuererscheinklasse " + fahrer.get(i).getFuererscheinklasse() + "\n" +
                    "---------------------------");
        }

    }

    //Fahrzeug anhand von Hersteller/Kennzeichen/Modell
    public ArrayList<Dienstwagen> sucheFahrzeug(String suchbegriff) {

        ArrayList<Dienstwagen> passendesFahrzeug = new ArrayList<>();

        for (int i = 0; i < fahrzeugListe.size(); i++) {

            if(fahrzeugListe.get(i).getHersteller().equalsIgnoreCase(suchbegriff) ||
            fahrzeugListe.get(i).getKennzeichen().equalsIgnoreCase(suchbegriff) ||
            fahrzeugListe.get(i).getModell().equalsIgnoreCase(suchbegriff)) {

                passendesFahrzeug.add(fahrzeugListe.get(i));

            }

        }

        return passendesFahrzeug;

    }

    //Methode zur Darstellung aller gefundenen Fahrzeugen (Nutzerfreundlich)
    public void printDienstwagen(ArrayList<Dienstwagen> waegen, String suchbegriff) {

        System.out.println("----------| Fahrzeug mit der Info: " + suchbegriff + " |----------");

        for (int i = 0; i < waegen.size(); i++) {
            System.out.println("FahrzeugID: " + waegen.get(i).getId() + "\n" +
                    "Hersteller: " + waegen.get(i).getHersteller() + "\n" +
                    "Modell: " + waegen.get(i).getModell() + "\n" +
                    "Kennzeichen " + waegen.get(i).getKennzeichen() + "\n" +
                    "---------------------------");
        }

    }

    //Feature 1: Fahrzeug wurde geblitzt und Fahrer muss gefunden werden!
    public Fahrer geblitzt(String kennzeichen, LocalDateTime zeitpunkt) {

        Dienstwagen fahrzeug = getFahrzeugMitKennzeichen(kennzeichen);
        Fahrer fahrer = null;

        //Fahrtenbuch durchgehen und Personen suchen, die mit dem Fahrzeug in diesem Zeitraum gefahren sind
        for(int i = 0; i < getFahrtenBuch().size(); i++) {

            if(getFahrtenBuch().get(i).getFahrzeugId().equals(fahrzeug.getId())) {
                if(!zeitpunkt.isBefore(getFahrtenBuch().get(i).getStartzeit()) &&
                        !zeitpunkt.isAfter(getFahrtenBuch().get(i).getEndzeit())) {
                    fahrer = getFahrerMitID(getFahrtenBuch().get(i).getFahrerId());
                }
            }

        }

        return fahrer;
    }

    //Methode zur Darstellung des geblitzten Fahrers (Benutzerfreundlichkeit)
    public void printGeblitzt(Fahrer fahrer) {

        if(fahrer == null) {
            System.out.println("Kein Fahrer gefunden!");
        } else {

            System.out.println(fahrer.getVorname() + " " + fahrer.getNachname());

        }
    }


    //Hilfs-Methode für Feature 2 um die gefahrenen Fahrzeuge eines Fahrers zu bestimmen
    public ArrayList<Dienstwagen> getGefahreneAutos(Fahrer fahrer, LocalDate tag) {

        ArrayList<Dienstwagen> gefahreneAutos = new ArrayList<>();

        for (int i = 0; i < getFahrtenBuch().size(); i++) {

            if(getFahrtenBuch().get(i).getFahrerId().equalsIgnoreCase(fahrer.getId())) {
                LocalDate startDatum = getFahrtenBuch().get(i).getStartzeit().toLocalDate();
                LocalDate endDatum = getFahrtenBuch().get(i).getEndzeit().toLocalDate();
                if(!tag.isBefore(startDatum) && !tag.isAfter(endDatum)) {
                    gefahreneAutos.add(getFahrzeugMitID(getFahrtenBuch().get(i).getFahrzeugId()));
                }
            }

        }

        return gefahreneAutos;
    }

    //Feature 2: Fahrer hat etwas im Fahrzeug liegen lassen und sucht andere Fahrer
    public HashMap<Fahrer, String> getAlleFahrerVonAutos(String fahrerID, ArrayList<Dienstwagen> autos, LocalDate tag) {

        //HashMap zur Zuordnung von Fahrern und Kennzeichen
        HashMap<Fahrer, String> alleFahrer = new HashMap<>();

        for (int i = 0; i < getFahrtenBuch().size(); i++) {

            //Start -und Endzeitpunkt aller Fahrten im Fahrzeugbuch
            LocalDate startDatum = getFahrtenBuch().get(i).getStartzeit().toLocalDate();
            LocalDate endDatum = getFahrtenBuch().get(i).getEndzeit().toLocalDate();

            //Für jedes Datum jeder Fahrt jedes Auto durchgehen
            for (int j = 0; j < autos.size(); j++) {

                if(getFahrtenBuch().get(i).getFahrzeugId().equalsIgnoreCase(autos.get(j).getId())) {
                    if(!tag.isBefore(startDatum) && !tag.isAfter(endDatum)) {
                        if(!getFahrtenBuch().get(i).getFahrerId().equalsIgnoreCase(fahrerID)) {
                            //HashMap mit Fahrer und dazugehörigem Kennzeichen speichern
                            alleFahrer.put(getFahrerMitID(getFahrtenBuch().get(i).getFahrerId()), getFahrzeugMitID(getFahrtenBuch().get(i).getFahrzeugId()).getKennzeichen());
                        }
                    }
                }

            }

        }

        return alleFahrer;

    }

    //Methode zur Darstellung aller gefundenen Fahrer + Kennzeichen (Benutzerfreundlichkeit)
    public void printAlleFahrer(HashMap<Fahrer, String> alleFahrer) {

        System.out.println("Die Fahrer sind:");

        ArrayList<Fahrer> fahrerListe = new ArrayList<>(alleFahrer.keySet());

        for (int i = 0; i < fahrerListe.size(); i++) {
            Fahrer fahrer = fahrerListe.get(i);
            String kennzeichen = alleFahrer.get(fahrer);
            System.out.println(i + 1 + ". " + fahrer.getVorname() + " " + fahrer.getNachname() + " (" + kennzeichen + ")");
        }

    }

}
