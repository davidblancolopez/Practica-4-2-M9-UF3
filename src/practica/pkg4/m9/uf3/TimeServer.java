
package practica.pkg4.m9.uf3;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import static practica.pkg4.m9.uf3.TimeClient.HOST;
import static practica.pkg4.m9.uf3.TimeClient.PORT;


public class TimeServer {
    private static Scanner entrada;
    private static GregorianCalendar cal;
    
    private static SSLServerSocketFactory sslFactory;
    private static SSLServerSocket sslserversocket;
    private static SSLSocket sserver;
    
    private static ServerSocketFactory sFactory;
    
    //Magatzem de claus.
    private static final String PROPIETAT1="javax.net.ssl.TrustStore";
    //Ruta del magatzem de claus.
    private static final String V_PROPIETAT="C:\\Users\\ALUMNEDAM\\Documents\\NetBeansProjects\\Practica-4-M9-UF3\\src\\SSL\\ServidorKey.jks";
    //Magatzem de contrasenyas en que es confien.
    private static final String PROPIETAT2 = "javax.net.ssl.TrustStorePassword";
    //Contrasenya del certificat.
    private static String V_PROPIETAT2 = "123456";
    
    
    private static final String[] setmana = {
        "Diumenge", 
        "Dilluns",
        "Dimarts",
        "Dimecres", 
        "Dijous",
        "Divendres",
        "Dissabte"};

    public static void main(String[] argv) throws Exception {
        try {
            System.setProperty(PROPIETAT1, V_PROPIETAT);
            System.setProperty(PROPIETAT2, V_PROPIETAT2);
        
        //Inicialitzem el Socket , outToServer i BufferReader
        sFactory = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
        ServerSocket srvSocket = (SSLServerSocket)sFactory.createServerSocket(8745);
            
            

            while (true) {
                Socket sServer = srvSocket.accept();
                Scanner in = new Scanner(sServer.getInputStream());
                int[] data = new int[3];
                boolean ok = true;
                for (int i = 0; i < data.length; i++) {
                    if (in.hasNextInt()) {
                        data[i] = in.nextInt();
                    } else {
                        ok = false;
                    }
                }
                PrintStream out = new PrintStream(sServer.getOutputStream());
                if (ok) {
                    data[1] -= 1; 
                    GregorianCalendar cal = new GregorianCalendar(data[2],
                            data[1], data[0]);
                    int dia = cal.get(Calendar.DAY_OF_WEEK) - 1;
                    System.out.println("Aquest dia era " + setmana[dia] + ".");

                } else {
                    System.out.println("Format de les dades incorrecte.");
                }
                sServer.close();
            }
        } catch (Exception ex) {
            System.out.println("Error en les comuncacions: " + ex);
        }
    }

}
