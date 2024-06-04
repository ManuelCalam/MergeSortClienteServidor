package mergeChat;

import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import mergesort.mainWindow;

public class servidorRMI {
    public static void main(String[] args) {
        try {
            Registry rmi = LocateRegistry.createRegistry(1099);
            
            implementacionChat servidor = new implementacionChat(); 
            rmi.rebind("Chat", servidor);
            System.out.println("Servidor RMI listo");
        } catch (Exception e) {
            System.out.println("Excepcion en servidorRMI: " + e);
            e.printStackTrace();
        }
    }
}
