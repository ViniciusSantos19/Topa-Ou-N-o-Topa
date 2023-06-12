package servidor;

import modelo.TopaOuNãoTopa;

import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(1234);
            System.out.println("Servidor em rede");
            while (true) {
                Socket client = server.accept();
                Thread thread = new Thread(new TopaOuNãoTopa(client));
                thread.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
