package com.ayrton.clinic.service;

import com.ayrton.clinic.dto.ResourceDTO;
import com.ayrton.clinic.model.Resource;
import com.ayrton.clinic.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    public Resource createResource(ResourceDTO dto){
        Resource resource = new Resource();
        resource.setId(dto.getId());
        resource.setName(dto.getName());
        resource.setType(dto.getType());
        resource.setActive(dto.isActive());
        return resourceRepository.save(resource);
    }

    public List<Resource> getAllResources(){
        return resourceRepository.findAll();
    }

    public Optional<Resource> getById(String id){
        return resourceRepository.findById(id);
    }

    public List<Resource> getByNameIgnoreCase(String name){
        return resourceRepository.findByNameIgnoreCase(name);
    }

    public List<Resource> getByTypeIgnoreCase(String type){
        return resourceRepository.findByTypeIgnoreCase(type);
    }

    public List<Resource> getByActive(boolean active){
        return resourceRepository.findByActive(active);
    }

    public List<Resource> updateResource(String name, ResourceDTO dto){
        List<Resource> exists = resourceRepository.findByNameIgnoreCase(name);

        for (Resource r : exists){
            r.setName(dto.getName());
            r.setType(dto.getType());
            r.setActive(dto.isActive());
            resourceRepository.save(r);
        }
        return exists;
    }

    public boolean deleteResource(String name){
        List<Resource> resources = resourceRepository.findByNameIgnoreCase(name);

        if (!resources.isEmpty()){
            resourceRepository.deleteAll(resources);
            return true;
        }
        return  false;
    }
}
