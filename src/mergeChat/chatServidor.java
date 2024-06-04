package mergeChat;

import java.rmi.Remote;

public interface chatServidor extends Remote {
    void registro(chatCliente cliente) throws java.rmi.RemoteException;
    void mensaje(String mensaje) throws java.rmi.RemoteException;
    void enviarTiempo(double tiempo, String metodo) throws java.rmi.RemoteException;
    void enviarArray(int[] array, boolean ordenado, String metodo) throws java.rmi.RemoteException;
    void enviarCantidad(int cantidad) throws java.rmi.RemoteException;
}
