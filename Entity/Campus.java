package za.ac.cput.GoShuttle.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "campus")
public class Campus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long campusId;

    @Column(name = "campus_name", nullable = false)
    private String campusName;

    public Campus() {}

    private Campus(Builder builder) {
        this.campusName = builder.campusName;
    }

    public static class Builder {
        private String campusName;

        public Builder setCampusName(String campusName) {
            this.campusName = campusName;
            return this;
        }
        public Campus build() {
            return new Campus(this);
        }
    }

    public Long getCampusId()       { return campusId; }
    public String getCampusName()   { return campusName; }
    public void setCampusId(Long campusId)       { this.campusId = campusId; }
    public void setCampusName(String campusName) { this.campusName = campusName; }
}