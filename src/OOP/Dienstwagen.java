package OOP;

public class Dienstwagen {

    private String id;
    private String hersteller;
    private String modell;
    private String kennzeichen;

    public Dienstwagen(String id, String hersteller, String modeell, String kennzeichen) {
        this.id = id;
        this.hersteller = hersteller;
        this.modell = modeell;
        this.kennzeichen = kennzeichen;
    }

    public String getId() {
        return id;
    }

    public String getHersteller() {
        return hersteller;
    }

    public String getModell() {
        return modell;
    }

    public String getKennzeichen() {
        return kennzeichen;
    }
}