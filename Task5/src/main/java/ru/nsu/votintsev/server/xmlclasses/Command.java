package ru.nsu.votintsev.server.xmlclasses;


import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Command {
    private String command;

    private String userName = "null";
    private String userPassword = "null";

    private String message = "null";

    public String getCommand() {
        return command;
    }

    @XmlAttribute(name="name")
    public void setCommand(String command) {
        this.command = command;
    }

    public String getUserName() {
        return userName;
    }

    @XmlElement(name="name")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    @XmlElement(name="password")
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getMessage() {
        return message;
    }

    @XmlElement(name="message")
    public void setMessage(String message) {
        this.message = message;
    }
}
