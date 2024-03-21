import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.io.*;

public class client {
    public static void main(String[] args) throws UnknownHostException, IOException {
        try {
            Socket socket = new Socket("192.168.0.119", 8082);
            System.out.println("遊戲開始");
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            Scanner sc = new Scanner(System.in);
            String message;
            String response;
            int playid = -1;
            do {
                if (playid == -1) {
                    System.out.print("這回合換我:");
                }
                message = sc.nextLine();
                outputStream.write(message.getBytes());
                byte[] buffer = new byte[1024];
                int bytesRead = inputStream.read(buffer);
                response = new String(buffer, 0, bytesRead);
                System.out.println(response);
                playid *= -1;
                if (playid == 1) {
                    bytesRead = inputStream.read(buffer);
                    response = new String(buffer, 0, bytesRead);
                    System.out.println(response);
                    if (response.contains("4A0B")) {
                        bytesRead = inputStream.read(buffer);
                        response = new String(buffer, 0, bytesRead);
                        System.out.println(response);
                    }

                    playid *= -1;
                }

                if (response.equals("win")) {
                    break;
                }
                if (response.equals("lose")) {
                    break;
                }

            } while (!response.equals("end"));
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
