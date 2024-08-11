package sba.sms.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.mapping.PrimaryKey;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "course")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@ToString


/**
 * Course is a POJO, configured as a persistent class that represents (or maps to) a table
 * name 'course' in the database. A Course object contains fields that represent course
 * information and a mapping of 'courses' that indicate an inverse or referencing side
 * of the relationship. Implement Lombok annotations to eliminate boilerplate code.
 */

public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id")
    protected int id ;
    @NonNull
    @Column(length = 50, name = "Name")
    String name;
    @Column(name = "Instructor", nullable = false, length = 50)
    String instructor;
    @JoinColumn(name = "Students")
    public Set<Student> students;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id && Objects.equals(name, course.name) && Objects.equals(instructor, course.instructor) && Objects.equals(students, course.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, instructor, students);
    }

}
