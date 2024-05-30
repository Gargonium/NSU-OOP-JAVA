package ru.nsu.votintsev.xmlclasses;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
    private String name;
    private boolean mute;

    public String getName() {
        return name;
    }

    @XmlElement(name="name")
    public void setName(String name) {
        this.name = name;
    }

    public boolean getMute() {
        return mute;
    }

    @XmlElement(name="mute")
    public void setMute(boolean mute) {
        this.mute = mute;
    }
}
