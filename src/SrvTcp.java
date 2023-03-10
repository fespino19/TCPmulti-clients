import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SrvTcp { private int port;
    private ServerSocket serverSocket;
    private Socket clientSocket;

    public SrvTcp(int port ) {
        this.port = port;
    }

    public void listen() throws IOException {

        serverSocket = new ServerSocket(port);
        while(true) {
            clientSocket = serverSocket.accept();
            ThreadSevidorAdivina FilServidor = new ThreadSevidorAdivina(clientSocket);
            Thread client = new Thread(FilServidor);
            client.start();
        }
    }

    public static void main(String[] args) throws IOException {
        SrvTcp server = new SrvTcp(5558);
        server.listen();

    }

}
