package com.example.Employee.service;

import com.example.Employee.entity.Employee;
import com.example.Employee.exceptions.UserNotFoundException;
import com.example.Employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {


    /* EmployeeRepository employeeRepository ;

     default List<Employee> getSalaryLessThan(double salary)
     {
          return employeeRepository.findBySalaryLessThan(salary);
     }*/

     List<Employee> getEmployees();
     Optional<Employee> getEmployeeById(long id) throws UserNotFoundException;
     List<Employee> getEmployeeByName(String name);
     Employee addEmployee(Employee employee);
     Employee updateEmployee(long id, Employee updatedEmployee) throws UserNotFoundException;
     String deleteEmployee(long id) throws UserNotFoundException;
     //Typed Query
     List<Employee> findByDepartment(String department);
     //Named Query
     List<Employee> findBySalaryLessThan(double salary);
     //Native SQL Query
     List<Employee> findByDepartmentNativeSql(String department);
     //Criteria Query
     List<Employee> findBySalaryRange(double minSalary, double maxSalary);



    }
