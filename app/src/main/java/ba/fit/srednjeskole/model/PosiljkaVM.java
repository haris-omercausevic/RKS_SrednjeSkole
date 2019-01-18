package ba.fit.srednjeskole.model;

import java.io.Serializable;

public class PosiljkaVM implements Serializable {
    public UIKorisnik primaoc;
    public float masa;
    public String napomena;
    public int brojPosiljke;
    public float iznos;
    public boolean placaPouzecem;

    public PosiljkaVM(UIKorisnik primaoc, float masa, String napomena, float iznos) {
        this.primaoc = primaoc;
        this.masa = masa;
        this.napomena = napomena;
        this.iznos = iznos;
    }
    public PosiljkaVM(){

    }
}
