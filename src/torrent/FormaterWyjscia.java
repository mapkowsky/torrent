package torrent;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author macbookair
 */
public class FormaterWyjscia extends Thread{
    
    private OutputStream outputStream;
    
    private int port;
    
    private ServerSocket gniazdoNasluchujace;
    
    public OutputStream getOutputStream(){
//        if(this.outputStream != null){
//            return this.outputStream;
//        }
//        for (int numerInstancji = 0; numerInstancji < 100; numerInstancji++) {
//            this.port = Torrent.config.getInt("serwer_http", "port", 9999) + numerInstancji;
//            try {
//                this.gniazdoNasluchujace = new ServerSocket(port);
//            } catch (IOException ex) {
//            }
//            if (this.gniazdoNasluchujace != null) {
//                break;
//            }
//        }
//        try {
//            Socket gniazdoKlienta = gniazdoNasluchujace.accept();
//            outputStream = gniazdoKlienta.getOutputStream();
//        } catch (IOException ex) {
//            Logger.getLogger(FormaterWyjscia.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        System.out.println("Otworzylem serwer http na porcie "+this.port);
        return outputStream;
    } 
    
    public void out(String out){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        //System.out.println(dateFormat.format(date)+"> "+out);
        if(getOutputStream() != null){
            try {
                outputStream.write(out.getBytes());
            } catch (IOException ex) {
                Logger.getLogger(FormaterWyjscia.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void appOut(String out){
        System.out.println(">>> "+out);
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
