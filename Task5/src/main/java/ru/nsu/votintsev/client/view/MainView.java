package ru.nsu.votintsev.client.view;

import ru.nsu.votintsev.XMLParser;
import ru.nsu.votintsev.xmlclasses.Success;
import ru.nsu.votintsev.xmlclasses.User;
import ru.nsu.votintsev.xmlclasses.Users;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainView {
    public static void main(String[] args) {
        XMLParser xmlParser = new XMLParser();

        Success success = new Success();
        List<User> userList = new ArrayList<>();
        Users users = new Users();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setName("User" + i);
            userList.add(user);
        }
        users.setUsers(userList);
        success.setUsers(users);

        try {
            xmlParser.parseToXML(success, new File("src\\main\\resources\\tmpxml.xml"));
        } catch (Exception ignored) {}
    }
}
