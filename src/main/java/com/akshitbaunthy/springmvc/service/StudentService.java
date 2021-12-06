package com.akshitbaunthy.springmvc.service;

import com.akshitbaunthy.springmvc.entity.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {

    void insert(Student student);
    void delete(int id);
    List<Student> printAll();
    Student findById(int id);

}
