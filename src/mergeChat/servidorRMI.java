package mergeChat;

import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class servidorRMI {
    public static void main(String[] args) {
        try {
            Registry rmi = LocateRegistry.createRegistry(1099);
            rmi.rebind("Chat", (Remote) new implementacionChat());
            System.out.println("Servidor RMI listo");

        } catch (Exception e) {
            System.out.println("Excepcion en servidorRMI: " + e);
            e.printStackTrace();
        }
    }
}
