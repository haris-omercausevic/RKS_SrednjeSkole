package ba.fit.srednjeskole.model;

import android.os.Parcel;

import java.io.Serializable;
import java.util.List;

public class UIKorisnik {
    public int KorisnikId;
    public String Ime;
    public String Prezime;
    public String KorisnickoIme;
    public List<String> razredi;//ID-ovi razreda
    public List<String> razrediBrojcano; //razredi brojcano
    public String Slika;
    public String Email;
    public String Aktivan;
    public String AuthToken;
}
