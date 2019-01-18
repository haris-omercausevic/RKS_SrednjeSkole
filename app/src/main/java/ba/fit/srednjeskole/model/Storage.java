package ba.fit.srednjeskole.model;

import java.util.ArrayList;
import java.util.List;

import ba.fit.srednjeskole.helper.MyObjects;

public class Storage {
    private static List<OpstinaVM> opstine;
    private static int brojacPosiljki;

    public static List<OpstinaVM> getOpstine(){

        if(opstine == null){
            opstine = new ArrayList<>();
            opstine.add(new OpstinaVM(1,"Mostar","BiH"));
            opstine.add(new OpstinaVM(2,"Sarajevo","BiH"));
            opstine.add(new OpstinaVM(3,"Zenica","BiH"));
            opstine.add(new OpstinaVM(4,"Banja Luka","BiH"));
            opstine.add(new OpstinaVM(5,"Zagreb","Hrvatska"));
            opstine.add(new OpstinaVM(6,"Tuzla","BiH"));
            opstine.add(new OpstinaVM(7,"Gorazde","BiH"));
            opstine.add(new OpstinaVM(8,"Konjic","BiH"));
        }

        return opstine;
    }

    private static List<UIKorisnik> korisnici;
//    public static List<UIKorisnik> getKorisnici(){
//        if(korisnici == null){
//            korisnici = new ArrayList<>();
//            korisnici.add(new UIKorisnik(1,"Emina","Obradovic","Hemija - Nadoknada nastave", "14.09.2018", "emina","Mostar2018"));
//            korisnici.add(new UIKorisnik(2,"Adil","Joldic","Neradni dan na fakultetu", "12.09.2018", "adil","Mostar2018"));
//            korisnici.add(new UIKorisnik(3,"Larisa","Dedovic","Obavještenje za ponovce prve (I) godine", "12.09.2018", "larisa","Mostar2018"));
//            korisnici.add(new UIKorisnik(4,"Elmin","Sudic","ADS :: Uvid u radove", "06.09.2018", "elmin","Mostar2018"));
//            korisnici.add(new UIKorisnik(5,"Marija","Herceg","RS II :: Četvrti rok za predaju seminarskih radova", "06.09.2018", "marija","Mostar2018"));
//            korisnici.add(new UIKorisnik(6,"Fuad","Dedic","BI :: Predaja seminarskih radova", "05.09.2018", "fuad","Mostar2018"));
//        }
//
//        return korisnici;
//    }

//    public static UIKorisnik LoginCheck(String username, String password){
//
//        for (UIKorisnik x: getKorisnici()){
//            if(MyObjects.equals(x.getUsername(),username) && MyObjects.equals(x.getPassword(), password))
//                return x;
//        }
//
//        return null;
//    }

//    private static List<MaterijalVM> materijali;
//    public static List<MaterijalVM> getMaterijali(){
//        if(materijali == null){
//            materijali = new ArrayList<>();
//            materijali.add(new MaterijalVM("matematika-1-matic-kekic.pdf","doc.dr Denis Music", "4.00","06.09.2018",2));
//            materijali.add(new MaterijalVM("Lekcije_iz_Matematike1.pdf","doc.dr Denis Music", "4.00","06.09.2018",1));
//            materijali.add(new MaterijalVM("zbirka-za-prijemni-matematika.pdf","doc.dr Denis Music", "3.00","5.09.2018",1));
//
//            materijali.add(new MaterijalVM("Rjesenje ispita.txt","doc.dr Denis Music", "4.9","07.09.2018",15));
//            materijali.add(new MaterijalVM("Priprema za ispit - PRII","doc.dr Denis Music", "4.2","06.09.2018",20));
//        }
//
//        return materijali;
//    }

//    private static List<MaterijalVM> preporuka;
//    public static List<MaterijalVM> getPreporuka(){
//        if(preporuka == null){
//            preporuka = new ArrayList<>();
//            preporuka.add(new MaterijalVM("Rjesenje ispita.txt","doc.dr Denis Music", "4.9","07.09.2018",15));
//            preporuka.add(new MaterijalVM("[SP] Priprema za ispit","doc.dr Denis Music", "4.2","06.09.2018",25));
//            preporuka.add(new MaterijalVM("Fizika - Priprema za ispit", "mr.sc Adil Joldic","4.85","18.09.2018", 7));
//        }
//
//        return preporuka;
//    }

    private static List<PosiljkaVM> posiljke;
//    public static List<PosiljkaVM> getPosiljke(){
//        if(posiljke == null){
//            posiljke = new ArrayList<>();
//            posiljke.add(new PosiljkaVM(getKorisnici().get(0), 15,"Pazi, lomljivo",5));
//            posiljke.add(new PosiljkaVM(getKorisnici().get(2), 15,"",5));
//            posiljke.add(new PosiljkaVM(getKorisnici().get(3), 105,"Uspravno drzati",15));
//            posiljke.add(new PosiljkaVM(getKorisnici().get(0), 5,"",5));
//            posiljke.add(new PosiljkaVM(getKorisnici().get(2), 51,"",5));
//
//        }
//        return posiljke;
//    }

    private static List<OcjenaVM> ocjene;
    public static List<OcjenaVM> getOcjene() {
        if(ocjene == null){
            ocjene = new ArrayList<>();
            ocjene.add(new OcjenaVM("Fizika", "3.75", "4, 4, 2, 5"));
            ocjene.add(new OcjenaVM("Informatika", "4.00", "5, 4, 3"));
            ocjene.add(new OcjenaVM("Matematika I", "3.67", "4, 3, 4"));
            ocjene.add(new OcjenaVM("Matematika II", "3.33", "5, 3, 2"));
        }
        return ocjene;
    }

//    public static List<UIKorisnik> getKorisniciByIme(String query){
//        List<UIKorisnik> rezultat = new ArrayList<>();
//
//        for (UIKorisnik x: getKorisnici()){
//            if(x.getIme().startsWith(query))
//                rezultat.add(x);
//        }
//
//        return rezultat;
//    }

//    public static void addPosiljka(PosiljkaVM posiljkaVM){
//        posiljkaVM.brojPosiljke = brojacPosiljki++;
//        getPosiljke().add(posiljkaVM);
//    }

    public static void removePosiljka(PosiljkaVM posiljkaVM){
       if(posiljkaVM != null)
           posiljke.remove(posiljkaVM);
    }
}
