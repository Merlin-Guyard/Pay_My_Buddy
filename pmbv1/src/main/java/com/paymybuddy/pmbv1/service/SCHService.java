package com.paymybuddy.pmbv1.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SCHService {
    public String getName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
