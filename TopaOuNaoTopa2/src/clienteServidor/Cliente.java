package clienteServidor;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try {
            Socket client = new Socket("127.0.0.1", 1234);
            Scanner in = new Scanner(client.getInputStream());
            Scanner inUser = new Scanner(System.in);
            PrintWriter out = new PrintWriter(client.getOutputStream(),true);

            while (true) {
                out.flush();
                while(in.hasNextLine()) {
                	 String serverMessage = in.nextLine();
                	System.out.println(serverMessage);
                	if (serverMessage.equals("Escolha uma maleta para abrir" ) || serverMessage.equals("Você topa ou não topa? (1 para sim 2 para não)")) {
                        break;
                    }
                }
                out.println(inUser.nextInt());
                
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
