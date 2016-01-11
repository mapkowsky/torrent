package torrent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Klasa odpowiada za nas�uchiwanie i w zale�no�ci od otrzymanego pakietu
 * skierowania go do odpowiedniej metody/klasy tworz�c najpierw jego obiekt
 *
 *
 * @author mlewandowski
 */
public class SerwerTcp extends Thread {

    protected int port;

    public int numerInstancji;

    ObjectOutputStream wyjscieDoKlienta;

    ObjectInputStream wejscieOdKlienta;

    public static ServerSocket gniazdoNasluchujace;

    public SerwerTcp() {
    }

    /**
     * TODO dorobi� stawianie kolejnego gniaza po po��czeniu klienta dorobi�
     * skanowanie wolnych port�w je�eli pierwszy jest zaj�ty
     */
    public void run() {
        for (int numerInstancji = 0; numerInstancji < 100; numerInstancji++) {
            this.port = Torrent.config.getInt("serwer_tcp", "port", 9999) + numerInstancji;
            try {
                gniazdoNasluchujace = new ServerSocket(port);
            } catch (IOException ex) {
                Torrent.fw.out("Port " + port + " wydaje si� zaj�ty...");
            }
            if (gniazdoNasluchujace != null) {
                Torrent.fw.out("Uda�o si� otworzy� gniazdo na porcie " + this.port);
                break;
            }
        }
        try {
            // inicjalizujemy gniazda i tworzymy "wpisywacz" wyj�cia i "odczytywacz" wej�cia
            Torrent.fw.out("Serwer TCP czeka na klienta...");
            Torrent.fw.appOut("Serwer TCP w��czony.");
            while (true) {
                Socket gniazdoKlienta = gniazdoNasluchujace.accept();
                //tworzymy nowy serwer kt�ry mo�e w tym czasie obs�u�y� kogo� innego
                SerwerTcp nowySerwer = new SerwerTcp();
                nowySerwer.start();
                wyjscieDoKlienta = new ObjectOutputStream(gniazdoKlienta.getOutputStream());
                wejscieOdKlienta = new ObjectInputStream(gniazdoKlienta.getInputStream());
                Torrent.fw.out("Klient po��czony. Ruch in/out otwarty...");
                Object obiekt;
                while ((obiekt = wejscieOdKlienta.readObject()) != null) {
                    //sprawdzamy czy to co przysz�o nam pasuje :)
                    if (!(obiekt instanceof Pakiet)) {
                        Torrent.fw.outErr("Odebrany niezydentyfikowany obiekt lataj�cy: " + obiekt.getClass());
                        continue;
                    }
                    Pakiet pakiet = (Pakiet) obiekt;
                    pakiet.setIpNadawcy(gniazdoKlienta.getInetAddress().toString());
                    HandlerPakietow handler = new HandlerPakietow();
                    Pakiet zwrotka = handler.handluj(pakiet);
                    wyjscieDoKlienta.writeObject(zwrotka);
                    if(pakiet.getWiadomosc().equals(Pakiet.wiadomosc_dobranoc)){
                        break;
                    }
                }
                System.out.println("---------------------");
            }
        } catch (IOException ex) {
            Torrent.fw.outErr("Nie udalo si� utworzy� gniazda serwera tcp: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SerwerTcp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
