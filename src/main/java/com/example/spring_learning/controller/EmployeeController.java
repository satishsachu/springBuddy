
package com.example.spring_learning.controller;

import com.example.spring_learning.entity.Employee;
import com.example.spring_learning.repository.EmployeeRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeRepository repository;

    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Employee> create(
            @RequestBody Employee employee) {

        Employee saved = repository.save(employee);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

    // READ ALL
    @GetMapping
    public List<Employee> getAll() {
        return repository.findAll();
    }

    // READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(
            @PathVariable Long id) {

        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                    ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(
            @PathVariable Long id,
            @RequestBody Employee details) {

        return repository.findById(id)
                .map(employee -> {
                    employee.setName(details.getName());
                    employee.setEmail(details.getEmail());
                    employee.setDepartment(details.getDepartment());

                    Employee updated = repository.save(employee);

                    return ResponseEntity.ok(updated);
                })
                .orElseGet(() ->
                    ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
