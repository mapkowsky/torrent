package torrent;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Klasa pakietu tworzona na podstawie odserializowanego pakietu
 * @author mlewandowski
 */
public class Pakiet implements Serializable{
    
    public static final String typ_lista_plikow = "lista-plikow";
    public static final String typ_wiadomosc = "wiadomosc";
    public static final String typ_pull = "pull";
    public static final String typ_plik_in = "plik_in";
    public static final String typ_plik_out = "plik_out";
    public static final String typ_plik_end = "plik_end";
    
    public static final String wiadomosc_zapodaj_liste_plikow = "zapodaj_liste_plikow";
    public static final String wiadomosc_ok_czekam = "ok_czekam";
    public static final String wiadomosc_dobranoc = "dobranoc";
    public static final String wiadomosc_mam_caly_plik = "mam_caly_plik";
    
    
    
    
    private String typ;
    
    private String ipNadawcy;
    
    private ArrayList<Plik> listaPlikow;
    
    private String wiadomosc;
    
    private String plikDoPobrania;
    
    private int ktoryKawalek = 0;
    
    private int dlugoscKawalka;
    
    private byte[] kawalekPliku;
    
    private String sciaganyPlik;
    
    private String wysylanyPlik;
    
    private String sh1;
    
    public Pakiet(){
        this.setWiadomosc(Pakiet.wiadomosc_ok_czekam);
    }
    
    public Pakiet(ArrayList<Plik> listaPlikow){
        this.setListaPlikow(listaPlikow);
    }
    
    public Pakiet(String wiadomosc){
        this.setWiadomosc(wiadomosc);
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public ArrayList<Plik> getListaPlikow() {
        return listaPlikow;
    }

    public void setListaPlikow(ArrayList<Plik> listaPlikow) {
        this.typ = Pakiet.typ_lista_plikow;
        this.listaPlikow = listaPlikow;
    }

    public String getIpNadawcy() {
        return ipNadawcy;
    }

    public void setIpNadawcy(String ipNadawcy) {
        this.ipNadawcy = ipNadawcy;
    }
    
    public void setWiadomosc(String wiadomosc){
        this.typ = Pakiet.typ_wiadomosc;
        this.wiadomosc = wiadomosc;
    }

    public String getWiadomosc() {
        return wiadomosc;
    }

    public String getPlikDoPobrania() {
        return plikDoPobrania;
    }

    public void setPlikDoPobrania(String plikDoPobrania) {
        this.typ = Pakiet.typ_pull;
        this.plikDoPobrania = plikDoPobrania;
    }

    public void setKawalekPliku(byte[] kawalekPliku) {
        this.typ = Pakiet.typ_plik_out;
        this.kawalekPliku = kawalekPliku;
    }

    public byte[] getKawalekPliku() {
        return kawalekPliku;
    }

    public String getSciaganyPlik() {
        return sciaganyPlik;
    }

    public void setSciaganyPlik(String sciaganyPlik) {
        this.typ = Pakiet.typ_plik_in;
        this.sciaganyPlik = sciaganyPlik;
    }

    public String getWysylanyPlik() {
        return wysylanyPlik;
    }

    public void setWysylanyPlik(String wysylanyPlik) {
        this.wysylanyPlik = wysylanyPlik;
    }

    public int getKtoryKawalek() {
        return ktoryKawalek;
    }

    public void setKtoryKawalek(int ktoryKawalek) {
        this.ktoryKawalek = ktoryKawalek;
    }

    public int getDlugoscKawalka() {
        return dlugoscKawalka;
    }

    public void setDlugoscKawalka(int dlugoscKawalka) {
        this.dlugoscKawalka = dlugoscKawalka;
    }

    public String getSh1() {
        return sh1;
    }

    public void setSh1(String sh1) {
        this.sh1 = sh1;
    }
    
}
