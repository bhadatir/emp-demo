package com.example.emp_demo.controller;

import com.example.emp_demo.dto.EmployeeDTO;
import com.example.emp_demo.entity.Employee;
import com.example.emp_demo.entity.ValidationGroups;
import com.example.emp_demo.service.EmployeeService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/dto-demo/{id}")
    public  ResponseEntity<EmployeeDTO> getEmpById(@PathVariable Long id){
        Employee employee=service.getEmployeeById(id);

        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
        return ResponseEntity.ok(employeeDTO);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(service.getAllEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getEmployeeById(id));
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
        return new ResponseEntity<>(service.saveEmployee(employee), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable Long id,
            @Validated(ValidationGroups.Update.class) @RequestBody Employee employee) {
        return ResponseEntity.ok(service.updateEmployee(id, employee));
    }

    @PatchMapping("/{id}/{department}")
    public ResponseEntity<Employee> updateDepartment(@PathVariable Long id, @PathVariable String department) {
        return ResponseEntity.ok(service.patchEmployee(id, department));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        service.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }

    @PostMapping("/{id}/image")
    public ResponseEntity<String> uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        String response = service.uploadImage(id, file);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}/image", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> downloadImage(@PathVariable Long id) throws IOException {
        byte[] image = service.downloadImage(id);
        return ResponseEntity.ok(image);
    }
}
