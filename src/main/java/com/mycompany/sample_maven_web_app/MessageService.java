/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sample_maven_web_app;

import data.Model;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import objects.Message;
import objects.User;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * REST Web Service
 *
 * @author wlloyd
 */
@Path("messages")
public class MessageService {

    static final Logger logger = Logger.getLogger(UserService.class.getName());
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public MessageService() {
    }

    /**
     * Retrieves representation of an instance of services.GenericResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getMessages() {
        //TODO return proper representation object
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body><style>table, th, td {font-family:Arial,Verdana,sans-serif;font-size:16px;padding: 0px;border-spacing: 0px;}</style><b>MESSAGES LIST:</b><br><br><table cellpadding=10 border=1><tr><td>messageId</td><td>message</td><td>userid</td></tr>");
        try
        {
            Model db = Model.singleton();
            Message[] messages = db.getMessages();
            for (int i=0;i<messages.length;i++)
                sb.append("<tr><td>" + messages[i].getMessageid() + "</td><td>" + messages[i].getMessage() + "</td><td>" + messages[i].getUserid() + "</td></tr>");
        }
        catch (Exception e)
        {
            sb.append("</table><br>Error getting messages: " + e.toString() + "<br>");
        }
        sb.append("</table></body></html>");
        return sb.toString();
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     * @param content representation for the resource
     */
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateMessage(String jobj) throws IOException    
    {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(jobj.toString(), Message.class);
        StringBuilder text = new StringBuilder();
        try {
            Model db = Model.singleton();
            int msgId = message.getMessageid();
            db.updateMessage(message);
            logger.log(Level.INFO, "update message with messageid=" + msgId);
            text.append("Message updated with message id=" + msgId + "\n");
        }
        catch (SQLException sqle)
        {
            String errText = "Error updating message after db connection made:\n" + sqle.getMessage() + " --- " + sqle.getSQLState() + "\n";
            logger.log(Level.SEVERE, errText);
            text.append(errText);
        }
        catch (Exception e)
        {
            logger.log(Level.SEVERE, "Error connecting to db.");
            text.append("Error connecting to db.");
        }
        return text.toString();
    }
    
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String deleteMessage(String jobj) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(jobj.toString(), Message.class);
        StringBuilder text = new StringBuilder();
        try {
            Model db = Model.singleton();
            int messageId = message.getMessageid();
            db.deleteMessage(messageId);
            logger.log(Level.INFO, "message deleted from db=" + messageId);
            text.append("Message id deleted with id=" + messageId);
        }
        catch (SQLException sqle)
        {
            String errText = "Error deleting message after db connection made:\n" + sqle.getMessage() + " --- " + sqle.getSQLState() + "\n";
            logger.log(Level.SEVERE, errText);
            text.append(errText);
        }
        catch (Exception e)
        {
            logger.log(Level.SEVERE, "Error connecting to db.");
            text.append("Error connecting to db.");
        }
        return text.toString();
    }
    
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String createMessage(String jobj) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(jobj.toString(), Message.class);
        
        StringBuilder text = new StringBuilder();
        text.append("The JSON obj:" + jobj.toString() + "\n");
        text.append("The message: " + message.getMessage() + "\n");
        text.append("userId: " + message.getUserid() + "\n");
        
        try {
            Model db = Model.singleton();
            int msgid = db.newMessage(message.getUserid(), message);
            logger.log(Level.INFO, "message persisted to db as messageId=" + msgid);
            text.append("Message id persisted with id=" + msgid);
        }
        catch (SQLException sqle)
        {
            String errText = "Error persisting message after db connection made:\n" + sqle.getMessage() + " --- " + sqle.getSQLState() + "\n";
            logger.log(Level.SEVERE, errText);
            text.append(errText);
        }
        catch (Exception e)
        {
            logger.log(Level.SEVERE, "Error connecting to db.");
        }
        
        
        return text.toString();
    }
}

