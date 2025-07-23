package com.ayrton.clinic.controller;

import com.ayrton.clinic.dto.ResourceDTO;
import com.ayrton.clinic.model.Resource;
import com.ayrton.clinic.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/resources")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @PostMapping
    public ResponseEntity<Resource> createResource(@RequestBody ResourceDTO dto){
        Resource resource = resourceService.createResource(dto);
        return  new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Resource>> getAllResources(){
        List<Resource> resources = resourceService.getAllResources();
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Optional<Resource>> getById(@RequestParam String id){
        Optional<Resource> resource = resourceService.getById(id);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Resource>> getByNameIgnoreCase(@RequestParam String name){
        List<Resource> resources = resourceService.getByNameIgnoreCase(name);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Resource>> getByTypeIgnoreCase(@RequestParam String type){
        List<Resource> resources = resourceService.getByTypeIgnoreCase(type);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @GetMapping("/active/{active}")
    public ResponseEntity<List<Resource>> getByActive(@RequestParam boolean active){
        List<Resource> resources = resourceService.getByActive(active);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @PutMapping("/name/{name}")
    public ResponseEntity<List<Resource>> updateResource(@RequestParam String name, @RequestBody ResourceDTO dto){
        List<Resource> updatedList = resourceService.updateResource(name, dto);

        if (updatedList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updatedList);
    }

    @DeleteMapping("/name/{name}")
    public ResponseEntity<Void> deleteResource(@RequestParam String name){
        if (resourceService.deleteResource(name)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
