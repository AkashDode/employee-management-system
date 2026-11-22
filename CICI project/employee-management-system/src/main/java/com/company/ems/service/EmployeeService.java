package com.company.ems.service;

import com.company.ems.entity.Employee;
import com.company.ems.repository.EmployeeRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {

        this.repository = repository;
    }

    public List<Employee> findAll() {

        return repository.findAll();
    }

    public Employee save(Employee employee) {

        return repository.save(employee);
    }

    public Employee findById(Long id) {

        return repository.findById(id)
                .orElse(null);
    }

    public void delete(Long id) {

        repository.deleteById(id);
    }

    public List<Employee> search(String query) {

        if (query == null || query.isBlank()) {
            return repository.findAll();
        }

        String normalizedQuery = query.toLowerCase(Locale.ROOT);
        return repository.findAll()
                .stream()
                .filter(employee ->
                        containsIgnoreCase(employee.getName(), normalizedQuery)
                                || containsIgnoreCase(employee.getEmail(), normalizedQuery)
                                || containsIgnoreCase(employee.getDepartment(), normalizedQuery))
                .toList();
    }

    public long count() {

        return repository.count();
    }

    public long departmentCount() {

        return repository.findAll()
                .stream()
                .map(Employee::getDepartment)
                .filter(Objects::nonNull)
                .distinct()
                .count();
    }

    public double averageSalary() {

        return repository.findAll()
                .stream()
                .map(Employee::getSalary)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0);
    }

    public double highestSalary() {

        return repository.findAll()
                .stream()
                .map(Employee::getSalary)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .max()
                .orElse(0);
    }

    private boolean containsIgnoreCase(String value, String query) {

        return value != null && value.toLowerCase(Locale.ROOT).contains(query);
    }
}
