package ru.nsu.votintsev.xmlclasses;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public non-sealed class Success extends ClientClasses {
    private Users users = null;

    public Users getUsers() {
        return users;
    }

    @XmlElement(name="users")
    public void setUsers(Users users) {
        this.users = users;
    }
}
