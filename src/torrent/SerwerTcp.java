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
 * Klasa odpowiada za nas³uchiwanie i w zale¿no¶ci od otrzymanego pakietu
 * skierowania go do odpowiedniej metody/klasy tworz±c najpierw jego obiekt
 * 
 *
 * @author mlewandowski
 */
public class SerwerTcp extends Thread {

    protected int port;

    ObjectOutputStream wyjscieDoKlienta;
    
    ObjectInputStream wejscieOdKlienta;
            

    public SerwerTcp(int port) {
        this.port = port;
    }

    public void run() {
        try {
            // inicjalizujemy gniazda i tworzymy "wpisywacz" wyj¶cia i "odczytywacz" wej¶cia
            ServerSocket gniazdoNasluchujace = new ServerSocket(this.port);
            Torrent.fw.out("Serwer TCP czeka na klienta...");
            Socket gniazdoKlienta = gniazdoNasluchujace.accept();
            wyjscieDoKlienta = new ObjectOutputStream(gniazdoKlienta.getOutputStream());
            wejscieOdKlienta = new ObjectInputStream(gniazdoKlienta.getInputStream());
            Torrent.fw.out("Klient po³±czony. Ruch in/out otwarty...");
            Object obiekt;
            while ((obiekt = wejscieOdKlienta.readObject()) != null) {
                //sprawdzamy czy to co przysz³o nam pasuje :)
                if(!(obiekt instanceof Pakiet)){
                    Torrent.fw.outErr("Odebrany niezydentyfikowany obiekt lataj±cy: "+obiekt.getClass());
                    continue;
                }
                Pakiet pakiet = (Pakiet) obiekt;
                pakiet.setIpNadawcy(gniazdoKlienta.getInetAddress().toString());
                HandlerPakietow handler = new HandlerPakietow();
                wyjscieDoKlienta.writeObject(handler.handluj(pakiet));
            }
        } catch (IOException ex) {
            Torrent.fw.outErr("Nie udalo siê utworzyæ gniazda serwera tcp: "+ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SerwerTcp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
