package torrent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author macbookair
 */
public class FormaterWyjscia extends Thread{
    
    
    public void out(String out){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date)+"> "+out);
    }
    
    public void outErr(String err){
        System.err.println("<!!!>: "+err);
    }
    
    public void lista(Object[] out){
        for(int i = 0; i < out.length;i++){
            System.out.println("+ "+out[i]);
        }
    }
    
    public void tytul(String title){
        for(int i = 0; i < title.length()+8;i++){
            System.out.print("-");
        }
        System.out.println("\n--- "+title+" ---");
        for(int i = 0; i < title.length()+8;i++){
            System.out.print("-");
        }
        System.out.println();
    }
    
    public void linia(){
        System.out.println("---------------");
    }
    
    
}
