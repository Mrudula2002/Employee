package com.example.Employee;

import com.example.Employee.entity.Address;
import com.example.Employee.entity.Employee;
import com.example.Employee.exceptions.UserNotFoundException;
import com.example.Employee.repository.EmployeeRepository;
import com.example.Employee.service.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {EmployeeServiceTest.class})
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;
    List<Address> addresses = new ArrayList<>();

    @Test
    public void test_getEmp() {

        List<Employee> myEmp;
        //This is the better approach while adding data in the real time using constructor such that we can have reference for retrieving or doing operations on data
        Address address = new Address();
        address.setAddId(1L);
        address.setCity("Bengaluru");
        address.setAddType("Temporary");
        addresses.add(address);
        Address address1 = new Address();
        address1.setAddId(2L);
        address1.setCity("Pune");
        address1.setAddType("Permanent");
        addresses.add(address1);
        myEmp = new ArrayList<>();
        myEmp.add(new Employee(1, "Mrudula", "Development", 10000,addresses ));
        myEmp.add(new Employee(2,"Reddy","Development",40000,addresses));
        when(employeeRepository.findAll()).thenReturn(myEmp);
        assertEquals(2,employeeService.getEmployees().size());
    }

    @Test
    public void test_getById() throws UserNotFoundException {
        List<Employee> myEmp = new ArrayList<>();
        myEmp.add(new Employee(1,"Reddy","Development",10000,addresses));
        myEmp.add(new Employee(2,"Mrudula","Development",10000,addresses));
        int empId=1;
        when(employeeRepository.findById(1L)).thenReturn(Optional.ofNullable(myEmp.get(0)));
        Optional<Employee> retrievedEmp = employeeService.getEmployeeById(empId);
        assertTrue(retrievedEmp.isPresent());
        Employee retrieved = retrievedEmp.get();
        assertEquals(empId,retrieved.getId());
    }

    @Test
    public void test_getByName()
    {
        List<Employee> myEmp = new ArrayList<>();
        myEmp.add(new Employee(1,"Mrudula","Development",10000,addresses));
        myEmp.add(new Employee(2,"Mrudula","Development",10000,addresses));
        String name = "Mrudula";
        when(employeeRepository.findByName("Mrudula")).thenReturn(myEmp);
        assertEquals(myEmp,employeeService.getEmployeeByName(name));
    }

    @Test
    public void test_add()
    {
        Employee employee = new Employee(1,"Mrudula","Development",10000,addresses);
        when(employeeRepository.save(employee)).thenReturn(employee);
        Employee addedEmp = employeeService.addEmployee(employee);
        assertNotNull(addedEmp);
        assertEquals("Mrudula",addedEmp.getName());
        assertEquals("Development",addedEmp.getDepartment());
        assertEquals(10000,addedEmp.getSalary());
        assertEquals(addresses,addedEmp.getAddresses());

    }

    @Test
    public void test_updateEmployee() throws UserNotFoundException {
        List<Address> addresses2 = new ArrayList<>();
        Address addresses1 = new Address();
        addresses1.setAddId(3L);
        addresses1.setCity("Hyderabad");
        addresses1.setAddType("Temporary");
        addresses2.add(addresses1);

        Employee existingEmployee = new Employee(1,"Mrudula","Development",10000,addresses);
        Employee updatedEmployee = new Employee();
        updatedEmployee.setId(existingEmployee.getId());
        updatedEmployee.setName("Sanjana");
        updatedEmployee.setDepartment("Development");
        updatedEmployee.setSalary(40000);
        updatedEmployee.setAddresses(addresses2);
        when(employeeRepository.findById(existingEmployee.getId())).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.save(existingEmployee)).thenReturn(updatedEmployee);
        assertEquals(updatedEmployee,employeeService.updateEmployee(existingEmployee.getId(), updatedEmployee));

    }

    @Test
    public void test_delete() throws UserNotFoundException {
        int empId = 1;
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(new Employee()));
        String result = employeeService.deleteEmployee(empId);
        assertEquals("Employee details deleted successfully!",result);
    }

}
