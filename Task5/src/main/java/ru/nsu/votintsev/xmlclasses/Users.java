package ru.nsu.votintsev.xmlclasses;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Users {
    private List<User> users = new ArrayList<>();

    public List<User> getUsers() {
        return users;
    }

    @XmlElement(name="user")
    public void setUsers(List<User> users) {
        this.users = users;
    }
}
