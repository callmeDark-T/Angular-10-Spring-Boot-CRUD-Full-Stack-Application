package com.javaguides.springcrud.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaguides.springcrud.exception.ResourceNotFoundException;
import com.javaguides.springcrud.model.Employee;
import com.javaguides.springcrud.repository.EmployeeRepository;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	//get all employees
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		
		return employeeRepository.findAll();
	}
	
	
	// create employee REST Api
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		
		return employeeRepository.save(employee);
	}
	
	// get employee by id rest api
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		
		Employee employee = employeeRepository.findById(id).orElseThrow
				(() -> new ResourceNotFoundException("Employee not exist with id: "+id));
		
		return ResponseEntity.ok(employee);
		
	}
	
	
	// update employee rest api
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> upatemdployee(@PathVariable Long id, @RequestBody Employee employee){
		
		// 1. retrieve employee to be update by id
		Employee employeeToBeUpdated = employeeRepository.findById(id).orElseThrow
				(() -> new ResourceNotFoundException("Employee not exist with id: "+id));
		
		//updating the retrived employee
		employeeToBeUpdated.setFirstName(employee.getFirstName());
		employeeToBeUpdated.setLastName(employee.getLastName());
		employeeToBeUpdated.setEmailId(employee.getEmailId());
		
		// saving the update employee 
		Employee updatedEmployee = employeeRepository.save(employeeToBeUpdated);
		
		return ResponseEntity.ok(updatedEmployee);
	}
	
	
	// delete employee rest api
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
		
		// 1. retrieve employee to be deleted by id
		Employee employeeToBeDeleted = employeeRepository.findById(id).orElseThrow
				(() -> new ResourceNotFoundException("Employee not exist with id: "+id));
		
		// delete employee
		employeeRepository.delete(employeeToBeDeleted);
		
		Map<String, Boolean> response = new HashMap<>();
		
		response.put("deleted", Boolean.TRUE);
		
		return ResponseEntity.ok(response);
				
	}

}
