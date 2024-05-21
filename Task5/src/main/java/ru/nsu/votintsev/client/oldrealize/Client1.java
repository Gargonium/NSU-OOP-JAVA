package ru.nsu.votintsev.client.oldrealize;

import jakarta.xml.bind.JAXBException;
import ru.nsu.votintsev.FileExchanger;
import ru.nsu.votintsev.XMLParser;
import ru.nsu.votintsev.client.Observable;
import ru.nsu.votintsev.client.Observer;
import ru.nsu.votintsev.client.view.ViewEvents;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Client1 implements Observable, Observer {
    private final Socket socket;
    private final DataInputStream in;
    private final DataOutputStream out;
    private final FileExchanger fileExchanger = new FileExchanger();
    private final XMLParser xmlParser = new XMLParser();

    private final ScheduledExecutorService clientTP = Executors.newScheduledThreadPool(2);

    private Observer observer;

    public Client1(Socket socket) throws SocketException {
        this.socket = socket;
        try {
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() throws JAXBException, IOException {
        File file = new File("src\\main\\resources\\ClientProtocol" + Thread.currentThread().getName() + ".xml");
        ClientThreadInput1 clientThreadInput = new ClientThreadInput1(xmlParser, fileExchanger, out, file);
        clientThreadInput.addObserver(this);
        clientTP.scheduleWithFixedDelay(clientThreadInput, 0, 100, TimeUnit.MILLISECONDS);

        ClientThreadOutput1 clientThreadOutput = new ClientThreadOutput1(fileExchanger, in, xmlParser, file);
        clientThreadOutput.addObserver(this);
        clientTP.scheduleWithFixedDelay(clientThreadOutput, 0, 100, TimeUnit.MILLISECONDS);
        while (!socket.isClosed()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {
            }

        }
        file.delete();
    }

    @Override
    public void addObserver(Observer observer) {
        this.observer = observer;
    }

    @Override
    public void notifyObservers(ViewEvents change) {
        observer.update(change);
    }

    @Override
    public void update(ViewEvents change) {
        notifyObservers(change);
    }
}
