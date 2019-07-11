package sample.Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

    ServerSocket ss;
    NetManager netManager;

    Server(NetManager netManager, int port) {
        this.netManager = netManager;
        try {
            ss = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket s = ss.accept();
                System.out.println("Client Connected");
                ClientHandler ch = new ClientHandler(netManager, s);
                netManager.clientHandlers.add(ch);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}