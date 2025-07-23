package com.ayrton.clinic.controller;

import com.ayrton.clinic.dto.EmployeeDTO;
import com.ayrton.clinic.model.Employee;
import com.ayrton.clinic.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeDTO dto){
        Employee employee = employeeService.createEmployee(dto);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees(){
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Employee>> getById(@RequestParam String id){
        Optional<Employee> employee = employeeService.getById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<List<Employee>> getByNameIgnoreCase(@RequestParam String name){
        List<Employee> employees = employeeService.getByNameIgnoreCase(name);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/email")
    public ResponseEntity<List<Employee>> getByEmailIgnoreCase(@RequestParam String email){
        List<Employee> employees = employeeService.getByEmailIgnoreCase(email);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/phone")
    public ResponseEntity<List<Employee>> getByPhoneIgnoreCase(@RequestParam String phone){
        List<Employee> employees = employeeService.getByPhoneIgnoreCase(phone);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/rating")
    public ResponseEntity<List<Employee>> getByRating(@RequestParam double rating){
        List<Employee> employees = employeeService.getByRating(rating);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PutMapping("/name")
    public ResponseEntity<List<Employee>> updateEmployee(@RequestParam String name, @RequestBody EmployeeDTO dto){
        List<Employee> updatedList = employeeService.updateEmployee(name, dto);

        if (updatedList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(updatedList);
    }

    @DeleteMapping("/name")
    public ResponseEntity<Void> deleteEmployee(@RequestParam String name){
        if (employeeService.deleteEmployee(name)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
