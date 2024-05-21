package ru.nsu.votintsev;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;

public class XMLParser {
    public String parseToXML(Object objectToWrite) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(objectToWrite.getClass());
        Marshaller marshaller = context.createMarshaller();
        synchronized (marshaller) {
            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(objectToWrite, stringWriter);
            return stringWriter.toString();
        }
    }

    public Object parseFromXML(Class<?> objectToRead, String xmlString) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(objectToRead);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        synchronized (unmarshaller) {
            StringReader stringReader = new StringReader(xmlString);
            return unmarshaller.unmarshal(stringReader);
        }
    }
}