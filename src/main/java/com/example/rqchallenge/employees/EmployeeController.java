package com.example.rqchallenge.employees;

import com.example.rqchallenge.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class EmployeeController implements IEmployeeController {
    Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    String baseURI = "https://dummy.restapiexample.com/api/v1";
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() throws IOException {
        List<Employee> allEmp = getAllEmp();
        if(allEmp == null)
            return ResponseEntity.internalServerError().build();
        return ResponseEntity.ok(allEmp);

    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
        List<Employee> employees = getAllEmp();
        if(employees == null)
            return ResponseEntity.internalServerError().build();
        List<Employee> matchingEmps = new ArrayList<>();
        for(Employee emp : employees) {
            if(emp.getName().contains(searchString))
                matchingEmps.add(emp);
        }
        if( matchingEmps.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(matchingEmps);
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) {
        String uri = baseURI + "/employee/" + id;
        try {
            Map response = restTemplate.getForObject(uri,Map.class);
            if (response != null && response.get("status").equals("success")) {
                Employee emp = new Employee((Map<String, Object>) response.get("data"));
                return ResponseEntity.ok(emp);
            }
        } catch (RestClientException ex) {
            logger.error(ex.getMessage());
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        List<Employee> emps = getAllEmp();
        if (emps == null)
            return ResponseEntity.internalServerError().build();
        List<Employee> employees = selectTopKEmp(emps,1);

        if(employees.isEmpty())
            ResponseEntity.notFound().build();
        return ResponseEntity.ok(employees.get(0).getSalary());
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        List<Employee> emps = getAllEmp();
        if (emps == null)
            return ResponseEntity.internalServerError().build();
        List<Employee> employees = selectTopKEmp(emps,10);

        if(employees.isEmpty())
            ResponseEntity.notFound().build();
        List<String> empNames = new ArrayList<>();
        for(Employee emp : employees)
            empNames.add(emp.getName());

        return ResponseEntity.ok(empNames);
    }

    @Override
    public ResponseEntity<Employee> createEmployee(Map<String, Object> employeeInput) {

        String uri = baseURI + "/create";
        try {
            Map response = restTemplate.postForObject(uri, employeeInput, Map.class);
            if (response != null && response.get("status").equals("success")) {
                Employee emp = Employee.fromMap((Map<String, Object>) response.get("data"));
                return ResponseEntity.ok(emp);
            }
        } catch (RestClientException ex) {
            logger.error(ex.getMessage());
        }
        return ResponseEntity.internalServerError().build();
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        String uri = baseURI + "/delete".concat("/") .concat(id);
        try {
            restTemplate.delete(uri);
            return ResponseEntity.ok("Deleted emp with Id: ".concat(id));
        }
        catch (RestClientException exception) {
            logger.error(exception.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    private List<Employee> getAllEmp() {
        String uri = baseURI + "/employees";
        List<Employee> allEmp = new ArrayList<>();
        try {

            Map empList = restTemplate.getForObject(uri, Map.class);
            if (empList != null) {
                List emps = (List) empList.get("data");
                for (Object emp : emps) {

                    allEmp.add(new Employee((Map)emp));
                }
            }
            return allEmp;
        } catch (HttpClientErrorException exception) {
            logger.error("Error in reading employees {}", exception.getStatusText());
            return null;
        }
    }

    private List<Employee> selectTopKEmp(List<Employee> employees, int k) {
        Set<Employee> sortedSet = new TreeSet<>(Comparator.comparingInt(Employee::getSalary));
        sortedSet.addAll(employees);
        return sortedSet.stream().limit(k).collect(Collectors.toList());
    }
}
