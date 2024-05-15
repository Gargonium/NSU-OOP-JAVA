package ru.nsu.votintsev.xmlclasses;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public non-sealed class Event extends ClientClasses {
    private String event = "null";
    private String message = "null";
    private String from = "null";
    private String name = "null";

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

    public String getName() {
        return name;
    }

    @XmlElement(name="name")
    public void setName(String name) {
        this.name = name;
    }
}
