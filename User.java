package za.ac.cput.GoShuttle.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "student_number", nullable = false, unique = true)
    private String studentNumber;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "campus_id")
    private Long campusId;

    public User() {}

    private User(Builder builder) {
        this.fullName      = builder.fullName;
        this.studentNumber = builder.studentNumber;
        this.email         = builder.email;
        this.passwordHash  = builder.passwordHash;
        this.campusId      = builder.campusId;
    }

    public static class Builder {
        private String fullName;
        private String studentNumber;
        private String email;
        private String passwordHash;
        private Long campusId;

        public Builder setFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }
        public Builder setStudentNumber(String studentNumber) {
            this.studentNumber = studentNumber;
            return this;
        }
        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }
        public Builder setPasswordHash(String passwordHash) {
            this.passwordHash = passwordHash;
            return this;
        }
        public Builder setCampusId(Long campusId) {
            this.campusId = campusId;
            return this;
        }
        public User build() {
            return new User(this);
        }
    }

    public Long getUserId()         { return userId; }
    public String getFullName()     { return fullName; }
    public String getStudentNumber(){ return studentNumber; }
    public String getEmail()        { return email; }
    public String getPasswordHash() { return passwordHash; }
    public Long getCampusId()       { return campusId; }

    public void setUserId(Long userId)               { this.userId = userId; }
    public void setFullName(String fullName)         { this.fullName = fullName; }
    public void setStudentNumber(String studentNumber){ this.studentNumber = studentNumber; }
    public void setEmail(String email)               { this.email = email; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public void setCampusId(Long campusId)           { this.campusId = campusId; }
}