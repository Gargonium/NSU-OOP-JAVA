package ru.nsu.votintsev.xmlclasses;


import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.File;

@XmlRootElement
public class Command {
    private String command;

    private String name;
    private String password;
    private String message;

    private String mimeType;
    private String encoding;
    private File content;
    private Integer id;

    public String getCommand() {
        return command;
    }

    @XmlAttribute(name="name")
    public void setCommand(String command) {
        this.command = command;
    }

    public String getName() {
        return name;
    }

    @XmlElement(name="name")
    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    @XmlElement(name="password")
    public void setPassword(String password) {
        this.password = password;
    }

    public String getMessage() {
        return message;
    }

    @XmlElement(name="message")
    public void setMessage(String message) {
        this.message = message;
    }

    public String getMimeType() {
        return mimeType;
    }

    @XmlElement(name="mimeType")
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getEncoding() {
        return encoding;
    }

    @XmlElement(name="encoding")
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public File getContent() {
        return content;
    }

    @XmlElement(name="content")
    public void setContent(File content) {
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    @XmlElement(name="id")
    public void setId(Integer id) {
        this.id = id;
    }
}
