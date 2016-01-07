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
    
    private String dodatkowyArgument;

    public KlientTcp(int port, String polecenie, String dodatkowyArgument) {
        this.port = port;
        //this.host = host;
        this.dodatkowyArgument = dodatkowyArgument;
        this.polecenie = polecenie;
    }

    public void run() {
        switch(polecenie){
            case "lista":
                pobierzListe();
                break;
            case "pull":
                pobierzPlik(dodatkowyArgument);
                break;
        }
    }
    
    private void pobierzListe(){
        Socket gniazdoKlienta;
        try {
            gniazdoKlienta = new Socket(host, this.port);
            ObjectOutputStream wyjscie = new ObjectOutputStream(gniazdoKlienta.getOutputStream());
            ObjectInputStream wejscie = new ObjectInputStream(gniazdoKlienta.getInputStream());
            Pakiet pakiet = new Pakiet(Pakiet.wiadomosc_zapodaj_liste_plikow);
            wyjscie.writeObject(pakiet);
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
    
    private void pobierzPlik(String nazwa){
        Socket gniazdoKlienta;
        try {
            gniazdoKlienta = new Socket(host, this.port);
            ObjectOutputStream wyjscie = new ObjectOutputStream(gniazdoKlienta.getOutputStream());
            ObjectInputStream wejscie = new ObjectInputStream(gniazdoKlienta.getInputStream());
            Pakiet pakiet = new Pakiet();
            pakiet.setPlikDoPobrania("plik_testowy.txt");
            wyjscie.writeObject(pakiet);
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

}
