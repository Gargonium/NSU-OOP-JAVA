package ru.nsu.votintsev;

import ru.nsu.votintsev.server.WrongLengthMessageException;

import java.io.*;

public class FileExchanger {
    public void sendFile(DataOutputStream out, File file) throws IOException {
        DataInputStream fileIn = new DataInputStream(new FileInputStream(file));

            out.writeInt((int) file.length());

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileIn.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

            out.flush();
            fileIn.close();
    }

    public void receiveFile(DataInputStream in, File file) throws IOException, WrongLengthMessageException {
        byte[] buffer = new byte[1024];
        long messageLength = in.readInt();
        long messageReadedLength = 0;
        int bytesRead;

        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write("");
        fileWriter.close();
        DataOutputStream fileOut = new DataOutputStream(new FileOutputStream(file));

        while ((bytesRead = in.read(buffer)) != -1) {

            if (messageReadedLength > messageLength) {
                fileOut.flush();
                fileOut.close();
                throw new WrongLengthMessageException();
            }

            fileOut.write(buffer, 0, bytesRead);
            messageReadedLength += bytesRead;
            if (bytesRead == messageLength) {
                break;
            }
        }

        fileOut.flush();
        fileOut.close();
    }
}
