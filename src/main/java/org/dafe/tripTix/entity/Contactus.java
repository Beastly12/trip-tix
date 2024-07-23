package org.dafe.tripTix.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contactus")
@NoArgsConstructor
@Data
public class Contactus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    private String fullName;
    private String email;
    private String phoneNumber;
    private String subject;
    private String message;

    public Contactus(String fullName, String email, String phoneNumber, String subject, String message) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.subject = subject;
        this.message = message;
    }
}
