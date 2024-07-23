package org.dafe.tripTix.service;

import org.dafe.tripTix.entity.Contactus;
import org.dafe.tripTix.repository.ContactUsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactUsService {
    private final ContactUsRepository contactusRep;

    public ContactUsService(ContactUsRepository contactusRep) {
        this.contactusRep = contactusRep;
    }

    public void save(Contactus contactus){
        contactusRep.save(contactus);
    }

    public List<Contactus> findAll(){
        return contactusRep.findAll();
    }

}
