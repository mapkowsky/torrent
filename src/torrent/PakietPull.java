package torrent;

/**
 * @author mlewandowski
 */
public interface PakietPull {
    
    /**
     * Jaki plik chcesz zaci±gn±æ?
     * @return 
     */
    public Plik getPlikDoPobrania();
    
    /**
     * Gdzie chcesz zacz±æ ¶ci±ganie :)?
     * @TODO PAMIÊTAJ W ZWROTCE TRZEBA PODAC ILE BYTÓW POLECI! ALBO USTAWIÆ NA STA£E
     * @return 
     */
    public long getByteRozpoczynajacy();
    
}
