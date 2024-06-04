package mergeChat;

import java.rmi.Remote;

public interface chatCliente extends Remote {
    void mensajeCliente(String mensaje) throws java.rmi.RemoteException;
    void recibirTiempo(double tiempo, String metodo) throws java.rmi.RemoteException;
    void recibirArray(int[] array, boolean ordenado, String metodo) throws java.rmi.RemoteException;
    void recibirCantidad(int cantidad) throws java.rmi.RemoteException;
}
