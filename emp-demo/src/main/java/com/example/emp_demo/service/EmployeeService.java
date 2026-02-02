package com.example.emp_demo.service;

import com.example.emp_demo.dto.EmployeeDTO;
import com.example.emp_demo.entity.Employee;
import com.example.emp_demo.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public EmployeeDTO getEmployeeDTO(Long id){
        Employee employee = repository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    public Employee saveEmployeeDTO(EmployeeDTO dto){
        Employee employee = modelMapper.map(dto, Employee.class);
        return repository.save(employee);
    }

    private final String FOLDER_PATH = "uploads/";

    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }

    public Employee saveEmployee(Employee employee)
    {
        return repository.save(employee);
    }

    @CacheEvict(value = "employeeImages", key = "#id")
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = getEmployeeById(id);
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setDepartment(employeeDetails.getDepartment());
        employee.setSalary(employeeDetails.getSalary());
        return repository.save(employee);
    }

    @CacheEvict(value = "employeeImages", key = "#id")
    public Employee patchEmployee(Long id, String department) {
        Employee employee = getEmployeeById(id);
        employee.setDepartment(department);
        return repository.save(employee);
    }

    @CacheEvict(value = "employeeImages", key = "#id")
    public void deleteEmployee(Long id) {
        repository.deleteById(id);
    }

    public String uploadImage(Long id, MultipartFile file) throws IOException {
        Employee employee = getEmployeeById(id);

        File directory = new File(FOLDER_PATH);
        if (!directory.exists()) {
            directory.mkdir();
        }

        String filePath = FOLDER_PATH + id + "_" + file.getOriginalFilename();
        file.transferTo(new File(System.getProperty("user.dir") + "/" + filePath));

        employee.setProfileImageUrl(filePath);
        repository.save(employee);

        return "Image uploaded successfully: " + filePath;
    }

    @Cacheable(value = "employeeImages", key = "#id")
    public byte[] downloadImage(Long id) throws IOException {
        Employee employee = getEmployeeById(id);
        String path = employee.getProfileImageUrl();

        if (path == null) {
            throw new RuntimeException("No image found for this employee");
        }

        return Files.readAllBytes(new File(System.getProperty("user.dir") + "/" + path).toPath());
    }
}