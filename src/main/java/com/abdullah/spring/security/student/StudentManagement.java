package com.abdullah.spring.security.student;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagement {
    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1, "James"),
            new Student(2, "Maria"),
            new Student(3, "Anna")
    );

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_TRAINEEADMIN')")
    public List<Student> getAllStudent() {
        return STUDENTS;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('STUDENT:WRITE')")
    public void registerNewStudent(@RequestBody Student student) {
        System.out.println(student);
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("hasAuthority('STUDENT:WRITE')")
    public void deleteStudent(@PathVariable("id") Integer id) {
        System.out.println(id);
    }

    @PutMapping(path = "{id}")
    @PreAuthorize("hasAuthority('STUDENT:WRITE')")
    public void updateStudent(@PathVariable("id") Integer id, @RequestBody Student student) {
        System.out.println(id + student.toString());
    }

}
