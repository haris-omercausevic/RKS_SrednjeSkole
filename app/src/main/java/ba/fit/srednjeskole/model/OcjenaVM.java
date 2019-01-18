package ba.fit.srednjeskole.model;

public class OcjenaVM {
    private String predmet;
    private String prosjek;
    private String ocjene;

    public OcjenaVM(String predmet, String prosjek, String ocjene) {
        this.predmet = predmet;
        this.prosjek = prosjek;
        this.ocjene = ocjene;
    }

    public String getPredmet() {
        return predmet;
    }

    public void setPredmet(String predmet) {
        this.predmet = predmet;
    }

    public String getProsjek() {
        return prosjek;
    }

    public void setProsjek(String prosjek) {
        this.prosjek = prosjek;
    }

    public String getOcjene() {
        return ocjene;
    }

    public void setOcjene(String ocjene) {
        this.ocjene = ocjene;
    }
}


