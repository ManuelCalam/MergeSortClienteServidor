package mergeChat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import mergesort.mainWindow;

public class implementacionChat extends UnicastRemoteObject implements chatServidor {
    public ArrayList<chatCliente> clientes;
    private mainWindow servidorWindow; 

    public implementacionChat() throws RemoteException {
        try {
            servidorWindow = new mainWindow(this);
            servidorWindow.drawWindow(); 
        } catch (Exception e) {
            System.out.println("Excepcion en implementacionChat: " + e);
            e.printStackTrace();
        }

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
        
        servidorWindow.actualizarTiempo(tiempo, metodo); // Actualizar la ventana del servidor
    }
}
