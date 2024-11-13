package com.javacourse.springboot_rest_api.service;

import com.javacourse.springboot_rest_api.bean.Student;
import com.javacourse.springboot_rest_api.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student updateStudent(Long id, Student studentDetails) throws RuntimeException {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            Student temp = student.get();
            temp.setFirstName(studentDetails.getFirstName());
            temp.setLastName(studentDetails.getLastName());
            return studentRepository.save(temp);
        } else {
            throw new RuntimeException("Student not found with id: " + id);
        }
    }
}
