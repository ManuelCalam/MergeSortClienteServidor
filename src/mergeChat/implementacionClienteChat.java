package mergeChat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import mergesort.mainWindow;

public class implementacionClienteChat extends UnicastRemoteObject implements chatCliente, Runnable{
    public chatServidor servidor;
    public String nombre  = null;
    public mainWindow window;
    
    implementacionClienteChat(String nombre, chatServidor servidor, mainWindow window) throws java.rmi.RemoteException {
        this.nombre = nombre;
        this.servidor = servidor;
        this.window = window;
        servidor.registro(this);
    }
    @Override
    public void mensajeCliente(String mensaje) throws RemoteException{
        System.out.println(mensaje);
    }
    
    @Override
    public void recibirTiempo(double tiempo, String metodo) throws RemoteException {
        window.actualizarTiempo(tiempo, metodo); // Actualizar la ventana principal con el tiempo recibido
    }
    
    @Override
    public void run() {
        Scanner s = new Scanner(System.in);
        String mensaje;
        while (true) {
            mensaje = s.nextLine();
            try {
                servidor.mensaje(nombre + " dice: " + mensaje);
            } catch (RemoteException e) {
                System.out.println("Excepcion en implementacionClienteChat: " + e);
                e.printStackTrace();
            }
        }
    }
}
