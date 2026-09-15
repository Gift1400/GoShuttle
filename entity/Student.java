package za.ac.cput.goshuttle.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_number", nullable = false, unique = true)
    private String studentNumber;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "home_area")
    private String homeArea;

    public Student() {}

    private Student(Builder builder) {
        this.studentNumber = builder.studentNumber;
        this.fullName = builder.fullName;
        this.email = builder.email;
        this.homeArea = builder.homeArea;
    }

    public static class Builder {
        private String studentNumber;
        private String fullName;
        private String email;
        private String homeArea;

        public Builder setStudentNumber(String studentNumber) {
            this.studentNumber = studentNumber;
            return this;
        }
        public Builder setFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }
        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }
        public Builder setHomeArea(String homeArea) {
            this.homeArea = homeArea;
            return this;
        }
        public Student build() {
            return new Student(this);
        }
    }

    // ── Getters ──────────────────────────────────────
    public Long getId()             { return id; }
    public String getStudentNumber(){ return studentNumber; }
    public String getFullName()     { return fullName; }
    public String getEmail()        { return email; }
    public String getHomeArea()     { return homeArea; }

    // ── Setter for id only (needed for update) ───────
    public void setId(Long id)      { this.id = id; }
    public void setStatus(String status) {}
}