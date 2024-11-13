package com.javacourse.springboot_rest_api.controller;

import com.javacourse.springboot_rest_api.bean.Student;
import com.javacourse.springboot_rest_api.repository.StudentRepository;
import com.javacourse.springboot_rest_api.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentRepository studentRepository;
    private StudentService studentService;

    public StudentController(StudentRepository studentRepository, StudentService studentService) {
        this.studentRepository = studentRepository;
        this.studentService = studentService;
    } // sau folosesc adnotarea @AllArgsConstructor din lombok

    /**
     * Endpoint POST pentru adăugarea unui student nou - l-am adaugat din postman
     * m-am abatut putin de la curs pt ca nu foloseste JPA
     * /** REST API that handles Http POST request
     * localhost:8080/api/students/create + json
     */
    @PostMapping("/create")
    public ResponseEntity<Student> saveStudent(@RequestBody Student newStudent) {
        Student savedStudent = studentRepository.save(newStudent);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    /**
     * /** REST API GET request
     * localhost:8080/api/students
     */
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    /**
     * REST API GET with PathVariable
     * localhost:8080/api/students/3
     */
    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentRepository.findById(id);
        return student.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * REST API GET with RequestParam
     * localhost:8080/api/students/find?id=3&firstName=Marilena
     */
    @GetMapping("/find")
    public ResponseEntity<Student> findStudentById(@RequestParam Long id, @RequestParam String firstName) {
        Optional<Student> student = studentRepository.findByIdAndFirstName(id, firstName);
        return student.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * REST API that handles Http PUT request
     * localhost:8080/api/students/4/update + json
     */
    @PutMapping("/{id}/update")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        try {
            Student updatedStudent = studentService.updateStudent(id, studentDetails);
            return ResponseEntity.ok(updatedStudent);
        } catch (RuntimeException e){
            return ResponseEntity.status(404).body(null);
        }
    }

    /**
     * REST API that handles Http DELETE request
     * localhost:8080/api/students/5/delete
     */
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id){
        try {
            studentRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null); // 404 Not Found
        }
    }

}
