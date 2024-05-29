package ru.nsu.votintsev;

import ru.nsu.votintsev.server.WrongLengthMessageException;

import java.io.*;

public class FileExchanger {
    public void sendFile(DataOutputStream out, String xmlString) throws IOException {
        System.out.println("Send: " + xmlString);
        out.writeInt(xmlString.length());
        out.writeBytes(xmlString);
        out.flush();
    }

    public String receiveFile(DataInputStream in) throws IOException, WrongLengthMessageException {
        long messageLength = in.readInt();
        byte[] buffer = new byte[1024];
        long messageReadedLength = 0;
        int bytesRead;

        StringBuilder stringBuilder = new StringBuilder();

        while ((bytesRead = in.read(buffer)) != -1) {

            messageReadedLength += bytesRead;

            stringBuilder.append(new String(buffer, 0, bytesRead));

            if (messageReadedLength > messageLength) {
                System.out.println("Must: " + messageLength + "\nReaded: " + messageReadedLength + "\n" + stringBuilder);
                //throw new WrongLengthMessageException();
            }

            if (messageReadedLength == messageLength)
                break;
        }
        System.out.println("Received: " + stringBuilder);
        return stringBuilder.toString();
    }
}
