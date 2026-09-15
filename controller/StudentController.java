package za.ac.cput.goshuttle.controller;

import za.ac.cput.goshuttle.entity.Student;
import za.ac.cput.goshuttle.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentRepository.findById(id);
        return student
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-student-number/{studentNumber}")
    public ResponseEntity<Student> getByStudentNumber(@PathVariable String studentNumber) {
        Optional<Student> student = studentRepository.findByStudentNumber(studentNumber);
        return student
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-area/{homeArea}")
    public ResponseEntity<List<Student>> getByHomeArea(@PathVariable String homeArea) {
        List<Student> students = studentRepository.findByHomeArea(homeArea);
        return ResponseEntity.ok(students);
    }

    @PostMapping
    public ResponseEntity<Student> registerStudent(@RequestBody Student student) {
        Student saved = studentRepository.save(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id,
                                                 @RequestBody Student updated) {
        Optional<Student> existing = studentRepository.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Student student = new Student.Builder()
                .setStudentNumber(updated.getStudentNumber())
                .setFullName(updated.getFullName())
                .setEmail(updated.getEmail())
                .setHomeArea(updated.getHomeArea())
                .build();
        student.setId(existing.get().getId());
        Student saved = studentRepository.save(student);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        if (!studentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        studentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}