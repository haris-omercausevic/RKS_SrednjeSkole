package ba.fit.srednjeskole.model;

import java.io.Serializable;

public class ObavijestVM implements Serializable {
    public int ObavijestId;
    public String Naslov;
    public String Tekst;
    public String Datum;
    public int KorisnikId;
    public String Objavio;


    public ObavijestVM(int obavijestId, String naslov, String tekst, String datum, int korisnikId, String objavio) {
        ObavijestId = obavijestId;
        Naslov = naslov;
        Tekst = tekst;
        Datum = datum;
        KorisnikId = korisnikId;
        Objavio = objavio;
    }

    public String getImePrezimeDatumObjave() {
        return Objavio + " - " + Datum;
    }

    public int getObavijestId() {
        return ObavijestId;
    }

    public void setObavijestId(int obavijestId) {
        ObavijestId = obavijestId;
    }

    public String getNaslov() {
        return Naslov;
    }

    public void setNaslov(String naslov) {
        Naslov = naslov;
    }

    public String getTekst() {
        return Tekst;
    }

    public void setTekst(String tekst) {
        Tekst = tekst;
    }

    public String getDatum() {
        return Datum;
    }

    public void setDatum(String datum) {
        Datum = datum;
    }

    public int getKorisnikId() {
        return KorisnikId;
    }

    public void setKorisnikId(int korisnikId) {
        KorisnikId = korisnikId;
    }

    public String getObjavio() {
        return Objavio;
    }

    public void setObjavio(String objavio) {
        Objavio = objavio;
    }
}
