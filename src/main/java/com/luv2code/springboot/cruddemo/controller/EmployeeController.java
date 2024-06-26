package com.luv2code.springboot.cruddemo.controller;

import com.luv2code.springboot.cruddemo.entity.Employee;
import com.luv2code.springboot.cruddemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/list")
    public String listEmployees(Model theModel){
        //get the list of the employyes from db
        List<Employee> theEmployees =employeeService.findAll();
        //store it on model
        theModel.addAttribute("employees",theEmployees);
        return "employees/list-employees";
    }

    @GetMapping("/showFormForAdd")
    public String showFormToADD(Model thModel){
        Employee thEmployee = new Employee();
        thModel.addAttribute("employee", thEmployee);
        return "employees/employee-form";
    }

    @PostMapping("/save")
    public String saveForm(@ModelAttribute("employee") Employee thEmployee){
        employeeService.save(thEmployee);
        //use redurect to prevent duplicate submission
        return "redirect:/employees/list";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("employeeId") int id, Model thModel)
    {
        //get employe based on id
        Employee thEmployee = employeeService.findById(id);
        thModel.addAttribute("employee",thEmployee);
        return "employees/employee-form";
    }
    @GetMapping("/delete")
    public String delete(@RequestParam("employeeId") int id)
    {
        employeeService.deleteById(id);
        return "redirect:/employees/list";
    }
}
