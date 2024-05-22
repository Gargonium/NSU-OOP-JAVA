package ru.nsu.votintsev;

import ru.nsu.votintsev.server.WrongLengthMessageException;

import java.io.*;

public class FileExchanger {
    public void sendFile(DataOutputStream out, String xmlString) throws IOException {
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

            if (messageReadedLength > messageLength) {
                throw new WrongLengthMessageException();
            }

            stringBuilder.append(new String(buffer, 0, bytesRead));

            if (messageReadedLength == messageLength)
                break;
        }

        return stringBuilder.toString();
    }
}
