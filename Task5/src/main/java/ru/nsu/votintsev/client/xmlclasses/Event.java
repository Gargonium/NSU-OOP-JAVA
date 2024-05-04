package ru.nsu.votintsev.client.xmlclasses;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Event {
    private String event;
    private String message;
    private String from;

    public String getEvent() {
        return event;
    }

    @XmlAttribute(name="name")
    public void setEvent(String event) {
        this.event = event;
    }

    public String getMessage() {
        return message;
    }

    @XmlElement(name="message")
    public void setMessage(String message) {
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    @XmlElement(name="from")
    public void setFrom(String from) {
        this.from = from;
    }
}
