package com.example.rqchallenge.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    public static final String PROFILE_IMAGE = "profile_image";
    public static final String EMPLOYEE_SALARY = "employee_salary";
    public static final String EMPLOYEE_AGE = "employee_age";
    public static final String EMPLOYEE_NAME = "employee_name";
    public static final String ID = "id";

    @JsonProperty(ID)
    int id;
    @JsonProperty(EMPLOYEE_SALARY)
    int salary;
    @JsonProperty(EMPLOYEE_NAME)
    String name;
    @JsonProperty(EMPLOYEE_AGE)
    int age;
    @JsonProperty(PROFILE_IMAGE)
    String profileImage;

    public Employee(String name, int age, int salary, String profileImage){
        this.name = name;
        this.age = age;
        this.salary= salary;
        this.profileImage = profileImage;
    }

    public Employee(@NonNull Map<String, Object> inputEmp) {
        this.name = inputEmp.get(EMPLOYEE_NAME).toString();
        this.age = Integer.parseInt(inputEmp.get(EMPLOYEE_AGE).toString());
        this.salary = Integer.parseInt(inputEmp.get(EMPLOYEE_SALARY).toString());
        this.profileImage = inputEmp.get(PROFILE_IMAGE).toString();
        this.id = Integer.parseInt(inputEmp.get(ID).toString());
    }

    public static Employee fromMap(@NonNull Map<String, Object> inputEmp) {
        Employee emp = new Employee();
        emp.setName( inputEmp.get("name").toString());
        emp.setAge( Integer.parseInt(inputEmp.get("age").toString()));
        emp.setSalary(Integer.parseInt(inputEmp.get("salary").toString()));
        emp.setId(Integer.parseInt(inputEmp.get("id").toString()));
        return emp;
    }
}
