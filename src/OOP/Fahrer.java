package OOP;

public class Fahrer {

    private String id;
    private String vorname;
    private String nachname;
    private String fuererscheinklasse;

    public Fahrer(String id, String vorname, String nachname, String fuererscheinklasse) {
        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
        this.fuererscheinklasse = fuererscheinklasse;
    }

    public String getId() {
        return id;
    }

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public String getFuererscheinklasse() {
        return fuererscheinklasse;
    }
}
