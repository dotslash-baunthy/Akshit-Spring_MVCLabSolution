package com.akshitbaunthy.springmvc.service;

import com.akshitbaunthy.springmvc.entity.Student;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private SessionFactory sessionFactory;

    private Session session;

    @Autowired
    public StudentServiceImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException ex) {
            session = sessionFactory.openSession();
        }
    }

    @Override
    @Transactional
    public void insert(Student student) {
        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(student);
        tx.commit();
    }

    @Override
    @Transactional
    // Function that is called by /delete, this does the actual deletion
    public void delete(int id) {
        Transaction tx = session.beginTransaction();
        Student student = session.get(Student.class, id);
        if (student != null)
            session.delete(student);
        tx.commit();
    }

    @Override
    // Function that is called by /list, this does the actual listing
    public List<Student> getAll() {
        Transaction tx = session.beginTransaction();
        List<Student> students = session.createQuery("from Student").list();
        tx.commit();
        return students;
    }

    @Override
    // Function that is called by /insert (in case of update), this does the finding part (get by ID, as the name suggests)
    public Student getById(int id) {
        Transaction tx = session.beginTransaction();
        Student student = session.get(Student.class, id);
        tx.commit();
        return student;
    }
}
