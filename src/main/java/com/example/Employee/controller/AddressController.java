package com.example.Employee.controller;

import com.example.Employee.entity.Address;
import com.example.Employee.exceptions.UserNotFoundException;
import com.example.Employee.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/getadd/{id}")
    public Object getAddressById(@PathVariable (value ="id") long id) throws  UserNotFoundException {
        return addressService.getAddressById(id);
    }
}
