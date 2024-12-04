package com.java.employee_service.service;

import com.java.employee_service.dto.APIResponseDto;
import com.java.employee_service.dto.EmployeeDto;

public interface EmployeeService {
    EmployeeDto saveEmployee(EmployeeDto employeeDto);

    APIResponseDto getEmployeeById(Long employeeId);
}