package org.dafe.tripTix.email;

public interface EmailSender {

    void send(String to, String email,String subject);

}
