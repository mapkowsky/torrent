package torrent;

import java.io.File;
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

    private byte[] md5;
    
    private MessageDigest md;
    
    public Plik(File file) {
        this.nazwa = file.getName();
        this.sciezka = file.getPath();
    }

    public String toString() {
        return sciezka;
    }
}
