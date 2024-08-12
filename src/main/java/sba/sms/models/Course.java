package sba.sms.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


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
@FieldDefaults(level = AccessLevel.PRIVATE)


public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id")
    int id ;
    @NonNull
    @Column(length = 50, name = "Name")
    String name;
    @Column(name = "Instructor", nullable = false, length = 50)
    String instructor;
    @ToString.Exclude
    @ManyToMany(mappedBy = "courses", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.EAGER)
    private Set<Student> students = new LinkedHashSet<>();

    public Course(@NonNull String name, String instructor) {
        this.name = name;
        this.instructor = instructor;
    }

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
