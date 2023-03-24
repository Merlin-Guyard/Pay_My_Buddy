package com.paymybuddy.pmbv1;

import com.paymybuddy.pmbv1.configuration.MessageConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Locale;

public class testmain {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MessageConfig.class);
        System.out.println(ctx.getMessage("err.duplicate_contact", null, "Default", new Locale("eng")));
    }
}