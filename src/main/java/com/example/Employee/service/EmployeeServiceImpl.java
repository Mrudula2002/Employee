package com.example.Employee.service;

import com.example.Employee.entity.Address;
import com.example.Employee.entity.Employee;
import com.example.Employee.exceptions.UserNotFoundException;
import com.example.Employee.repository.AddressRepository;
import com.example.Employee.repository.EmployeeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Queue;


@Service
public class EmployeeServiceImpl implements EmployeeService{


    //Need to import correct library that is Logger from log4j library not from logging else it throws error
    private static final Logger logger = Logger.getLogger(EmployeeServiceImpl.class);
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private EntityManager entityManager;

    public List<Employee> getEmployees() {
     logger.info("Entered into getEmployees() method of EmployeeServiceImpl");
        List<Employee> values = employeeRepository.findAll();
        if(values.isEmpty())
            return Collections.emptyList();
        return values;
    }

    public Optional<Employee> getEmployeeById(long id) throws UserNotFoundException {
        logger.info("Entered into getEmployeeById() method of EmployeeServiceImpl");
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent())
            return employee;
        else
        {
            logger.error("Exception has occurred in getEmployeeById {}" );
            throw new UserNotFoundException("User not found with id:" + id);
        }
    }

    public List<Employee> getEmployeeByName(String name)  {
        logger.info("Entered into getEmployeeByName() method of EmployeeServiceImpl");
       return employeeRepository.findByName(name);
    }

    public Employee addEmployee(Employee employee) {
        logger.info("Entered into addEmployee() method of EmployeeServiceImpl");
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(long id, Employee updatedEmployee) throws UserNotFoundException {
        logger.info("Entered into updateEmployee() method of EmployeeServiceImpl");
        Optional<Employee> existingEmployee = employeeRepository.findById(id);
            if (existingEmployee.isPresent()) {
                Employee employee = existingEmployee.get();
            employee.setName(updatedEmployee.getName());
            employee.setDepartment(updatedEmployee.getDepartment());
            employee.setSalary(updatedEmployee.getSalary());
            List<Address> existing = null;
            if (employee.getAddresses() != null) {
                List<Address> newAdd = updatedEmployee.getAddresses();
                System.out.println(newAdd);
                existing = employee.getAddresses();
                for (int i = 0; i < existing.size(); i++) {
                    existing.get(i).setAddId(newAdd.get(i).getAddId());
                    existing.get(i).setCity(newAdd.get(i).getCity());
                    existing.get(i).setAddType(newAdd.get(i).getAddType());
                    addressRepository.save(existing.get(i));
                }
            }
            employee.setAddresses(existing);
            return employeeRepository.save(employee);
        }
            else
            {
                logger.error("Exception has occurred in updateEmployee {}" );
                throw new UserNotFoundException("User Not found with id:"+id);
            }
    }


    public String deleteEmployee(long id) throws UserNotFoundException {
        logger.info("Entered into deleteEmployee() method of EmployeeServiceImpl");
        Employee employee = employeeRepository.findById(id).get();
        if(employee!= null)
        {
            employeeRepository.deleteById(id);
            return "Employee details deleted successfully!";
        }
        else {
            logger.error("Exception has occurred in deleteEmployee {}");
            throw new UserNotFoundException("User not found with id:" + id);
        }
    }

    public List<Employee> findByDepartment(String department) {
        String typedQuery = "SELECT e FROM Employee e WHERE e.department = :department";
        TypedQuery<Employee> query = entityManager.createQuery(typedQuery,Employee.class);
        query.setParameter("department",department);
        return query.getResultList();
    }

    public List<Employee> findBySalaryLessThan(double salary) {
        TypedQuery<Employee> query = entityManager.createQuery("findBySalary",Employee.class);
//        Query query1= entityManager.createQuery("findBySalary",Employee.class);
        query.setParameter("salary",salary);
        return query.getResultList();
    }

    public List<Employee> findByDepartmentNativeSql(String department) {
        Query query = entityManager.createNativeQuery("SELECT * FROM employee WHERE department =:department",Employee.class);
        query.setParameter("department",department);
        return query.getResultList();
    }

    public List<Employee> findBySalaryRange(double minSalary, double maxSalary) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery= criteriaBuilder.createQuery(Employee.class);
        Root<Employee> root = criteriaQuery.from(Employee.class);
        Predicate salaryPredicate = criteriaBuilder.between(root.get("salary"),minSalary,maxSalary);
        criteriaQuery.select(root).where(salaryPredicate);
        Query query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();

    }


}
