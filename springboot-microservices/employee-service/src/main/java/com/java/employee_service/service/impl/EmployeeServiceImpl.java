package com.java.employee_service.service.impl;

import com.java.employee_service.dto.APIResponseDto;
import com.java.employee_service.dto.DepartmentDto;
import com.java.employee_service.dto.EmployeeDto;
import com.java.employee_service.entity.Employee;
import com.java.employee_service.mapper.EmployeeMapper;
import com.java.employee_service.repository.EmployeeRepository;
import com.java.employee_service.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    //private RestTemplate restTemplate;
    private WebClient webClient;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Employee saveDEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(saveDEmployee);
    }

    @Override
    public APIResponseDto getEmployeeById(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId).get();

//        ResponseEntity<DepartmentDto> responseEntity = restTemplate.getForEntity("http://localhost:8082/api/departments/" + employee.getDepartmentCode(),
//                DepartmentDto.class);
//        DepartmentDto departmentDto = responseEntity.getBody();

        DepartmentDto departmentDto = webClient.get()
                .uri("http://localhost:8082/api/departments/"+ employee.getDepartmentCode())
                .retrieve().bodyToMono(DepartmentDto.class)
                .block();

        EmployeeDto employeeDto= new EmployeeDto(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getDepartmentCode()
        );

        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setEmployee(employeeDto);
        apiResponseDto.setDepartment(departmentDto);
        return apiResponseDto;
    }
}