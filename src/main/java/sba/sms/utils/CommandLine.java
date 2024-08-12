package sba.sms.utils;

import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.services.CourseService;
import sba.sms.services.StudentService;

public class CommandLine {
    private CommandLine(){
    }
    private static final String PASSWORD = "password";


    public static void addData(){


        StudentService studentService = new StudentService();
        CourseService courseService = new CourseService();
        studentService.createStudent(new Student("reema@gmail.com", "reema brown", PASSWORD));
        studentService.createStudent(new Student("annette@gmail.com", "annette allen", PASSWORD));
        studentService.createStudent(new Student("anthony@gmail.com", "anthony gallegos", PASSWORD));
        studentService.createStudent(new Student("ariadna@gmail.com", "ariadna ramirez", PASSWORD));
        studentService.createStudent(new Student("bolaji@gmail.com", "bolaji saibu", PASSWORD));
        studentService.createStudent(new Student("shirese@gmail.com", "shirese smith", PASSWORD));

        courseService.createCourse(new Course("Java", "Roger Boaitey"));
        courseService.createCourse(new Course("Frontend", "William Roales"));
        courseService.createCourse(new Course("JPA", "Jafer Alhaboubi"));
        courseService.createCourse(new Course("Spring Framework", "LaTonya Lewis"));
        courseService.createCourse(new Course("SQL", "Ezra Williams"));
        courseService.createCourse(new Course("GitHub", "Igor Adulyan"));
        courseService.createCourse(new Course("Web Services", "Raheem Abolfathzadeh"));
        courseService.createCourse(new Course("Microservices", "Eric Heilig"));


    }
}
