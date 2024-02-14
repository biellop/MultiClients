import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ServerTCP {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345); // Puerto de escucha
            System.out.println("Servidor TCP esperando conexiones...");

            while (true) {
                Socket socket = serverSocket.accept(); // Espera a que un cliente se conecte
                System.out.println("Cliente conectado desde: " + socket.getInetAddress());

                // Configurar streams de objetos
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

                // Recibir objeto Llista desde el cliente
                Llista receivedList = (Llista) objectInputStream.readObject();
                System.out.println("Lista recibida desde el cliente: " + receivedList.getNom() + ", " + receivedList.getNumberList());

                // Procesar la lista (eliminar repetidos y ordenar)
                Set<Integer> uniqueNumbers = new HashSet<>(receivedList.getNumberList());
                receivedList.setNumberList(new ArrayList<>(uniqueNumbers));
                Collections.sort(receivedList.getNumberList());

                // Enviar la lista procesada de vuelta al cliente
                objectOutputStream.writeObject(receivedList);
                System.out.println("Lista procesada enviada de vuelta al cliente.");

                // Cerrar conexiones
                objectInputStream.close();
                objectOutputStream.close();
                socket.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
