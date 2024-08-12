package sba.sms.services;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import sba.sms.dao.StudentI;
import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.HibernateUtil;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * StudentService is a concrete class. This class implements the
 * StudentI interface, overrides all abstract service methods and
 * provides implementation for each method. Lombok @Log used to
 * generate a logger file.
 */

public class StudentService implements StudentI {
    CourseService courseService = new CourseService();
    SessionFactory factory = new Configuration().configure().buildSessionFactory();

    @Override
    public List<Student> getAllStudents() {


        Session s = factory.openSession();
        Transaction tx = null;
        List<Student> studentList = new ArrayList<>();

        try {
            tx = s.beginTransaction();
            Query<Student> q = s.createQuery("from Student", Student.class);
            studentList = q.getResultList();
            tx.commit();
        } catch (HibernateException exception) {
            if (tx != null) tx.rollback();
            exception.printStackTrace();
        } finally {
            s.close();
        }
        return studentList;
    }

    @Override
    public void createStudent(Student student) {
        Session s = factory.openSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            s.persist(student);
            tx.commit();
        } catch (HibernateException exception) {
            if (tx != null) tx.rollback();
            exception.printStackTrace();
        } finally {
            s.close();
        }
    }

    @Override
    public Student getStudentByEmail(String email) {

        Session s = factory.openSession();
        Transaction tx = null;
        Student student = null;
        try {
            tx = s.beginTransaction();
            Query<Student> q = s.createQuery("from Student where email = :email", Student.class);
            q.setParameter("email", email);
            student = s.get(Student.class,email);
            tx.commit();

        } catch (Exception exception) {
            if (tx != null) tx.rollback();
            exception.printStackTrace();
        } finally {
            s.close();
        }
        return student;
    }

    @Override
    public boolean validateStudent(String email, String password) {
        Student s = getStudentByEmail(email);
        return s != null && s.getPassword().equals(password);
    }


    @Override
    public void registerStudentToCourse(String email, int courseId) {

        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Set<Course> courseList;

        try {
            tx = s.beginTransaction();
            Student student = s.get(Student.class,email);
            Course course = s.get(Course.class, courseId);
            courseList = student.getCourses();
            if(!courseList.contains(course)){
                courseList.add(course);
                student.setCourses(courseList);
                s.merge(student);
                tx.commit();
            }else{
                System.out.println(" Student already registered @!" + course.getName());
            }

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            s.close();
        }

    }


    @Override
    public List<Course> getStudentCourses(String email) {
        Session s = factory.openSession();
        Transaction tx = null;
        List<Course> courseList = new ArrayList<>();
        try {
            tx = s.beginTransaction();
            String nativeGetStudentCourses = "select c.id, c.name, c.instructor from course as c join student_courses as sc on c.id = sc.courses_id join student as s on s.email = sc.student_email where s.email = :email";
            NativeQuery<Course> studentCourses = s.createNativeQuery(nativeGetStudentCourses, Course.class);
            studentCourses.setParameter("email", email);
            courseList = studentCourses.getResultList();
            tx.commit();
        } catch (HibernateException exception) {
            if (tx != null) tx.rollback();
            exception.printStackTrace();
        } finally {
            s.close();
        }
        return courseList;
    }
}
