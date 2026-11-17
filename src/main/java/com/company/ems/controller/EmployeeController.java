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

    @GetMapping("/")
    public String viewHome(Model model) {

        model.addAttribute(
                "employees",
                service.findAll());

        return "index";
    }

    @GetMapping("/add")
    public String addEmployee(Model model) {

        model.addAttribute(
                "employee",
                new Employee());

        return "add-employee";
    }

    @PostMapping("/save")
    public String saveEmployee(
            @ModelAttribute Employee employee) {

        service.save(employee);

        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(
            @PathVariable Long id) {

        service.delete(id);

        return "redirect:/";
    }
}