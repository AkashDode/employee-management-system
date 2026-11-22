package com.company.ems.controller;

import com.company.ems.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DashboardController {

    private static final int MAX_QUERY_LENGTH = 100;

    private final EmployeeService employeeService;

    public DashboardController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/dashboard")
    public String dashboard(
            @RequestParam(name = "q", required = false) String q,
            Model model) {

        String query = q == null ? "" : q.trim();
        if (query.length() > MAX_QUERY_LENGTH) {
            query = query.substring(0, MAX_QUERY_LENGTH);
        }

        model.addAttribute("employees", employeeService.search(query));
        model.addAttribute("totalEmployees", employeeService.count());
        model.addAttribute("departmentCount", employeeService.departmentCount());
        model.addAttribute("averageSalary", employeeService.averageSalary());
        model.addAttribute("highestSalary", employeeService.highestSalary());
        model.addAttribute("query", query);

        return "dashboard";
    }
}
