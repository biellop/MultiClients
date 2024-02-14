import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientTCP {
    public static void main(String[] args) {
        try {
            // Crear conexión con el servidor
            Socket socket = new Socket("localhost", 12345); // Cambia localhost por la dirección del servidor

            // Configurar streams de objetos
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            // Crear una lista con datos
            List<Integer> numberList = Arrays.asList(5, 3, 8, 3, 1, 5, 7, 8, 2);
            Llista listaToSend = new Llista("Ejemplo", numberList);

            // Enviar la lista al servidor
            objectOutputStream.writeObject(listaToSend);
            System.out.println("Lista enviada al servidor: " + listaToSend.getNom() + ", " + listaToSend.getNumberList());

            // Recibir la lista procesada del servidor
            Llista receivedList = (Llista) objectInputStream.readObject();
            System.out.println("Lista procesada recibida del servidor: " + receivedList.getNom() + ", " + receivedList.getNumberList());

            // Cerrar conexiones
            objectOutputStream.close();
            objectInputStream.close();
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
