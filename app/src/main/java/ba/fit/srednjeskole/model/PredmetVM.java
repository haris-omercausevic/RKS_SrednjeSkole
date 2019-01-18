package ba.fit.srednjeskole.model;

public class PredmetVM {
    public int PredmetId;
    public String Naziv;
    public String Oznaka;
    public int Razred;


    @Override
    public String toString() {
        return Oznaka + " - " + Naziv;
    }
}
