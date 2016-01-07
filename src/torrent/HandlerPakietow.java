package torrent;

import java.util.ArrayList;

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
        switch(pakiet.getTyp()){
            case Pakiet.typ_lista_plikow:
                return this.handlujZLista((PakietListaPlikow) pakiet);
            case Pakiet.typ_pull:
                return this.handlujZPull(pakiet);
            case Pakiet.typ_wiadomosc:
                return this.handlujZWiadomoscia(pakiet);
        }
        Torrent.fw.out("Przyszed³ pakiet od "+pakiet.getIpNadawcy()+" ...");
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
    
    private Pakiet handlujZLista(PakietListaPlikow pakietListaPlikow){
        Torrent.fw.out("Jest to lista plikow rozmair: ("+pakietListaPlikow.getListaPlikow().size()+")");
        Torrent.fw.lista(pakietListaPlikow.getListaPlikow().toArray());
        
        return new Pakiet(Pakiet.wiadomosc_ok_czekam);
    }
    
    private Pakiet handlujZPull(Pakiet pakiet){
        Torrent.fw.out("Jest to pro¶ba o plik: "+pakiet.getPlikDoPobrania());
        return new Pakiet(Pakiet.wiadomosc_ok_czekam);
        
    }
    
    private ArrayList<Plik> przygotujListePlikow(){
        return (new ManagerPlikow()).zczytajListePlikow();
    }
    
}
