package com.javacourse.springboot_rest_api.repository;

import com.javacourse.springboot_rest_api.bean.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByIdAndFirstName(Long id, String firstName);
}
