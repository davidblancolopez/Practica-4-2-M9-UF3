
package practica.pkg4.m9.uf3;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import javax.swing.JOptionPane;


public class TimeClient {
    
    //Declarem el HOST i el PORT, DataoutputStream, el buffer i el socket.
    static String HOST = "localhost";
    static int PORT = 8745;
    DataOutputStream outToServer;
    BufferedReader buffer;
    Socket sServer;
    
    private static final String PROPIETAT1="javax.net.ssl.KeyStore";
    private static final String V_PROPIETAT="C:\\Users\\ALUMNEDAM\\Documents\\NetBeansProjects\\Practica-4-M9-UF3\\src\\SSL\\ServidorKey.jks";
    
    private static final String PROPIETAT2 = "javax.net.ssl.KeyStorePassword";
    private static String V_PROPIETAT2 = "123456";
    
    /**
     * Constructor de TimeClient
     * Arriba per parametre un host i el port, realitza la conexio,  es demana dia, mes i any i s'envien al server.
     * @param host
     * @param port
     * @throws IOException 
     */
    public TimeClient(String host, int port) throws IOException {
        //Inicialitzem el Socket , outToServer i BufferReader
        sServer = new Socket(host, port);
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
        //Enviem el dia, mes i any, separat per espais en forma de string
        outToServer.writeBytes(dia + " " + mes + " " + any);
        //Tanquem els recursos
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
