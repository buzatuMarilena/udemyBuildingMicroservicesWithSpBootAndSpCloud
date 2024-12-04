package com.java.employee_service.service;

import com.java.employee_service.dto.DepartmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:8082", value = "DEPARTMENT-SERVICE")
public interface ApiClient {
    /** I have declared the method to which I am going to make a rest api call
     * OpenFeign library will dinamically create an implementation
     * for this interface.
     *
     * I just have to create an interface and use the adnotations and declare
     * the method to witch I need to make a Rest api call*/
    @GetMapping("api/departments/{department-code}")
    DepartmentDto getDepartment(@PathVariable("department-code") String departmentCode);
}
