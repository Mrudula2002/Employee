package com.example.Employee.service;

import com.example.Employee.entity.Address;
import com.example.Employee.exceptions.UserNotFoundException;
import com.example.Employee.repository.AddressRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService{
    @Autowired
    private AddressRepository addressRepository;

    private static Logger logger = Logger.getLogger(AddressServiceImpl.class);

    public Object getAddressById(long id) throws UserNotFoundException {
        logger.info("Entered into getAddressById() method of EmployeeServiceImpl");
       Optional<Address> address =  addressRepository.findById(id);
       if(address.isPresent()){
           Address address1 = address.get();
        return address1.getEmp();
       }
       else
       {
           logger.error("Exception has occurred in getAddressById {}" );
           throw new UserNotFoundException("User not found with id:"+id);
       }
    }
}
