package com.example.rqchallenge;

import com.example.rqchallenge.employees.EmployeeController;
import com.example.rqchallenge.entity.Employee;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RqChallengeApplicationTests {
    private static RestTemplate restTemplate;

    @Mock
    RestTemplate customRestTemplate;

    @InjectMocks
    private EmployeeController employeeController;

    @LocalServerPort
    private int port;
    private String baseUrl = "http://localhost";

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "");
    }

    @Test
    void contextLoads() {
    }

    @Test
    void testGetEmployeesByNameSearchReturnInternalServerError() throws IOException {
        doThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(customRestTemplate).getForObject(eq("https://dummy.restapiexample.com/api/v1/employees"), eq(Map.class));

        ResponseEntity<List<Employee>> res = employeeController.getEmployeesByNameSearch("test");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,res.getStatusCode());
    }
    @Test
    void testGetEmployeesByNameSearchReturnHTTPOK() throws IOException {
        LinkedHashMap<String, Object> empMap = getEmpMap(1,"foo", 7000, 40, "");

        Employee emp = new Employee(empMap);
        List<Employee> allEmp = new ArrayList<>();
        allEmp.add(emp);
        ResponseEntity<List<Employee>> expectedResponse = ResponseEntity.ok(allEmp);
        Map<String, Object> map = new HashMap<>();
        List<LinkedHashMap<String,Object>> retValue = new ArrayList<>();
        retValue.add(empMap);
        map.put("data", retValue);
        when(customRestTemplate.getForObject(eq("https://dummy.restapiexample.com/api/v1/employees"), eq(Map.class))).thenReturn(map);

        var response = employeeController.getEmployeesByNameSearch("foo");

        assertEquals(response, expectedResponse);
    }


    @Test
    void testGetAllEmployeesFailedOnServer() throws IOException {
        doThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(customRestTemplate).getForObject(eq("https://dummy.restapiexample.com/api/v1/employees"), eq(Map.class));

        ResponseEntity<List<Employee>> res = employeeController.getAllEmployees();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,res.getStatusCode());
    }
    @Test
    void testGetAllEmployeesReturnsHTTPOK() throws IOException {
        LinkedHashMap<String, Object> empMap = getEmpMap(1,"foo", 7000, 40, "");

        Employee emp = new Employee(empMap);
        List<Employee> allEmp = new ArrayList<>();
        allEmp.add(emp);
        ResponseEntity<List<Employee>> expectedResponse = ResponseEntity.ok(allEmp);
        Map<String, Object> map = new HashMap<>();
        List<LinkedHashMap<String,Object>> retValue = new ArrayList<>();
        retValue.add(empMap);
        map.put("data", retValue);
        when(customRestTemplate.getForObject(eq("https://dummy.restapiexample.com/api/v1/employees"), eq(Map.class))).thenReturn(map);

        var response = employeeController.getAllEmployees();

        assertEquals(response, expectedResponse);
    }

    @Test
    void testGetEmployeeByIdFailedOnServer() throws IOException {
        doThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(customRestTemplate).getForObject(eq("https://dummy.restapiexample.com/api/v1/employees"), eq(Map.class));

        ResponseEntity<Employee> res = employeeController.getEmployeeById("99");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,res.getStatusCode());
    }
    @Test
    void testGetEmployeeByIdReturnsHTTPOK() throws IOException {
        LinkedHashMap<String, Object> empMap = getEmpMap(1,"foo", 7000, 40, "");

        Employee emp = new Employee(empMap);


        ResponseEntity<Employee> expectedResponse = ResponseEntity.ok(emp);
        Map<String, Object> map = new HashMap<>();
        List<LinkedHashMap<String,Object>> retValue = new ArrayList<>();
        retValue.add(empMap);
        map.put("data", retValue);
        when(customRestTemplate.getForObject(eq("https://dummy.restapiexample.com/api/v1/employees"), eq(Map.class))).thenReturn(map);

        var response = employeeController.getEmployeeById("1");

        assertEquals( expectedResponse,response);
    }


    @Test
    void testGetHighestSalaryOfEmployeesFailedOnServer() throws IOException {
        doThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(customRestTemplate).getForObject(eq("https://dummy.restapiexample.com/api/v1/employees"), eq(Map.class));

        ResponseEntity<Integer> res = employeeController.getHighestSalaryOfEmployees();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,res.getStatusCode());
    }
    @Test
    void testGetHighestSalaryOfEmployeesReturnsHTTPOK() throws IOException {
        LinkedHashMap<String, Object> empMap = getEmpMap(1,"foo", 7000, 40, "");

        Employee emp = new Employee(empMap);


        ResponseEntity<Integer> expectedResponse = ResponseEntity.ok(emp.getSalary());
        Map<String, Object> map = new HashMap<>();
        List<LinkedHashMap<String,Object>> retValue = new ArrayList<>();
        retValue.add(empMap);
        map.put("data", retValue);
        when(customRestTemplate.getForObject(eq("https://dummy.restapiexample.com/api/v1/employees"), eq(Map.class))).thenReturn(map);

        var response = employeeController.getHighestSalaryOfEmployees();

        assertEquals( expectedResponse,response);
    }


    @Test
    void testGetTopTenHighestEarningEmployeeNamesFailedOnServer() throws IOException {
        doThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(customRestTemplate).getForObject(eq("https://dummy.restapiexample.com/api/v1/employees"), eq(Map.class));

        ResponseEntity<List<String>> res = employeeController.getTopTenHighestEarningEmployeeNames();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,res.getStatusCode());
    }
    @Test
    void testGetTopTenHighestEarningEmployeeNamesReturnsHTTPOK() throws IOException {
        LinkedHashMap<String, Object> empMap = getEmpMap(1,"foo", 7000, 40, "");

        Employee emp = new Employee(empMap);


        ResponseEntity<List<String>> expectedResponse = ResponseEntity.ok(Arrays.asList(emp.getName()));
        Map<String, Object> map = new HashMap<>();
        List<LinkedHashMap<String,Object>> retValue = new ArrayList<>();
        retValue.add(empMap);
        map.put("data", retValue);
        when(customRestTemplate.getForObject(eq("https://dummy.restapiexample.com/api/v1/employees"), eq(Map.class))).thenReturn(map);

        var response = employeeController.getTopTenHighestEarningEmployeeNames();

        assertEquals( expectedResponse,response);
    }

    @Test
    void testCreateEmployeeReturnsInternalServerError() {
        Map<String, Object> inputEmp = new HashMap<>();
        inputEmp.put("name", "foo");
        inputEmp.put("age", "40");
        inputEmp.put("salary", "7000");

        doThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR))
                .when(customRestTemplate)
                .postForObject(eq("https://dummy.restapiexample.com/api/v1/create"), eq(inputEmp),eq(Map.class));

        var response = employeeController.createEmployee(inputEmp);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR , response.getStatusCode());
    }

    @Test
    void testCreateEmployeeReturnsHTTPOK() {
        Map<String, Object> inputEmp = new HashMap<>();
        inputEmp.put("name", "foo");
        inputEmp.put("age", "40");
        inputEmp.put("salary", "7000");
        Map<String,Object> expected = new HashMap<>();
        expected.put("name", "foo");
        expected.put("age", "40");
        expected.put("salary", "7000");
        expected.put("id", 1);
        HashMap<String,Object> expecteOutput = new HashMap<>();
        expecteOutput.put("status", "success");
        expecteOutput.put("data", expected);
        when(customRestTemplate
                .postForObject(eq("https://dummy.restapiexample.com/api/v1/create"), eq(inputEmp),eq(Map.class)))
                .thenReturn(expecteOutput);

        var response = employeeController.createEmployee(inputEmp);
        assertEquals(HttpStatus.OK , response.getStatusCode());
        Employee employee = response.getBody();
        assertEquals(expected.get("name"), employee.getName());
        assertEquals(Integer.parseInt(expected.get("age").toString()), employee.getAge());
        assertEquals(Integer.parseInt(expected.get("salary").toString()), employee.getSalary());
    }


    @Test
    void testDeleteEmployeeReturnsInternalServerError() {
        doThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR))
                .when(customRestTemplate)
                .delete(eq("https://dummy.restapiexample.com/api/v1/delete/4"));

        var response = employeeController.deleteEmployeeById("4");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR , response.getStatusCode());

    }

    @Test
    void testDeleteEmployeeReturnsHttpOK() {
        doNothing()
                .when(customRestTemplate)
                .delete(eq("https://dummy.restapiexample.com/api/v1/delete/4"));

        var response = employeeController.deleteEmployeeById("4");
        assertEquals(HttpStatus.OK , response.getStatusCode());

    }
    private LinkedHashMap<String, Object> getEmpMap(int id, String name, int sal, int age, String profileImage) {
        LinkedHashMap<String,Object> empMap = new LinkedHashMap<String,Object>();
        empMap.put(Employee.ID, id);
        empMap.put(Employee.EMPLOYEE_NAME, name);
        empMap.put(Employee.EMPLOYEE_SALARY, sal);
        empMap.put(Employee.EMPLOYEE_AGE, age);
        empMap.put(Employee.PROFILE_IMAGE, profileImage);
        return empMap;
    }
}
