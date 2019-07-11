package sample.Network;

import java.util.HashSet;
import java.util.Set;

public class NetManager implements Runnable {
    protected Set<ClientHandler> clientHandlers = new HashSet<ClientHandler>();
    private Server server;

    NetManager() {
        Thread t = new Thread(this);
        t.start();
    }

    public void begin(int port) {
        server = new Server(this, port);
    }

    @Override
    public void run() {

    }

    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter port: 1234");
        int port = 1234;//scanner.nextInt();
        System.out.println("Starting server...");
        NetManager netManager = new NetManager();
        netManager.begin(port);
        System.out.println("Started server");
    }

}
