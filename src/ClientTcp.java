import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClientTcp extends Thread {
    private String hostname;
    private int port;
    private List<Integer> numberList;
    private Llista lista;
    private Llista recivedList;
    Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Random random = new Random();

    public ClientTcp(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void run() {

        numberList = new ArrayList<>();
        numberList.add(random.nextInt(100) + 1);
        numberList.add(random.nextInt(100) + 1);
        numberList.add(random.nextInt(100) + 1);
        numberList.add(random.nextInt(100) + 1);
        numberList.add(random.nextInt(100) + 1);
        numberList.add(random.nextInt(100) + 1);
        numberList.add(random.nextInt(100) + 1);



        System.out.println("Enviada al servidor");
        System.out.println(numberList);


        lista = new Llista("Frank", numberList);
        try {
            socket = new Socket(InetAddress.getByName(hostname), port);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream.writeObject(lista);
            recivedList = (Llista) inputStream.readObject();
            getRequest(recivedList);


            close(socket);

        } catch (UnknownHostException ex) {
            System.out.println("Error de connexió. No existeix el host: " + ex.getMessage());
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Error de connexió indefinit: " + ex.getMessage());
        }

    }

    public void getRequest(Llista llista) {

        System.out.println("\nNombre: " + llista.getNom());
        System.out.println("Recibida del servidor ");
        System.out.println(llista.getNumberList());

    }

    private void close(Socket socket) throws IOException {

        if (socket != null && !socket.isClosed()) {
            if (!socket.isInputShutdown()) {
                socket.shutdownInput();
            }
            if (!socket.isOutputShutdown()) {
                socket.shutdownOutput();
            }
            socket.close();
        }
    }

    public static void main(String[] args) {
        ClientTcp clientTcp = new ClientTcp("localhost", 5558);
        clientTcp.start();
    }
}