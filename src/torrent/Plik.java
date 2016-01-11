package torrent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author mlewandowski
 */
public class Plik implements Serializable {

    private String nazwa;

    private String sciezka;

    private String sh1;
    
    public Plik(File file) {
        ManagerPlikow mp = new ManagerPlikow();
        this.nazwa = file.getName();
        this.sciezka = file.getPath();
        try {
            this.sh1 = mp.sha1(new FileInputStream(file));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Plik.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Plik.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Plik.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String toString() {
        return "Nazwa pliku: " + nazwa + " SHA1: "+sh1;
    }
    
    public String getSh1(){
        return this.sh1;
    }
    
}
