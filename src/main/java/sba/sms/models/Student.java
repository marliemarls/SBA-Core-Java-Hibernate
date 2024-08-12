package sba.sms.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;


/**
 * Student is a POJO, configured as a persistent class that represents (or maps to) a table
 * name 'student' in the database. A Student object contains fields that represent student
 * login credentials and a join table containing a registered student's email and course(s)
 * data. The Student class can be viewed as the owner of the bi-directional relationship.
 * Implement Lombok annotations to eliminate boilerplate code.
 */

@Entity
@Table(name = "student")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)


public class Student {
    @NonNull
    @Id @Column(length = 50, name = "Email")
    String email ;
    @NonNull
    @Column(name = "Name", nullable = false, length = 50)
    String name;
    @NonNull
    @Column(name = "Password", nullable = false, length = 50)
    String password;

    @ToString.Exclude
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch =
            FetchType.EAGER)
    @JoinTable(name = "student_courses", joinColumns = @JoinColumn(name = "student_email"), inverseJoinColumns =
    @JoinColumn(name = "courses_id"))
    Set<Course> courses = new LinkedHashSet<>();


    public void addCourse(Course c){
        courses.add(c);
        c.getStudents().add(this);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(name, student.name) && Objects.equals(email, student.email) && Objects.equals(password,
                student.password) && Objects.equals(courses, student.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, password, courses);
    }
}



