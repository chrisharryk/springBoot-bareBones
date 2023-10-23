package com.chk.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class StudentService {

    @Autowired
    private final StudentRepository studentRepository;
    
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
	}

    public void addNewStudent(Student student) {
        if (studentRepository.findStudentByEmail(student.getEmail()).isPresent()) {
            throw new IllegalStateException("Email taken");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Integer id) {
        if (studentRepository.existsById(id)) {
            studentRepository.delete(studentRepository.getReferenceById(id));
            System.out.println("deleted that person");
        } else {
            throw new IllegalStateException("student with given id does not exist");
        }
    }

    public String getStudentById(Integer id) {
        if (studentRepository.existsById(id)) {
            return studentRepository.getReferenceById(id).toString();
        }
        return new String("student with id " + id + " not found");
    }

    @Transactional
    public String updateStudent(Integer id, Student newStudent) {
        if (studentRepository.existsById(id)) {
            Student student = studentRepository.getReferenceById(id);
            if (newStudent.getName() != null) student.setName(newStudent.getName());
            if (newStudent.getEmail() != null) student.setEmail(newStudent.getEmail());
        } else {
            throw new IllegalStateException("no student with id " + id + " exists");
        }
        return studentRepository.getReferenceById(id).toString();
    }

}
