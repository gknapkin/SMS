package jpa.service;

import java.util.List;

import javax.persistence.*;

import jpa.dao.CourseDAO;
import jpa.entitymodels.Course;

public class CourseService implements CourseDAO {

	@Override
	public List<Course> getAllCourses() {
		List<Course> foundCourses = null;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("SMS");
		EntityManager em = emf.createEntityManager();
		
		try {
			Query query = em.createNamedQuery("Find all courses");
			foundCourses= query.getResultList();
		}catch (PersistenceException e) {
			System.out.println(e.getMessage());
		}finally {
			em.close();
			emf.close();
		}		
		return foundCourses;
	}
	
	

}
