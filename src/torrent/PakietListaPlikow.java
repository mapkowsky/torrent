package torrent;

import java.util.ArrayList;

/**
 * Interfejs listy plik�w
 * @author mlewandowski
 */
public interface PakietListaPlikow {
    
    public ArrayList<Plik> getListaPlikow();
    
    public void setListaPlikow(ArrayList<Plik> listaPlikow);
    
    public String getIpNadawcy();
    
}
