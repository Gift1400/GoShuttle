package za.ac.cput.GoShuttle.domain;


import jakarta.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;

    private String fullName;

    private Long studentNumber;

    private String email;

    private String passwordHash;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "campusId")
    private Campus campusId;

}
