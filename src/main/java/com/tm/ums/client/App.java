package com.tm.ums.client;

import com.tm.ums.model.User;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        UmsClient client = new UmsClient("http://localhost:8080/restjersey/ums");
//        User user = new User();
//        user.setEmail("sohailehmad@gmail.com");
//        user.setFirstName("Sohail");
//        user.setLastName("Ahmad");
//        user.setPassword("hello");
//        client.createUser(user);
        
//        System.out.println(client.deleteUser(3L) + "records deleted.");
        
        System.out.println(client.readUser(1L));
//        user.setLastName("Sheikh");
//        user.setId(4L);
//        System.out.println(client.updateUser(user) + "records updated.");
                
    }
}
