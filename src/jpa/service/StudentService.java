package jpa.service;

import java.util.List;

import javax.persistence.*;

import jpa.dao.StudentDAO;
import jpa.entitymodels.Course;
import jpa.entitymodels.Student;

public class StudentService implements StudentDAO {

	@Override
	public List<Student> getAllStudents() {
		List<Student> studentList = null;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("SMS");
		EntityManager em = emf.createEntityManager();		
		try {
			Query query = em.createNamedQuery("Find all students");
			studentList = query.getResultList();			
		}catch (PersistenceException e) {
			System.out.println(e.getMessage());
		}finally {
			em.close();
			emf.close();
		}		
		return studentList;
	}

	@Override
	public Student getStudentByEmail(String email) {
		Student foundStudent = null;
		List<Student> studentList = null;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("SMS");
		EntityManager em = emf.createEntityManager();		
		try {
			Query query = em.createNamedQuery("Find student by email");
			query.setParameter("smail", email);
			studentList = query.getResultList();
			foundStudent = studentList.get(0);
		}catch (PersistenceException e) {
			System.out.println(e.getMessage());
		}finally {
			em.close();
			emf.close();
		}	
		return foundStudent;
	}

	@Override
	public boolean validateStudent(String sEmail, String sPassword) {
		boolean result;
		List<Student> studentList = null;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("SMS");
		EntityManager em = emf.createEntityManager();
		
		try {
			Query query = em.createNamedQuery("Validate Student");
			query.setParameter("smail", sEmail);
			query.setParameter("spass", sPassword);
			studentList = query.getResultList();
			if (studentList.isEmpty()) {
				result = false;
			}else {
				result = true;
			}
		}catch (PersistenceException e) {
			System.out.println(e.getMessage());
			result = false;
		}finally {
			em.close();
			emf.close();
		}		
		return result;
	}

	@Override
	public void registerStudentToCourse(String sEmail, int cId) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("SMS");
		EntityManager em = emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			Course selectedCourse = em.find(Course.class, cId);
			Student foundStudent = em.find(Student.class, sEmail);
			List <Course> studCourseList = foundStudent.getCourses();
			
			if (studCourseList.contains(selectedCourse)){
				System.out.println("Already enrolled!");
			}else {
				studCourseList.add(selectedCourse);
				foundStudent.setCourses(studCourseList);
				em.persist(foundStudent);
				em.getTransaction().commit();
			}
		}catch (PersistenceException e) {
			System.out.println(e.getMessage());
		}finally {
			em.close();
			emf.close();
		}		
	}

	@Override
	public List<Course> getStudentCourses(String sEmail) {
		List<Course> foundStudentCourses = null;
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("SMS");
		EntityManager em = emf.createEntityManager();
		
		try {
			Student foundStudent = em.find(Student.class, sEmail);
			foundStudentCourses = foundStudent.getCourses();
		}catch (PersistenceException e) {
			System.out.println(e.getMessage());
		}finally {
			em.close();
			emf.close();
		}	
		return foundStudentCourses;
	}
	
	public boolean addStudent(Student student) {
		boolean result = true;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("SMS");
		EntityManager em = emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			em.persist(student);
			em.getTransaction().commit();
		}catch (PersistenceException e) {
			System.out.println(e.getMessage());
		}finally {
			em.close();
			emf.close();
		}	
		return result;
	}
	
}
