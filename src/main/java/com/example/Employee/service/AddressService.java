package com.example.Employee.service;
import com.example.Employee.entity.Employee;
import com.example.Employee.exceptions.UserNotFoundException;
import com.example.Employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public interface AddressService {


    Object getAddressById(long id) throws UserNotFoundException;

}
