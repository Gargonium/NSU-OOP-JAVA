package ru.nsu.votintsev.xmlclasses;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Users {
    @XmlElement(name = "user")
    private List<User> userList = new ArrayList<>();

    public List<User> getUserList() {
        return userList;
    }
}
