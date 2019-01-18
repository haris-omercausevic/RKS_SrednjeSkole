package ba.fit.srednjeskole.model;

import java.io.Serializable;

public class MaterijalVM implements Serializable {
    private int MaterijalId;
    private String Naziv;
    private String Url;
    private String BlobName;
    private String Datum;
    private int BrojOcjena;
    private double Rating;
    private int PredmetId;
    private int NastavnikId;
    private String nastavnik;
    private String detalji;
    private String Predmet;
    private int Razred;


    //Get & Set
    public int getMaterijalId() {
        return MaterijalId;
    }

    public void setMaterijalId(int materijalId) {
        MaterijalId = materijalId;
    }

    public String getNaziv() {
        return Naziv;
    }

    public void setNaziv(String naziv) {
        Naziv = naziv;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getBlobName() {
        return BlobName;
    }

    public void setBlobName(String blobName) {
        BlobName = blobName;
    }

    public String getDatum() {
        return Datum;
    }

    public void setDatum(String datum) {
        Datum = datum;
    }

    public int getBrojOcjena() {
        return BrojOcjena;
    }

    public void setBrojOcjena(int brojOcjena) {
        BrojOcjena = brojOcjena;
    }

    public double getRating() {
        return Rating;
    }

    public void setRating(double rating) {
        Rating = rating;
    }

    public int getPredmetId() {
        return PredmetId;
    }

    public void setPredmetId(int predmetId) {
        PredmetId = predmetId;
    }

    public int getNastavnikId() {
        return NastavnikId;
    }

    public void setNastavnikId(int nastavnikId) {
        NastavnikId = nastavnikId;
    }

    public String getNastavnik() {
        return nastavnik;
    }

    public void setNastavnik(String nastavnik) {
        this.nastavnik = nastavnik;
    }

    public String getDetalji() {
        return detalji;
    }

    public void setDetalji(String detalji) {
        this.detalji = detalji;
    }

    public String getPredmet() {
        return Predmet;
    }

    public void setPredmet(String predmet) {
        Predmet = predmet;
    }

    public int getRazred() {
        return Razred;
    }

    public void setRazred(int razred) {
        Razred = razred;
    }
}


