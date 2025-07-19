package com.ayrton.clinic.repository;

import com.ayrton.clinic.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {

    Optional<Employee> findById(String id);

    List<Employee> findAll();

    List<Employee> findByNameIgnoreCase(String name);

    List<Employee> findByEmailIgnoreCase(String email);

    List<Employee> findByPhoneIgnoreCase(String phone);

    List<Employee> findByRating(double rating);
}
