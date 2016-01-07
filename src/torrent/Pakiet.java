package torrent;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Klasa pakietu tworzona na podstawie odserializowanego pakietu
 * @author mlewandowski
 */
public class Pakiet implements Serializable, PakietListaPlikow{
    
    public static final String typ_lista_plikow = "lista-plikow";
    public static final String typ_wiadomosc = "wiadomosc";
    public static final String typ_pull = "pull";
    
    public static final String wiadomosc_zapodaj_liste_plikow = "zapodaj_liste_plikow";
    public static final String wiadomosc_ok_czekam = "ok_czekam";
    public static final String wiadomosc_dobranoc = "dobranoc";
    
    
    
    private String typ;
    
    private String ipNadawcy;
    
    private ArrayList<Plik> listaPlikow;
    
    private String wiadomosc;
    
    private String plikDoPobrania;
    
    private long byteRozpoczynajacy = 0;
    
    public Pakiet(){
        this.setWiadomosc(Pakiet.wiadomosc_dobranoc);
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

    public long getByteRozpoczynajacy() {
        return byteRozpoczynajacy;
    }
    
}
