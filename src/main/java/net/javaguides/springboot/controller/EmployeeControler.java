package net.javaguides.springboot.controller;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/employees")
public class EmployeeControler {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    //build create Employee Rest Api
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee){
     return  employeeRepository.save(employee);
    }

    //build get employee by id
    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeId(@PathVariable  long id){
        Employee employee = employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("EMPLOYEE NIT EXIST BY THIS ID :"+ id));
        return  ResponseEntity.ok(employee);
    }

//    bulid update employee rest api
    @PutMapping("{id}")
    public  ResponseEntity<Employee> updateEmployee(@PathVariable long id, @RequestBody Employee employeeDetails){
        Employee updateemployee = employeeRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("NHI HAI EMPLOYEE IDHER IS ID KA : "+ id));
        updateemployee.setFirstName(employeeDetails.getFirstName());
        updateemployee.setLastName(employeeDetails.getLastName());
        updateemployee.setEmailId(employeeDetails.getEmailId());

        employeeRepository.save(updateemployee);
        return ResponseEntity.ok(updateemployee);
    }

//    build delete rest api
    @DeleteMapping("{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable long id){
        Employee deleteEmployee = employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("EMPLOYEE NOT FOUND "+ id));
        employeeRepository.delete(deleteEmployee);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
