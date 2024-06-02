package mergeChat;

import javax.swing.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import mergesort.mainWindow;

public class clienteRMI {
    public static void main(String[] args) {
        try {
            String nombre = JOptionPane.showInputDialog("Introduce tu nombre: ");
            String nom = nombre;
            Registry rmii = LocateRegistry.getRegistry("localhost", 1099);
            chatServidor servidor = (chatServidor) rmii.lookup("Chat");
            mainWindow window = new mainWindow(servidor);
            new Thread(new implementacionClienteChat(nom,servidor, window)).start();
            window.drawWindow();
        } catch (Exception e) {
            System.out.println("Excepcion en clienteRMI: " + e);
            e.printStackTrace();
        }

      }
}
