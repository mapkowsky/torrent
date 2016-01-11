package torrent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Klasa dostarcza fasady obslugujace pliki
 *
 * @author mlewandowski
 */
public class ManagerPlikow {

    private ArrayList<Plik> listaPlikow;

    private int dlugoscOstatniegoKawalka;

    public ArrayList<Plik> zczytajListePlikow() {
        listaPlikow = new ArrayList();
        File curDir = new File("/Users/macbookair/TORrent_1");
        File[] filesList = curDir.listFiles();
        for (File f : filesList) {
            if (f.isFile()) {
                Plik plik = new Plik(f);
                listaPlikow.add(new Plik(f));
            }
        }
        return listaPlikow;
    }

    /**
     * Zapisuje do pliku content przeslany w pakiecie
     */
    public void zapiszDoPliku(String nazwaPliku, byte[] buffer, int numerKawalka, int dlugoscKawalka) {
        try {
            String fileName = "/Users/macbookair/TORrent_1/" + nazwaPliku + "_tmp";
            Torrent.fw.out("Zapisuje do pliku: " + fileName);
            int offset = (numerKawalka - 1) * 1000;
            FileOutputStream outputStream = new FileOutputStream(fileName, true);
            Torrent.fw.out("******offset: " + offset + " len:" + dlugoscKawalka);
            byte[] arrayToAppend = Arrays.copyOfRange(buffer, 0, dlugoscKawalka);
            outputStream.write(arrayToAppend);
            outputStream.close();
        } catch (IOException ex) {
            Torrent.fw.outErr("xD" + ex.printStackTrace());
        }
    }

    public byte[] pobierzKawalekPliku(String nazwaPliku, int numerKawalka) {
        String sciezka = "/Users/macbookair/TORrent_1/" + nazwaPliku;
        byte[] buffer = new byte[1000];
        byte[] bufferDoZwrocenia = null;
        try {
            FileInputStream inputStream
                    = new FileInputStream(sciezka);

            int numerAktualnegoKawalka = 1;
            int nRead = 0;

            while ((nRead = inputStream.read(buffer)) != -1) {
                if (numerAktualnegoKawalka == numerKawalka) {
                    this.dlugoscOstatniegoKawalka = nRead;
                    Torrent.fw.out("Pobralem kawalek");
                    bufferDoZwrocenia = buffer;
                    break;
                }
                numerAktualnegoKawalka++;
            }
            inputStream.close();
        } catch (FileNotFoundException ex) {
            Torrent.fw.outErr(ex.getMessage());
        } catch (IOException ex) {
            Torrent.fw.outErr(ex.getMessage());
        }
        return bufferDoZwrocenia;
    }

    public String sha1(FileInputStream fis) throws IOException, NoSuchAlgorithmException {
        byte[] dataBytes = new byte[1024];
        int nread = 0;
        MessageDigest md = MessageDigest.getInstance("SHA1");
        while ((nread = fis.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, nread);
        };

        byte[] mdbytes = md.digest();
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < mdbytes.length; i++) {
            sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    public String sh1(byte[] dataBytes) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        md.update(dataBytes, 0, dataBytes.length);

        byte[] mdbytes = md.digest();
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < mdbytes.length; i++) {
            sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    public int getDlugoscOstatniegoKawalka() {
        return dlugoscOstatniegoKawalka;
    }

}
