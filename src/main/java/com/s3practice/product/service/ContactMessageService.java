package com.s3practice.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.s3practice.product.dao.ContactMessageRepository;
import com.s3practice.product.model.ContactMessage;

@Service
public class ContactMessageService {

    @Autowired
    private ContactMessageRepository contactMessageRepository;

    @Autowired
    private EmailService emailService;

    public ContactMessage saveContactMessage(ContactMessage message) {

        String respond = String.format("""
        Hello %s %s!

        Thank you for contacting with us. We will be responding this mail soon.

        Sign: AppProduct CEO
        """, message.getName(), message.getLastName());

        emailService.sendEmail(message.getEmail(), "Message received - AppProduct", respond);

        return contactMessageRepository.save(message);
    }

}
