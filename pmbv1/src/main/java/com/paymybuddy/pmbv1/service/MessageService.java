package com.paymybuddy.pmbv1.service;

import com.paymybuddy.pmbv1.configuration.MessageConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MessageService {

    public String returnMessage(String status) {

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MessageConfig.class);

        return ctx.getMessage(status, null, "Default", new Locale("eng"));
    }
}
