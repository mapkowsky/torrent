package torrent;

import java.io.File;
import java.util.ArrayList;

/**
 * Klasa dostarcza fasady obslugujace pliki
 * @author mlewandowski
 */
public class ManagerPlikow {
    private ArrayList<Plik> listaPlikow;
    
    public ArrayList<Plik> zczytajListePlikow(){
        listaPlikow = new ArrayList();
        File curDir = new File("/Users/macbookair/TORrent_1");
        File[] filesList = curDir.listFiles();
        for(File f : filesList){
            if(f.isFile()){
                listaPlikow.add(new Plik(f));
            }
        }
        return listaPlikow;
    }
    
}
