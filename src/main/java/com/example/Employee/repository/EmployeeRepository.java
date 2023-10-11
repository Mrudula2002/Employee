package com.example.Employee.repository;

import com.example.Employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
   //Derived Query
    List<Employee> findByName(String name);
//    List<Employee> findBySalaryLessThan(double salary);
}
