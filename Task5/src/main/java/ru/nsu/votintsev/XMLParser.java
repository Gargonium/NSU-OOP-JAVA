package ru.nsu.votintsev;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;

public class XMLParser {
    public void parseToXML(Object objectToWrite, File file) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(objectToWrite.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(objectToWrite, file);
    }

    public Object parseFromXML(Class<?> objectToRead, File file) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(objectToRead);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return unmarshaller.unmarshal(file);
    }
}