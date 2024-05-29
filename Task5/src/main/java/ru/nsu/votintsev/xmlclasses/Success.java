package ru.nsu.votintsev.xmlclasses;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.File;

@XmlRootElement
public non-sealed class Success extends ClientClasses {
    private Users users = null;

    private Integer id;
    private String name;
    private String mimeType;
    private String encoding;
    private File content;

    public Users getUsers() {
        return users;
    }

    @XmlElement(name="users")
    public void setUsers(Users users) {
        this.users = users;
    }

    public Integer getId() {
        return id;
    }

    @XmlElement(name="id")
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @XmlElement(name="name")
    public void setName(String name) {
        this.name = name;
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
}
