package OOP;

import java.time.LocalDateTime;

public class Fahrt {

    private String fahrerId;
    private String fahrzeugId;
    private int startKm;
    private int endKm;
    private LocalDateTime startzeit;
    private LocalDateTime endzeit;

    public Fahrt(String fahrerId, String fahrzeugId, int startKm, int endKm, LocalDateTime startzeit, LocalDateTime endzeit) {
        this.fahrerId = fahrerId;
        this.fahrzeugId = fahrzeugId;
        this.startKm = startKm;
        this.endKm = endKm;
        this.startzeit = startzeit;
        this.endzeit = endzeit;
    }

    public String getFahrerId() {
        return fahrerId;
    }

    public String getFahrzeugId() {
        return fahrzeugId;
    }

    public int getStartKm() {
        return startKm;
    }

    public int getEndKm() {
        return endKm;
    }

    public LocalDateTime getStartzeit() {
        return startzeit;
    }

    public LocalDateTime getEndzeit() {
        return endzeit;
    }
}
