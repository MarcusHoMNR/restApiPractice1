package com.example.restapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void cleanRepository() {
        companyRepository.clearAll();
    }

    List<Employee> getEmployees(){
        List<Employee> employeeList = new ArrayList<>();
        Employee employee = new Employee(1, "Marcus", 19, "Male", 1920213);
        employeeList.add(employee);
        return employeeList;
    }

    @Test
    void should_get_all_companies_when_perform_get_given_companies() throws Exception {
        //given
        List<Employee> employees = getEmployees();
        Company company1 = new Company(1, "Spring", employees);
        Company company2 = new Company(2, "Spring2", employees);

        companyRepository.create(company1);
        companyRepository.create(company2);

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value("Spring"))
                .andExpect(jsonPath("$[0].employees[0].id").value(employees.get(0).getId()))
                .andExpect(jsonPath("$[0].employees[0].name").value(employees.get(0).getName()))
                .andExpect(jsonPath("$[0].employees[0].gender").value(employees.get(0).getGender()))
                .andExpect(jsonPath("$[0].employees[0].salary").value(employees.get(0).getSalary()));
    }
}