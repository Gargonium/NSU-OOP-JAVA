package ru.nsu.votintsev;

import jakarta.xml.bind.JAXBException;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) throws JAXBException {

        User user = new User();
        user.setId(0);
        user.setName("Gargonium");
        user.setCountry("Russia");

        User user1 = new User();
        user1.setId(1);
        user1.setName("OmNomDom");
        user1.setCountry("Ukraine");

        Users users = new Users();
        users.getUserList().add(user);
        users.getUserList().add(user1);

        XMLParser xmlParser = new XMLParser();

        File file = new File("src\\main\\resources\\Test.xml");

        xmlParser.parseToXML(users, file);
        Users users1 = (Users) xmlParser.parseFromXML(Users.class, file);

        List<User> userList = users1.getUserList();

        for (User tmpUser : userList) {
            System.out.println("Id: " + tmpUser.getId());
            System.out.println("Name: " + tmpUser.getName());
            System.out.println("Country: " + tmpUser.getCountry());
        }
    }
}
