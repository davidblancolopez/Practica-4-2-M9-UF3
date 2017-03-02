
package practica.pkg4.m9.uf3;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.JOptionPane;


public class TimeClient {
    
    //Declarem el HOST i el PORT, DataoutputStream, el buffer i el socket.
    static String HOST = "localhost";
    static int PORT = 8745;
    DataOutputStream outToServer;
    BufferedReader buffer;
    Socket sServer;
    SocketFactory sFactory;
    
    //Magatzem de claus.
    private static final String PROPIETAT1="javax.net.ssl.TrustStore";
    //Ruta del magatzem de claus.
    private static final String V_PROPIETAT="C:\\Users\\ALUMNEDAM\\Documents\\NetBeansProjects\\Practica-4-M9-UF3\\src\\SSL\\ServidorKey.jks";
    //Magatzem de contrasenyas en que es confien.
    private static final String PROPIETAT2 = "javax.net.ssl.TrustStorePassword";
    //Contrasenya del certificat.
    private static String V_PROPIETAT2 = "123456";
    

    
    
    /**
     * Constructor de TimeClient
     * Arriba per parametre un host i el port, realitza la conexio,  es demana dia, mes i any i s'envien al server.
     * @param host
     * @param port
     * @throws IOException 
     */
    public TimeClient(String host, int port) throws IOException {
        System.setProperty(PROPIETAT1, V_PROPIETAT);
        System.setProperty(PROPIETAT2, V_PROPIETAT2);
        
        //Inicialitzem el Socket , outToServer i BufferReader
        sFactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
        sServer = (SSLSocket) sFactory.createSocket(HOST, PORT);
        outToServer = new DataOutputStream(sServer.getOutputStream());
        buffer = new BufferedReader(new InputStreamReader(sServer.getInputStream()));
        
        
        //Es demanem dia, mes, any
        String dia = JOptionPane.showInputDialog(null, "Diu el numero del dia: ", "Entrando", 3);
        String mes = JOptionPane.showInputDialog(null, "Diu el mes: ", "Entrando", 3);
        String any = JOptionPane.showInputDialog(null, "Diu l'any: ", "Entrando", 3);
        //Pasem com int les dades i les enviem a el metode enviarServidor
        enviarServidor(Integer.parseInt(dia), Integer.parseInt(mes), Integer.parseInt(any));

    }


    /**
     * Metode que li arriba per parametre dia, mes i any.
     * Agafa la informació que li ha arribat i l'envia al servidor. 
     * @param dia
     * @param mes
     * @param any
     * @throws IOException 
     */
    public void enviarServidor(int dia, int mes, int any) throws IOException {
        outToServer.writeBytes(dia + " " + mes + " " + any);
        //Tanquem recursos.
        buffer.close();
        outToServer.close();
        sServer.close();
    }
    /**
     * Metode main que crida a la clase amb un Host i un port.
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        new TimeClient(HOST, PORT);
    }
}
