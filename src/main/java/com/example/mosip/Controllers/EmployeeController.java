package com.example.mosip.Controllers;

import com.example.mosip.Beans.Employee;
import com.example.mosip.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {
   // @RequestMapping("/hello")
    @Autowired
    EmployeeService es;
    @PostMapping("/createEmployee")
    public void createEmployee(@RequestBody Employee e){  //mapping the JSON Body tot he object directly

         es.createEmployee(e);
    }
    @GetMapping("/hello")
    public List<Employee> getHello(){  //mapping the JSON Body tot he object directly

       return es.getAllEmployees();
    }
}
