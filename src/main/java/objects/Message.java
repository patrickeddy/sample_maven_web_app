/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.sql.Date;

/**
 *
 * @author patrickeddy
 */
public class Message {
    private int messageid;

    public int getMessageid() {
        return messageid;
    }

    public void setMessageId(int messageid) {
        this.messageid = messageid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userId) {
        this.userid = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDateadded() {
        return dateadded;
    }

    public void setDateadded(Date dateadded) {
        this.dateadded = dateadded;
    }
    private int userid;
    private String message;
    private Date dateadded;
    
    
}
