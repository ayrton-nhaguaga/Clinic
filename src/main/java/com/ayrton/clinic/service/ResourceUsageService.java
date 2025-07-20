package com.ayrton.clinic.service;

import com.ayrton.clinic.repository.ResourceRepository;
import com.ayrton.clinic.repository.ResourceUsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceUsageService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ResourceUsageRepository resourceUsageRepository;


}
