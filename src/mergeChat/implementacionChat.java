package mergeChat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class implementacionChat extends UnicastRemoteObject implements chatServidor {
    public ArrayList<chatCliente> clientes;
    
    public implementacionChat() throws java.rmi.RemoteException {
        //super();
        clientes = new ArrayList<chatCliente>();
    }

    @Override
    public void registro(chatCliente cliente) throws RemoteException {
        clientes.add(cliente);
    }

    @Override
    public void mensaje(String mensaje) throws RemoteException {
        
        System.out.println("Mensaje recibido en el servidor: " + mensaje);
        
        int a = 0;
        while (a < clientes.size()) {
            clientes.get(a).mensajeCliente(mensaje);
            a++;
        }
    }

    public void enviarTiempo(double tiempo, String metodo) throws RemoteException {
        for (chatCliente cliente : clientes) {
            cliente.recibirTiempo(tiempo, metodo);
        }
    }
}
