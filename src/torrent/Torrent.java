package torrent;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

/**
 * @author m.lewandowski
 */
public class Torrent {

    /**
     * TODO:
     * - KLIENT - skanowanie portów
     * - SERWER - multihost 
     * - WYSTAWIENIE HTTP
     */
    public static FormaterWyjscia fw;
    public static IniFile config;
    
    public static void main(String[] args) throws IOException {
        Torrent.fw = new FormaterWyjscia();
        Torrent.fw.run();
        Scanner scan = new Scanner(System.in);
        Torrent.config = new IniFile("/Users/macbookair/NetBeansProjects/torrent/src/torrent/konfiguracja.ini");
        fw.tytul("TORRENT");
        fw.out("Lista komend:");
        String[] listaKomend = {
            "tcps",
            "tcplist [host]",
            "tcppull [nazwapliku]",
            "wyjdz"
        };
        fw.lista(listaKomend);
        fw.linia();
        String komenda;
        String wejscie;
        do {
            wejscie = scan.nextLine();
            String[] split = wejscie.split("\\s");
            System.out.println(wejscie);
            komenda = split[0];
            switch (komenda) {
                case "tcps":
                    Torrent.uruchomSerwerTcp();
                    break;
                case "tcplist":
                    Torrent.pobierzListeTcp(split[1]);
                    break;
                case "tcppull":
                    Torrent.pobierzPlikTcp(split[1], split[2]);
                    break;
                case "tcppush":
                    Torrent.wystawPlikTcp(split[1], split[2]);
                    break;
                case "wyjdz":
                    fw.out("Wychodze...");
                    ;
                    break;
                default:
                    fw.out("Brak takiej komendy!");
            }
        } while (!"wyjdz".equals(komenda));
        System.exit(0);

    }
    
    private static void uruchomSerwerTcp(){
        SerwerTcp s = new SerwerTcp();
        s.start();
    }
    
    private static void pobierzListeTcp(String argument1){
        KlientTcp k = new KlientTcp("lista", argument1, null);
        k.start();
    }
    private static void pobierzPlikTcp(String argument1, String argument2){
        KlientTcp k = new KlientTcp("pull", argument1, argument2);
        k.start();
    }
    private static void wystawPlikTcp(String argument1, String argument2){
        KlientTcp k = new KlientTcp("push", argument1, argument2);
        k.start();
    }




}
