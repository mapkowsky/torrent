package torrent;

/**
 * @author mlewandowski
 */
public interface PakietPull {
    
    /**
     * Jaki plik chcesz zaci�gn��?
     * @return 
     */
    public Plik getPlikDoPobrania();
    
    /**
     * Gdzie chcesz zacz�� �ci�ganie :)?
     * @TODO PAMI�TAJ W ZWROTCE TRZEBA PODAC ILE BYT�W POLECI! ALBO USTAWI� NA STA�E
     * @return 
     */
    public long getByteRozpoczynajacy();
    
}
