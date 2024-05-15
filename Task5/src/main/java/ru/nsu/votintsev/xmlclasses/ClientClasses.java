package ru.nsu.votintsev.xmlclasses;

import jakarta.xml.bind.JAXBException;
import ru.nsu.votintsev.XMLParser;

import java.io.File;

public sealed class ClientClasses permits Event, Error, Success, InvalidClass {

    public ClientClasses parseFromXML(XMLParser xmlParser, File file) {
        try {
            return (Event) xmlParser.parseFromXML(Event.class, file);
        } catch (JAXBException e) {
            try {
                return (Success) xmlParser.parseFromXML(Success.class, file);
            } catch (JAXBException ex) {
                try {
                    return (Error) xmlParser.parseFromXML(Error.class, file);
                }
                catch (JAXBException exp) {
                    return new InvalidClass();
                }
            }
        }
    }
}
