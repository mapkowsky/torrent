package torrent;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author mlewandowski
 */
public class KlientTcp extends Thread {

    private int port;
    
    private String polecenie;
    
    private String host = "localhost";
    
    private String pierwszyArgument;
    
    private String drugiArgument;
    
    private Socket gniazdoKlienta;
    
    public KlientTcp(String polecenie, String pierwszyArgument, String drugiArgument) {
        //this.host = host;
        this.polecenie = polecenie;
        this.pierwszyArgument = pierwszyArgument;
        this.drugiArgument = drugiArgument;
        this.host = pierwszyArgument;
        
    }

    public void run() {
        Pakiet pakietStartowy = null;
        /**
         * Dobór od jakiego pakietu zacz±æ
         */
        switch(polecenie){
            case "lista":
                pakietStartowy = new Pakiet(Pakiet.wiadomosc_zapodaj_liste_plikow);
                break;
            case "pull":
                pakietStartowy = new Pakiet();
                pakietStartowy.setPlikDoPobrania(drugiArgument);
                break;
            case "push":
                pakietStartowy = new Pakiet();
                ManagerPlikow mp = new ManagerPlikow();
                pakietStartowy.setKawalekPliku(mp.pobierzKawalekPliku(drugiArgument, 1));
                pakietStartowy.setKtoryKawalek(1);
                pakietStartowy.setDlugoscKawalka(mp.getDlugoscOstatniegoKawalka());
                pakietStartowy.setWysylanyPlik(drugiArgument);
                break;
        }
        /**
         * Uruchamianie klienta
         */
        try {
            gniazdoKlienta = this.polaczZHostem();
            ObjectOutputStream wyjscie = new ObjectOutputStream(gniazdoKlienta.getOutputStream());
            ObjectInputStream wejscie = new ObjectInputStream(gniazdoKlienta.getInputStream());
            wyjscie.writeObject(pakietStartowy);
            Object obiekt;
            while ((obiekt = wejscie.readObject()) != null) { 
                if(!(obiekt instanceof Pakiet)){
                    Torrent.fw.out("Odebrany niezydentyfikowany obiekt lataj±cy: "+obiekt.getClass());
                    continue;
                }
                Pakiet pakietOdebrany = (Pakiet) obiekt;
                pakietOdebrany.setIpNadawcy(gniazdoKlienta.getInetAddress().toString());
                HandlerPakietow handler = new HandlerPakietow();
                Pakiet zwrotka = handler.handluj(pakietOdebrany);
                wyjscie.writeObject(zwrotka);
                if(zwrotka.getWiadomosc() == Pakiet.wiadomosc_dobranoc){
                    break;
                }
            }
        } catch (Exception ex) {
            Torrent.fw.outErr(ex.getClass() + ex.getMessage() + ex.getStackTrace());
        }
    }
    
    private Socket polaczZHostem(){
        int portDefaultowy = Torrent.config.getInt("serwer_tcp", "port", 9999);
        for(int numerInstancji = 0;numerInstancji < 100;numerInstancji++){
                this.port = portDefaultowy+numerInstancji;
                Torrent.fw.out("Skanujê port: "+this.port+" na ho¶cie: "+this.host);
            try {
                gniazdoKlienta = new Socket(host, this.port);
            } catch (IOException ex) {
                Torrent.fw.out("Port "+this.port+" wydaje siê zamkniêty...");
            }
            if(gniazdoKlienta != null){
                Torrent.fw.out("Znalaz³em serwer na porcie "+this.port+" na ho¶cie: "+this.host);
                break;
            }
        }
        return gniazdoKlienta;
    }
}
