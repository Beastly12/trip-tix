package org.dafe.tripTix.dto;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ContactUsDto {

    private String fullName;
    private String email;
    private String phoneNumber;
    private String subject;
    private String message;

}
