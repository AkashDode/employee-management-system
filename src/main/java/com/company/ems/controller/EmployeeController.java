package com.company.ems.controller;

import com.company.ems.entity.Employee;
import com.company.ems.service.EmployeeService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    /**
     * Dashboard / Home Page
     */
    @GetMapping("/")
    public String viewHome(Model model) {

        model.addAttribute(
                "employees",
                service.findAll());

        return "index";
    }

    /**
     * Add Employee Page
     */
    @GetMapping("/add")
    public String addEmployee(Model model) {

        model.addAttribute(
                "employee",
                new Employee());

        return "add-employee";
    }

    /**
     * Save Employee
     */
    @PostMapping("/save")
    public String saveEmployee(
            @ModelAttribute Employee employee) {

        service.save(employee);

        return "redirect:/";
    }

    /**
     * Edit Employee Page
     */
    @GetMapping("/edit/{id}")
    public String editEmployee(
            @PathVariable Long id,
            Model model) {

        Employee employee =
                service.findById(id);

        model.addAttribute(
                "employee",
                employee);

        return "edit-employee";
    }

    /**
     * Update Employee
     */
    @PostMapping("/update")
    public String updateEmployee(
            @ModelAttribute Employee employee) {

        service.save(employee);

        return "redirect:/";
    }

    /**
     * Delete Employee
     */
    @GetMapping("/delete/{id}")
    public String deleteEmployee(
            @PathVariable Long id) {

        service.delete(id);

        return "redirect:/";
    }
}