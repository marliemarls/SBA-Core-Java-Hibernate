package sba.sms.services;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import sba.sms.dao.CourseI;
import sba.sms.models.Course;


import java.util.ArrayList;
import java.util.List;

/**
 * CourseService is a concrete class. This class implements the
 * CourseI interface, overrides all abstract service methods and
 * provides implementation for each method.
 */
public class CourseService implements CourseI {
    SessionFactory factory = new Configuration().configure().buildSessionFactory();

    @Override
    public void createCourse(Course course) {
        Session s = factory.openSession();
        Transaction tx = null;

        try {
            tx = s.beginTransaction();
            s.persist(course);
            tx.commit();
        } catch (HibernateException exception) {
            if (tx != null) tx.rollback();
            exception.printStackTrace();
        } finally {
            s.close();
        }
    }

    @Override
    public Course getCourseById(int courseId) {
        Session s = factory.openSession();

        Transaction tx = null;
        Course course = null;
        try {
            tx = s.beginTransaction();
            Query<Course> q = s.createQuery("from Course where id = :id", Course.class);
            q.setParameter("id", courseId);
            course = s.get(Course.class, courseId);
            tx.commit();
        } catch (HibernateException exception) {
            if (tx != null) tx.rollback();
            exception.printStackTrace();
        } finally {
            s.close();
        }
        return course;
    }


    @Override
    public List<Course> getAllCourses() {
        Session s = factory.openSession();
        Transaction tx = null;
        List<Course> courseList = null;
        try {
            tx = s.beginTransaction();
            Query<Course> q = s.createQuery("from Course ", Course.class);
            courseList = q.getResultList();
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