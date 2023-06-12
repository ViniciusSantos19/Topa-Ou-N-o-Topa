package cliente;

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
                System.out.println("Digite um número: ");
                out.println(inUser.nextInt());
                System.out.println(in.nextLine());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
