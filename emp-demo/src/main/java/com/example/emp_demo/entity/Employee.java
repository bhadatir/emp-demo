package com.example.emp_demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is mandatory", groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    private String firstName;

    @NotBlank(message = "Last name is mandatory", groups = {ValidationGroups.Create.class})
    private String lastName;

    @Email(message = "Email should be valid", groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    @NotBlank(message = "Email is mandatory")
    @Column(unique = true)
    private String email;

    @NotNull(message = "Department cannot be null", groups = {ValidationGroups.Create.class})
    private String department;

    @Min(value = 0,message = "Salary cannot be negative", groups = {ValidationGroups.Update.class})
    private double salary;

    private String profileImageUrl;
}
