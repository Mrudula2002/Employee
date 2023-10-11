package com.example.Employee.controller;

import com.example.Employee.entity.Employee;
import com.example.Employee.exceptions.UserNotFoundException;
import com.example.Employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/getemployees")
    public List<Employee> getEmployees(){
        return employeeService.getEmployees();
    }

    @GetMapping("/getid/{id}")
    public Optional<Employee> getEmployeeById(@PathVariable (value = "id") long id) throws UserNotFoundException {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/getname/{name}")
    public List<Employee> getEmployeeByName(@PathVariable (value ="name") String name) {
        return employeeService.getEmployeeByName(name);
    }

    @PostMapping("/add")
    public Employee addEmployee(@RequestBody Employee employee){
        return  employeeService.addEmployee(employee);
    }

    @PutMapping("/update/{id}")
    public Employee updateEmployee(@PathVariable (value ="id") long id, @RequestBody Employee updatedEmployee) throws UserNotFoundException {
        return employeeService.updateEmployee(id, updatedEmployee);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable (value = "id") long id) throws UserNotFoundException {
        return  employeeService.deleteEmployee(id);
    }

    @GetMapping("/getdept/{department}")
    public List<Employee> findByDepartment(@PathVariable (value = "department") String department)
    {
        return employeeService.findByDepartment(department);
    }

    @GetMapping("/getSal/{salary}")
    public List<Employee> findBySalaryLessThan(@PathVariable (value = "salary") double salary)
    {
        return employeeService.findBySalaryLessThan(salary);
    }

    @GetMapping("/getdeptsql/{department}")
    public List<Employee> findByDepartmentNativeSql(@PathVariable (value = "department")String department)
    {
        return employeeService.findByDepartmentNativeSql(department);
    }

    @GetMapping("/getsalrange/{minSalary}/{maxSalary}")
    public List<Employee> findBySalaryRange(@PathVariable (value = "minSalary") double minSalary,@PathVariable (value="maxSalary") double maxSalary)
    {
        return employeeService.findBySalaryRange(minSalary,maxSalary);
    }


}
