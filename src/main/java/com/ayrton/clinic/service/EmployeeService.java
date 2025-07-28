package com.ayrton.clinic.service;

import com.ayrton.clinic.dto.EmployeeDTO;
import com.ayrton.clinic.model.Employee;
import com.ayrton.clinic.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee createEmployee(EmployeeDTO dto){
        Employee employee = new Employee();
        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setPhone(dto.getPhone());
        employee.setRating(0);
        employee.setSchedules(dto.getSchedules());
        employee.setSpeciality(dto.getSpeciality());
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    public Optional<Employee> getById(String id){
        return employeeRepository.findById(id);
    }

    public List<Employee> getByNameIgnoreCase(String name){
        return employeeRepository.findByNameIgnoreCase(name);
    }

    public List<Employee> getByEmailIgnoreCase(String email){
        return employeeRepository.findByEmailIgnoreCase(email);
    }

    public List<Employee> getByPhoneIgnoreCase(String phone){
        return employeeRepository.findByPhoneIgnoreCase(phone);
    }

    public List<Employee> getByRating(double rating){
        return employeeRepository.findByRating(rating);
    }

    public Optional<Employee> updateEmployee(String id, EmployeeDTO dto){
        return employeeRepository.findById(id)
                .map(employee -> {
                    employee.setName(dto.getName());
                    employee.setEmail(dto.getEmail());
                    employee.setPhone(dto.getPhone());
                    employee.setSchedules(dto.getSchedules());
                    employee.setSpeciality(dto.getSpeciality());
                    return employeeRepository.save(employee);
                });
    }

    public boolean deleteEmployee(String id){
        return employeeRepository.findById(id)
                .map(employee -> {
                    employeeRepository.delete(employee);
                    return true;
                }).orElse(false);
    }
}
