/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tm.ums.client;

import com.tm.ums.model.User;
import com.tm.ums.util.JerseyClient;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Sohail Ahmad
 */
public class UmsClient {

    private JerseyClient client;
//    Gson gson;    
    private Marshaller marshaller;
    private Unmarshaller um;

    private User unMarshal(String xml) {
        User user = null;
        try {            
            user = (User) um.unmarshal(new StringReader(xml));

        } catch (JAXBException ex) {
            Logger.getLogger(UmsClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    private String marshal(User user) {
        String xml = null;
        try {            
            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(user, stringWriter);
            xml = stringWriter.toString();

        } catch (JAXBException ex) {
            Logger.getLogger(UmsClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return xml;
    }

    public UmsClient(String url) {
        client = new JerseyClient(url);
        try {
            JAXBContext ctx = JAXBContext.newInstance(User.class);
            marshaller = ctx.createMarshaller();
            um = ctx.createUnmarshaller();
        } catch (JAXBException ex) {
            Logger.getLogger(UmsClient.class.getName()).log(Level.SEVERE, null, ex);
        }

//        gson = new Gson();
    }

    public long createUser(User user) {
//        String json = gson.toJson(user);
        String result = client.sendPostRequestXmlData("user", marshal(user));
        return Long.valueOf(result);
    }

    public User readUser(Long id) {
//        String result = client.getWebResource().path("user").path(id.toString()).accept(MediaType.APPLICATION_XML).get(String.class);
        String result = client.sendGetRequest("user/"+id);
//        return gson.fromJson(result, User.class);
        return unMarshal(result);
    }

    public int updateUser(User user) {

        String result = client.sendPutRequestXmlData("user/" + user.getId(), marshal(user));
        return Integer.valueOf(result);
    }

    public int deleteUser(Long id) {
        String response = client.sendDeleteRequest("user/" + id, null);
        return Integer.valueOf(response);
    }
}
