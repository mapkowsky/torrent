package torrent;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * G³ówna klasa diluj±ca z pakietami. Taka fabryka na sterydach :)
 * 
 * @author mlewandowski
 */
public class HandlerPakietow {
    
    /**
     * Wrzucasz do ¶rodka obiekt pakietu i ta metoda sobie z nim handluje(deals with them he he he...)
     * @param pakiet
     * @return 
     */
    public Pakiet handluj(Pakiet pakiet){
        try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {
            Logger.getLogger(HandlerPakietow.class.getName()).log(Level.SEVERE, null, ex);
        }
        Torrent.fw.out("Przyszed³ pakiet od "+pakiet.getIpNadawcy()+" - "+pakiet.getTyp());
        switch(pakiet.getTyp()){
            case Pakiet.typ_lista_plikow:
                return this.handlujZLista(pakiet);
            case Pakiet.typ_pull:
                return this.handlujZPull(pakiet);
            case Pakiet.typ_wiadomosc:
                return this.handlujZWiadomoscia(pakiet);
            case Pakiet.typ_plik_in:
                return this.handlujZTransferPlikIn(pakiet);
            case Pakiet.typ_plik_end:
                return this.handlujZPlikEnd(pakiet);
            case Pakiet.typ_plik_out:
                return this.handlujZTransferPlikOut(pakiet);
        }
        return null;
    }
    
    private Pakiet handlujZWiadomoscia(Pakiet pakiet){
        Pakiet pakietZwrotny = null;
        Torrent.fw.out("Jest to wiadomo¶æ: "+pakiet.getWiadomosc());
        switch(pakiet.getWiadomosc()){
            case Pakiet.wiadomosc_zapodaj_liste_plikow:
                pakietZwrotny = new Pakiet(this.przygotujListePlikow());
                break;
            default:
                pakietZwrotny = new Pakiet(Pakiet.wiadomosc_dobranoc);
        }
        return pakietZwrotny;
    }
    
    private Pakiet handlujZLista(Pakiet pakiet){
        Torrent.fw.out("Jest to lista plikow rozmair: ("+pakiet.getListaPlikow().size()+")");
        Torrent.fw.appOut("Lista plików hosta "+pakiet.getIpNadawcy());
        Torrent.fw.lista(pakiet.getListaPlikow().toArray());
        
        return new Pakiet(Pakiet.wiadomosc_ok_czekam);
    }
    
    private Pakiet handlujZPull(Pakiet pakiet){
        Torrent.fw.out("Jest to pro¶ba pocz±tek pliku: "+pakiet.getPlikDoPobrania());
        Pakiet pakietWyjsciowy = new Pakiet();
        ManagerPlikow mp = new ManagerPlikow();
        pakietWyjsciowy.setKawalekPliku(mp.pobierzKawalekPliku(pakiet.getPlikDoPobrania(), 1));
        pakietWyjsciowy.setKtoryKawalek(1);
        pakietWyjsciowy.setDlugoscKawalka(mp.getDlugoscOstatniegoKawalka());
        pakietWyjsciowy.setWysylanyPlik(pakiet.getPlikDoPobrania());
        return pakietWyjsciowy;
    }
    
    /**
     * Metoda obsluguje rzadanie o kawalek pliku
     * @param pakiet
     * @return 
     */
    private Pakiet handlujZTransferPlikIn(Pakiet pakiet){
        Torrent.fw.out("Jest to pro¶ba kawa³ek nr "+pakiet.getKtoryKawalek()+" pliku: "+pakiet.getSciaganyPlik());
        Pakiet pakietWyjsciowy = new Pakiet();
        ManagerPlikow mp = new ManagerPlikow();
        byte[] kawalekPliku = mp.pobierzKawalekPliku(pakiet.getSciaganyPlik(), pakiet.getKtoryKawalek());
        if(kawalekPliku != null){
            pakietWyjsciowy.setWysylanyPlik(pakiet.getSciaganyPlik());
            pakietWyjsciowy.setKawalekPliku(kawalekPliku);
            pakietWyjsciowy.setDlugoscKawalka(mp.getDlugoscOstatniegoKawalka());
            pakietWyjsciowy.setKtoryKawalek(pakiet.getKtoryKawalek());
        } else {
            pakietWyjsciowy.setWysylanyPlik(pakiet.getSciaganyPlik());
            pakietWyjsciowy.setTyp(Pakiet.typ_plik_end);
            Plik plik = new Plik(new File("/Users/macbookair/TORrent_1/"+pakiet.getSciaganyPlik()));
            pakietWyjsciowy.setSh1(plik.getSh1());
        }
        
        return pakietWyjsciowy;
    }
    
    /**
     * Metoda obsluguje plik przychodzacy z serwera
     * @param pakiet
     * @return 
     */
    private Pakiet handlujZTransferPlikOut(Pakiet pakiet){
        int pobranychBajtow = pakiet.getKtoryKawalek() * 1000;
        Torrent.fw.appOut("Pobra³em "+pobranychBajtow+" pliku " + pakiet.getWysylanyPlik());
        Torrent.fw.out("Jest to kawa³ek nr "+pakiet.getKtoryKawalek()+" pliku: "+pakiet.getWysylanyPlik());
        Torrent.fw.out(pakiet.getKawalekPliku().toString());
        
        ManagerPlikow mp = new ManagerPlikow();
        mp.zapiszDoPliku(pakiet.getWysylanyPlik(), pakiet.getKawalekPliku(),pakiet.getKtoryKawalek(), pakiet.getDlugoscKawalka());
        
        Pakiet pakietDawajNastepny = new Pakiet();
        pakietDawajNastepny.setSciaganyPlik(pakiet.getWysylanyPlik());
        pakietDawajNastepny.setKtoryKawalek(pakiet.getKtoryKawalek() + 1);
        return pakietDawajNastepny;
    }
    
    private Pakiet handlujZPlikEnd(Pakiet pakiet){
        ManagerPlikow mp = new ManagerPlikow();
        Plik plik = new Plik(new File("/Users/macbookair/TORrent_1/"+pakiet.getWysylanyPlik()));
        if(pakiet.getSh1().equals(plik.getSh1())){
            Torrent.fw.appOut("Plik "+pakiet.getWysylanyPlik()+" pobrany poprawnie! (Sprawdzono zgodno¶æ sum kontrolnych)");
        } else {
            Torrent.fw.appOut("Suma kontrolna pliku "+pakiet.getWysylanyPlik()+" nie zgadza siê z orygina³em!");
            Torrent.fw.appOut("SH1 Orygina³u: "+pakiet.getWiadomosc());
            Torrent.fw.appOut("SH1 Pliku lokalnego: "+plik.getSh1());
            Torrent.fw.appOut("Spróbuj pobraæ plik ponownie.");
        }
        return new Pakiet();
    }
    
    private ArrayList<Plik> przygotujListePlikow(){
        return (new ManagerPlikow()).zczytajListePlikow();
    }
    
}
