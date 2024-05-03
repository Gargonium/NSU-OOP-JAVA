package ru.nsu.votintsev.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8886)) {

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            System.out.print("To Server: ");
            Scanner scanner = new Scanner(System.in);

            while (scanner.hasNextLine()) {
                String inputLine = scanner.nextLine();
                out.println(inputLine);
                System.out.println("From Server: " + in.readLine());
                System.out.print("To Server: ");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

/*
            // Получаем потоки для ввода и вывода
            BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
            BufferedOutputStream fileOut = new BufferedOutputStream(new FileOutputStream("received_file.txt"));

            // Получаем файл от сервера
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                fileOut.write(buffer, 0, bytesRead);
            }
            fileOut.flush();

            // Закрываем соединение
            in.close();
            fileOut.close();
 */
