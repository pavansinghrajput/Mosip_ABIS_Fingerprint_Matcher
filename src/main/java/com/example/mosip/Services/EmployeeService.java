package com.example.mosip.Services;

import com.example.mosip.Beans.Employee;
import com.example.mosip.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {


    @Autowired
     EmployeeRepository erjpa;;


    public  void createEmployee(Employee e) {
    erjpa.save(e);
    }

    public List<Employee> getAllEmployees() {
        return erjpa.findAll();
    }
}
