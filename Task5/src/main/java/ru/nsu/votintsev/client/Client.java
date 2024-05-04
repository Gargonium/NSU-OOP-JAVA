package ru.nsu.votintsev.client;

import jakarta.xml.bind.JAXBException;
import ru.nsu.votintsev.FileExchanger;
import ru.nsu.votintsev.XMLParser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Client {
    private final Socket socket;
    private final DataInputStream in;
    private final DataOutputStream out;
    private final FileExchanger fileExchanger = new FileExchanger();
    private final XMLParser xmlParser = new XMLParser();

    private final ScheduledExecutorService clientTP = Executors.newScheduledThreadPool(2);

    private final static File file = new File("src\\main\\resources\\ClientProtocol.xml");

    public Client(Socket socket) throws SocketException {
        this.socket = socket;
        try {
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() throws JAXBException, IOException { // У клиента надо в 2 отдельных потока захуярить приём и отправку сообщений
        ClientThreadInput clientThreadInput = new ClientThreadInput(xmlParser, fileExchanger, out, file);
        clientTP.scheduleWithFixedDelay(clientThreadInput, 0, 100, TimeUnit.MILLISECONDS);

        ClientThreadOutput clientThreadOutput = new ClientThreadOutput(fileExchanger, in, xmlParser, file);
        clientTP.scheduleWithFixedDelay(clientThreadOutput, 0, 100, TimeUnit.MILLISECONDS);
        while (!socket.isClosed()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {}
        }
    }
}
